public interface SetInterface<T> {

    /**
     * Add item to Set.
     * @param item - item to be added to List.
     * @return boolean true if successful, else false.
     */
    boolean add(T item);

    /**
     * Clear Set to new Empty Set
     */
    void clear();

    /**
     * Check if Set contains item
     * @param item - item to be searched for in List.
     * @return boolean true if contains, else false.
     */
    boolean contains(T item);
//
//    /**
//     * Check if Set is Empty.
//     * @return True if empty.
//     */
//    boolean isEmpty();
//
//    /**
//     * Remove object from Set.
//     * @param item to be removed
//     * @return true if successful.
//     */
//    boolean remove(T item);
//
//    /**
//     * Get size of set
//     * @return size as Integer
//     */
//    int size();
//
//    /**
//     * Get Set object as String
//     * @return String representation of Set
//     */
//    String toString();
}
