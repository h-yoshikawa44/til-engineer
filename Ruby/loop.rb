# 繰り返し文

# 制御
# ループから抜ける：break
# 次のループへ：next

# ※doは省略可能
# while 条件式 do
#   処理
# end
test_while = 1
while test_while < 4 do
  puts test_while
  test_while += 1
end
# 1
# 2
# 3

# ※do-whileと同様のことができるもの
# loop do
#   処理
#   break if 条件
# end
test_loop = 1
loop do
  puts test_loop
  test_loop += 1
  break if test_loop > 3
end
# 1
# 2
# 3

# 条件式が偽の間繰り返す
# ※doは省略可能
# until 条件式 do
#   処理
# end
test_until = 6
until test_until < 3 do
  puts test_until
  test_until -= 1
end
# 6
# 5
# 4
# 3


# ※doは省略可能
# for 変数名 in 配列 do
#   処理
# end
list = ['A', 'B', 'C']
for value in list do
    puts value
end
# A
# B
# C

# ※doは省略可能
# for キー, 値 in 配列 do
#     処理
# end
hash = {:apple => "アップル", :orange => "オレンジ", :grape => "グレープ"}
for key, value in hash do
  puts "KEY:#{key} VALUE:#{value}"
end
# KEY:apple VALUE:アップル
# KEY:orange VALUE:オレンジ
# KEY:grape VALUE:グレープ

# 配列.each do | 変数名 |
# 　処理
# end
each_list = ['D', 'E', 'F']
each_list.each do | value |
  puts value
end
# D
# E
# F

# ※他の言語のfor文に近い
# カウントは0から始まる
# 回数.times do
#     処理
# end
3.times do |i|
  puts i
end
# 0
# 1
# 2

# ※他の言語のfor文に近い
# 1ずつカウントアップされていく
# 初期値.upto(最大値) do |i|
#   処理
# end
2.upto(4) do |i|
  puts i
end
# 2
# 3
# 4

# ※他の言語のfor文に近い
# 1ずつカウントダウンしていく
# 初期値.downto(最小値) do |i|
#   処理
# end
4.downto(2) do |i|
  puts i
end
# 4
# 3
# 2