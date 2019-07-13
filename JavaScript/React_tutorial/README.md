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
`$ npm install`  
※Windowsの場合は`npm install --no-bin-links`  
参考：[LaravelのHomestead上のnpm installでMaximum call stack size exceededが発生する場合](https://qiita.com/asuzuki2008/items/acc963569526ff941619)


- サーバ立ち上げ  
`$ npm start`

- ブラウザでアクセス  
`localhost:3000`もしくは`(DockerホストIP):3000`にアクセス