## ビュー
パス：app/views/フォルダ/各種ファイル

.erbファイルに記述していく  
HTMLのなかにRubyのコードを書くには`<% %>`や出力を意味する`<%= %>`で囲う

### link_to
リンクを作成する

```ruby
<td><%= link_to 'Show', user %></td>
<td><%= link_to 'Edit', edit_user_path(user) %></td>
<td><%= link_to 'Destroy', user, method: :delete,
                                     data: { confirm: 'Are you sure?' } %></td>
```