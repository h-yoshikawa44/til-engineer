## Webpack
ビルドツール

### 前準備
以下が必要？
- webpack
- webpack-dev-server

SCSSを使う場合
- node-sass
- style-loader
- css-loader
- sass-loader
- import-glob-loader
- extract-text-webpack-plugin

### webpack.config.js設定例

```js
const path = require('path');
const ExtractTextPlugin = require('extract-text-webpack-plugin');

const publidDir = path.join(__dirname, '/public');
module.exports = [
  {
    entry: [
      './src/index.js',
    ],
    output: {
      path: publidDir,
      publicPath: '/',
      filename: 'bundle.js',
    },
    module: {
      loaders: [{
        exclude: /node_modules/,
        loader: 'babel-loader',
        query: {
          presets: ['react', 'es2015'],
        },
      }],
    },
    resolve: {
      extensions: ['.js', '.jsx'],
    },
    devServer: {
      historyApiFallback: true,
      contentBase: publidDir,
    },
  },
  {
    entry: {
      style: './stylesheets/index.scss',
    },
    output: {
      path: publidDir,
      publicPath: '/',
      filename: 'bundle.css',
    },
    module: {
      loaders: [
        {
          test: /\.css$/,
          loader: ExtractTextPlugin.extract({ fallback: 'style-loader', use: 'css-loader' }),
        },
        {
          test: /\.scss$/,
          loader: ExtractTextPlugin.extract({ fallback: 'style-loader', use: 'css-loader!sass-loader' }),
        },
      ],
    },
    plugins: [
      new ExtractTextPlugin('bundle.css'),
    ],
  },
];
```

### ビルドするまでの例（単純なJSでの例）
#### ファイル作成・実行手順
public/index.htmlを作成

例
```html
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>udemy_react</title>
  </head>
  <body>
    <div class="container">
      Hello udemy
    </div>
  </body>
  <script src="bundle.js" charset="utf-8"></script>
</html>
```

src/index.jsを作成  
中身は空でも可能

`$ ./node_modules/.bin/webpack-dev-server`でビルドを実行してサーバを起動  
※　`$ ./node_modules/.bin/webpack`とするとビルドだけ走り、定義したアウトプットファイル（今回はbundle.js）が作成される  
※  コマンドを毎回打つのが面倒な時はpackage.jsonでscriptsの箇所にコマンドもエイリアスを作成しておくと良い

ブラウザでアクセス  
以降コードを変更すると、自動的に再ビルドしてリロードしてくれる

#### 実際の流れ
webpack.config.jsのentryに定義されているファイルをビルドし、outputで定義されているファイルに抽出

サーバ起動でpublic/index.htmlを読み込み