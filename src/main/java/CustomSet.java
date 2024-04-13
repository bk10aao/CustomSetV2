import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

@SuppressWarnings("unchecked")
public class CustomSet<T> implements Cloneable, SetInterface<T> {

    public static double LOAD_FACTOR = 0.75;
    private int primesIndex = 0;
    private int size = 0;
    private int setSize = 11;

    private LinkedList<Object>[] set;

    public CustomSet() {
        set = new LinkedList[setSize];
    }

    public CustomSet(Collection<T> c) {
        if(c == null)
            throw new NullPointerException();
        set = new LinkedList[Primes.primes[0]];
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
        if(loadFactor < 0)
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
        setSize = Primes.primes[++primesIndex];
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
        setSize = Primes.primes[primesIndex];
        LinkedList<Object>[] newSet = new LinkedList[setSize];
        for(int i = 0; i < set.length; i++) {
            if(set[i] != null) {
                for(Object item : set[i]) {
                    int index = Math.abs(item.hashCode()) % setSize;
                    if(newSet[index] == null)
                        newSet[index] = new LinkedList<>();
                    newSet[i].add(item);
                }
            }
        }
        set = newSet;
    }

    private void generateSet(int initialCapacity) {
        if(initialCapacity > Primes.primes[primesIndex]) {
            for(int i = primesIndex + 1; i < Primes.primes.length; i++) {
                if(Primes.primes[i] > initialCapacity) {
                    setSize = Primes.primes[i];
                    primesIndex = i - 1;
                    break;
                }
            }
        }
        set = new LinkedList[setSize];
    }
}