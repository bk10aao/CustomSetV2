# Set
Implementation of a Set using a Map. This was based off of my initial version using an array: https://github.com/bk10aao/CustomSet

# Methods
1. `CustomSet()` - default constructor.
2. `CustomSet(final Collection<T> c)` - create set with items from given collection, throws NullPointerException on null collection.
3. `CustomSet(final int initialCapacity)` - create a set with a given capacity, throws IllegalArgumentException on negative size.
4. `CustomSet(final int initialCapacity, final double loadFactor)` - create a set with a given capacity and load factor, throws IllegalArgumentException on negative size or load factor less than 0 or larger than 1.
5. `boolean add(final T item)` - adds item to set if not currently present. Returns true if not already present in Set. 
6. `boolean addAll(final Collection<T> c` - adds all items in collection to set. Throws NullPointerException on null item in set. Returns true if set is modified. 
7. `void clear()` - clears the set back to an empty state.
8. `boolean contains(final T item)` - returns boolean determining if set contains item. 
9. `boolean containsAll(final Collection<T> c)` - returns boolean determining if all items in collection are contained within Set. Throws NullPointerException if collection contains null item.
10. `boolean isEmpty()` - returns boolean determining if set is empty. 
11. `boolean remove(final T item)` - returns boolean determining if value existed and removed from set.
12. `boolean removeAll(final Collection<T> c)` - removes all items from set in collection. Returns true if Set is modified.
13. `boolean retainAll(final Collection<T> c)` - retains all items from set in collection and removes all others. Returns true if Set is modified.
14. `int size()` - returns size of Set as Integer.
15. `T[] toArray()` - returns values in set as Array.
16. `String toString()` - returns String representation of Set.

<br/>

## Performance Complexity

|            Method            |        V1 LinkedList-Based       |          V2 HashMap-Based          |            Java HashSet           |          Winner         |
|:----------------------------:|:--------------------------------:|:----------------------------------:|:---------------------------------:|:-----------------------:|
| add(T item)                  | O(n)     | O(1) average, O(n) worst | O(1) average, O(n) worst | HashSet & HashMap-Based |
| addAll(Collection<T> c)      | O(m * n)  | O(m)         | O(m)        | HashSet & HashMap-Based |
| clear()                      | O(1)                             | O(1)               | O(1)            | Tie                     |
| contains(T item)             | O(n)            | O(1) average, O(n) worst               | O(1) average, O(n) worst              | HashSet & HashMap-Based |
| containsAll(Collection<T> c) | O(n * m)          | O(m)       | O(m)     | HashSet & HashMap-Based |
| isEmpty()                    | O(1)                             | O(1)                               | O(1)                              | Tie                     |
| remove(T item)               | O(n)  | O(1) average, O(n) worst               | O(1) average, O(n) worst              | HashSet & HashMap-Based |
| removeAll(Collection<T> c)   | O(n * m)   | O(m) average                           | O(m) average                          | HashSet & HashMap-Based |
| retainAll(Collection<T> c)   | O(n * m)     | O(n) average | O(n) average                          | HashSet & HashMap-Based |
| size()                       | O(1)                             | O(1)                               | O(1)                              | Tie                     |
| toArray()                    | O(n)                             | O(n)                               | O(n)                              | Tie                     |
| toString()                   | O(n)                             | O(n)                               | O(n)                              | Tie                     |

<br/>

## Space Complexity

|            Method            |        V1 LinkedList-Based        |          V2 HashMap-Based          |            Java HashSet            | Winner |
|:----------------------------:|:---------------------------------:|:----------------------------------:|:----------------------------------:|:------:|
| add(T item)                  | O(n) | O(n) | O(n) | Tie    |
| addAll(Collection<T> c)      | O(m + n)                          | O(m + n)                           | O(m + n)                           | Tie    |
| clear()                      | O(1)           | O(1)             | O(1)              | Tie    |
| contains(T item)             | O(1)                 | O(1)                   | O(1)                   | Tie    |
| containsAll(Collection<T> c) | O(1)                              | O(1)                               | O(1)                               | Tie    |
| isEmpty()                    | O(1)                              | O(1)                               | O(1)                               | Tie    |
| remove(T item)               | O(1)                | O(1)                  | O(1)                  | Tie    |
| removeAll(Collection<T> c)   | O(1)                              | O(1)                               | O(1)                               | Tie    |
| retainAll(Collection<T> c)   | O(1)                              | O(1)                               | O(1)                               | Tie    |
| size()                       | O(1)                              | O(1)                               | O(1)                               | Tie    |
| toArray()                    | O(n)                              | O(n)                               | O(n)                               | Tie    |
| toString()                   | O(n)        | O(n)         | O(n)         | Tie    |
