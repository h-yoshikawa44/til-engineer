# 条件文

# if  条件式1:
#     処理1
# elif  条件式2:
#     処理2
# else :
#     処理3
def if_value(value):
    if value > 5:
        print('大')
    elif value > 3:
        print('中')
    else:
        print('小')

if_value(6) # 大
if_value(4) # 中
if_value(2) # 小