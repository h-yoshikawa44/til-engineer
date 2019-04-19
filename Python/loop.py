# 繰り返し文

# while 条件式:
#     処理
testWhile = 1
while testWhile < 4:
    print(testWhile)
    testWhile += 1
# 1
# 2
# 3

# for 変数名 in range:
#     処理
for value in range(1, 4):
    print(value)
# 1
# 2
# 3

# for 変数名 in リスト:
#     処理
list = ['A', 'B', 'C']
for value in list:
    print(value)
# A
# B
# C

# for 変数名 in 辞書:
#     処理
# ※辞書を指定した時は、キーのループになる（dict.keys()でも同様）
dict = {'a': 'A', 'b': 'B', 'c': 'C'}
for key in dict:
    print(dict[key])
# A
# B
# C

# ※辞書の値のループ
for value in dict.values():
    print(value)
# A
# B
# C

# ※辞書型のキーと値のループ
for key, value in dict.items():
    print(key + '：' + value)
# a：A
# b：B
# c：C