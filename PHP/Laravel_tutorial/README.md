## LaradockによるLaravel環境作成

### 参考
- 手順  
  [PHPフレームワーク「Laradock」で、手軽にLaravel+Dockerな開発環境を構築する](https://liginc.co.jp/364089)

- Laradockフォルダの名称注意、各種シェル  
  [LaravelとLaradockを使ったチームでの開発環境を用意する vol.1 Laradock設定編](https://qiita.com/dev_satsuki/items/e2769925da33bfa77df5)  
  [LaravelとLaradockを使ったチームでの開発環境を用意する vol.2 Laravelインストール編](https://qiita.com/dev_satsuki/items/5ce8b98100997f230775)

- MySQL8対応  
  [DockerとLaradockでPHPフレームワークとCMSの開発環境を構築する](https://ninolog.com/docker-build-php-from-laradock/)

- NO_ZERO_DATEとか  
  [MySQL のログに CA 関連と NO_ZERO_DATE 関連の Warning が出ていたので対応した](https://mgng.mugbum.info/1344)

### 前提
- Docker導入済み
- docker-composeコマンドが使用できる

### 手順（初回）
- 任意のプロジェクトフォルダを作成（ここがgitの管理対象になるイメージ）

- プロジェクトフォルダに移動し`$ git init`

- プロジェクトフォルダの中にLaravelプロジェクト用のフォルダを作成

- プロジェクトフォルダの中でLaradockをサブモジュールとして登録  
`$ git submodule add https://github.com/Laradock/laradock.git (※Laradock用のフォルダ名)`  

この時点のフォルダ構成
```
(gitプロジェクトフォルダ)
　├─ (Laravelプロジェクトフォルダ)
　├─ (Laradockフォルダ)
```

#### Laradockのenvファイル準備
チームメンバーと共有のしやすさとして、シェルを作成

- シェル用のフォルダとファイルを作成（以下の`.sh/`）
```
(gitプロジェクトフォルダ)
　├─ (Laravelプロジェクトフォルダ)
　├─ (Laradockフォルダ)
　├─　.sh/
　　　　├─ conf/
　　　　├─ setup.sh
```

- `conf/`のなかに`.laradock-env`を作成
laradockの中の`env-example`をコピーして作成  
(以降、Laradockを更新した際はenv-exampleの内容が更新されている場合があるので、その際は再度合わせる)

- .laradock-envを編集  
`APP_CODE_PATH_HOST`はLaradockのwebサーバー上で同期するディレクトリ  
`DATA_PATH_HOST`はdockerのストレージなどを保存するローカルのディレクトリ  
`COMPOSE_PROJECT_NAME`はコンテナの接頭辞（デフォルトのlaradockだと、複数プロジェクトでLaradockを使用する際に名前が被って上書きになってしまうので変更しておく）
```
例
APP_CODE_PATH_HOST=../(Laravelプロジェクトフォルダ)/
DATA_PATH_HOST=../.(Laradockフォルダ)/data

COMPOSE_PROJECT_NAME=laraveltutorial
```

- setup.shを編集
```
SCRIPT_DIR=$(cd $(dirname $0); pwd)

cd $SCRIPT_DIR

cp -f conf/.laradock-env ../(Laradockフォルダ)/.env
```

- setup.shを実行して.envを作成
`$ sh .sh/setup.sh`

- Laradockのmysql/my.cnfに設定を追記
mysql 8 の対応として以下を追記する
```
innodb_use_native_aio=0
default_authentication_plugin=mysql_native_password
```

- .sh/conf以下にmysql.cnfを作成（Laradockのmysql/my.cnfをコピー）

- .sh/setup.shに以下を追加
```
cp -f conf/mysql.cnf ../（Laradockフォルダ）/mysql/my.cnf
```

#### dockerの起動
- イメージの作成と起動  
`$ docker-compose up --build -d nginx mysql`  
※起動するコンテナは使用したいものに応じて変化  
**初回は作成に少し時間がかかる**

  - phpmyadmin　DBのGUIクライアント
  - mailhog　SMTPサーバ

- 起動イメージの確認  
`$ docker-compose ps`  
StateがすべてUpになっていればOK
（workspaceとphp-fpmも一緒に起動しているはず）

- 動作確認  
[http://localhost](http://localhost/)にアクセス  
※DockerToolboxの場合は、`http://(dockerのIP):80`  
この段階では、nginxの404画面になればOK

### Laravelプロジェクト
#### 作成
- workspaceコンテナに入る  
`$ docker-compose exec workspace bash`

- Laravelのインストール  
`$ composer create-project --prefer-dist laravel/laravel ./`

- 動作確認  
[http://localhost](http://localhost/)にアクセス  
※DockerToolboxの場合は、`http://(dockerのIP):80`  
Laravelのトップページが表示されればOK

#### MySQLの接続設定
- Laravelプロジェクトの`.env`を修正
接頭辞が `DB_` の部分を編集し、laradockフォルダ/.envに記述されている値に合わせる形になる
```
例
DB_CONNECTION=mysql
DB_HOST=mysql
DB_PORT=3306
DB_DATABASE=default
DB_USERNAME=default
DB_PASSWORD=secret
```

- マイグレーションを実行して接続テスト  
workspaceコンテナで`$ php artisan migrate`
ちゃんとテーブルが作成されていればOK

#### Laravelのenvファイル準備
- `(gitプロジェクト)/.sh/conf`に`.laravel-env`を作成
  (Laravelプロジェクトの`.env`をコピー)

- setup.shを更新
以下を追記する
```
cp -f conf/.**laravel**-env ../(Laravelプロジェクトフォルダ）/.env
```

### メンバーが環境を作るときは
- プロジェクトをクローン  
`$ git clone (プロジェクトパス)`

- シェルでenvファイル作成 + mysql設定ファイルコピー   
`$ sh .sh/setup.sh`

- dockerのイメージ作成と起動  
`$ docker-compose ip --build -d nginx mysql`  
※作成するコンテナは必要に応じて  
**初回は作成に少し時間がかかる**

- 起動イメージの確認   
`$ docker-compose ps`
StateがすべてUpになっていればOK

- 動作確認  
[http://localhost](http://localhost/)にアクセス  
※DockerToolboxの場合は、`http://(dockerのIP):80`
ちゃんとコードに応じた画面が表示されればOK

