dp[m][n]表示：剩余m片整片，n片半片的概率

假设每次取的时候，取到每片（不论半片或者整片）的概率是一样的

则：
dp[m][n] = prob(剩m整片，n+1半片的时候取到了半片) + prob(剩m+1整片，n-1半片的时候取到了一整片)
         = n+1/(m + n + 1) * dp[m][n+1] + m+1/(m + 1 + n - 1) * dp[m+1][n-1]

对dp index m,n还有如下限制:
m >=0, n >=0
m + 0.5 * n <= 100  //max 100整片

初始可知:
dp[100][0] = 1
dp[99][1] = 1
