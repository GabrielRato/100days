def bit_count(n):
	count = 0
	#until n is not all 0 ("binary speaking")
	while(n):
		#remove the least 1 bit
		n &= (n-1)
		count+=1
	return count

