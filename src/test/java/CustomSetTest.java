import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomSetTest {

    @Test
    public void onCreatingSetWitNegativeSize_and_loadFactorOf_50_throws_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                ()-> new CustomSet<>(-10, 0.5));
    }

    @Test
    public void onCreatingSetWithNegativeSize_and_negativeLoadFactor_throws_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                ()-> new CustomSet<>(-10, -10));
    }

    @Test
    public void onCreatingSetWithSizeOf_50_and_negativeLoadFactor_throws_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                ()-> new CustomSet<>(50, -10));
    }

    @Test
    public void onCreatingSetWithSizeOf_50_andLoadFactorOf_50_onAdding_25Numbers_increasesSetSizeTo_53() {
        CustomSet<Integer> customSet = new CustomSet<>(50, 0.5);
        Random random = new Random();
        for (int i = 0; i < 27; i++)
            customSet.add(random.nextInt());
        assertEquals(59, customSet.getSetSize());
    }

    @Test
    public void onCreatingSetWithSizeOf_50_andLoadFactorOf_50_onAdding_50Numbers_increasesSetSizeTo_101() {
        CustomSet<Integer> customSet = new CustomSet<>(50, 0.5);
        Random random = new Random();
        for (int i = 0; i < 50; i++)
            customSet.add(random.nextInt());
        assertEquals(107, customSet.getSetSize());
    }

    @Test
    public void onCreatingSetWithSizeOf_50_returnsSetSizeOf_53() {
        CustomSet<Integer> customSet = new CustomSet<>(50);

        assertEquals(59, customSet.getSetSize());
    }

    @Test
    public void onConstructingSet_returnsEmptySet() {
        CustomSet<Integer> customSet = new CustomSet<>();
        assertTrue(customSet.isEmpty());
        assertEquals(0, customSet.size());
    }

    @Test
    public void onConstructingSet_withCollectionOfFiveItems_returnsSetSizeOf_10_andSizeOf_5() {
        Collection<Integer> collection = IntStream.iterate(10, i -> i <= 50, i -> i + 10).boxed().collect(Collectors.toList());

        CustomSet<Integer> customSet = new CustomSet<>(collection);

        assertEquals(5, customSet.size());
        assertTrue(customSet.contains(10));
        assertTrue(customSet.contains(20));
        assertTrue(customSet.contains(30));
        assertTrue(customSet.contains(40));
        assertTrue(customSet.contains(50));
        assertFalse(customSet.contains(100));
    }

    @Test
    public void onConstructingSet_withSizeLessThan_0_throws_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                ()-> new CustomSet<>(-1));
    }

    @Test
    public void onConstructingSet_withCollectionOfNull_throws_NullPointerException() {
        assertThrows(NullPointerException.class,
                ()-> new CustomSet<>(null));
    }

    @Test
    public void onConstructingSetWithZeroItems_on_isEmpty_returnsTrue() {
        assertTrue(new CustomSet<>().isEmpty());
    }

    @Test
    public void onConstructingSetWithOneItem_on_isEmpty_returnsFalse() {
        CustomSet<Integer> customSet = new CustomSet<>();

        assertTrue(customSet.add(10));
        assertTrue(customSet.contains(10));
        assertFalse(customSet.isEmpty());
    }

    @Test
    public void onAddingToSet_10_returns_true_and_sizeOf_1() {
        CustomSet<Integer> customSet = new CustomSet<>();

        assertTrue(customSet.add(10));
        assertEquals(1, customSet.size());
    }

    @Test
    public void onAddingToSet_10_20_returns_true_and_sizeOf_2() {
        CustomSet<Integer> customSet = createDynamicSet(2);

        assertEquals(2, customSet.size());
    }

    @Test
    public void onAddingToSet_10_items_returns_true_andSizeOf_10() {
        CustomSet<Integer> customSet = createDynamicSet(10);

        assertEquals(10, customSet.size());
    }

    @Test
    public void onAddingToSet_twoIdenticalNumbersToSet_onlyAddsOne() {
        CustomSet<Integer> customSet = new CustomSet<>();

        assertTrue(customSet.add(10));
        assertEquals(1, customSet.size());
        assertFalse(customSet.add(10));
        assertEquals(1, customSet.size());
    }

    @Test
    public void onAddingToSet_50_items_returns_true_andSizeOf_50_andSetSizeOf_40() {
        CustomSet<Integer> customSet = createDynamicSet(50);

        assertEquals(50, customSet.size());
    }

    @Test
    public void onAddingToSet_twoSameValues_returns_sizeOf_1() {
        CustomSet<Integer> customSet = new CustomSet<>();

        assertTrue(customSet.add(1));
        assertFalse(customSet.add(1));
        assertEquals(1, customSet.size());
    }

    @Test
    public void onAddingToSet_twoSameValues_andOneUnique_returns_sizeOf_2() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(1);
        customSet.add(1);
        customSet.add(2);

        assertTrue(customSet.contains(1));
        assertTrue(customSet.contains(2));
        assertEquals(2, customSet.size());
    }

    @Test
    public void onAddingToSet_twoValuesOf_10_20_onRemove_30_returns_false() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);

        assertEquals(2, customSet.size());
        assertFalse(customSet.remove(30));
    }

    @Test
    public void onAddingToSet_twoValuesOf_10_20_onRemove_null_returns_false() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);

        assertEquals(2, customSet.size());
        assertFalse(customSet.remove(null));
        assertEquals(2, customSet.size());
    }

    @Test
    public void onAddingToSet_twoValuesOf_10_20_onRemove_10_returns_true() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);

        assertEquals(2, customSet.size());
        assertTrue(customSet.remove(10));
        assertEquals(1, customSet.size());
    }

    @Test
    public void onAddingToSet_50_items_andClearingSet_returns_newSet() {
        CustomSet<Integer> customSet = createDynamicSet(50);

        assertEquals(50, customSet.size());

        customSet.clear();

        assertEquals(0, customSet.size());
    }

    @Test
    public void addLargeRangeOfNumbers_scalesAndIndexesCorrectlyOnHashCode() {
        CustomSet<Integer> customSet = new CustomSet<>();

        for(int i = 0; i < 1000; i++)
            customSet.add(i);

        assertFalse(customSet.contains(1001));
        assertFalse(customSet.contains(-1001));
        assertTrue(customSet.contains(999));
        assertTrue(customSet.contains(556));
        assertTrue(customSet.contains(333));
    }

    @Test
    public void addLargeRangeOfNumbers_scalesAndIndexesCorrectlyOnHashCode_andReducesCorrectly() {
        CustomSet<Integer> customSet = new CustomSet<>();

        for(int i = 0; i < 100; i++)
            customSet.add(i);

        assertFalse(customSet.contains(101));
        assertTrue(customSet.contains(99));
        assertTrue(customSet.contains(55));
        assertTrue(customSet.contains(33));

        for(int i = 0; i < 50; i++)
            customSet.remove(i);

        assertFalse(customSet.contains(10));
        assertFalse(customSet.contains(11));
        assertFalse(customSet.contains(22));
        assertFalse(customSet.contains(33));
        assertTrue(customSet.contains(55));
        assertTrue(customSet.contains(95));
        assertTrue(customSet.contains(60));
    }

    @Test
    public void addEvenLargerRangeOfNumbers_scalesAndIndexesCorrectlyOnHashCode_andReducesCorrectly() {
        CustomSet<Integer> customSet = new CustomSet<>();

        for(int i = 0; i < 1000; i++)
            customSet.add(i);

        assertTrue(customSet.contains(101));
        assertTrue(customSet.contains(99));
        assertTrue(customSet.contains(55));
        assertTrue(customSet.contains(33));

        for(int i = 0; i < 250; i++)
            customSet.remove(i);

        assertFalse(customSet.contains(10));
        assertFalse(customSet.contains(11));
        assertFalse(customSet.contains(22));
        assertFalse(customSet.contains(33));
        assertTrue(customSet.contains(290));
        assertTrue(customSet.contains(950));
        assertTrue(customSet.contains(600));
    }

    private static CustomSet<Integer> createDynamicSet(int x) {
        CustomSet<Integer> customSet = new CustomSet<>();
        Random random = new Random();
        for (int i = 0; i < x; i++)
            customSet.add(random.nextInt());
        return customSet;
    }
}