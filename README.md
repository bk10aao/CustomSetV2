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

| Method                    |    `V1`    |  `V2`  | `JDK`  |  Winner  |
|:--------------------------|:----------:|:------:|:------:|:--------:|
| `add(E)`                  |   $O(n)$   | $O(n)$ | $O(n)$ |   Tie    |
| `addAll(Collection)`      | $O(n * m)$ | $O(n)$ | $O(n)$ | V2 & JDK |
| `clear()`                 |   $O(1)$   | $O(1)$ | $O(1)$ |   Tie    |
| `contains(E)`             |   $O(n)$   | $O(1)$ | $O(1)$ | V2 & JDK |
| `containsAll(Collection)` | $O(n * m)$ | $O(n)$ | $O(n)$ | V2 & JDK |
| `isEmpty()`               |   $O(1)$   | $O(1)$ | $O(1)$ |   Tie    |
| `remove(E)`               |   $O(n)$   | $O(1)$ | $O(1)$ | V2 & JDK |
| `removeAll(Collection)`   | $O(n * m)$ | $O(n)$ | $O(n)$ | V2 & JDK |
| `retainAll(Collection)`   | $O(n * m)$ | $O(n)$ | $O(n)$ | V2 & JDK |
| `size()`                  |   $O(1)$   | $O(1)$ | $O(1)$ |   Tie    |
| `toArray()`               |   $O(n)$   | $O(n)$ | $O(n)$ |   Tie    |
| `toString()`              |   $O(n)$   | $O(n)$ | $O(n)$ |   Tie    |

## Space Complexity

| Method                    |    `V1`    |    `V2`    |   `JDK`    |  Winner  |
|:--------------------------|:----------:|:----------:|:----------:|:--------:|
| `add(E)`                  |   $O(n)$   |   $O(n)$   |   $O(n)$   |   Tie    |
| `addAll(Collection)`      | $O(n + m)$ | $O(n + m)$ | $O(n + m)$ |   Tie    |
| `clear()`                 |   $O(1)$   |   $O(1)$   |   $O(1)$   |   Tie    |
| `contains(E)`             |   $O(n)$   |   $O(1)$   |   $O(1)$   | V2 & JDK |
| `containsAll(Collection)` | $O(n * m)$ |   $O(n)$   |   $O(n)$   | V2 & JDK |
| `isEmpty()`               |   $O(1)$   |   $O(1)$   |   $O(1)$   |   Tie    |
| `remove(E)`               |   $O(n)$   |   $O(1)$   |   $O(1)$   | V2 & JDK |
| `removeAll(Collection)`   | $O(n * m)$ |   $O(n)$   |   $O(n)$   | V2 & JDK |
| `retainAll(Collection)`   | $O(n * m)$ |   $O(n)$   |   $O(n)$   | V2 & JDK |
| `size()`                  |   $O(1)$   |   $O(1)$   |   $O(1)$   |   Tie    |
| `toArray()`               |   $O(n)$   |   $O(n)$   |   $O(n)$   |   Tie    |
| `toString()`              |   $O(n)$   |   $O(n)$   |   $O(n)$   |   Tie    |

- `n`: Number of elements in the Set.
- `m`: Number of elements in the input collection.

# Performance 

Below performance is a comparison made at 50000 operations per method.

| Method                  | V2 (ns)  | JDK (ns) |            Winner            | Margin |
|:------------------------|:---------|:---------|:----------------------------:|:------:|
| add(E)                  | 233182   | 229506   | **Statistically Equivalent** | 1.02×  |
| addAll(Collection)      | 255128   | 264354   | **Statistically Equivalent** | 1.04×  |
| clear()                 | 13735    | 14886    | **Statistically Equivalent** | 1.08×  |
| clone()                 | 195211   | 227002   |            **V2**            | 1.16×  |
| constructor()           | 40       | 49       |            **V2**            | 1.23×  |
| constructor(Collection) | 197882   | 198857   | **Statistically Equivalent** | 1.00×  |
| contains(Object)        | 100      | 66       |           **JDK**            | 1.53×  |
| containsAll(Collection) | 16922    | 15828    | **Statistically Equivalent** | 1.07×  |
| equals(Object)          | 452081   | 693257   |            **V2**            | 1.53×  |
| hashCode()              | 64098    | 140641   |            **V2**            | 2.19×  |
| isEmpty()               | 42       | 71       |            **V2**            | 1.71×  |
| iterator()              | 100736   | 181739   |            **V2**            | 1.80×  |
| remove(Object)          | 66       | 118      |            **V2**            | 1.80×  |
| removeAll(Collection)   | 35869    | 39977    |            **V2**            | 1.11×  |
| retainAll(Collection)   | 93187198 | 93672223 | **Statistically Equivalent** | 1.01×  |
| size()                  | 38       | 40       | **Statistically Equivalent** | 1.06×  |
| toArray()               | 56113    | 61206    | **Statistically Equivalent** | 1.09×  |
| toArray(T[])            | 65604    | 86787    |            **V2**            | 1.32×  |
| toString()              | 467994   | 482031   | **Statistically Equivalent** | 1.03×  |

# Performance Testing

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
