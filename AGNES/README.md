# AGNES

## Introduction

AGNES, known as Agglomerative Hierarchical clustering. This algorithm  works by  grouping  the data one by one on the basis of the  nearest distance measure of all the pairwise distance between the data point. Again distance between the data point is recalculated but which distance to consider when the groups has been formed? For this there are many available methods. Some of them are:

- Single-nearest distance or single linkage
- Complete-farthest distance or complete linkage
- **Average-average distance or average linkage**
- Centroid distance
- Ward's method - sum of squared euclidean distance is minimized

[Read More...](https://sites.google.com/site/dataclusteringalgorithms/hierarchical-clustering-algorithm)

## How to Use it

**Requirements: Java 1.8+**

### Compile Source Code

Run following command in terminal:

```
javac AgnesGui.java
```

### Run

Run following command in terminal:

- `n` is the number of points, you should replace it with a specific number
- `k` is the number of clusters, you should replace it with a specific number (2 is recommended)

**Run Terminal Version**

```
java Agnes n
```

**Run GUI Version**

```
java AgnesGui k
```

### Screenshot

<img width="752" alt="AGNES Screenshot" src="https://cloud.githubusercontent.com/assets/1730504/11680765/6609eada-9e95-11e5-8b3d-73c1228bb906.png">