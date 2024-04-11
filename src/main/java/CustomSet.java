import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.IntStream;

@SuppressWarnings("unchecked")
public class CustomSet<T> implements Cloneable, SetInterface<T> {

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
        if(!isPrime(initialCapacity)) {
            initialCapacity = nextPrime(initialCapacity);
        }
        if(initialCapacity > setSize) {
            setSize = initialCapacity;
            MOD_VALUE = getLargestPrime();
        }
        set = new LinkedList[setSize];

    }

    //TODO: implement and test
    public CustomSet(int initialCapacity, double loadFactor) {
        if(initialCapacity > setSize) setSize = initialCapacity;
        set = new LinkedList[setSize];
    }

    public boolean add(Object item) {
        if(!contains(item)) {
            int index = Math.abs(item.hashCode()) % MOD_VALUE;
            if (set[index] == null) {
                set[index] = new LinkedList<>();
                set[index].add(item);
            } else set[index].add(item);
            size++;
            if(set[index].size() > 3) expand();
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

    public static int nextPrime(int num) {
        num++;
        for (int i = 2; i < num; i++) {
            if(num % i == 0) {
                num++;
                i = 2;
            }
        }
        return num;
    }

    private void reduce() {
        if(setSize <= 10) return;
        LinkedList<Object>[] reducedSet = new LinkedList[setSize / 2];
        int insertIndex = 0;
        for (LinkedList<Object> list : set) if (list != null) reducedSet[insertIndex++] = list;
        setSize = setSize / 2;
        set = reducedSet;
    }
}