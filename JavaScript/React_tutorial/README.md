### 教材
- [公式 チュートリアル](https://ja.reactjs.org/tutorial/tutorial.html)

### Docker環境作成参考
- [Docker環境内でcreate-react-app](https://qiita.com/mii288/items/aac597bc02575831ea90)

#### バージョン
- Node：10.16.0
- React：16.8.6

### Docker環境作成手順
- Dockerfile作成
```dockerfile
FROM "node:10-alpine"

WORKDIR /usr/src/app/

RUN npm i -g create-react-app
```

- docker-compose.yml作成
```yml
version: "2"
services:
  node:
    build: ./node
    environment:
      - NODE_ENV=development
    volumes:
      - ./app/:/usr/src/app
    tty: true
    stdin_open: true
    ports:
      - "3000:3000"
```

- dockerのビルド  
`$ docker-compose build`

- コンテナに入る  
`$ docker-compose exec node sh`

- Reactプロジェクト作成  
`$ create-react-app (プロジェクトフォルダパス)"`

- npm インストール  
`$ yarn install`

- サーバ立ち上げ  
`$ yarn start`

- ブラウザでアクセス  
`localhost:3000`もしくは`(DockerホストIP):3000`にアクセス

### パッケージのバージョンが古い時は
参考：[Rails lodash.templateの脆弱性](https://qiita.com/sakakinn/items/f55ee3bdd6cce92a4fde)

`lodash`、`lodash-template`のバージョンが古いために、脆弱性があるとGitHubから警告がくる

その際は、yarn.lockで該当パッケージのことが記述してある部分（以下のような箇所）を一度削除して`yarn install`を実行
```
lodash.template@^4.2.4, lodash.template@^4.4.0:
version "4.4.0"
resolved "https://registry.yarnpkg.com/lodash.template/-/lodash.template-4.4.0.tgz#e73a0385c8355591746e020b99679c690e68fba0"
integrity sha1-5zoDhcg1VZF0bgILmWecaQ5o+6A=
dependencies:
  lodash._reinterpolate "~3.0.0"
  lodash.templatesettings "^4.0.0"
```