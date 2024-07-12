def fibonacci(n):
    if n <= 1:
        return n

    a, b = 0, 1
    for _ in range(2, n + 1):
        a, b = b, a + b

    return b

def serieFibonacci(m):
    for i in range(m):
        print(fibonacci(i), end=" ")

def main():
    try:
        X = int(input("Ingresa un número X: "))
        serieFibonacci(X)
    except ValueError:
        print("Error: Ingresa un número válido.")

main()
