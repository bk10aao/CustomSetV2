import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;


@SuppressWarnings("unchecked")
public class CustomSet<E> implements Set<E> {

    private final Map<E, Object> set;
    private static final Object PRESENT = new Object();

    public CustomSet() {
        this.set = new HashMap<>(primes[0], 0.75f);
    }

    public CustomSet(final Collection<E> c) {
        if(c == null)
            throw new NullPointerException();
        set = new HashMap<>(getNextPrime(c.size()), 0.75f);
        addAll(c);
    }

    public CustomSet(final int initialCapacity) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException();
        set = new HashMap<>(getNextPrime(initialCapacity), 0.75f);
    }

    public CustomSet(final int initialCapacity, final float loadFactor) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException();
        if(loadFactor < 0 || loadFactor > 1)
            throw new IllegalArgumentException();
        set = new HashMap<>(getNextPrime(initialCapacity), loadFactor);
    }

    @Override
    public boolean add(final E t) {
        return set.put(t, PRESENT) == null;
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (add(e)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        set.clear();
    }

    @Override
    public boolean contains(final Object o) {
        return set.containsKey(o);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        return c.stream().allMatch(this::contains);
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return set.keySet().iterator();
    }

    @Override
    public boolean remove(final Object o) {
        return set.remove(o) != null;
    }

    @Override
    public boolean 	removeAll(final Collection<?> c) {
        int n = set.size();
        for (Object o : c)
            remove(o);
        return n != set.size();
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        return set.keySet().retainAll(c);
    }

    @Override
    public int size() {
        return set.size();
    }

    @Override
    public Spliterator<E> spliterator() {
        return set.keySet().spliterator();
    }

    @Override
    public Object[] toArray() {
        return set.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return set.keySet().toArray(a);
    }

    @Override
    public String toString() {
        return set.keySet().toString();
    }

    private int getNextPrime(final int initialCapacity) {
        if (initialCapacity <= 0)
            return primes[0];
        for (int prime : primes)
            if (prime >= initialCapacity)
                return prime;
        return primes[primes.length - 1]; // Return largest prime if no match
    }

    protected static final int[] primes = { 3, 7, 11, 17, 23, 29, 37, 47, 59, 71, 89, 107, 131, 163, 197, 239, 293, 353, 431, 521, 631, 761, 919,
                                            1103, 1327, 1597, 1931, 2333, 2801, 3371, 4049, 4861, 5839, 7013, 8419, 10103, 12143, 14591,
                                            17519, 21023, 25229, 30293, 36353, 43627, 52361, 62851, 75431, 90523, 108631, 130363, 156437,
                                            187751, 225307, 270371, 324449, 389357, 467237, 560689, 672827, 807403, 968897, 1162687, 1395263,
                                            1674319, 2009191, 2411033, 2893249, 3471899, 4166287, 4999559, 5999471, 7199369 };
}