


def postfix(vet):
	operators = ['*','+','/','-']
	stack = []
	for num in vet:
		if num not in operators:
			stack.append(num)
		#is operator
		else:
			num1 = stack.pop()
			num2 = stack.pop()
			opcode = operators.index(num)
			if opcode==0:
				res = num1 * num2
				stack.append(res)
			elif opcode==1:
				res = num1 + num2
				stack.append(res)
			elif opcode==2:
				res = num1 / num2
				stack.append(res)
			else:
				res = num1 - num2
				stack.append(res)
	return stack.pop()