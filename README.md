# Set
Implementation of a Set using a Map. This was based off of my initial version using an array: https://github.com/bk10aao/CustomSet

1. [time complexity](https://github.com/bk10aao/CustomSetV2/tree/main?tab=readme-ov-file#performance-complexity)
2. [space complexity](https://github.com/bk10aao/CustomSetV2/tree/main?tab=readme-ov-file#space-complexity)
3. [performance testing](https://github.com/bk10aao/CustomSetV2/blob/main/README.md#performance-testing)

All methods implemented are identical to those found in the Java [Set](https://docs.oracle.com/javase/8/docs/api/java/util/Set.html) interface.


## Build and Test

1. To build and test the project run command `./gradlew clean build`
2. To test the project run command `gradle test --tests CustomSetTest`

## Time Complexity

|         Method          | CustomSet V1 (LinkedList) | CustomSet V2 (HashMap) |     Java HashSet     |         Winner         |
|:-----------------------:|:-------------------------:|:----------------------:|:--------------------:|:----------------------:|
|         add(E)          |           O(n)            |  O(1) avg, O(n) worst  | O(1) avg, O(n) worst | CustomSet V2 & HashSet |
|   addAll(Collection)    |         O(n * m)          |          O(n)          |         O(n)         | CustomSet V2 & HashSet |
|         clear()         |           O(1)            |          O(1)          |         O(1)         |          Tie           |
|       contains(E)       |           O(n)            |          O(1)          |         O(1)         | CustomSet V2 & HashSet |
| containsAll(Collection) |         O(n * m)          |          O(n)          |         O(n)         | CustomSet V2 & HashSet |
|        isEmpty()        |           O(1)            |          O(1)          |         O(1)         |          Tie           |
|        remove(E)        |           O(n)            |          O(1)          |         O(1)         | CustomSet V2 & HashSet |
|  removeAll(Collection)  |         O(n * m)          |          O(n)          |         O(n)         | CustomSet V2 & HashSet |
|  retainAll(Collection)  |         O(n * m)          |          O(n)          |         O(n)         | CustomSet V2 & HashSet |
|         size()          |           O(1)            |          O(1)          |         O(1)         |          Tie           |
|        toArray()        |           O(n)            |          O(n)          |         O(n)         |          Tie           |
|       toString()        |           O(n)            |          O(n)          |         O(n)         |          Tie           |
<br/>

## Space Complexity

|         Method          |  CustomSet V1 (LinkedList)  | CustomSet V2 (HashMap) | Java HashSet |         Winner         |
|:-----------------------:|:---------------------------:|:----------------------:|:------------:|:----------------------:|
|         add(E)          |  O(n) + chaining overhead   |          O(n)          |     O(n)     | CustomSet V2 & HashSet |
|   addAll(Collection)    |          O(n + m)           |        O(n + m)        |   O(n + m)   |          Tie           |
|         clear()         |            O(1)             |          O(1)          |     O(1)     |          Tie           |
|       contains(E)       | O(1) per bucket, O(n) worst |          O(1)          |     O(1)     | CustomSet V2 & HashSet |
| containsAll(Collection) |          O(n * m)           |          O(n)          |     O(n)     | CustomSet V2 & HashSet |
|        isEmpty()        |            O(1)             |          O(1)          |     O(1)     |          Tie           |
|        remove(E)        | O(1) per bucket, O(n) worst |          O(1)          |     O(1)     | CustomSet V2 & HashSet |
|  removeAll(Collection)  |          O(n * m)           |          O(n)          |     O(n)     | CustomSet V2 & HashSet |
|  retainAll(Collection)  |          O(n * m)           |          O(n)          |     O(n)     | CustomSet V2 & HashSet |
|         size()          |            O(1)             |          O(1)          |     O(1)     |          Tie           |
|        toArray()        |            O(n)             |          O(n)          |     O(n)     |          Tie           |
|       toString()        |            O(n)             |          O(n)          |     O(n)     |          Tie           |

**Legend**:
- `n`: Number of elements in the Set.
- `m`: Number of elements in the input collection.

# Performance Testing

The following charts were generated using the following values, this was ran 5 times with the average of these runs used to plot the performance:
```
16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536
```

Larger values were tested for Version 1 and Version 2 but when testing HashSet with those it became very slow and have therefore been scaled down. This happened predominantely by the method `removeAll` suggesting that this is caused by the HashSet resizing.

#### Note: The following performance charts are designed to be viewed in dark mode.

### Heat Maps
![Heatmap](PerformanceTesting/V2_HashSet/charts/heatmap.png)
![Heatmap](PerformanceTesting/V1_V2/charts/heatmap.png)

### Geometric
![Geometric](PerformanceTesting/V2_HashSet/charts/geometric.png)
![Geometric](PerformanceTesting/V1_V2/charts/geometric.png)


### CustomSet V1 vs CustomSet V2 vs HashSet Performance

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

### CustomSet V2 vs HashSet Performance
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

### CustomSet V1 vs CustomSet V2 Performance
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
