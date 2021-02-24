## eslint
静的解析ツール

自記事：[create-react-appで作成した雛形 + VSCodeにESLintとPrettierを導入する](https://qiita.com/h-yoshikawa44/items/14a114da903fb0eff886)

### インストール
`$ yarn add -D eslint eslint-plugin-react babel-eslint`

create-react-appで作成したプロジェクトの場合はすでに導入されているので一からインストールは不要。`react-script`の依存関係にある。
```
eslint "^6.1.0"
eslint-config-react-app "^5.0.2"
eslint-loader "3.0.2"
eslint-plugin-flowtype "3.13.0"
eslint-plugin-import "2.18.2"
eslint-plugin-jsx-a11y "6.2.3"
eslint-plugin-react "7.14.3"
eslint-plugin-react-hooks "^1.6.1"
```
ルール追加に必要なパッケージを随時追加していけばいい。

### 設定ファイル
package.jsonのeslintConfigキーで設定が書ける
```json
"eslintConfig": {
    "extends": [
      "react-app",
      "airbnb"
    ],
    "settings": {
      "react": {
        "version": "16.11"
      }
    }
  },
```

.eslintrc.js(もしくはyml、json)にファイルを切り出しても設定が書ける。  
記述方式は公式参照  
[ESLint - Configuring ESLint](https://eslint.org/docs/user-guide/configuring)


#### 初期化+設定ファイルテンプレ作成  
`$ /node_modules/.bin/eslint --init`

設定内容
? How would you like to use ESLint?
  To check syntax only
  To check syntax and find problems
❯ To check syntax, find problems, and enforce code style 

? What type of modules does your project use? (Use arrow keys)
❯ JavaScript modules (import/export)
  CommonJS (require/exports)
  None of these

? Which framework does your project use? (Use arrow keys)
❯ React
  Vue.js
  None of these

? Does your project use TypeScript? (y/N) N

? Where does your code run? (Press <space> to select, <a> to toggle all, <i> to invert selection)
❯◉ Browser
 ◯ Node

How would you like to define a style for your project? (Use arrow keys)
❯ Use a popular style guide
  Answer questions about your style
  Inspect your JavaScript file(s)

? Which style guide do you want to follow? (Use arrow keys)
❯ Airbnb (https://github.com/airbnb/javascript)
  Standard (https://github.com/standard/standard)
  Google (https://github.com/google/eslint-config-google)

? What format do you want your config file to be in? (Use arrow keys)
❯ JavaScript
  YAML
  JSON

### 実行  
#### コマンドライン
`$ /node_modules/.bin/eslint ファイル名`

#### VSCode
1. ESLintに関するパッケージをインストール
2. 設定を記述
3. [拡張：ESLint](https://marketplace.visualstudio.com/items?itemName=dbaeumer.vscode-eslint)をインストール

4. ワークスペースに追加したフォルダ直下に設定ファイルがない場合は、フォルダの設定（.vscode/setting.json）にパスを指定する
```json
※例（app直下に設定ファイルがある場合）
{
  "eslint.workingDirectories": [
    "./app"
  ]
}
```

**ローカルにNode.jsがないとWarningが出る？**  
eslint-plugin-reactパッケージでReactのバージョンを指定しているが、デフォルトがdetectになっており、バージョンを自動検知するようになっている。ここでうまく検知ができなくなっている模様。  
↓  
この場合は設定に明示的にバージョンを指定する
```json
"eslintConfig": {
    "extends": "react-app",
    "settings": {  ←以下追加
      "react": {
        "version": "16.11"
      }
    }
  },
```

## Prettier
コード整形ツール

ESLintと連携させて動作させることが可能

### コマンドラインとしてESLint連携

1.yarnで必要パッケージをインストール
```
$ yarn add -D prettier prettier-eslint prettier-eslint-cli
```

2.package.jsonにformatコマンドを追記

```json
※一部抜粋
"scripts": {
    "start": "react-scripts start",
    "build": "react-scripts build",
    "test": "react-scripts test",
    "eject": "react-scripts eject",
    "format": "prettier-eslint --write 'src/**/*.js'"　←追加
  },
 ```

これで以下のコマンドで実行できる。
prettier-eslintを利用することで、Prettierで整形されたコードがESLintに渡されて、ESLintで整形されます。
```
$ yarn format
```

### VSCode上でESLint連携
1.yarnで必要なパッケージをインストール
```
$ yarn add -D eslint-config-prettier eslint-plugin-prettier
```

2.ESLintの設定にPrettierのルールを追加
```json
※一部抜粋
"eslintConfig": {
    "extends": [
      "react-app",
      "plugin:prettier/recommended"　←追加
    ],
    "settings": {
      "react": {
        "version": "16.11"
      }
    }
  },
```

この一文を追加するだけで以下と同じ効果が得られる。

```json
"eslintConfig": {
    "extends": ["prettier"],
    "plugins": ["prettier"],
    "rules": {
        "prettier/prettier": "error"
    }
  },
 ```
これでESLintとPrettier両方のルールでコードをチェックしてくれるようになる。
ESLintとPrettier連携の仕組みについて
[ESLint - Prettier連携のやり方と仕組み](https://qiita.com/ikngtty/items/4df2e13d2fa1c4c47528)

### 設定ファイル
package.jsonのprettierキーで書ける。  
もしくは.prettierrc.js(もしくはyml、json)ファイルに切り出して書ける。

記述方式は公式参照
[Prettier - Configration File](https://prettier.io/docs/en/configuration.html)

### ファイル保存時に自動整形
.vscode/setting.jsonに追記
```json
{
  "eslint.workingDirectories": [
    "./app"
  ],
  "editor.formatOnSave": false, ←追記
  "eslint.autoFixOnSave": true, ←追記
}
```
editor.formatOnSaveはtrueにすると、自動整形が衝突するのでfalse。
eslint.autoFixOnSaveをtrueにすることで、ファイル保存時に自動整形が動作してくれる。
