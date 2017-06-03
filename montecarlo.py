
import random
def montecarlo(n):
	cont = 0
	for i in range(n):
		x = random.randrange(-1,1)
		y = random.randrange(-1,1)
		if x**2 + y**2 == 1:
			cont+=1	
	return (cont/float(n)) * 4
print(montecarlo(10000000))	
