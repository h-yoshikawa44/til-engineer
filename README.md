# TIL
[![Netlify Status](https://api.netlify.com/api/v1/badges/8af81460-1140-4eb4-823d-56a99af5353a/deploy-status)](https://app.netlify.com/sites/h-yoshikawa0724-til/deploys)

小さなアウトプット

## 目的
勉強したコードやメモなどをこのリポジトリにあげていく。
- 勉強したことを可視化することでモチベーションに繋げる
- 他の人に勉強した内容を伝えられる材料になる

また、それらをただあげていくのでなく、ドキュメントのようにまとめて静的サイト化して、より見やすくする。  
**現在上げているコードも順次ドキュメントへ移行（フレームワークのコードはそのまま残す）**

## Docusaurus
ドキュメント特化の静的サイトジェネレータ。

2系を使用しているが、alpha版であることに注意。  
Dockerだとうまく動かせない？（サーバ起動はできるが、ブラウザから確認ができない）ようなので、yarnが使える環境でサーバを起動すること。（自分の場合はWSL)

### 公開された静的サイトとして確認
[ドキュメントサイト](https://h-yoshikawa0724-til.netlify.com/)

### ローカルでの確認
サーバ起動
```
$ cd Docusaurus/website

$ yarn start
```

ブラウザでアクセス
```
localhost:3000
```