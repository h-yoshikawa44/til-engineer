### docker環境参考
[公式](https://docs.docker.com/compose/rails/)

#### エラー対応参考
- ADDする際に、Dockerfileがある場所の親ディレクトリのファイルを指定したいときに  
[docker-composeでADDやCOPYをする際に注意すること](https://qiita.com/mk-tool/items/1c7e4929055bb3b7aeda)

- standard_init_linux.go:178: exec user process caused "no such file or directory"のエラーが出た時  
[docker-compose upするとコンテナが一瞬でexited with code 1する話](https://qiita.com/nrk_baby/items/d872e8f051a96a313601)

- postgresのコンテナ起動時にFATAL: data directory "/var/lib/postgresql/data" has wrong ownershipが出た時  
[Docker for WindowsとLaradocでpostgresのコンテナが起動できない場合の対処](https://mrkmyki.com/2018/11/25/docker-for-windows%E3%81%A8laradoc%E3%81%A7postgres%E3%81%AE%E3%82%B3%E3%83%B3%E3%83%86%E3%83%8A%E3%81%8C%E8%B5%B7%E5%8B%95%E3%81%A7%E3%81%8D%E3%81%AA%E3%81%84%E5%A0%B4%E5%90%88%E3%81%AE%E5%AF%BE/)