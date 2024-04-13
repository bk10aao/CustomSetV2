import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class CustomSet<T> implements Cloneable, SetInterface<T> {

    private static double LOAD_FACTOR = 0.75;
    private int primesIndex = 0;
    private int size = 0;
    private int setSize = 3;

    private LinkedList<Object>[] set;

    public CustomSet() {
        set = new LinkedList[setSize];
    }

    public CustomSet(Collection<T> c) {
        if(c == null)
            throw new NullPointerException();
        set = new LinkedList[primes[0]];
        for(T item : c) add(item);
    }

    public CustomSet(int initialCapacity) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException();
        generateSet(initialCapacity);
    }

    public CustomSet(int initialCapacity, double loadFactor) {
        if(initialCapacity < 0)
            throw new IllegalArgumentException();
        if(loadFactor < 0 || loadFactor > 1)
            throw new IllegalArgumentException();
        generateSet(initialCapacity);
        LOAD_FACTOR = loadFactor;
    }

    public boolean add(Object item) {
        if(!contains(item)) {
            int index = Math.abs(item.hashCode()) % setSize;
            if (set[index] == null) {
                set[index] = new LinkedList<>();
                set[index].add(item);
            } else
                set[index].add(item);
            size++;
            if((double)size / (double)setSize > LOAD_FACTOR)
                expand();
            return true;
        }
        return false;
    }

    public void clear() {
        setSize = 11;
        size = 0;
        primesIndex = 0;
        set = new LinkedList[setSize];
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean contains(Object item) {
        int index = Math.abs(item.hashCode()) % setSize;
        if(set[index] != null)
            return set[index].contains(item);
        return false;
    }

    //THIS IS REALLY JUST USED TO TEST LOAD FACTOR
    public int getSetSize() {
        return setSize;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean remove(Object item) {
        if(item == null) return false;
        if(contains(item)) {
            int index = Math.abs(item.hashCode()) % setSize;
            set[index].remove(item);
            size--;
            if(setSize > 11 && size <= setSize / 4)
                reduce();
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public String toString() {
        return Arrays.toString(set);
    }

    private void expand() {
        setSize = primes[++primesIndex];
        LinkedList<Object>[] newSet = new LinkedList[setSize];
        for (LinkedList<Object> objects : set)
            if (objects != null)
                for (Object item : objects) {
                    int index = Math.abs(item.hashCode()) % setSize;
                    if (newSet[index] == null)
                        newSet[index] = new LinkedList<>();
                    newSet[index].add(item);
                }
        set = newSet;
    }

    private void reduce() {
        primesIndex = (primesIndex / 2) + 1;
        setSize = primes[primesIndex];
        LinkedList<Object>[] newSet = new LinkedList[setSize];
        for(int i = 0; i < set.length; i++)
            if (set[i] != null)
                for (Object item : set[i]) {
                    int index = Math.abs(item.hashCode()) % setSize;
                    if (newSet[index] == null)
                        newSet[index] = new LinkedList<>();
                    newSet[i].add(item);
                }
        set = newSet;
    }

    private void generateSet(int initialCapacity) {
        if(initialCapacity > primes[primesIndex])
            for (int i = primesIndex + 1; i < primes.length; i++)
                if (primes[i] > initialCapacity) {
                    setSize = primes[i];
                    primesIndex = i - 1;
                    break;
                }
        set = new LinkedList[setSize];
    }

    protected static final int[] primes = { 3, 7, 11, 17, 23, 29, 37, 47, 59, 71, 89, 107, 131, 163, 197, 239, 293, 353, 431, 521, 631, 761, 919,
                                            1103, 1327, 1597, 1931, 2333, 2801, 3371, 4049, 4861, 5839, 7013, 8419, 10103, 12143, 14591,
                                            17519, 21023, 25229, 30293, 36353, 43627, 52361, 62851, 75431, 90523, 108631, 130363, 156437,
                                            187751, 225307, 270371, 324449, 389357, 467237, 560689, 672827, 807403, 968897, 1162687, 1395263,
                                            1674319, 2009191, 2411033, 2893249, 3471899, 4166287, 4999559, 5999471, 7199369 };
}