## レイアウト
パス：app/views/layouts

### デフォルトのレイアウト
Rails new時に、デフォルトのレイアウトである`application.html.erb`が作られる

`<%= csrf_meta_tags %>`の個所は、Web攻撃手法の１つであるクロスサイトリクエストフォージェリー (Cross-Site Request Forgery: CSRF)を防ぐために使われるRailsのメソッド

### Rubyの埋め込み
provideメソッドを使用して埋め込みをしている例  
(yieldで指定して埋め込まれる)
```html
<% provide(:title, "Home") %>
<!DOCTYPE html>
<html>
  <head>
    <title><%= yield(:title) %> | Ruby on Rails Tutorial Sample App</title>
  </head>
  <body>
    <h1>Sample App</h1>
    <p>
      This is the home page for the
      <a href="https://railstutorial.jp/">Ruby on Rails Tutorial</a>
      sample application.
    </p>
  </body>
</html>
```