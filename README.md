# Set
Implementation of a Set using an array

# Methods
1. CustomSet() - default constructor.
2. CustomSet(Collection<T> c) - create set with items from given collection, throws NullPointerException on null collection.
3. CustomSet(int initialCapacity) - create a set with a given capacity, throws IllegalArguementException on negative size.
4. add(T item) - adds item to set if not currently present. Returns true if not already present in Set. 
5. clear() - clears the set back to an exmpty state.
6. clone() - create a deep clone of the set.
7. contains(T item) - returns boolean determining if set contains item. 
8. isEmpty() - returns boolean determining if set is empty. 
9. remove(T item) - returns boolean determining if value existed and removed from set.
10. size() - returns size of Set as Integer.