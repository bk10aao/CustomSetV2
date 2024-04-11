import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.IntStream;

@SuppressWarnings("unchecked")
public class CustomSet<T> implements Cloneable, SetInterface<T> {

    public static double LOAD_FACTOR = 0.75;
    private int setSize = 11;
    private int size = 0;
    private int MOD_VALUE = getLargestPrime();

    private LinkedList<Object>[] set;

    public CustomSet() {
        set = new LinkedList[setSize];
    }

    public CustomSet(Collection<T> c) {
        if(c == null) throw new NullPointerException();
        set = new LinkedList[setSize];
        for(T item : c) add(item);
    }

    public CustomSet(int initialCapacity) {
        if(initialCapacity < 0) throw new IllegalArgumentException();
        if(!isPrime(initialCapacity)) initialCapacity = nextPrime(initialCapacity);
        if(initialCapacity > setSize) {
            setSize = initialCapacity;
            MOD_VALUE = getLargestPrime();
        }
        set = new LinkedList[setSize];

    }

    public CustomSet(int initialCapacity, double loadFactor) {
        if(initialCapacity < 0) throw new IllegalArgumentException();
        if(loadFactor < 0) throw new IllegalArgumentException();
        if(initialCapacity > setSize) setSize = initialCapacity;
        set = new LinkedList[setSize];
        LOAD_FACTOR = loadFactor;
    }

    public boolean add(Object item) {
        if(!contains(item)) {
            int index = Math.abs(item.hashCode()) % MOD_VALUE;
            if (set[index] == null) {
                set[index] = new LinkedList<>();
                set[index].add(item);
            } else set[index].add(item);
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
        MOD_VALUE = getLargestPrime();
        set = new LinkedList[setSize];
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean contains(Object item) {
        return Arrays.stream(set).filter(Objects::nonNull).anyMatch(l -> IntStream.range(0, l.size()).anyMatch(i -> l.get(i).equals(item)));
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
            for (LinkedList<Object> checkSet : set) {
                if (checkSet == null) continue;
                for (int x = 0; x < checkSet.size(); x++) {
                    if (checkSet.get(x) == item) {
                        checkSet.set(x, null);
                        size--;
                        if(setSize > 11 && size <= setSize / 4) reduce();
                        return true;
                    }
                }
            }
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
        setSize = nextPrime(setSize);
        LinkedList<Object>[] temp = new LinkedList[setSize];
        System.arraycopy(set, 0, temp, 0, set.length);
        set = temp;
        MOD_VALUE = getLargestPrime();
    }

    private int getLargestPrime() {
        for (int i = setSize - 1; i >= 2; i--)
            if (isPrime(i)) return i;
        return -1;
    }

    private static boolean isPrime(int number) {
        for (int i = 2; i <= Math.sqrt(number); i++)
            if (number % i == 0) return false;
        return true;
    }

    private static int nextPrime(int num) {
        num++;
        while(!isPrime(num)) num++;
        return num;
    }

    private int previousPrime(int num) {
        if(num <= 11) return 11;
        while(!isPrime(num)) num--;
        return num;
    }

    private void reduce() {
        if(setSize <= 10) return;
        setSize = previousPrime(setSize);
        LinkedList<Object>[] reducedSet = new LinkedList[setSize];
        int insertIndex = 0;
        for (LinkedList<Object> list : set) if (list != null) reducedSet[insertIndex++] = list;
        set = reducedSet;
        MOD_VALUE = getLargestPrime();
    }
}