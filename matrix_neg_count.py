




def binsearch(vet):
	ini = 0
	fim = len(vet ) -1
	while ini <= fim:	
		meio = (ini+fim)//2
		if vet[meio] >=0  :
			if vet[meio-1] < 0 :
				return meio
			else:
				fim = meio - 1
		elif 0 >= vet[meio] :
			ini = meio + 1
				
print binsearch([1,2,3,4,5],4)

def matrix_neg_count(M, n, m):
	count = 0
	for i in < n:
		#find the first non neg index
		first_pos = binsearch(M[i])
		count += M[i][first_pos - 1]
	return count

