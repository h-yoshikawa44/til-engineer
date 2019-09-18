## Docker環境
- [Running Expo/React Native in Docker](https://medium.com/@hmajid2301/running-expo-react-native-in-docker-ff9c4f2a4388)
- [Expoのアカウントを作ってReact Nativeの開発環境を整える](https://qiita.com/hitotch/items/ea4de1ed408a9ca14fce#expo%E3%83%97%E3%83%AD%E3%82%B8%E3%82%A7%E3%82%AF%E3%83%88%E3%82%92%E4%BD%9C%E6%88%90%E3%81%99%E3%82%8B)

### バージョン


### 作成手順
- Dockerfile作成

- .env-exampleから.env作成  
REACT_NATIVE_PACKAGER_HOSTNAMEの値をセットする

- docker-compose.yml作成

- dockerのビルド  
`$ docker-compose build`

- コンテナに入る  
`$ docker-compose exec node bash`

- Expoプロジェクト作成  
`$ expo init (プロジェクトフォルダパス)`
  - テンプレートを選択
  - プロジェクト表示名を入力
  - Yarnでパッケージをインストールするかどうか選択

- Expoプロジェクト起動  
`$ expo start`

### Docker環境の中にエミュレータ環境
※未完成

参考
- [SDKToolsだけでコマンドラインからエミュレータを設定 起動](https://qiita.com/SeijiNishiwaki/items/09ee4f50011ca825610d#emulator)
- [DockerでAndroidエミュレータを起動してconnectedAndroidTestを自動化する](https://qiita.com/sekitaka_1214/items/fcc12dbb546b94dc275a)

- avdmanager、sdkmanagerコマンドが使用できることを確認

- sdkmanagerで必要なSDKをインストール

※ここでなぜかemulatorコマンドが使えない状態で進めず...