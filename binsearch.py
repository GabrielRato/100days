


def binsearch(vet,x):
	ini = 0
	fim = len(vet ) -1
	while ini <= fim:	
		meio = (ini+fim)//2
		if vet[meio] == x : return meio
		elif x < vet[meio] :
			fim = meio - 1
		else:
			ini = meio + 1	
print binsearch([1,2,3,4,5],4)
