import matplotlib.pyplot as plt

poly_rank = [5, 100, 500, 1000, 5000, 10000, 20000, 30000]

karatsuba_seq = [0.020148, 0.49738, 4.09373, 14.0681, 102.54, 323.249, 848.066, 2115]
karatsuba_parallel = [0.005926, 0.404939, 3.424, 10.9693, 100.819, 279.249, 788.545, 2094]
sequential = [0.000791, 0.06637, 1.82835, 5.1196, 157.286, 505.937, 2076.82, 4346]
parallel = [35.3019, 45.8763, 49.103, 40.962, 97.1184, 212.5923, 816.21, 1684]

plt.plot(poly_rank, karatsuba_seq, label="simple karatsuba")
plt.plot(poly_rank, karatsuba_parallel, label="parallel karatsuba")
plt.plot(poly_rank, parallel, label="simple parallel")
plt.plot(poly_rank, sequential, label="simple sequential")


plt.xlabel('polynomial rank')
plt.ylabel('time of execution')
plt.legend()
plt.show()
