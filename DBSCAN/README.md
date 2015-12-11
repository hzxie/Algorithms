# DBSCAN

## Introduction

Density-based spatial clustering of applications with noise (DBSCAN) is a data clustering algorithm. 

It is a density-based clustering algorithm: given a set of points in some space, it groups together points that are closely packed together (points with many nearby neighbors), marking as outliers points that lie alone in low-density regions (whose nearest neighbors are too far away). DBSCAN is one of the most common clustering algorithms and also most cited in scientific literature.

[Read More...](https://en.wikipedia.org/wiki/DBSCAN)

## How to Use it

**Requirements: Java 1.8+**

### Compile Source Code

Run following command in terminal:

```
javac DbscanGui.java
```

### Run

Run following command in terminal:

- `n` is the number of points, you should replace it with a specific number
- `k` is the number of clusters, you should replace it with a specific number
- `min_points` is the number of points that are found within the ε-neighborhood
- `eps` is the value of `ε`

**Run Terminal Version**

```
java DbscanGui n minPoints eps
```

**Run GUI Version**

```
java AgnesGui k minPoints eps
```

### Screenshot

<img width="752" alt="DBSCAN Screenshot" src="https://cloud.githubusercontent.com/assets/1730504/11732625/f4495e6a-9fe0-11e5-81ef-db32be4e7940.png">