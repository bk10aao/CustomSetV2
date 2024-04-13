# Set
Implementation of a Set using an array

# Methods
1. CustomSet() - default constructor.
2. CustomSet(Collection<T> c) - create set with items from given collection, throws NullPointerException on null collection.
3. CustomSet(int initialCapacity) - create a set with a given capacity, throws IllegalArguementException on negative size.
4. CustomSet(int initialCapacity, double loadFactor) - create a set with a given capacity and load factor, throws IllegalArguementException on negative size or load factor less than 0 or larger than 1.
5. add(T item) - adds item to set if not currently present. Returns true if not already present in Set. 
6. clear() - clears the set back to an exmpty state.
7. clone() - create a deep clone of the set.
8. contains(T item) - returns boolean determining if set contains item. 
9. isEmpty() - returns boolean determining if set is empty. 
10. remove(T item) - returns boolean determining if value existed and removed from set.
11. size() - returns size of Set as Integer.