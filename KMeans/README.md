# KMeans

## Introduction

k-means clustering is a method of vector quantization, originally from signal processing, that is popular for cluster analysis in data mining. k-means clustering aims to partition n observations into k clusters in which each observation belongs to the cluster with the nearest mean, serving as a prototype of the cluster.

## How to Use it

**Requirements: Java 1.8+**

### Compile Source Code

Run following command in terminal:

```
javac KMeansGui.java
```

### Run

Run following command in terminal:

- `n` is the number of points, you should replace it with a specific number
- `k` is the number of clusters, you should replace it with a specific number

**Run Terminal Version**

```
java KMeans n k
```

**Run GUI Version**

```
java KMeansGui n k
```

### Screenshot

<img width="752" alt="KMeans Screenshot" src="https://cloud.githubusercontent.com/assets/1730504/11613777/50a5eb0a-9c68-11e5-84bd-5750b0fe3eb6.png">

## Improvements

- [ ] Eliminate outliers
- [ ] Eliminate small clusters that may represent outliers
- [ ] Split ‘loose’ clusters, i.e., clusters with relatively high SSE
- [ ] Merge clusters that are ‘close’ and that have relatively low SSE
