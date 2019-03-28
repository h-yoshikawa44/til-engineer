# 配列

# 宣言+初期化（空）
list = []

# 宣言+初期化
list2 = [1, 2, 3]
print list2[0] # 1
print list2[1] # 2
print list2[2] # 3

# 配列末尾に追加（複数指定可能）
list2.push(4)
print list2 # [1, 2, 3, 4]

# 配列末尾に追加・省略形
list2 << 5
print list2 # [1, 2, 3, 4, 5]

# 要素の挿入
# insert(挿入する位置, 挿入する値)
# 挿入する値は複数指定可能
list3 = [4, 5, 6]
list3.insert(1, 7)
print list3 # [4, 7, 5, 6]

# 配列先頭に要素を追加（複数指定可能）
list3.unshift(3)
print list3 # [3, 4, 7, 5, 6]

# 配列のサイズ
print list3.length # 5
print list3.size # 5