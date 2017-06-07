

def mcCarthy91(n):
	if n> 100:
		return n-10
	return mcCarthy91(mcCarthy91(n+11))
