
import sys

def MultminCost(p,i,j):
    if i==j:
        return 0
    _min=sys.maxint
    for k in range(i,j):
        count = MultminCost(p,i,k) + MultminCost(p,k+1,j) + p[i-1]*p[k]*p[j]
        if(count<_min): 
            _min = count
    return _min
        

