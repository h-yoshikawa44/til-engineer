### Docker環境作成参考
- [Docker環境内でcreate-react-app](https://qiita.com/mii288/items/aac597bc02575831ea90)

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

- サーバ立ち上げ  
`$ npm start`

- ブラウザでアクセス  
`localhost:3000`もしくは`(DockerホストIP):3000`にアクセス