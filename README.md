# Set

Implementation of a Set using a Map. This was based off of my initial version using an array: https://github.com/bk10aao/CustomSet

1. [time complexity](https://github.com/bk10aao/CustomSetV2/tree/main?tab=readme-ov-file#performance-complexity)
2. [space complexity](https://github.com/bk10aao/CustomSetV2/tree/main?tab=readme-ov-file#space-complexity)
3. [performance testing](https://github.com/bk10aao/CustomSetV2/blob/main/README.md#performance-testing)

All methods implemented are identical to those found in the Java [Set](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html) interface.

## Build and Test

1. To build and test the project run command `./gradlew clean build`
2. To test the project run command `gradle test --tests customset.CustomSetTest`

## Time Complexity

|         Method          |    V1    |          V2          |         JDK          |  Winner  |
|:-----------------------:|:--------:|:--------------------:|:--------------------:|:--------:|
|         add(E)          |   O(n)   | O(1) avg, O(n) worst | O(1) avg, O(n) worst | V2 & JDK |
|   addAll(Collection)    | O(n * m) |         O(n)         |         O(n)         | V2 & JDK |
|         clear()         |   O(1)   |         O(1)         |         O(1)         |   Tie    |
|       contains(E)       |   O(n)   |         O(1)         |         O(1)         | V2 & JDK |
| containsAll(Collection) | O(n * m) |         O(n)         |         O(n)         | V2 & JDK |
|        isEmpty()        |   O(1)   |         O(1)         |         O(1)         |   Tie    |
|        remove(E)        |   O(n)   |         O(1)         |         O(1)         | V2 & JDK |
|  removeAll(Collection)  | O(n * m) |         O(n)         |         O(n)         | V2 & JDK |
|  retainAll(Collection)  | O(n * m) |         O(n)         |         O(n)         | V2 & JDK |
|         size()          |   O(1)   |         O(1)         |         O(1)         |   Tie    |
|        toArray()        |   O(n)   |         O(n)         |         O(n)         |   Tie    |
|       toString()        |   O(n)   |         O(n)         |         O(n)         |   Tie    |

## Space Complexity

|         Method          |             V1              |    V2    |   JDK    |  Winner  |
|:-----------------------:|:---------------------------:|:--------:|:--------:|:--------:|
|         add(E)          |  O(n) + chaining overhead   |   O(n)   |   O(n)   | V2 & JDK |
|   addAll(Collection)    |          O(n + m)           | O(n + m) | O(n + m) |   Tie    |
|         clear()         |            O(1)             |   O(1)   |   O(1)   |   Tie    |
|       contains(E)       | O(1) per bucket, O(n) worst |   O(1)   |   O(1)   | V2 & JDK |
| containsAll(Collection) |          O(n * m)           |   O(n)   |   O(n)   | V2 & JDK |
|        isEmpty()        |            O(1)             |   O(1)   |   O(1)   |   Tie    |
|        remove(E)        | O(1) per bucket, O(n) worst |   O(1)   |   O(1)   | V2 & JDK |
|  removeAll(Collection)  |          O(n * m)           |   O(n)   |   O(n)   | V2 & JDK |
|  retainAll(Collection)  |          O(n * m)           |   O(n)   |   O(n)   | V2 & JDK |
|         size()          |            O(1)             |   O(1)   |   O(1)   |   Tie    |
|        toArray()        |            O(n)             |   O(n)   |   O(n)   |   Tie    |
|       toString()        |            O(n)             |   O(n)   |   O(n)   |   Tie    |

**Legend**:
- `n`: Number of elements in the Set.
- `m`: Number of elements in the input collection.

# Performance 

Below performance is a comparison made at 65,536 operations per method.

| Method      | V2 (ns) | JDK (ns) |            Winner            | Margin |
|:------------|:--------|:---------|:----------------------------:|:------:|
| Add         | 87398   | 94922    | **Statistically Equivalent** | 1.09×  |
| AddAll      | 112398  | 107375   | **Statistically Equivalent** | 1.05×  |
| Clear       | 15264   | 16769    | **Statistically Equivalent** | 1.10×  |
| Contains    | 21      | 26       |            **V2**            | 1.24×  |
| ContainsAll | 58433   | 60989    | **Statistically Equivalent** | 1.04×  |
| IsEmpty     | 8       | 8        | **Statistically Equivalent** | 1.00×  |
| Remove      | 21      | 21       | **Statistically Equivalent** | 1.00×  |
| RemoveAll   | 1911580 | 1730896  | **Statistically Equivalent** | 1.10×  |
| RetainAll   | 1391203 | 1333035  | **Statistically Equivalent** | 1.04×  |
| Size        | 7       | 6        | **Statistically Equivalent** | 1.14×  |
| ToArray     | 52070   | 50461    | **Statistically Equivalent** | 1.03×  |
| ToString    | 148609  | 125984   |           **JDK**            | 1.18×  |

# Performance Testing

The following charts were generated using the following values:
```
5000, 10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000
```

<b>Note: The following performance charts are designed to be viewed in dark mode.</b>

### Heat Maps
![Heatmap](PerformanceTesting/V2_HashSet/charts/heatmap.png)
![Heatmap](PerformanceTesting/V1_V2/charts/heatmap.png)

### Geometric
![Geometric](PerformanceTesting/V2_HashSet/charts/geometric.png)
![Geometric](PerformanceTesting/V1_V2/charts/geometric.png)

### V1 vs V2 vs JDK (HashSet) Performance
![Combined Performance Charts](PerformanceTesting/V1_V2_HashSet/charts/add.png)
![Combined Performance Charts](PerformanceTesting/V1_V2_HashSet/charts/addAll.png)
![Combined Performance Charts](PerformanceTesting/V1_V2_HashSet/charts/clear.png)
![Combined Performance Charts](PerformanceTesting/V1_V2_HashSet/charts/contains.png)
![Combined Performance Charts](PerformanceTesting/V1_V2_HashSet/charts/containsAll.png)
![Combined Performance Charts](PerformanceTesting/V1_V2_HashSet/charts/isEmpty.png)
![Combined Performance Charts](PerformanceTesting/V1_V2_HashSet/charts/remove.png)
![Combined Performance Charts](PerformanceTesting/V1_V2_HashSet/charts/removeAll.png)
![Combined Performance Charts](PerformanceTesting/V1_V2_HashSet/charts/retainAll.png)
![Combined Performance Charts](PerformanceTesting/V1_V2_HashSet/charts/size.png)
![Combined Performance Charts](PerformanceTesting/V1_V2_HashSet/charts/toArray.png)
![Combined Performance Charts](PerformanceTesting/V1_V2_HashSet/charts/toString.png)

### V2 vs JDK (HashSet) Performance
![Combined Performance Charts](PerformanceTesting/V2_HashSet/charts/add.png)
![Combined Performance Charts](PerformanceTesting/V2_HashSet/charts/addAll.png)
![Combined Performance Charts](PerformanceTesting/V2_HashSet/charts/clear.png)
![Combined Performance Charts](PerformanceTesting/V2_HashSet/charts/contains.png)
![Combined Performance Charts](PerformanceTesting/V2_HashSet/charts/containsAll.png)
![Combined Performance Charts](PerformanceTesting/V2_HashSet/charts/isEmpty.png)
![Combined Performance Charts](PerformanceTesting/V2_HashSet/charts/remove.png)
![Combined Performance Charts](PerformanceTesting/V2_HashSet/charts/removeAll.png)
![Combined Performance Charts](PerformanceTesting/V2_HashSet/charts/retainAll.png)
![Combined Performance Charts](PerformanceTesting/V2_HashSet/charts/size.png)
![Combined Performance Charts](PerformanceTesting/V2_HashSet/charts/toArray.png)
![Combined Performance Charts](PerformanceTesting/V2_HashSet/charts/toString.png)

### V1 vs V2 Performance
![Combined Performance Charts](PerformanceTesting/V1_V2/charts/add.png)
![Combined Performance Charts](PerformanceTesting/V1_V2/charts/addAll.png)
![Combined Performance Charts](PerformanceTesting/V1_V2/charts/clear.png)
![Combined Performance Charts](PerformanceTesting/V1_V2/charts/contains.png)
![Combined Performance Charts](PerformanceTesting/V1_V2/charts/containsAll.png)
![Combined Performance Charts](PerformanceTesting/V1_V2/charts/isEmpty.png)
![Combined Performance Charts](PerformanceTesting/V1_V2/charts/remove.png)
![Combined Performance Charts](PerformanceTesting/V1_V2/charts/removeAll.png)
![Combined Performance Charts](PerformanceTesting/V1_V2/charts/retainAll.png)
![Combined Performance Charts](PerformanceTesting/V1_V2/charts/size.png)
![Combined Performance Charts](PerformanceTesting/V1_V2/charts/toArray.png)
![Combined Performance Charts](PerformanceTesting/V1_V2/charts/toString.png)
