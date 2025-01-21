# Set
Implementation of a Set using a Map.

# Methods
1. `CustomSet()` - default constructor.
2. `CustomSet(final Collection<T> c)` - create set with items from given collection, throws NullPointerException on null collection.
3. `CustomSet(final int initialCapacity)` - create a set with a given capacity, throws IllegalArgumentException on negative size.
4. `CustomSet(ginal int initialCapacity, ginal double loadFactor)` - create a set with a given capacity and load factor, throws IllegalArgumentException on negative size or load factor less than 0 or larger than 1.
5. `boolean add(final T item)` - adds item to set if not currently present. Returns true if not already present in Set. 
6. `boolean addAll(final Collection<T> c` - adds all items in collection to set. Throws NullPointerException on null item in set. Returns true if set is modified. 
7. `void clear()` - clears the set back to an empty state.
8. `boolean contains(T item)` - returns boolean determining if set contains item. 
9. `boolean containsAll(Collection<T> c)` - returns boolean determining if all items in collection are contained within Set. Throws NullPointerException if collection contains null item.
10. `boolean isEmpty()` - returns boolean determining if set is empty. 
11. `boolean remove(T item)` - returns boolean determining if value existed and removed from set.
12. `boolean removeAll(Collection<T> c)` - removes all items from set in collection. Returns true if Set is modified.
13. `boolean retainAll(Collection<T> c)` - retains all items from set in collection and removes all others. Returns true if Set is modified.
14. `int size()` - returns size of Set as Integer.
15. `T[] toArray()` - returns values in set as Array.
16. `String toString()` - returns String representation of Set.