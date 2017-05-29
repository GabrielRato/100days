def next_perm(p):
	#find lgt increasing suffix
	tam = len(p)
	for i in range(tam):
		if p[i] > p[i+1]:
			pivot = i-1
			break
	for j in reversed(range(pivot,tam)):
		if p[pivot] < p[j]:
			p[pivot],p[j] = p[j],p[pivot]
			p[pivot+1:] = reversed(p[pivot+1:])
			break
	return p
