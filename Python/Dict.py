# 辞書型
# 辞書型は値の順序を保持しないため、取り出し時に違う順序になることがある

# 宣言+初期化（空）
dict = {}

# 初期化
dict['a'] = 'A'
dict['b'] = 'B'
dict['c'] = 'C'
print(dict) # {'a': 'A', 'b': 'B', 'c': 'C'}

# キーだけ取り出し
print(dict.keys()) # dict_keys(['a', 'b', 'c'])

# 値だけ取り出し
print(dict.values()) # dict_values(['A', 'B', 'C'])