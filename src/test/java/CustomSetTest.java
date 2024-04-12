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
    public void onCreatingSetWithSizeOf_50_andLoadFactorOf_50_onAdding_25Numbers_increasesSetSizeTo_51() {
        CustomSet<Integer> customSet = new CustomSet<>(50, 0.5);
        Random random = new Random();
        for (int i = 0; i < 27; i++)
            customSet.add(random.nextInt());
        assertEquals(53, customSet.getSetSize());
    }

    @Test
    public void onCreatingSetWithSizeOf_50_andLoadFactorOf_50_onAdding_50Numbers_increasesSetSizeTo_101() {
        CustomSet<Integer> customSet = new CustomSet<>(50, 0.5);
        Random random = new Random();
        for (int i = 0; i < 50; i++)
            customSet.add(random.nextInt());
        assertEquals(101, customSet.getSetSize());
    }

    @Test
    public void onCreatingSetWithSizeOf_50_returnsSetSizeOf_53() {
        CustomSet<Integer> customSet = new CustomSet<>(50);

        assertEquals(53, customSet.getSetSize());
    }

    @Test
    public void onConstructingSet_returnsSetSizeOf_11() {
        CustomSet<Integer> customSet = new CustomSet<>();

        assertEquals(11, customSet.getSetSize());
        assertTrue(customSet.isEmpty());
    }

    @Test
    public void onConstructingSet_withCollectionOfFiveItems_returnsSetSizeOf_10_andSizeOf_5() {
        Collection<Integer> collection = IntStream.iterate(10, i -> i <= 50, i -> i + 10).boxed().collect(Collectors.toList());

        CustomSet<Integer> customSet = new CustomSet<>(collection);

        assertEquals(5, customSet.size());
        assertEquals(11, customSet.getSetSize());
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
    public void onConstructingSet_withSizeOf_5_returnsSetSizeOf_10() {
        CustomSet<Integer> customSet = new CustomSet<>(5);

        assertEquals(11, customSet.getSetSize());
    }

    @Test
    public void onConstructingSet_withCollectionOfNull_throws_NullPointerException() {
        assertThrows(NullPointerException.class,
                ()-> new CustomSet<>(null));
    }

    @Test
    public void onConstructingSetWithZeroItems_on_isEmpty_returnsTrue() {
        CustomSet<Integer> customSet = new CustomSet<>();

        assertTrue(customSet.isEmpty());
    }

    @Test
    public void onConstructingSetWithOneItem_on_isEmpty_returnsFalse() {
        CustomSet<Integer> customSet = new CustomSet<>();

        assertTrue(customSet.add(10));
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
        assertEquals(11, customSet.getSetSize());
    }

    @Test
    public void onAddingToSet_twoSameValues_andOneUnique_returns_sizeOf_2() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(1);
        customSet.add(1);
        customSet.add(2);

        assertEquals(2, customSet.size());
        assertEquals(11, customSet.getSetSize());
    }

    @Test
    public void onAddingToSet_twoValuesOf_10_20_onRemove_30_returns_false() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);

        assertEquals(2, customSet.size());
        assertFalse(customSet.remove(30));
        assertEquals(11, customSet.getSetSize());
    }

    @Test
    public void onAddingToSet_twoValuesOf_10_20_onRemove_null_returns_false() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);

        assertEquals(2, customSet.size());
        assertFalse(customSet.remove(null));
        assertEquals(2, customSet.size());
        assertEquals(11, customSet.getSetSize());
    }

    @Test
    public void onAddingToSet_twoValuesOf_10_20_onRemove_10_returns_true() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);

        assertEquals(2, customSet.size());
        assertTrue(customSet.remove(10));
        assertEquals(1, customSet.size());
        assertEquals(11, customSet.getSetSize());
    }

    @Test
    public void onAddingToSet_50_items_andClearingSet_returns_newSet() {
        CustomSet<Integer> customSet = createDynamicSet(50);

        assertEquals(50, customSet.size());

        customSet.clear();

        assertEquals(0, customSet.size());
        assertEquals(11, customSet.getSetSize());
    }

    @Test
    public void onCreatingSetWithSizeOf_5_returnsSetSizeOf_11() {
        CustomSet<Integer> customSet = new CustomSet<>(5);

        assertEquals(11, customSet.getSetSize());
    }

    private static CustomSet<Integer> createDynamicSet(int x) {
        CustomSet<Integer> customSet = new CustomSet<>();
        Random random = new Random();
        for (int i = 0; i < x; i++) customSet.add(random.nextInt());
        return customSet;
    }
}