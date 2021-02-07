## docker環境参考
[公式](https://docs.docker.com/compose/rails/)

### バージョン
- Ruby：2.5.5
- Rails：5.2.4.4

### 手順
自分の場合のディレクトリ構造
```
(プロジェクトルート)
　|- docker
　|　|- ruby
　|　　　|-　Dockerfile
　|-※Railsのソース
　|- docker-compose.yml
　|- entrypoint.sh
　|- Gemfile
　|- Gemfile.lock
　|- README.md
```

- [Dockerfile](https://github.com/h-yoshikawa0724/TIL/blob/master/Ruby/Rails/docker/ruby/Dockerfile) 作成

- Gemfile を作成
```ruby
source 'https://rubygems.org'
gem 'rails', '~>5'
```

- Gemfile.lock を作成(中身は空で OK)

- [entrypoint.sh](https://github.com/h-yoshikawa0724/TIL/blob/master/Ruby/Rails/entrypoint.sh) を作成

- [docker-compose.yml](https://github.com/h-yoshikawa0724/TIL/blob/master/Ruby/Rails/docker-compose.yml) を作成

- ビルドして、Railsプロジェクトを作成
```
$ docker-compose run --no-deps web rails new . --force --database=postgresql
```

- もう一度、コンテナをビルド( rails new で Gemfile の内容が変わったため)
```
$ docker-compose build
```

- データベースの接続設定(src/config/database.yml)  データベース名は任意
```yml
default: &default
  adapter: postgresql
  encoding: unicode
  host: db
  username: postgres
  password: password
  pool: <%= ENV.fetch("RAILS_MAX_THREADS") { 5 } %>

development:
  <<: *default
  database: myapp_development


test:
  <<: *default
  database: myapp_test
```

- コンテナの起動
```
$ docker-compose up -d
```

- 初期DBの作成
```
$ docker-compose run web rake db:create
```

以下の用になれば無事作成されている
```
Created database 'myapp_development'
Created database 'myapp_test'
```

- ブラウザでアクセス
```
localhost:3000
```

### エラー対応参考
#### ADDする際に、Dockerfileがある場所の親ディレクトリのファイルを指定したいときに
[docker-composeでADDやCOPYをする際に注意すること](https://qiita.com/mk-tool/items/1c7e4929055bb3b7aeda)

#### standard_init_linux.go:178: exec user process caused "no such file or directory"のエラーが出た時
[docker-compose upするとコンテナが一瞬でexited with code 1する話](https://qiita.com/nrk_baby/items/d872e8f051a96a313601)

#### postgresのコンテナ起動時にFATAL: data directory "/var/lib/postgresql/data" has wrong ownershipが出た時
[Docker for WindowsとLaradocでpostgresのコンテナが起動できない場合の対処](https://mrkmyki.com/2018/11/25/docker-for-windows%E3%81%A8laradoc%E3%81%A7postgres%E3%81%AE%E3%82%B3%E3%83%B3%E3%83%86%E3%83%8A%E3%81%8C%E8%B5%B7%E5%8B%95%E3%81%A7%E3%81%8D%E3%81%AA%E3%81%84%E5%A0%B4%E5%90%88%E3%81%AE%E5%AF%BE/)

#### File exists @ dir_s_mkdir - /src/tmp/cache/assets/sprockets/v3.0/2D でアプリがエラーになった時
[Windows+Docker+Railsでsprocketsのエラーが出る時](https://crieit.net/posts/Windows-Docker-Rails-sprockets)