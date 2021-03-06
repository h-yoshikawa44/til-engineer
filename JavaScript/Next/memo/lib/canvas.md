## node-canvas
ブラウザ上で canvas を使用するのと同様に画像処理を行うことができるライブラリ。

### 導入手順
```
$ yarn add canvas
```

コード例
```ts
import { NextApiRequest, NextApiResponse } from 'next'
import * as path from 'path'
import { createCanvas, registerFont, loadImage } from 'canvas'

type SeparatedText = {
  line: string
  remaining: string
}

function createTextLine(context, text: string): SeparatedText {
  const maxWidth = 400

  for (let i = 0; i < text.length; i++) {
    const line = text.substring(0, i + 1)
    if (context.measureText(line).width > maxWidth) {
      return {
        line,
        remaining: text.substring(i + 1),
      }
    }
  }

  return {
    line: text,
    remaining: '',
  }
}

function createTextLines(context, text: string): string[] {
  const lines: string[] = []
  let currentText = text

  while (currentText !== '') {
    const separatedText = createTextLine(context, currentText)
    lines.push(separatedText.line)
    currentText = separatedText.remaining
  }

  return lines
}

export default async (req: NextApiRequest, res: NextApiResponse) => {
  // question のデータ取得
  .
  .
  .
  const width = 600
  const height = 315
  registerFont(path.resolve('./fonts/ipaexm.ttf'), {
    family: 'ipaexm',
  })
  const canvas = createCanvas(width, height)
  const context = canvas.getContext('2d')

  const backgroundImage = await loadImage(
    path.resolve('./images/ogp_background.png')
  )
  context.drawImage(backgroundImage, 0, 0, width, height)

  context.font = '20px ipagp'
  context.fillStyle = '#424242'
  context.textAlign = 'center'
  context.textBaseline = 'middle'
  const lines = createTextLines(context, question.body)
    lines.forEach((line, index) => {
    const y = 157 + 40 * (index - (lines.length - 1) / 2)
    context.fillText(line, 300, y)
  })

  const buffer = canvas.toBuffer()

  res.writeHead(200, {
    'Content-Type': 'image/png',
    'Content-Length': buffer.length,
  })
  res.end(buffer, 'binary')
}
```
registerFont で使用するフォントの指定（ライセンスに注意）  
createCanvas で画像サイズを指定して canvas を作成。  
getContext('2d') で 2D 描画用のコンテキストを取得。  
これに対して描画の操作を行っていく。

描画の操作後、toBuffer() で画像のバッファを取得。  
それをバイナリとして返すことで、ブラウザに表示する。

createTextLines は文字を適度に改行するための関数の例。

### 本番環境向け設定
本番環境で node-canvas を動作させるには、追加でライブラリが必要になる。

このファイルダウンロードして解凍。
https://github.com/jwerre/node-canvas-lambda/raw/master/node12_canvas_lib64_layer.zip

この中に含まれる、.so.1 拡張子のファイルをコピーして、プロジェクトの中に含める。

package.json に script を追記（格納ディレクトリ名を canvas_lib64 とした例）
```json
"now-build": "cp canvas_lib64/*so.1 node_modules/canvas/build/Release/ && yarn build"
```
この now-build スクリプトは、デプロイ時のビルド時に実行される。  
ライブラリを canvas のディレクトリにコピーして通常のビルドを行うようになる。

### meta タグに画像 URL を指定
あとは、この node-canvas で作成した画像の URL を meta タグに指定することで、OGP として利用できる。
