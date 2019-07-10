## 参考記事
- 構成ベース  
  [DockerによるPHP開発環境構築（PHP + MySQL + Nginx）](https://qiita.com/sitmk/items/f911be7ffa4f29293fd5)

---

- MySQL設定ファイルに以下を追加（MySQL 8.0からの認証形式の違いにより接続エラーが出るため、従来の認証形式に設定しなおす）
```
[mysqld]
default_authentication_plugin=mysql_native_password
```
- PDO接続コード  
  [docker-composeでPHPとMySQLを連携させてみる](https://qiita.com/fujitak/items/b56122e2ecd94022a7b6)

---

- DBデータ永続化
```yml
volumes:
  # DB data persistence
  - ./mysql/data:/var/lib/mysql
```
- 初期データ投入（DBデータ永続化のマウントの上に書くとエラーになったので下に書く）
```yml
volumes:
  # DB initialize data
  - ./mysql/mysql_init:/docker-entrypoint-initdb.d
```
  [docker-compose＋MySQL5.7(8.0も)+初期化+永続化](https://qiita.com/juhn/items/274e44ee80354a39d872)

---

- エラー対応で以下を追加
```
command: --innodb-use-native-aio=0
```
  [Docker 18.03でMySQL5.7コンテナ起動時に File ./ib_logfile101: 'aio write' returned OS error 122. メッセージが表示されたときの対処法](https://blog.tes.co.jp/entry/2018/07/09/100555)

---

- MySQL設定ファイルに文字コード設定を追加
```
[mysqld]
character-set-server=utf8mb4

[client]
default-character-set=utf8mb4
```
  [DockerのMySQLコンテナに外部からアクセスする方法まとめ改](https://qiita.com/saken649/items/00e752d89f2a6c5a82f6)

---
- MySQLのDockerfileで設定ファイルをADDするように記述（設定ファイルをボリュームマウントではうまくいかなかったため）
```dockerfile
FROM mysql:8.0

ADD ./mysql_conf/custom.cnf /etc/mysql/conf.d/.

CMD ["mysqld"]

CMD ["client"]
```
[DockerでLAMP環境を作るときにハマったこと](https://qiita.com/uutarou10/items/94913e6e7536b713a574)

---

- PHPMyAdminのコンテナで各種環境変数を追加
```yml
    environment:
      - PMA_ARBITRARY=1
      - PMA_HOST=db
      - PMA_USER=root
      - PMA_PASSWORD=secret
```
  [phpMyAdmin on docker が便利すぎる](https://qiita.com/furu8ma/items/50718efebee20fd24517)

ERROR: yaml.reader.ReaderError: unacceptable character #x0080: special characters are not allowed
  in ".\docker-compose.yml", position 387