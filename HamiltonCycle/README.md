# Hamilton Cycle

## Intoduction

A Hamiltonian cycle, also called a Hamiltonian circuit, Hamilton cycle, or Hamilton circuit, is a graph cycle (i.e., closed loop) through a graph that visits each node exactly once (Skiena 1990, p. 196).

[Read More...](http://mathworld.wolfram.com/HamiltonianCycle.html)

## Problems and Solutions

### Hamilton Cycle

- Find a Hamilton Cycle using DFS, BFS and Hill Climbing(爬山法) methods.

### Minimum Hamilton Cycle

- Find a Hamilton Cycle of the minimum possibly length in **a complete graph** using Branch and Bound(分支限界法) Method.

## How to Use it

**Requirements: Java 1.7+**

### Compile Source Code

Run following command in terminal:

```
javac HamiltonDfs.java
javac HamiltonBfs.java
javac HamiltonHillClimbing.java
javac MinHamilton.java
```

### Run

Run following command in terminal:

- `n` is the number of points, you should replace it with a specific number
- `s` is the index of start point (obviously, `0 <= s < n`), you should replace it with a specific number
- `p` is the ratio of points and edge  (obviously, `0 <= p <= 1`), you should replace it with a specific number

```
java HamiltonDfs n s p
java HamiltonBfs n s p
java HamiltonHillClimbing n s p
java MinHamilton n s
```

You'll get an output as following for each program:

```
0 57 75 66 31 39 82 28 63 52 
57 0 31 39 42 99 18 32 31 30 
75 31 0 30 71 11 5 25 74 38 
66 39 30 0 32 55 27 25 64 81 
31 42 71 32 0 34 5 5 18 80 
39 99 11 55 34 0 27 36 39 64 
82 18 5 27 5 27 0 59 42 48 
28 32 25 25 5 36 59 0 5 29 
63 31 74 64 18 39 42 5 0 22 
52 30 38 81 80 64 48 29 22 0 
minPathway = 214
1 5 8 9 10 2 4 7 3 6 1 
Total Time: 82 ms
```