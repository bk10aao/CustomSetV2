import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HashSetPerformanceTest {
    public static void main(String[] args) {
        // Input sizes to test (matching previous tests)
        int[] sizes = {1, 10, 50, 100, 250, 500, 750, 1000, 2500, 5000, 7500, 10000,
                25000, 50000, 100000, 250000, 500000, 750000, 1000000 };
        // Store results
        ArrayList<long[]> results = new ArrayList<>();
        Random random = new Random();

        for (int size : sizes) {
            System.out.println(size);
            // Pre-generate data for consistent testing
            Collection<Integer> collection = generateCollection(size, random);
            Set<Integer> set = new HashSet<>();
            set.addAll(collection); // Populate set for methods needing data

            // Benchmark each method
            long addTime = benchmarkAdd(new HashSet<>(), size, random);
            long addAllTime = benchmarkAddAll(new HashSet<>(), collection);
            long clearTime = benchmarkClear(new HashSet<>(collection));
            long containsTime = benchmarkContains(set, random);
            long containsAllTime = benchmarkContainsAll(set, collection);
            long isEmptyTime = benchmarkIsEmpty(set);
            long removeTime = benchmarkRemove(set, random);
            long removeAllTime = benchmarkRemoveAll(set, collection);
            long retainAllTime = benchmarkRetainAll(set, collection);
            long sizeTime = benchmarkSize(set);
            long toArrayTime = benchmarkToArray(set);
            long toStringTime = benchmarkToString(set);

            // Store results (in nanoseconds)
            results.add(new long[]{size, addTime, addAllTime, clearTime, containsTime,
                    containsAllTime, isEmptyTime, removeTime, removeAllTime,
                    retainAllTime, sizeTime, toArrayTime, toStringTime});
        }

        // Write results to CSV
        writeToCSV("hashset_performance_data.csv", results, sizes);

        System.out.println("Performance data written to hashset_performance_data.csv");
    }

    private static long benchmarkAdd(Set<Integer> set, int size, Random random) {
        System.out.println("add");
        long start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            set.add(random.nextInt());
        }

        return System.nanoTime() - start;
    }

    private static long benchmarkAddAll(Set<Integer> set, Collection<Integer> collection) {
        System.out.println("addAll");

        long start = System.nanoTime();
        set.addAll(collection);

        return System.nanoTime() - start;
    }

    private static long benchmarkClear(Set<Integer> set) {
        System.out.println("clear");
        long start = System.nanoTime();
        set.clear();
        return System.nanoTime() - start;
    }

    private static long benchmarkContains(Set<Integer> set, Random random) {
        System.out.println("contains");
        int item = random.nextInt();
        long start = System.nanoTime();
        set.contains(item);
        return System.nanoTime() - start;
    }

    private static long benchmarkContainsAll(Set<Integer> set, Collection<Integer> collection) {
        System.out.println("containsAll");
        long start = System.nanoTime();
        set.containsAll(collection);
        return System.nanoTime() - start;
    }

    private static long benchmarkIsEmpty(Set<Integer> set) {
        System.out.println("isEmpty");
        long start = System.nanoTime();
        set.isEmpty();
        return System.nanoTime() - start;
    }

    private static long benchmarkRemove(Set<Integer> set, Random random) {
        System.out.println("remove");

        int item = random.nextInt();
        long start = System.nanoTime();
        set.remove(item);
        return System.nanoTime() - start;
    }

    private static long benchmarkRemoveAll(Set<Integer> set, Collection<Integer> collection) {
        System.out.println("removeAll");

        long start = System.nanoTime();
        set.removeAll(collection);
        return System.nanoTime() - start;
    }

    private static long benchmarkRetainAll(Set<Integer> set, Collection<Integer> collection) {
        System.out.println("retainAll:" );

        long start = System.nanoTime();
        set.retainAll(collection);
        return System.nanoTime() - start;
    }

    private static long benchmarkSize(Set<Integer> set) {
        System.out.println("size");

        long start = System.nanoTime();
        set.size();
        return System.nanoTime() - start;
    }

    private static long benchmarkToArray(Set<Integer> set) {
        System.out.println("toArray");

        long start = System.nanoTime();
        set.toArray();
        return System.nanoTime() - start;
    }

    private static long benchmarkToString(Set<Integer> set) {
        System.out.println("toString");

        long start = System.nanoTime();
        set.toString();
        return System.nanoTime() - start;
    }

    private static Collection<Integer> generateCollection(int size, Random random) {
        Collection<Integer> collection = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            collection.add(random.nextInt());
        }
        return collection;
    }

    private static void writeToCSV(String filename, ArrayList<long[]> results, int[] sizes) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("Size,AddTime,AddAllTime,ClearTime,ContainsTime,ContainsAllTime," +
                    "IsEmptyTime,RemoveTime,RemoveAllTime,RetainAllTime,SizeTime," +
                    "ToArrayTime,ToStringTime\n");
            for (int i = 0; i < results.size(); i++) {
                long[] result = results.get(i);
                writer.write(String.format("%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d\n",
                        sizes[i], result[1], result[2], result[3], result[4], result[5],
                        result[6], result[7], result[8], result[9], result[10], result[11],
                        result[12]));
            }
        } catch (IOException e) {
            System.err.println("Error writing CSV " + filename + ": " + e.getMessage());
        }
    }
}