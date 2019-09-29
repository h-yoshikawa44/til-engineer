## ESLint
JavaScriptの静的解析ツール。

### インストール
`yarn add`で以下をインストール

- eslint
- babel-eslint
- eslint-config-airbnb　※airbnbのルールを導入

追加
- eslint-plugin-import
- eslint-plugin-jsx-a11y
- eslint-plugin-react

#### VSCode拡張
- [ESLint](https://marketplace.visualstudio.com/items?itemName=dbaeumer.vscode-eslint)

### 設定ファイル
.eslintrc.json

例
```json
{
  "extends": "airbnb",
  "plugins": [
    "react",
    "jsx-a11y",
    "import"
  ],
  "rules": {
    "no-use-before-define": 0,
    "react/prefer-stateless-function": 0,
    "resct/jsx-filename-extension": 0
  },
  "parser": "babel-eslint"
}
```

#### rules
ルール設定を定義。0の値をセットすると無視できる。

- no-use-before-define: 変数の定義が使用する場所より後に定義されている
- react/prefer-stateless-function: ステートレスコンポーネントはpureファンクションで書く
- resct/jsx-filename-extension: jsxを書いているファイルは拡張子がjsxである