## Docker環境

### バージョン
- Node：12系

### Docker環境作成手順
・各種Dockerfile作成

・docker-compose.yml作成  
 （この時点ではnodeのcommandはコメントアウト）

・コンテナのビルド
```
$ docker-compose build
```

・コンテナの起動
```
$ docker-compose up -d
```

・Reactプロジェクト作成  
　カレントディレクトリを指定する場合、そこに別ファイルやフォルダがあるとエラーになるので注意
```
$ docker-compose exec node yarn create react-app （フォルダパス）
```

フォルダパスを指定したけど、カレントディレクトリに持ってきたい場合
```
$ docker-compose exec node mv ./(フォルダパス)/* .
$ docker-compose exec node mv ./(フォルダパス)/.gitignore .
$ docker-compose exec node rm -rf (フォルダパス)
```

・コンテナ起動時のコマンドの有効化  
　docker-compose.ymlのnodeのcommandのコメントアウトを解除

・コンテナの再ビルド
```
$ docker-compose up -d --build
```

・ブラウザでアクセス  
`localhost:3000`もしくは`(DockerホストIP):3000`にアクセス

・swagger-uiにアクセス  
`localhost:8000/docs`
