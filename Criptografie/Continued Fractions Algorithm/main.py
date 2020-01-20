import math

print("Hello World!")

print("Enter value of n: ")
n = int(input())

# Step 1
steps = 15
a = dict()
b = {-1: 1}
x = dict()
res = dict()

a[0] = math.floor(math.sqrt(n))
b[0] = a[0]
x[0] = math.sqrt(n) - a[0]

# Step 2
res[0] = b[0] * b[0] % n

# Step 3
for i in range(1, steps):
    a[i] = int(1/x[i-1] // 1.0)
    x[i] = 1/x[i-1] - a[i]
    b[i] = (a[i] * b[i-1] + b[i-2]) % n
    res[i] = b[i] * b[i] % n

print('i', end='\t\t\t')
for i in range(0, steps):
    print(i, end='\t')
print()

print('ai', end='\t\t\t')
for i in range(0, steps):
    print(a[i], end='\t')
print()

print('bi', end='\t\t\t')
for i in range(0, steps):
    print(b[i], end='\t')
print()

print('bi^2 mod n', end='\t')
for i in range(0, steps):
    print(res[i], end='\t')
print()