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

### パスワード再設定
1. ユーザがパスワードの再設定をリクエストすると、ユーザが送信したメールアドレスをキーにしてデータベースからユーザを見つける  
   (app/controllers/password_resets_controller.rbのcreateメソッド)
2. 該当のメールアドレスがデータベースにある場合は、再設定用トークンとそれに対応するリセットダイジェストを生成する  
   (app/models/user.rbのcreate_reset_digestメソッドで生成して保存)
3. 再設定用ダイジェストはデータベースに保存しておき、再設定用トークンはメールアドレスと一緒に、ユーザに送信する有効化用メールのリンクに仕込んでおく  
   (app/views/user_mailer/password_reset/text.erb)
4. ユーザがメールのリンクをクリックしたら、メールアドレスをキーとしてユーザを探し、データベース内に保存しておいた再設定用ダイジェストと比較する（トークンを認証する）  
   (app/controllers/password_resets_controller.rbのvalid_userメソッド)
5. 認証に成功したら、パスワード変更用のフォームをユーザに表示する

フォームからの送信に対応するupdateアクションでは、次の4つのケースを考慮する必要がある
1. パスワード再設定の有効期限が切れていないか
   (app/controllers/password_resets_controller.rbのcheck_expirationメソッド)
2. 無効なパスワードであれば失敗させる（失敗した理由も表示する）  
   ((app/controllers/password_resets_controller.rbのif文のelse)
3. 新しいパスワードが空文字列になっていないか（ユーザ情報の編集ではOKだった）  
   (app/controllers/password_resets_controller.rbのupdateメソッドの最初のif文)
4. 新しいパスワードが正しければ、更新する  
    (app/controllers/password_resets_controller.rbのupdateメソッドののif文のelsif)