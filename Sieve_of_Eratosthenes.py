def crivo_erastostenes(n):
	#all numbers to n are prime for now
	p = [1 for x in range(2,n+1)]
	for i in range(2,int(math.sqrt(n))):
		if(p[i]):
			#remove all multiples
			for j in range(i,n,i):
				p[j] = 0
	prime = [i+2 for i, valor in enumerate(p) if valor==1]
	return prime