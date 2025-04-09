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
        int[] sizes = { 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000, 2100, 2200, 2300, 2400, 2500, 2600, 2700, 2800, 2900, 3000, 3100, 3200, 3300, 3400, 3500, 3600, 3700, 3800, 3900, 4000, 4100, 4200, 4300, 4400, 4500, 4600, 4700, 4800, 4900, 5000, 5100, 5200, 5300, 5400, 5500, 5600, 5700, 5800, 5900, 6000, 6100, 6200, 6300, 6400, 6500, 6600, 6700, 6800, 6900, 7000, 7100, 7200, 7300, 7400, 7500, 7600, 7700, 7800, 7900, 8000, 8100, 8200, 8300, 8400, 8500, 8600, 8700, 8800, 8900, 9000, 9100, 9200, 9300, 9400, 9500, 9600, 9700, 9800, 9900, 10000, 10100, 10200, 10300, 10400, 10500, 10600, 10700, 10800, 10900, 11000, 11100, 11200, 11300, 11400, 11500, 11600, 11700, 11800, 11900, 12000, 12100, 12200, 12300, 12400, 12500, 12600, 12700, 12800, 12900, 13000, 13100, 13200, 13300, 13400, 13500, 13600, 13700, 13800, 13900, 14000, 14100, 14200, 14300, 14400, 14500, 14600, 14700, 14800, 14900, 15000, 15100, 15200, 15300, 15400, 15500, 15600, 15700, 15800, 15900, 16000, 16100, 16200, 16300, 16400, 16500, 16600, 16700, 16800, 16900, 17000, 17100, 17200, 17300, 17400, 17500, 17600, 17700, 17800, 17900, 18000, 18100, 18200, 18300, 18400, 18500, 18600, 18700, 18800, 18900, 19000, 19100, 19200, 19300, 19400, 19500, 19600, 19700, 19800, 19900, 20000
        };

        // Store results
        ArrayList<long[]> results = new ArrayList<>();
        Random random = new Random();

        for (int size : sizes) {
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
        long start = System.nanoTime();
        for (int i = 0; i < size; i++) {
            set.add(random.nextInt());
        }
        return System.nanoTime() - start;
    }

    private static long benchmarkAddAll(Set<Integer> set, Collection<Integer> collection) {
        long start = System.nanoTime();
        set.addAll(collection);
        return System.nanoTime() - start;
    }

    private static long benchmarkClear(Set<Integer> set) {
        long start = System.nanoTime();
        set.clear();
        return System.nanoTime() - start;
    }

    private static long benchmarkContains(Set<Integer> set, Random random) {
        int item = random.nextInt();
        long start = System.nanoTime();
        set.contains(item);
        return System.nanoTime() - start;
    }

    private static long benchmarkContainsAll(Set<Integer> set, Collection<Integer> collection) {
        long start = System.nanoTime();
        set.containsAll(collection);
        return System.nanoTime() - start;
    }

    private static long benchmarkIsEmpty(Set<Integer> set) {
        long start = System.nanoTime();
        set.isEmpty();
        return System.nanoTime() - start;
    }

    private static long benchmarkRemove(Set<Integer> set, Random random) {
        int item = random.nextInt();
        long start = System.nanoTime();
        set.remove(item);
        return System.nanoTime() - start;
    }

    private static long benchmarkRemoveAll(Set<Integer> set, Collection<Integer> collection) {
        long start = System.nanoTime();
        set.removeAll(collection);
        return System.nanoTime() - start;
    }

    private static long benchmarkRetainAll(Set<Integer> set, Collection<Integer> collection) {
        long start = System.nanoTime();
        set.retainAll(collection);
        return System.nanoTime() - start;
    }

    private static long benchmarkSize(Set<Integer> set) {
        long start = System.nanoTime();
        set.size();
        return System.nanoTime() - start;
    }

    private static long benchmarkToArray(Set<Integer> set) {
        long start = System.nanoTime();
        set.toArray();
        return System.nanoTime() - start;
    }

    private static long benchmarkToString(Set<Integer> set) {
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