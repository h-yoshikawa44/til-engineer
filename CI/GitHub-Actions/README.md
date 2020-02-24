# GitHub Actions テンプレート

## 使い方
### 配置
プロジェクトルート直下に`.github/workflows`フォルダを作り、その配下にymlファイルを配置する。  
ファイル名は任意の名前でOK。

### GitHub Secrets
公開したくない秘匿情報を登録し、環境変数のように扱える機能。  
一度登録するとその値を確認することはできないようになっている。

`secrets.〇〇`の部分は、GitHub Secretsから値を参照しているので、あらかじめリポジトリのSettingから設定しておく。
ただし、`secrets.GITHUB_TOKEN`については自動で設定される。

### Actionのバージョン
`owner/repo@version`形式で指定するが、基本的に`master`は使用しない方がいい。  
バージョンアップにより、予期せぬ動作不良を招く可能性があるため。