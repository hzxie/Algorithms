# 0/1 Knapsack

## Intoduction

The knapsack problem is a problem in combinatorial optimization: Given a set of items, each with a weight and a value, determine the number of each item to include in a collection so that the total weight is less than or equal to a given limit and the total value is as large as possible. It derives its name from the problem faced by someone who is constrained by a fixed-size knapsack and must fill it with the most valuable items.

[Read More...](https://en.wikipedia.org/wiki/Knapsack_problem)

## Problems and Solutions

### Dynamic Programming Algorithm

A similar dynamic programming solution for the 0/1 knapsack problem runs in **pseudo-polynomial time**.

The following is pseudo code for the dynamic programming:

```
// Input:
// Values (stored in array v)
// Weights (stored in array w)
// Number of distinct items (n)
// Knapsack capacity (W)

for j = 0 to W do:
    if w[i] < j then:
        m[n, j] := 0
    else:
        m[n, j] := v[n]
    endif
for i = n - 1 downto 1 do:
    for j from 0 to W do:
        if w[i] <= j then:
            m[i, j] := max(m[i + 1, j], m[i + 1, j - w[i]] + v[i])
        else:
            m[i, j] := m[i + 1, j]
        endif
```

### Approximation Algorithm

The fully polynomial time approximation scheme (FPTAS) for the knapsack problem takes advantage of the fact that the reason the problem has no known polynomial time solutions is because the profits associated with the items are not restricted. If one rounds off some of the least significant digits of the profit values then they will be bounded by a polynomial and 1/ε where ε is a bound on the correctness of the solution. This restriction then means that an algorithm can find a solution in polynomial time that is correct within a factor of (1-ε) of the optimal solution.

[Read More...](http://math.mit.edu/~goemans/18434S06/knapsack-katherine.pdf)

## How to Use it

### Compile Source Code

Run following command in terminal:

```
javac Knapsack.java
javac ApproxKnapsack.java
```

### Run

Run following command in terminal:

- `n` is the number of items, you should replace it with a specific number
- `c` is the capcity of the knapsack, you should replace it with a specific number
- `e` is stand for `epsilon`, `1 + epsilon` is the approximation ratio of the algorithm

```
java Knapsack n c
java ApproxKnapsack n c e
```

You'll get an output as following for the approximation algorithm:

```
[DEBUG] Weight
28 11 81 63 97 66 52 59 74 54
[DEBUG] Value
64 78 94 50 97 79 34 7 24 41
1 1 0 0 0 0 0 0 0 1
Max Value (Accurate) = 183
[DEBUG] Rounding Value
21 26 32 17 33 27 11 2 8 14
1 1 0 0 0 0 0 0 0 1
Max Value (Approx) = 183
Total Time: 0 ms
```

### Test the Correctness

Run following command in terminal:

```
javac KnapsackTest.java
java KnapsackTest n c
```

You'll find that the result of the three solutions for the same input is the same.