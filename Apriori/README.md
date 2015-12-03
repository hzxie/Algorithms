# Apriori Algorithm

## Introduction

Apriori is an algorithm for frequent item set mining and association rule learning over transactional databases. It proceeds by identifying the frequent individual items in the database and extending them to larger and larger item sets as long as those item sets appear sufficiently often in the database. The frequent item sets determined by Apriori can be used to determine association rules which highlight general trends in the database: this has applications in domains such as market basket analysis.

The pseudo code for the algorithm is given below:

![Apriori Pseudo Code](https://upload.wikimedia.org/math/4/f/b/4fbedb1f878d4f8b49dd005d3c0dd873.png)

## Data Set

We use [Adult Data Set](https://archive.ics.uci.edu/ml/datasets/Adult) for this lab.

## How to Use it

### Download Data Set

- Download the data set [here](https://archive.ics.uci.edu/ml/machine-learning-databases/adult/adult.data).
- Rename it as `adult.txt`, and put it to this folder.

### Compile Source Code

```
javac AprioriTest.java
```

### Run

```
java AprioriTest
```

You'll get an output as following for the program:

```
ItemSet: {Race=Attribute: [AttrName = Race, AttrValue = WHITE] }
ItemSet: {HoursPerWeek=Attribute: [AttrName = HoursPerWeek, AttrValue = [39.6, 49.5)] }
ItemSet: {Sex=Attribute: [AttrName = Sex, AttrValue = MALE] }
ItemSet: {CapitalGain=Attribute: [AttrName = CapitalGain, AttrValue = [0.0, 10000.0)] }
ItemSet: {WorkClass=Attribute: [AttrName = WorkClass, AttrValue = PRIVATE] }
ItemSet: {NativeCountry=Attribute: [AttrName = NativeCountry, AttrValue = UNITED_STATES] }
ItemSet: {CapitalLoss=Attribute: [AttrName = CapitalLoss, AttrValue = [0.0, 435.7)] }
ItemSet: {Salary=Attribute: [AttrName = Salary, AttrValue = LESS_OR_EQUAL_TO_50K] }
ItemSet: {FinalWeight=Attribute: [AttrName = FinalWeight, AttrValue = [147242.1, 294484.2)] }
```

## Improvements

- [x] Aimed for different types of data sets
- [ ] Better discretization methods for continuous values of different distributions