currentPrices = [1000, 995, 990, 985, 980, 975, 970, 970, 975, 980]

tradePrices = [1000, 990, 980, 970, 980]
tradeVolumes = [5000, 10000, 30000, 90000, 2700000]
bidFee = []
askFee = []

ik = 0.003
# 1: up
# 0: down
steps = [1, 0, 0, 0, 0]

if __name__=="__main__":
  for volume in tradeVolumes:
    bidFee.append(volume*0.0005)

  for price in currentPrices:
    

