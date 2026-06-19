import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class CustomSetPerformanceTest {

    private static volatile Object blackHole;
    private static final int BATCH_RUNS = 10000;

    public static void main(String[] args) {
        int[] sizes = {16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536 };
        ArrayList<long[]> results = new ArrayList<>();
        Random random = new Random();
        System.out.println("Warming up JVM to trigger JIT compilation...");
        for (int i = 0; i < 500; i++) {
            CustomSet<Integer> warmUpSet = new CustomSet<>();
            for (int j = 0; j < 100; j++) warmUpSet.add(j);
            blackHole = warmUpSet.contains(50);
            blackHole = warmUpSet.toString();
        }
        for (int size : sizes) {
            System.out.println("Profiling size: " + size);
            Collection<Integer> collection = generateCollection(size, random);
            CustomSet<Integer> set = new CustomSet<>();
            set.addAll(collection);
            long addTime = benchmarkAdd(size, random);
            long addAllTime = benchmarkAddAll(collection);
            long clearTime = benchmarkClear(collection);
            long containsTime = benchmarkContains(set, size, random);
            long containsAllTime = benchmarkContainsAll(set, collection);
            long isEmptyTime = benchmarkIsEmpty(set);
            long removeTime = benchmarkRemove(set, size, random);
            long removeAllTime = benchmarkRemoveAll(collection);
            long retainAllTime = benchmarkRetainAll(collection);
            long sizeTime = benchmarkSize(set);
            long toArrayTime = benchmarkToArray(set);
            long toStringTime = benchmarkToString(set);
            results.add(new long[]{size, addTime, addAllTime, clearTime, containsTime,
                    containsAllTime, isEmptyTime, removeTime, removeAllTime,
                    retainAllTime, sizeTime, toArrayTime, toStringTime});
        }

        try (FileWriter writer = new FileWriter("V2_Hash_2_performance_data.csv")) {
            writer.write("Size,AddTime,AddAllTime,ClearTime,ContainsTime,ContainsAllTime," +
                    "IsEmptyTime,RemoveTime,RemoveAllTime,RetainAllTime,SizeTime," +
                    "ToArrayTime,ToStringTime\n");
            for (long[] result : results) {
                writer.write(String.format("%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d\n",
                        result[0], result[1], result[2], result[3], result[4], result[5],
                        result[6], result[7], result[8], result[9], result[10], result[11],
                        result[12]));
            }
            System.out.println("Accurate performance diagnostics exported successfully.");
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }

    private static long benchmarkAdd(int size, Random random) {
        int[] items = new int[size];
        for (int i = 0; i < size; i++)
            items[i] = random.nextInt();
        long start = System.nanoTime();
        CustomSet<Integer> set = new CustomSet<>();
        for (int i = 0; i < size; i++)
            set.add(items[i]);
        long duration = System.nanoTime() - start;
        blackHole = set;
        return duration;
    }

    private static long benchmarkAddAll(Collection<Integer> collection) {
        long start = System.nanoTime();
        CustomSet<Integer> set = new CustomSet<>();
        set.addAll(collection);
        long duration = System.nanoTime() - start;
        blackHole = set;
        return duration;
    }

    private static long benchmarkClear(Collection<Integer> initialData) {
        CustomSet<Integer> set = new CustomSet<>(initialData);
        long start = System.nanoTime();
        set.clear();
        long duration = System.nanoTime() - start;
        blackHole = set;
        return duration;
    }

    private static long benchmarkContains(CustomSet<Integer> set, int size, Random random) {
        int[] queries = new int[BATCH_RUNS];
        for (int i = 0; i < BATCH_RUNS; i++)
            queries[i] = random.nextInt(size * 2);
        boolean checksum = false;
        long start = System.nanoTime();
        for (int i = 0; i < BATCH_RUNS; i++)
            checksum ^= set.contains(queries[i]);
        long duration = System.nanoTime() - start;
        blackHole = checksum;
        return duration / BATCH_RUNS;
    }

    private static long benchmarkContainsAll(CustomSet<Integer> set, Collection<Integer> collection) {
        long start = System.nanoTime();
        boolean res = set.containsAll(collection);
        long duration = System.nanoTime() - start;
        blackHole = res;
        return duration;
    }

    private static long benchmarkIsEmpty(CustomSet<Integer> set) {
        boolean res = false;
        long start = System.nanoTime();
        for (int i = 0; i < BATCH_RUNS; i++)
            res ^= set.isEmpty();
        long duration = System.nanoTime() - start;
        blackHole = res;
        return duration / BATCH_RUNS;
    }

    private static long benchmarkRemove(CustomSet<Integer> set, int size, Random random) {
        int[] targets = new int[BATCH_RUNS];
        for (int i = 0; i < BATCH_RUNS; i++) targets[i] = random.nextInt(size * 2);
        CustomSet<Integer> workingCopy = new CustomSet<>(set);
        boolean res = false;
        long start = System.nanoTime();
        for (int i = 0; i < BATCH_RUNS; i++)
            res ^= workingCopy.remove(targets[i]);
        long duration = System.nanoTime() - start;
        blackHole = res;
        return duration / BATCH_RUNS;
    }

    private static long benchmarkRemoveAll(Collection<Integer> collection) {
        CustomSet<Integer> workingCopy = new CustomSet<>(collection);
        long start = System.nanoTime();
        workingCopy.removeAll(collection);
        long duration = System.nanoTime() - start;
        blackHole = workingCopy;
        return duration;
    }

    private static long benchmarkRetainAll(Collection<Integer> collection) {
        CustomSet<Integer> workingCopy = new CustomSet<>(collection);
        long start = System.nanoTime();
        workingCopy.retainAll(collection);
        long duration = System.nanoTime() - start;
        blackHole = workingCopy;
        return duration;
    }

    private static long benchmarkSize(CustomSet<Integer> set) {
        int finalSize = 0;
        long start = System.nanoTime();
        for (int i = 0; i < BATCH_RUNS; i++)
            finalSize += set.size();
        long duration = System.nanoTime() - start;
        blackHole = finalSize;
        return duration / BATCH_RUNS;
    }

    private static long benchmarkToArray(CustomSet<Integer> set) {
        long start = System.nanoTime();
        Object[] array = set.toArray();
        long duration = System.nanoTime() - start;
        blackHole = array;
        return duration;
    }

    private static long benchmarkToString(CustomSet<Integer> set) {
        long start = System.nanoTime();
        String str = set.toString();
        long duration = System.nanoTime() - start;
        blackHole = str;
        return duration;
    }

    private static Collection<Integer> generateCollection(int size, Random random) {
        Collection<Integer> collection = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
            collection.add(random.nextInt());
        return collection;
    }
}