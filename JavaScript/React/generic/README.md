## 教材
- [公式 チュートリアル](https://ja.reactjs.org/tutorial/tutorial.html)
- ※他、各種ドキュメントや記事を参考

## Docker環境
### バージョン
- Node：12.18.0
- React：16.8.6

### Docker環境作成手順参考
https://github.com/h-yoshikawa44/til-engineer/tree/master/Docker/JavaScript/React/latest/README.md

※補足
```
privileged: true
```
この設定はコンテナ内の時間がずれており、設定しようとすると権限エラーになるので、それに対応する設定。
https://hacknote.jp/archives/47573/