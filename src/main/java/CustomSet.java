import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;


/**
 * A custom implementation of the {@link Set} interface backed by a <a href="https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html"/>}.
 * This set does not allow duplicate elements and permits null elements, like {@link HashSet}}.
 *
 * @param <E> the type of elements maintained by this set
 * @author Benjamin Kane
 * LinkedIn - <a href="https://www.linkedin.com/in/benjamin-kane-81149482/"/>
 * GitHub account bk10aao - <a href="https://github.com/bk10aao"/>
 * Repository - <a href="https://github.com/bk10aao/CustomSetV2"/>
 */
public class CustomSet<E> implements Set<E> {

    private final Map<E, Object> set;
    private static final Object PRESENT = new Object();

    /**
     * Constructs an empty set with default initial capacity (16) and load factor (0.75).
     */
    public CustomSet() {
        this.set = new HashMap<>(16, 0.75f);
    }

    /**
     * Constructs a set containing the elements of the specified collection.
     *
     * @param c the collection whose elements are to be placed into this set
     * @throws NullPointerException if the specified collection is null
     */
    public CustomSet(final Collection<E> c) {
        if(c == null)
            throw new NullPointerException();
        set = new HashMap<>(Math.max(16, (int) (c.size() / 0.75f) + 1));
        addAll(c);
    }

    /**
     * Constructs an empty set with the specified initial capacity and default load factor (0.75).
     *
     * @param initialCapacity the initial capacity
     * @throws IllegalArgumentException if the initial capacity is negative
     */
    public CustomSet(final int initialCapacity) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException();
        set = new HashMap<>(Math.max(16, initialCapacity), 0.75f);
    }

    /**
     * Constructs an empty set with the specified initial capacity and load factor.
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor
     * @throws IllegalArgumentException if the initial capacity is negative or the load factor is non-positive or NaN
     */
    public CustomSet(final int initialCapacity, final float loadFactor) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException();
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException();
        set = new HashMap<>(Math.max(16, initialCapacity), loadFactor);
    }

    /**
     * Adds the specified element to this set if it is not already present.
     * If this set already contains the element, the call leaves the set unchanged
     * and returns {@code false}. This set permits null elements.
     *
     * @param e element to be added to this set
     * @return {@code true} if this set did not already contain the specified element
     */
    @Override
    public boolean add(final E e) {
        return set.put(e, PRESENT) == null;
    }

    /**
     * Adds all the elements in the specified collection to this set if they're
     * not already present. If the specified collection is also a set, the
     * {@code addAll} operation effectively modifies this set so that its value
     * is the union of the two sets. Null elements are permitted.
     *
     * @param c collection containing elements to be added to this set
     * @return {@code true} if this set changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean addAll(final Collection<? extends E> c) {
        if (c == null)
            throw new NullPointerException();
        boolean modified = false;
        for (E e : c)
            if (add(e))
                modified = true;
        return modified;
    }

    /**
     * Removes all the elements from this set.
     * The set will be empty after this call returns.
     */
    @Override
    public void clear() {
        set.clear();
    }

    /**
     * Returns {@code true} if this set contains the specified element.
     * This set permits null elements.
     *
     * @param o element whose presence in this set is to be tested
     * @return {@code true} if this set contains the specified element
     */
    @Override
    public boolean contains(final Object o) {
        return set.containsKey(o);
    }

    /**
     * Returns {@code true} if this set contains all the elements of the
     * specified collection. Null elements are permitted in the specified collection.
     *
     * @param c collection to be checked for containment in this set
     * @return {@code true} if this set contains all the elements of the specified collection
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean containsAll(final Collection<?> c) {
        if (c == null)
            throw new NullPointerException();
        return c.stream().allMatch(this::contains);
    }

    /**
     * Compares the specified object with this set for equality. Returns
     * {@code true} if the specified object is also a set, the two sets have
     * the same size, and every member of the specified set is contained in
     * this set (or equivalently, every member of this set is contained in
     * the specified set).
     *
     * @param o object to be compared for equality with this set
     * @return {@code true} if the specified object is equal to this set
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Set<?> other)) return false;
        return size() == other.size() && containsAll(other);
    }

    /**
     * Returns the hash code value for this set. The hash code of a set is
     * defined to be the sum of the hash codes of the elements in the set,
     * where the hash code of a {@code null} element is defined to be zero.
     *
     * @return the hash code value for this set
     */
    @Override
    public int hashCode() {
        return set.keySet().hashCode();
    }

    /**
     * Returns {@code true} if this set contains no elements.
     *
     * @return {@code true} if this set contains no elements
     */
    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Returns an iterator over the elements in this set. The elements are
     * returned in no particular order.
     *
     * @return an iterator over the elements in this set
     */
    @Override
    public Iterator<E> iterator() {
        return set.keySet().iterator();
    }

    /**
     * Removes the specified element from this set if it is present.
     * Returns {@code true} if this set contained the element. This set
     * permits null elements.
     *
     * @param o object to be removed from this set, if present
     * @return {@code true} if this set contained the specified element
     */
    @Override
    public boolean remove(final Object o) {
        return set.remove(o) != null;
    }

    /**
     * Removes from this set all of its elements that are contained in the
     * specified collection. If the specified collection is also a set, this
     * operation effectively modifies this set so that its value is the
     * asymmetric set difference of the two sets. Null elements are permitted
     * in the specified collection.
     *
     * @param c collection containing elements to be removed from this set
     * @return {@code true} if this set changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean 	removeAll(final Collection<?> c) {
        if (c == null)
            throw new NullPointerException();
        return set.keySet().removeAll(c);
    }

    /**
     * Retains only the elements in this set that are contained in the
     * specified collection. In other words, removes from this set all of
     * its elements that are not contained in the specified collection.
     * If the specified collection is also a set, this operation effectively
     * modifies this set so that its value is the intersection of the two sets.
     * Null elements are permitted in the specified collection.
     *
     * @param c collection containing elements to be retained in this set
     * @return {@code true} if this set changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    @Override
    public boolean retainAll(final Collection<?> c) {
        if (c == null)
            throw new NullPointerException();
        return set.keySet().retainAll(c);
    }

    /**
     * Returns the number of elements in this set (its cardinality).
     *
     * @return the number of elements in this set (its cardinality)
     */
    @Override
    public int size() {
        return set.size();
    }

    /**
     * Returns a {@link Spliterator} over the elements in this set.
     * The elements are returned in no particular order.
     *
     * @return a {@code Spliterator} over the elements in this set
     */
    @Override
    public Spliterator<E> spliterator() {
        return set.keySet().spliterator();
    }

    /**
     * Returns an array containing all the elements in this set.
     * The returned array will be "safe" in that no references to it are
     * maintained by this set.
     *
     * @return an array containing all the elements in this set
     */
    @Override
    public Object[] toArray() {
        return set.keySet().toArray();
    }

    /**
     * Returns an array containing all the elements in this set; the
     * runtime type of the returned array is that of the specified array.
     * If the set fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the
     * specified array and the size of this set.
     *
     * @param a the array into which the elements of this set are to be stored, if it is big enough;
     *          otherwise, a new array of the same runtime type is allocated
     * @return an array containing all the elements in this set
     * @throws ArrayStoreException if the runtime type of the specified array is not a supertype
     *         of the runtime type of every element in this set
     * @throws NullPointerException if the specified array is null
     */
    @Override
    public <T> T[] toArray(T[] a) {
        return set.keySet().toArray(a);
    }

    /**
     * Returns String representation of CustomSet
     *
     * @return String representation of CustomSet
     */
    public String toString() {
        return set.keySet().toString();
    }

}