## Docker環境
参考
- [Dockerを使ってVue CLI 3 の開発環境を構築する](https://qiita.com/satokibi/items/189945f984e5e53117ea)

### バージョン
- Node：12.9.0
- Vue：2.6.1~

### Docker環境作成手順
- Dockerfile作成

- docker-compose.yml作成

- .envのCOMPOSE_PROJECT_NAMEを任意のものに変更

- dockerのビルド  
`$ docker-compose build`

- コンテナに入る  
`$ docker-compose exec node sh`

- Vueプロジェクト作成  
`$ vue create (プロジェクトフォルダパス)`

- yarn インストール  
`$ yarn install`

- サーバ立ち上げ  
`$ yarn serve`

- ブラウザでアクセス  
`localhost:8080`もしくは`(DockerホストIP):8080`にアクセス

