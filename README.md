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
1, 10, 50, 100, 250, 500, 750, 1000, 2500, 5000, 7500, 10000, 25000, 50000, 100000, 250000, 500000, 750000, 1000000
```

Larger values were tested for Version 1 and Version 2 but when testing HashSet with those it became very slow and have therefore been scaled down. This happened predominantely by the method `removeAll` suggesting that this is caused by the HashSet resizing. 
## CustomSet V1 vs CustomSet V2 vs HashSet Performance

![Combined Performance Charts](PerformanceTesting/CompareAll/add.png)
![Combined Performance Charts](PerformanceTesting/CompareAll/addAll.png)
![Combined Performance Charts](PerformanceTesting/CompareAll/clear.png)
![Combined Performance Charts](PerformanceTesting/CompareAll/contains.png)
![Combined Performance Charts](PerformanceTesting/CompareAll/containsAll.png)
![Combined Performance Charts](PerformanceTesting/CompareAll/isEmpty.png)
![Combined Performance Charts](PerformanceTesting/CompareAll/remove.png)
![Combined Performance Charts](PerformanceTesting/CompareAll/removeAll.png)
![Combined Performance Charts](PerformanceTesting/CompareAll/retainAll.png)
![Combined Performance Charts](PerformanceTesting/CompareAll/size.png)
![Combined Performance Charts](PerformanceTesting/CompareAll/toArray.png)
![Combined Performance Charts](PerformanceTesting/CompareAll/toString.png)

## CustomSet V2 vs HashSet Performance
![Combined Performance Charts](PerformanceTesting/CompareV2ToHashSet/add.png)
![Combined Performance Charts](PerformanceTesting/CompareV2ToHashSet/addAll.png)
![Combined Performance Charts](PerformanceTesting/CompareV2ToHashSet/clear.png)
![Combined Performance Charts](PerformanceTesting/CompareV2ToHashSet/contains.png)
![Combined Performance Charts](PerformanceTesting/CompareV2ToHashSet/containsAll.png)
![Combined Performance Charts](PerformanceTesting/CompareV2ToHashSet/isEmpty.png)
![Combined Performance Charts](PerformanceTesting/CompareV2ToHashSet/remove.png)
![Combined Performance Charts](PerformanceTesting/CompareV2ToHashSet/removeAll.png)
![Combined Performance Charts](PerformanceTesting/CompareV2ToHashSet/retainAll.png)
![Combined Performance Charts](PerformanceTesting/CompareV2ToHashSet/size.png)
![Combined Performance Charts](PerformanceTesting/CompareV2ToHashSet/toArray.png)
![Combined Performance Charts](PerformanceTesting/CompareV2ToHashSet/toString.png)

## CustomSet V1 vs CustomSet V2 Performance
![Combined Performance Charts](PerformanceTesting/CompareV1ToV2/add.png)
![Combined Performance Charts](PerformanceTesting/CompareV1ToV2/addAll.png)
![Combined Performance Charts](PerformanceTesting/CompareV1ToV2/clear.png)
![Combined Performance Charts](PerformanceTesting/CompareV1ToV2/contains.png)
![Combined Performance Charts](PerformanceTesting/CompareV1ToV2/containsAll.png)
![Combined Performance Charts](PerformanceTesting/CompareV1ToV2/isEmpty.png)
![Combined Performance Charts](PerformanceTesting/CompareV1ToV2/remove.png)
![Combined Performance Charts](PerformanceTesting/CompareV1ToV2/removeAll.png)
![Combined Performance Charts](PerformanceTesting/CompareV1ToV2/retainAll.png)
![Combined Performance Charts](PerformanceTesting/CompareV1ToV2/size.png)
![Combined Performance Charts](PerformanceTesting/CompareV1ToV2/toArray.png)
![Combined Performance Charts](PerformanceTesting/CompareV1ToV2/toString.png)