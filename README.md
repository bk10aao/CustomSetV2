# Set
Implementation of a Set using a Map

# Methods
1. `CustomSet()` - default constructor.
2. `CustomSet(Collection<T> c)` - create set with items from given collection, throws NullPointerException on null collection.
3. `CustomSet(int initialCapacity)` - create a set with a given capacity, throws IllegalArguementException on negative size.
4. `CustomSet(int initialCapacity, double loadFactor)` - create a set with a given capacity and load factor, throws IllegalArguementException on negative size or load factor less than 0 or larger than 1.
5. `boolean add(T item)` - adds item to set if not currently present. Returns true if not already present in Set. 
6. `void clear()` - clears the set back to an exmpty state.
8. `boolean contains(T item)` - returns boolean determining if set contains item. 
9. `boolean isEmpty()` - returns boolean determining if set is empty. 
10. `boolean remove(T item)` - returns boolean determining if value existed and removed from set.
11. `int size()` - returns size of Set as Integer.
12. `String toString()` - returns String representation of Set.