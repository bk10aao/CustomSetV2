import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.hash;


@SuppressWarnings("unchecked")
public class CustomSet<T> implements SetInterface<T> {

    private int size = 0;

    private Map<Integer, LinkedList<T>> set = new HashMap<>(primes[0], 0.75f);

    public CustomSet() {
        this.set = new HashMap<>(primes[0], 0.75f);
    }

    public CustomSet(final Collection<T> c) {
        if(c == null)
            throw new NullPointerException();
        set = new HashMap<>(getNextPrime(c.size()), 0.75f);
        addAll(c);
    }

    public CustomSet(final int initialCapacity) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException();
        new HashMap<>(getNextPrime(initialCapacity), 0.75f);
    }

    public CustomSet(final int initialCapacity, final float loadFactor) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException();
        if(loadFactor < 0 || loadFactor > 1)
            throw new IllegalArgumentException();
        set = new HashMap<>(getNextPrime(initialCapacity), loadFactor);
    }

    public boolean add(T t) {
        if(t == null)
            throw new NullPointerException();
        LinkedList<T> previous = set.get(hash(t));
        if(previous != null && !previous.contains(t)) {
            previous.add(t);
            set.put(hash(t), previous);
            size++;
            return true;
        } else if(previous == null){
            previous = new LinkedList<>();
            previous.add(t);
            set.put(hash(t), previous);
            size++;
            return true;
        }
        return false;
    }
    public boolean addAll(final Collection<T> c) {
        int n = this.size;
        c.forEach(this::add);
        return size != n;
    }

    public void clear() {
        set = new HashMap<>(primes[0], 0.75f);
        size = 0;
    }

    public boolean contains(final T i) {
        if(i == null)
            throw new NullPointerException();
        return set.values().stream().filter(Objects::nonNull).anyMatch(l -> l.contains(i));
    }

    public boolean containsAll(final Collection<T> c) {
        return c.stream().allMatch(this::contains);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean remove(final T item) {
        if(item == null)
            throw new NullPointerException();
        if(set.remove(hash(item)) != null) {
            size--;
            return true;
        }
        return false;
    }

    public boolean removeAll(final Collection<T> c) {
        int n = size;
        c.forEach(this::remove);
        return n != size;
    }

    public boolean retainAll(final Collection<T> c) {
        if(c.isEmpty())
            return true;
        if(c.contains(null))
            throw new NullPointerException();
        CustomSet<T> temp = retain(c);
        if(temp.size() > 0) {
            set = temp.set;
            size = temp.size;
            return true;
        }
        return false;
    }

    private CustomSet<T> retain(final Collection<T> c) {
        CustomSet<T> temp = new CustomSet<>();
        for(T value : c) {
            LinkedList<T> v = set.get(hash(value));
            if(v != null)
                if (set.get(hash(value)).contains(value))
                    temp.add(value);
        }
        return temp;
    }

    public int size() {
        return size;
    }

    public T[] toArray() {
        Object[] arr = new Object[size];
        int insertIndex = 0;
        for(LinkedList<T> l : set.values())
            for (T t : l)
                arr[insertIndex++] = t;
        Arrays.sort(arr);
        return (T[])arr;
    }

    @Override
    public String toString() {
        if(size == 0)
            return "{ }";
        StringBuilder sb = new StringBuilder("{ ");
        set.values().forEach(values -> values.forEach(value -> sb.append(value).append(", ")));
        return sb.substring(0, sb.length() - 2) + " }";
    }

    private int getNextPrime(final int initialCapacity) {
        int primesIndex = 0;
        if(initialCapacity > primes[primesIndex])
            for (int i = primesIndex + 1; i < primes.length; i++)
                if (primes[i] > initialCapacity)
                    return primes[i];
        return 0;
    }

    protected static final int[] primes = { 3, 7, 11, 17, 23, 29, 37, 47, 59, 71, 89, 107, 131, 163, 197, 239, 293, 353, 431, 521, 631, 761, 919,
                                            1103, 1327, 1597, 1931, 2333, 2801, 3371, 4049, 4861, 5839, 7013, 8419, 10103, 12143, 14591,
                                            17519, 21023, 25229, 30293, 36353, 43627, 52361, 62851, 75431, 90523, 108631, 130363, 156437,
                                            187751, 225307, 270371, 324449, 389357, 467237, 560689, 672827, 807403, 968897, 1162687, 1395263,
                                            1674319, 2009191, 2411033, 2893249, 3471899, 4166287, 4999559, 5999471, 7199369 };
}