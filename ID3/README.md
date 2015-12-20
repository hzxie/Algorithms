# ID3

## Introduction

The ID3 algorithm begins with the original set S as the root node. On each iteration of the algorithm, it iterates through every unused attribute of the set S and calculates the entropy H(S) (or information gain IG(A)) of that attribute. It then selects the attribute which has the smallest entropy (or largest information gain) value. The set S is then split by the selected attribute (e.g. age is less than 50, age is between 50 and 100, age is greater than 100) to produce subsets of the data. The algorithm continues to recur on each subset, considering only attributes never selected before.

[Read More...](https://en.wikipedia.org/wiki/ID3_algorithm)

## Data Set

The weather problem is a toy data set which we will use to understand how a decision tree is built. It comes from Quinlan (1986), a paper which discusses the ID3 algorithm introduced in Quinlan (1979). It
is reproduced with slight modifications in Witten and Frank (1999), and concerns the conditions under which some hypothetical outdoor game may be played. The data is shown below:

| Outlook | Temperature | Humidity |  Wind  | Play Ball? |
|:-------:|:-----------:|:--------:|:------:|:----------:|
|Sunny    | Hot         | High     | Weak   | No         |
|Sunny    | Hot         | High     | Strong | No         |
|Overcast | Hot         | High     | Weak   | Yes        |
|Rain     | Mild        | High     | Weak   | Yes        |
|Rain     | Cool        | Normal   | Weak   | Yes        |
|Rain     | Cool        | Normal   | Strong | No         |
|Overcast | Cool        | Normal   | Strong | Yes        |
|Sunny    | Mild        | High     | Weak   | No         |
|Sunny    | Cool        | Normal   | Weak   | Yes        |
|Rain     | Mild        | Normal   | Weak   | Yes        |
|Sunny    | Mild        | Normal   | Strong | Yes        |
|Overcast | Mild        | High     | Strong | Yes        |
|Overcast | Hot         | Normal   | Weak   | Yes        |
|Rain     | Mild        | High     | Strong | No         |

## How to Use it

### Compile Source Code

Run following command in terminal:

```
javac ID3Test.java -encoding UTF-8
```

### Run

Run following command in terminal:

```
java ID3Test
```

You'll get an output as following:

```
Input Outlook: (Sunny/Overcast/Rain)
Sunny
Input Temperature: (Hot/Mild/Cool)
Mild
Input Humidity: (Normal/High)
Normal
Input Wind: (Strong/Weak)
Weak

Decision: Attribute: [AttrName = PlayBall, AttrValue = YES]
```