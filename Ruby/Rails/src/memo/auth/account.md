## アカウント操作

### アカウント有効化
1. ユーザの初期状態は「有効化されていない」状態にしておく  
   (db/migrate/20190730045353_add_activation_to_users.rb で`activated`のデフォルト値をfalseに)
2. ユーザ登録が行われたときに、有効トークンと、それに対応する有効化ダイジェストを生成する  
   (models/user.rbでbefore_create :create_activation_digestを実行)
3. 有効化ダイジェストはデータベースに保存しておき、有効化トークンはメールアドレスと一緒に、ユーザに送信する有効化用メールのリンクに仕込んでおく  
   (views/user_mailer/account_activation.text.erbのedit_account_activation_url(@user.activation_token, email: @user.email)でトークンを含んだリンク作成)
4. ユーザがメールのリンクをクリックしたら、アプリケーションはメールアドレスをキーにしてユーザを探し、データベース内に保存しておいた有効化ダイジェストと比較することでトークンを認証する  
   (controllers/account_activations_controllerのeditで呼び出されている、models/user.rbのauthenticated?(attribute, token)メソッドで比較)
5. ユーザを認証で来たら、ユーザのステータスを「有効化済み」に変更する  
   ((controllers/account_activations_controllerのeditのuser.update_attribute)