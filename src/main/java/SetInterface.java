import java.util.Collection;

public interface SetInterface<T> {

    /**
     * Add item to Set.
     * @param item - item to be added to List.
     * @throws NullPointerException - on null valye
     * @return boolean true if successful, else false.
     */
    boolean add(T item);

    /**
     * Add all items in collection to Set.
     * @param c - Collection of items to be added to Set.
     * @throws NullPointerException - on null element in collection.
     * @return boolean true if successful, else false.
     */
    boolean addAll(final Collection<T> c)
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

    /**
     * Check if Set is Empty.
     * @return True if empty.
     */
    boolean isEmpty();

    /**
     * Remove object from Set.
     * @param item to be removed
     * @return true if successful.
     */
    boolean remove(T item);

    /**
     * Get size of set
     * @return size as Integer
     */
    int size();

    /**
     * Get Set object as String
     * @return String representation of Set
     */
    String toString();
}
