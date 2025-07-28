import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("unchecked")
public class CustomSet<E> implements SetInterface<E> {

    private Map<Integer, E> set;

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

    public boolean add(final E t) {
        if(t == null)
            throw new NullPointerException();
        return set.put(hashObject(t), t) == null;
    }

    public boolean addAll(final Collection<E> c) {
        int n = set.size();
        c.forEach(this::add);
        return set.size() != n;
    }

    public void clear() {
        set = new HashMap<>(primes[0], 0.75f);
    }

    public boolean contains(final E i) {
        if(i == null)
            throw new NullPointerException();
        return set.get(hashObject(i)) != null;
    }

    public boolean containsAll(final Collection<E> c) {
        return c.stream().allMatch(this::contains);
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public boolean remove(final E item) {
        if(item == null)
            throw new NullPointerException();
        return set.remove(hashObject(item)) != null;
    }

    public boolean removeAll(final Collection<E> c) {
        int n = set.size();
        c.forEach(this::remove);
        return n != set.size();
    }

    public boolean retainAll(final Collection<E> c) {
        if(c.isEmpty())
            return true;
        if(c.contains(null))
            throw new NullPointerException();
        CustomSet<E> temp = retain(c);
        if(temp.size() > 0) {
            set = temp.set;
            return true;
        }
        return false;
    }

    public int size() {
        return set.size();
    }

    public E[] toArray() {
        return set.values().toArray((E[]) new Object[0]);
    }

    @Override
    public String toString() {
        if(set.isEmpty())
            return "{ }";
        StringBuilder sb = new StringBuilder("{ ");
        for(E t : set.values())
            sb.append(t).append(", ");
        return sb.substring(0, sb.length() - 2) + " }";
    }

    private int getNextPrime(final int initialCapacity) {
        if (initialCapacity <= 0)
            return primes[0];
        for (int prime : primes)
            if (prime >= initialCapacity)
                return prime;
        return primes[primes.length - 1]; // Return largest prime if no match
    }

    private int hashObject(E item) {
        int h;
        return (item == null) ? 0 : (h = item.hashCode()) ^ (h >>> 16);
    }

    private CustomSet<E> retain(final Collection<E> c) {
        CustomSet<E> temp = new CustomSet<>();
        for(E value : c)
            if (set.get(hashObject(value)) != null)
                temp.add(value);
        return temp;
    }

    protected static final int[] primes = { 3, 7, 11, 17, 23, 29, 37, 47, 59, 71, 89, 107, 131, 163, 197, 239, 293, 353, 431, 521, 631, 761, 919,
                                            1103, 1327, 1597, 1931, 2333, 2801, 3371, 4049, 4861, 5839, 7013, 8419, 10103, 12143, 14591,
                                            17519, 21023, 25229, 30293, 36353, 43627, 52361, 62851, 75431, 90523, 108631, 130363, 156437,
                                            187751, 225307, 270371, 324449, 389357, 467237, 560689, 672827, 807403, 968897, 1162687, 1395263,
                                            1674319, 2009191, 2411033, 2893249, 3471899, 4166287, 4999559, 5999471, 7199369 };
}