def hanoi(n, origem, alvo, aux):
    if (n>0):
        hanoi(n - 1, origem, aux, alvo)
        print(origem, '=>', alvo)
        hanoi(n - 1, aux, alvo, origem)

hanoi(3,'left', 'right', 'middle')