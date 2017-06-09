

#Prepare 2 Hufmman Code



def min_heapify(A, i):
	#A[1..n-1]
	#parent left e right
	right = 2*i + 1
	left = 2*i
	parent = i 
	actual_parent = i
	if(A[parent] > A[left]):
		A[parent], A[left] = A[left], A[parent]
	if(A[parent] > A[right]):
		A[parent], A[right] = A[right], A[parent]
	actual_parent = parent
	if actual_parent != i:
		min_heapify(A, actual_parent)

def min_heap(A):
	# i = len/2 downto 1
	for i in range((len(A)/2)-1,0,-1):
		min_heapify(A,i)









def build_min_heap(text):
	for node in text.length:
		if not father.left:
			son = Tree()
			son.data = node
			father.left = son
		if not father.right:
			son = Tree()
			son.data = node
			father.right = son
		else:
			if father:
				
			else:
				node = Tree()
				father = node
