# 条件文

# if  条件式1
# 　処理1
# elsif  条件式2
# 　処理2
# else
# 　処理3
# end
def if_value(value)
    if value > 5
      puts value
    elsif value > 3
      puts value
    else
      puts value
    end
  end

  if_value(6) # 6
  if_value(4) # 4
  if_value(2) # 2

  #   処理 if 条件式
  test_if = 5
  puts 'test' if test_if > 4

  # unless 条件式 then
  # 　条件が偽の時の処理
  # end
  def unless_value(value)
    unless value > 5
      puts value
    end
  end

  unless_value(6) # (出力無し)
  unless_value(4) # 4
  unless_value(2) # 2

  # case 式　※thenは省略可能
  # 　when 値1 then
  # 　　処理1
  # 　when 値2 then
  # 　　処理2
  # 　else
  # 　　どれにも当てはまらなかった時
  # end
  def case_value(value)
    case value
      when 6 then
        puts '大'
      when 4 then
        puts '中'
      else
        puts '小'
    end
  end

  case_value(6) # 大
  case_value(4) # 中
  case_value(2) # 小