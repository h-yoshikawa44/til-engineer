## eslint
静的解析ツール

### インストール
`$ yarn add eslint eslint-plugin-react`

### 使い方
- 初期化+設定ファイル作成  
`$ /node_modules/.bin/eslint --init`

設定内容
- How would you like to configure ESlint?
  1. Answer questions about your style
  2. Use a popular style guide
  3. Inspect your JavaScript file(s)
- Which style guide do you want to follow?(2を選択時)
  1. Google
  2. Airbnb
  3. Standard
- Do you use React? (y/N)
- What format do oyu want your config file to be in?(Use arrow keys)
  1. JavaScript
  2. YAML
  3. JSON

- 実行  
`$ /node_modules/.bin/eslint ファイル名`
