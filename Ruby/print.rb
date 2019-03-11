# 標準出力処理

# p
# 引数のオブジェクト分かりやすく出力
p 'A' # "A"
testP = 'B'
p testP # "B"
testP2 = ['A', 'B', 'C']
p testP2 # ["A", "B", "C"]

# puts
# 引数のオブジェクトを文字列に変換し、改行を加えて出力
puts 'A' # A
testPuts = 'ABC'
puts testPuts # ABC
testPuts2 = ['D', 'E', 'F']
puts testPuts2
# D
# E
# F

# print
# 引数のオブジェクトを文字列に変換し、出力(改行はしない)
print 'B' # B
testPrint = 'CDE'
print testPrint # CDE
testPrint2 = ['D', 'E', 'F']
print testPrint2 # ["D", "E", "F"]
