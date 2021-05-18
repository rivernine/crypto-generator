def cal(tradePrice, tradeBalance, curPrice) :
  earningRate = ((curPrice / tradePrice) - 1)
  bidFee = tradeBalance * 0.0005
  askFee = (tradeBalance + (tradeBalance * earningRate)) * 0.0005
  print("매수가격: ", tradePrice, "매수량: ", tradeBalance, "현재가격: ", curPrice)
  print("현재가 기준 수익률: ", round(earningRate * 100, 2), "%")
  print("수익 금액: ", round(tradeBalance * earningRate))
  print("수수료 제 금액: ", round(tradeBalance * earningRate - bidFee - askFee) )
  print()
  return round(tradeBalance * earningRate) - bidFee - askFee

if __name__=="__main__":
  total = 0
  investBalance = 0

  tradePrice = 1000
  tradePrice1 = round(tradePrice - (tradePrice * 0.01))
  testPrice1 = round(tradePrice1 * 1.005)
  tradePrice2 = round(tradePrice1 - (tradePrice1 * 0.01))
  testPrice2 = round(tradePrice2 * 1.005)
  tradePrice3 = round(tradePrice2 - (tradePrice2 * 0.01))
  testPrice3 = round(tradePrice3 * 1.005)
  tradePrice4 = round(tradePrice3 - (tradePrice3 * 0.01))
  testPrice4 = round(tradePrice4 * 1.005)
  tradePrice5 = round(tradePrice4 - (tradePrice4 * 0.01))
  testPrice5 = round(tradePrice5 * 1.005)

  curPrice = testPrice5

  # 매수가격, 매수량, 현재가격
  total += cal(tradePrice, 6000, curPrice)
  investBalance += 6000

  ## testPrice1
  total += cal(tradePrice1, 20000, curPrice)
  investBalance += 20000

  ## testPrice2
  total += cal(tradePrice2, 100000, curPrice)
  investBalance += 100000

  ## testPrice3
  total += cal(tradePrice3, 500000, curPrice)
  investBalance += 500000

  ## testPrice4
  total += cal(tradePrice4, 2000000, curPrice)
  investBalance += 2000000  

  ## testPrice5
  print()
  print("(투자금, 총 수익금): ", investBalance, ", ", round(total))
  print("투자금액 대비 수익률: ", round(total/investBalance * 100, 2), "%")