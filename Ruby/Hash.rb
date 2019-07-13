# ハッシュ

user = {:name => 'taro', :email => 'example.com'}
print user[:name] # taro
print user[:email] # example.com

# 値の追加（順番は保持しない）
user[:age] = 18
print user # {:name=>"taro", :email=>"example.com", :age=>18}

# ハッシュの中のハッシュ
params = {}
params[:user] = {:name => 'hanako', :email => 'example.com'}
print params[:user][:name] # hanako