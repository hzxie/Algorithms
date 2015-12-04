# FP Growth

## Introduction

FP-growth is a program to find frequent item sets (also closed and maximal as well as generators) with the FP-growth algorithm (Frequent Pattern growth [[Han et al. 2000]](http://www.borgelt.net/fpgrowth.html#Han_et_al_2000)), which represents the transaction database as a prefix tree which is enhanced with links that organize the nodes into lists referring to the same item. The search is carried out by projecting the prefix tree, working recursively on the result, and pruning the original tree. The implementation also supports filtering for closed and maximal item sets with conditional item set repositories as suggested in [[Grahne and Zhu 2003]](http://www.borgelt.net/fpgrowth.html#Grahne_and_Zhu_2003), although the approach used in the program differs in as far as it used top-down prefix trees rather than FP-trees. It does not cover the clever implementation of FP-trees with two integer arrays as suggested in [[Rasz 2004]](http://www.borgelt.net/fpgrowth.html#Rasz_2004). Since version 6.0 the program made available above can also be used to find association rules.

An example of FP Tree is shown below:

![FP Tree](https://upload.wikimedia.org/wikipedia/commons/1/1e/FPG_FIG_01.jpg)

Note that the current version of this program can only find frequent item sets, not association rules.

## Data Set

We use [Adult Data Set](https://archive.ics.uci.edu/ml/datasets/Adult) for this lab.

## How to Use it

### Download Data Set

- Download the data set [here](https://archive.ics.uci.edu/ml/machine-learning-databases/adult/adult.data).
- Rename it as `adult.txt`, and put it to this folder.

### Compile Source Code

```
javac FpGrowthTest.java
```

### Run

```
java FpGrowthTest
```

You'll get an output as following for the program:

```
ItemSet: {Attribute: [AttrName = FinalWeight, AttrValue = [147242.1, 294484.2)]=16307 }
ItemSet: {Attribute: [AttrName = HoursPerWeek, AttrValue = [39.6, 49.5)]=18335 Attribute: ..., AttrValue = UNITED_STATES]=16290 }
ItemSet: {Attribute: [AttrName = CapitalLoss, AttrValue = [0.0, 435.7)]=20642 Attribute: ..., AttrValue = UNITED_STATES]=19487 }
ItemSet: {Attribute: [AttrName = Salary, AttrValue = LESS_OR_EQUAL_TO_50K]=17732 Attribute: ..., AttrValue = UNITED_STATES]=20134 }
ItemSet: {Attribute: [AttrName = Salary, AttrValue = LESS_OR_EQUAL_TO_50K]=24718 Attribute: ..., AttrValue = UNITED_STATES]=21997 }
ItemSet: {Attribute: [AttrName = CapitalLoss, AttrValue = [0.0, 435.7)]=26443 Attribute: ..., AttrValue = UNITED_STATES]=25621 }
ItemSet: {Attribute: [AttrName = CapitalLoss, AttrValue = [0.0, 435.7)]=27800 Attribute: ..., AttrValue = UNITED_STATES]=27800 }
ItemSet: {Attribute: [AttrName = CapitalLoss, AttrValue = [0.0, 435.7)]=30283 Attribute: [AttrName = CapitalGain, AttrValue = [0.0, 10000.0)]=30283 }

```

## Improvements

- [x] Aimed for different types of data sets
- [ ] Better discretization methods for continuous values of different distributions