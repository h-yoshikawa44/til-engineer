## Hasura GraphQL Engine
RDB の PostgresSQL に GraphQL でアクセスできるようにしてくれる高速サーバ。
Hasura GraphQL Engine 自体は Haskell 製の OSS。

PostgreSQL のテーブル構造（RDB スキーマ定義）を元に GraphQL API を提供するコア機能の他、Hasura の設定や PostgreSQL の テーブル・データ操作を行える GUI コンソールも持ちあわせている。

### セットアップ
#### Docker 環境
以下のコマンドで、Hasura GraphQL Engine と PostgresSQL を動作させるための docker-compose.yml を取得できる
```
$ wget https://raw.githubusercontent.com/hasura/graphql-engine/stable/ install-manifests/docker-compose/docker-compose.yaml
```

ただ、自動コンソール立ち上げ設定はオフにしておく。
（自動マイグレーション機能を動作させるため）
```yml
HASURA_GRAPHQL_ENABLE_CONSOLE: "false"
```

コンテナ立ち上げ
```
$ docker-compose up -d
```

#### 自動マイグレーション設定
ライブラリインストール
```
$ yarn add -D hasura-cli
```

マイグレーション情報を保存するディレクトリ作成  
（このディレクトリ名がプロジェクト名となる）
```
$ yarn hasura init ※任意の名前
```

コンソール起動
```
$ yarn hasura console --project ※プロジェクト名
```

http://localhost:9695 でコンソール表示

#### GraphQL コード生成設定
ライブラリインストール
```
$ yarn add @apollo/react-hooks @apollo/client graphql

$ yarn add -D @graphql-codegen/cli @graphql-codegen/typescript @graphql-codegen/typescript-operations @graphql-codegen/typescript-react-apollo
```

設定ファイル作成
codegen.yml
```yml
schema: http://localhost:8080/v1/graphql/
documents:
  - ./src/**/*.graphql
overwrite: true
generates:
  ./src/generated/graphql.ts:
    plugins:
      - typescript
      - typescript-operations
      - typescript-react-apollo
    config:
      skipTypename: false
      withHooks: true
      withHOC: false
      withComponent: false
      scalars:
        timestamptz: string
        uuid: string
```
schema はサーバ側のスキーマ定義。  
documents はクライアント側のスキーマ定義（クライアントが欲しい情報指定したもの）

あとは Hasura コンソールで RDB をセットアップして、クライアント側の GraphQL クエリを作成する。

#### Apollo Client 初期化
最もメジャーな GraphQL クライアントライブラリ。

リセット CSS 取得
```
$ yarn add minireset.css
```

pages/_app.tsx
```tsx
import { AppProps } from 'next/app';
import { ApolloClient, InMemoryCache, HttpLink } from '@apollo/client';
import { ApolloProvider } from '@apollo/react-hooks';

import 'minireset.css';
import '../base.css';

const createApolloClient = () => {
  return new ApolloClient({
    link: new HttpLink({
      uri: 'http://localhost:8080/v1/graphql',
    }),
    cache: new InMemoryCache(),
  });
};

const MyApp = ({ Component, pageProps }: AppProps) => {
  const client = createApolloClient();

  return (
    <ApolloProvider client={client}>
      <Component {...pageProps} />
    </ApolloProvider>
  );
};

export default MyApp;
```

### Hasura でテーブルのセットアップ
コンソール立ち上げて、DATA タブからテーブルを作成。  

カラム名に関しては、一般的にスネークケースを採用する。
ただ、JS でそのデータを扱ううえではキャメルケースが採用されることが多い。  
この違いを吸収するために、カラム名には GraphQL field name をキャメルケースで設定しておくとよい。
（一度、テーブルを作成後に編集で操作）

リレーション設定をする場合
・子テーブル側
作成時に Foreign Keys を設定してテーブル作成。  
その後、Relationships タブの Suggested Object Relationships で Add を押す。  
（Foreign Keys をもとに、GraphQL 的に結合する条件設定）

・親テーブル側
Relationships タブの Suggested Array Relationships で Add を押す。

クエリの例（親：users 子：articles）
```
query getArticle {
  articles_by_pk(id: "a4647187-38b7-43fa-aa67-19d9a60c3989") {
    subject
    user {
      displayId
      displayName
    }
    createdAt
    publishedAt
    updatedAt
    content
  }
}`

```

**なお、コンソール上での操作によるテーブル情報は、マイグレーション情報として、migrations に作成される。**

マイグレーション情報確認
```
$ yarn hasura --project ※プロジェクト名 migrate status
```

マイグレーションファイルは操作ごとに作られるため、数が多くなりがち。  
そのため、1つのマイグレーションファイルにまとめるコマンドがある。
```
$ yarn hasura --project ※プロジェクト名 migrate squash --name "任意の名前" --from ※どこまでさかのぼるかのVERSION
```
VERSION は migrate status から確認できる。  
まとめた後は、別途マイグレーションコマンドを実行する。

マイグレーション実行
```
$ yarn hasura --project ※プロジェクト名 migrate apply --skip-execution --version ※実行したいVERSION
```

### コード自動生成
REST の場合は HTTP メソッドでわけられるが、GraphQL の場合は
- 参照用：クエリ（query）
- 更新用：ミューテーション（mutation）
でわけられる。

GraphQL コード（codegen.yml の documents で定義したファイル）がある状態で、以下のコマンド実行。
```
$ yarn gql-gen
```
codegen.yml の generates で指定していたファイルにコードを生成してくれる。
```
$ yarn gql-gen -w
```
で、随時差分監視しながら生成してくれる。

注意点として、Timestamp や UUID は Hasura で独自定義した方（GraphQL のスカラー型）であるため、any で割り当てられてしまう。  
型を指定したい時は codegen.yml に設定を追加したうえで、生成コマンド実行する。
```yml
generates:
  ./src/generated/graphql.ts:
    plugins:
      - typescript
      - typescript-operations
      - typescript-react-apollo
    config:
      skipTypename: false
      withHooks: true
      withHOC: false
      withComponent: false
      scalars:
        timestamptz: string
        uuid: string
```

あとは、この生成された GraphQL を使って、データを参照なり変更なりできる。
