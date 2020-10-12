# TIL-Engineer
[![Netlify Status](https://api.netlify.com/api/v1/badges/8af81460-1140-4eb4-823d-56a99af5353a/deploy-status)](https://app.netlify.com/sites/h-yoshikawa0724-til-engineer/deploys)

## TILとは？
> 「Today I Learned」の略で、Github上にTILというリポジトリを作成してそこに今日覚えたことを書いていくというものです。

出典：[Qiita - Githubのリポジトリ「TIL」を使って小さなアウトプットを習慣化する](https://qiita.com/nemui_/items/239335b4ed0c3c797add)

## 目的
勉強したコードやメモなどをこのリポジトリにあげていく。
- アウトプットの習慣が身につく
- 勉強したことを可視化することでモチベーションに繋げる
- 自分の財産になる
- 他の人に勉強した内容を伝えられる材料になる

また、ドキュメントのようにまとめて静的サイト化することで、より見やすくする。  
**現在上げているコードも順次ドキュメントへ移行していき、移行次第コードは削除（フレームワークのコードはそのまま残す）**

## ドキュメントサイト
- Node.js：12.18.3
- Docusaurus：2.0.0-alpha.65
- @docusaurus/preset-classic：2.0.0-alpha.65
  - Infima：0.2.0-alpha.13

※2020/08/02更新

### Docusaurusについて
- [Docusaurus(v2)](https://v2.docusaurus.io/)  
- [Infima](https://facebookincubator.github.io/infima/)

2系を使用しているが、開発バージョンであることに注意（Infimaに関しても同様）  
Dockerだとうまく動かせない？（サーバ起動はできるが、ブラウザから確認ができない）ようなので、yarnが使える環境でサーバを起動すること（自分の場合はWSL)

### 公開された静的サイトとして確認
[よしの勉強記録 & ポートフォリオ](https://h-yoshikawa0724-til-engineer.com/)

### ローカルでの確認
ライブラリインストール
```
$ cd Docusaurus/website

$ yarn install
```

サーバ起動
```
$ yarn start
```

ブラウザでアクセス
```
localhost:3000
```