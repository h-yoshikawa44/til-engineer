# 標準出力

# print
# 出力（改行あり）
test = 'ABC'
print(test) # ABC

# 出力（改行なし）
print(test, end='') # ABC

# print + format
# 複数指定可能
print('値：{0}'.format(test)) # 値：ABC

# print + %記法
num = 10
print('%d・%s' % (num, test)) # 10・ABC

# print + 文字数指定
test2 = 'DEF'
print(test2[0:2]) # DE