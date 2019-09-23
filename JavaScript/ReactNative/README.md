## 教材

## WSLでの環境構築
前提：npmかyarnが使用できる

1. `$ npm install -g expo-cli` か `$ yarn global add expo-cli`でインストール  
　※anyenv、nodenvを使用している場合は、インストール後に`$ exec $SHELL -l`

2. `$ expo init`でプロジェクトひな型作成  
  テンプレートタイプ選択とプロジェクト名を入力
```
bash-4.4# expo init
? Choose a template: (Use arrow keys)
  ----- Managed workflow -----
❯ blank                 a minimal app as clean as an empty canvas
  blank (TypeScript)    same as blank but with TypeScript configuration
  tabs                  several example screens and tabs using react-navigation
  ----- Bare workflow -----
  minimal               bare and minimal, just the essentials to get you started
  minimal (TypeScript)  same as minimal but with TypeScript configuration
```
```
? Choose a template: expo-template-blank
? Please enter a few initial configuration values.
  Read more: https://docs.expo.io/versions/latest/workflow/configuration/ ‣ 0% completed
 {
   "expo": {
     "name": "<The name of your app visible on the home screen>",
     "slug": "<A URL friendly name for your app>"
   }
 }
```
```
? Yarn v1.17.3 found. Use Yarn to install dependencies? (Y/n)
```

3. `$ expo start` でExpoサーバ起動（自動でブラウザでDevToolが起動する）

4. DevToolでCONNECTIONをTunnelにする

5. iOS/Android端末でExpo Clientを使用してアクセス

## シミュレータ
※Docker環境の中でなく、メインOSの中に入れるためのやり方

### XCode
※Macのみ

1.[App Store](https://itunes.apple.com/app/xcode/id497799835)からインストール

2.起動して、メニューのPreferencies→Components

3.インストールしたいシミュレータをインストール

4.メニューのOpen Developer Tool→Simulatorでシミュレータを起動

### Android Studio
[公式のガイド](https://docs.expo.io/versions/v34.0.0/workflow/android-studio-emulator/)

#### アプリのインストールと設定
1.[公式](https://developer.android.com/studio)からインストーラーをインストール

2.インストーラーを起動して、インストール

3.起動して右下のConfigure→Settingsを選択  
  初回起動時はセットアップがあり、必要なものをインストールするのに時間がかかる

4.Appearance & Behavior→System Setting→Andoroid SDKを選択

5.SDK Toolsタブを選択し、右下のShow Package Detailsにチェック

6.Android SDK Build-Toolsに一覧で必要なバージョンにチェックを入れてインストール

7.Android SDK Locationのパスをコピー

8.環境変数を追加
Windowsの場合は、システム環境変数に追記
```
ANDROID_SDK ... (※7でコピーしたパス）
PATH ... %ANDROID_SDK%\emulator と %ANDROID_SDK%\pratform_tools を追加
```

Macの場合は、ターミナルで`vi ~/.bash_profile`に追記

確認は`echo`コマンドで（例：`echo $PATH`）
```
export ANDROID_SDK=(※7でコピーしたパス　パス前半を`${HOME}`に置き換えるとよい)
export PATH=${PATH}:${ANDROID_SDK}/emulator
export PATH=${PATH}:${ANDROID_SDK}/pratform_tools
```

9.`emulator`コマンドと`adb`コマンドが使えるか確認

#### エミュレータ
1.起動して右下のConfigre→AVD Managerを選択

2.Create Virtual Deviceを選択

3.追加したいデバイスを選択してダウンロード

4.プレイボタンで起動

### Expo DevToolからシミュレータを起動
#### Android
はじめからRun on Android device/emulatorで立ち上げるとうまくいかない模様
あらかじめコマンドラインかAndroid Studioから立ち上げておく

1.インストールされているエミュレータの確認  
`emulator -list-avds`

2.エミュレータの起動  
`emulator -avd (エミュレータの名前) -dns-server 8.8.8.8`

3.Run on Android device/emulatorを選択でExpoクライアントアプリのインストール
