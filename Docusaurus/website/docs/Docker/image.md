---
id: docker-image
title: Docker - イメージ
sidebar_label: イメージ
description: Dockerのイメージページ
keywords:
  - Docker
  - イメージ
---

## イメージ
コンテナ作成の土台となるもの。コンテナ環境を作る上での設定などを記述したファイル群を指す。  
公式からや、他の開発者によるイメージが提供されているので、それらを利用すると良い。また、そのイメージをさらに改良することもできる。

イメージ上のデータは複数の読み取り専用のレイヤで構成されている。  
1つのコマンド実行につき1つのレイヤーが追加されていく感じ。

Dockerイメージのレイヤ。
- レイヤー４　←↓読み取り専用
- レイヤー３
- レイヤー２
- レイヤー１

コンテナのレイヤ。
- コンテナレイヤー　←読み書き可能
- レイヤー４　←↓読み取り専用
- レイヤー３
- レイヤー２
- レイヤー１

### Dockerfile
イメージの元となるファイル。  
各種パッケージや言語のインストール、環境変数の設定などを定義できる。  
また、元になるイメージの指定をすることで、既存のレイヤーに新しくレイヤーの追加ができる。

書き方は別ページ参照（準備中）

Dockerfileから自分でイメージを作成することを`イメージビルド`と呼ぶ。

### Docker Hub
#### 概要
[Docker Hub](https://hub.docker.com)

Dockerイメージのレジストリサービス。  
自分で作成したイメージの公開、公開されているイメージの検索、ダウンロードを行うことができる。

一からDockerfileを書いてイメージを作成するのは難しいため、Docker環境を作る際はまずここでイメージを検索してみると良い。

#### 自分で作成したイメージを公開
- Dockerアカウントを作成
- Docker Hubにログイン
- Create Repository
  - 必要事項を入力
  - 無料版の場合、privateは1つのみ
- Docker Hubへイメージをpush
  - `$ docker login` でレジストリサーバへログイン
  - `$ docker tag`でタグ付けのルールに沿ってタグをつけておく
    - Docker ID/イメージ名:タグ名
  - Docker Hubにpushする
    - `$ docker push Docker ID/イメージ名:タグ名`

#### Automated Build（自動ビルド）
GitHubやBitbucketといったソースコードのホスティングサービスでビルドコンテキスト（Dockerfileやその他のビルドに必要なファイル群）を管理。その上で、リポジトリ上のビルドコンテキストの内容が変更された場合に自動的にビルドを実行する仕組み。

リンクさせるまでの流れ（初回）
- ホスティングサービスで、Dockerイメージ用のGitリポジトリを用意
- Gitクライアントを用意
- Docker Hubのアカウント画面
  - Create → Create Automated Build
  - Link Accountsを選択
  - どのホスティングサービスとリンクするか選択
  - リンク方法を選択（Public and Privateの方が推奨）
  - Gitのホスティングサービスにログイン
  - Authrize Dockerを選択

リンク後にDocker Hubリポジトリの作成。
- Docker Hubのアカウント画面
  - Create → Create Automated Build
  - リポジトリを選択
  - Docker Hubとしてのリポジトリを作成
    - Push Type
    - Name
    - Docker Lacation
    - Docker Tag