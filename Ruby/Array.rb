# 配列

# 宣言+初期化（空）
list = []

# 宣言+初期化
list2 = [1, 2, 3]
print list2[0] # 1
print list2[1] # 2
print list2[2] # 3

print list2[-1] # 3

print list2.first # 1
print list2.last # 3

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

# 配列に含まれているか
list4 = [30, 5, 15]
print list4.include?(5) # true

# 元の配列の値は変化しないメソッドがある
# 変化させたい場合はメソッドの末尾に ! をつける

# 配列のソート（元の配列の値は変化しない）
print list4.sort # [5, 15, 30]

# 配列を逆順にする（元の配列の値は変化しない）
print list4.reverse # [15, 5, 30]

# 配列をシャッフル（元の配列の値は変化しない）
print list4.shuffle

# 配列を文字列に変換（元の配列の値は変化しない）
list5 = [30, 4, 17, "bar"]
print list5.join # 30417bar
print list5.join(', ') # 30, 4, 17, bar

# 範囲を配列に変換
print (0..9).to_a # [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
print ('a'..'e').to_a # ["a", "b", "c", "d", "e"]