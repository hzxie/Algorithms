# Covnex Hull

## Introduction

In mathematics, the convex hull or convex envelope of a set X of points in the Euclidean space is the smallest convex set that contains X.

![Covnex Hull](https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/ConvexHull.svg/440px-ConvexHull.svg.png)

[Read More...](https://en.wikipedia.org/wiki/Convex_hull)

## Solutions

### Brute Force 

**Time Complexity: O(n^3)**

The algorithm is based on the idea:

- The only way a point belongs to the convex hull is if the point isn't contained in any triangle based off another set of points.

### Graham Scan

**Time Complexity: O(nlogn)**

The algorithm works as following:

- Find an extreme point. This point will be the pivot, is guaranteed to be on the hull, and is chosen to be the point with largest y coordinate.
- Sort the points in order of increasing angle about the pivot. We end up with a star-shaped polygon (one in which one special point, in this case the pivot, can "see" the whole polygon).
- Build the hull, by marching around the star-shaped poly, adding edges when we make a left turn, and back-tracking when we make a right turn.

### Divide and Conquer

**Time Complexity: O(nlogn)**

The algorithm works as following:

- If |S| <= 3, then compute the convex hull by brute force in O(1) time and return.
- Otherwise, partition the point set S into two sets A and B, where A consists of half the points with the lowest x coordinates and B consists of half of the points with the highest x coordinates.
- Recursively compute HA = Hull(A) and HB = Hull(B).
- Merge the two hulls into a common convex hull, H, by computing the upper and lower tangents for HA and HB and discarding all the points lying between these two tangents.

## How to Use it

**Requirements: Java 1.7+**

### Compile Source Code

Run following command in terminal:

```
javac ConvexHullBruteForce.java
javac ConvexHullGrahamScan.java
javac ConvexHullDivideConquer.java
```

### Run

Run following command in terminal:

*(`n` is the number of points, you should replace it with a specific number)*

```
java ConvexHullBruteForce n
java ConvexHullGrahamScan n
java ConvexHullDivideConquer n
```

You'll get an output as following for each program:

```
Candidate Points:
(39.64, 12.63)
(93.77, 24.92)
(23.61, 66.87)
(38.85, 25.78)
(25.85, 26.86)
(26.04, 79.34)
(96.27, 23.20)
(39.88, 38.17)
(10.62, 25.29)
(9.28, 72.71)
Convex Hull Points:
(10.62, 25.29)
(39.64, 12.63)
(96.27, 23.20)
(26.04, 79.34)
(9.28, 72.71)
Total Time: 0 ms
```

### Test the Correctness

Run following command in terminal:

```
javac ConvexHullTest.java
java ConvexHullTest n
```

You'll find that the result of the three solutions for the same input is the same.