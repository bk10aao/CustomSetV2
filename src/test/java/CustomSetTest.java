import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomSetTest {

    @Test
    public void onCreatingSetWitNegativeSize_and_loadFactorOf_50_throws_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new CustomSet<>(-10, 0.5f));
    }

    @Test
    public void onCreatingSetWithNegativeSize_and_negativeLoadFactor_throws_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new CustomSet<>(-10, -10));
    }

    @Test
    public void onCreatingSetWithSizeOf_50_and_negativeLoadFactor_throws_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new CustomSet<>(50, -10));
    }

    @Test
    public void onConstructingSet_withSizeLessThan_0_throws_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new CustomSet<>(-1));
    }

    @Test
    public void onConstructingSet_withCollectionOfNull_throws_NullPointerException() {
        assertThrows(NullPointerException.class, () -> new CustomSet<>(null));
    }

    @Test
    public void onConstructingSet_returnsEmptySet() {
        CustomSet<Integer> customSet = new CustomSet<>();
        assertTrue(customSet.isEmpty());
    }

    @Test
    public void onConstructingSetWithOneItem_on_isEmpty_returnsFalse() {
        CustomSet<Integer> customSet = new CustomSet<>();
        assertTrue(customSet.add(10));
        assertTrue(customSet.contains(10));
        assertFalse(customSet.isEmpty());
    }

    @Test
    public void onConstructingSet_withCollectionOfFiveItems_sizeOf_5() {
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
    public void givenEmptySet_onAdd_null_returnsTrue_withSizeOf_1() {
        CustomSet<Integer> customSet = new CustomSet<>();
        assertTrue(customSet.add(null));
        assertEquals(1, customSet.size());
    }

    @Test
    public void givenEmptySet_onAdd_null_and_onContains_null_returns_true() {
        CustomSet<Integer> customSet = new CustomSet<>();
        assertTrue(customSet.add(null));
        assertTrue(customSet.contains(null));
        assertEquals(1, customSet.size());
    }

    @Test
    public void givenSetWithValueOf_null_onRemove_returns_true() {
        CustomSet<Integer> customSet = new CustomSet<>();
        assertTrue(customSet.add(null));
        assertTrue(customSet.remove(null));
        assertFalse(customSet.contains(null));
        assertEquals(0, customSet.size());
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
    public void onAddingToSet_50_items_returns_true_andSizeOf_50() {
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
    public void onAddingToSet_twoValuesOf_10_20_onRemove_10_returns_true() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);
        assertEquals(2, customSet.size());
        assertTrue(customSet.remove(10));
        assertEquals(1, customSet.size());
    }

    @Test
    public void givenNonEmptySet_onRetainAllWithEmptyCollection_clearsSet() {
        CustomSet<Integer> customSet = createDynamicSet(5);
        Collection<Integer> empty = new ArrayList<>();
        assertTrue(customSet.retainAll(empty));
        assertTrue(customSet.isEmpty());
    }

    @Test
    public void givenSetOfValue_10_20_30_40_50_onRetainAllForCollection_20_30_returnsSetOf_20_30() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);
        customSet.add(30);
        customSet.add(40);
        customSet.add(50);
        Collection<Integer> c = new ArrayList<>();
        c.add(20);
        c.add(30);
        assertTrue(customSet.retainAll(c));
        assertFalse(customSet.contains(10));
        assertFalse(customSet.contains(40));
        assertFalse(customSet.contains(50));
        assertTrue(customSet.contains(20));
        assertTrue(customSet.contains(30));
        assertEquals(2, customSet.size());
    }

    @Test
    public void givenSetOfValue_10_20_30_40_50_onRetailAllForCollectionContainingValueThatDoesNotExist_returnsFalse() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);
        customSet.add(30);
        customSet.add(40);
        customSet.add(50);
        Collection<Integer> c = new ArrayList<>();
        c.add(60);
        assertTrue(customSet.retainAll(c));
        assertEquals(0, customSet.size());
    }

    @Test
    public void givenSetOfValue_10_20_30_40_50_onRetailAllForCollection_20_30_60_returnsSetOf_20_30() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);
        customSet.add(30);
        customSet.add(40);
        customSet.add(50);
        Collection<Integer> c = new ArrayList<>();
        c.add(20);
        c.add(30);
        c.add(60);
        assertTrue(customSet.retainAll(c));
        assertFalse(customSet.contains(10));
        assertFalse(customSet.contains(40));
        assertFalse(customSet.contains(50));
        assertTrue(customSet.contains(20));
        assertTrue(customSet.contains(30));
        assertEquals(2, customSet.size());
    }

    @Test
    public void givenSetOfValue_10_20_30_onContainsAllForCollection_20_30_40_returns_false() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);
        customSet.add(30);
        Collection<Integer> c = new ArrayList<>();
        c.add(20);
        c.add(30);
        c.add(40);
        assertFalse(customSet.containsAll(c));
    }

    @Test
    public void givenSetOfValue_10_20_30_40_50_onRemoveAllForCollection_20_30_returnsCollectionOf_10_40_50() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);
        customSet.add(30);
        customSet.add(40);
        customSet.add(50);
        Collection<Integer> c = new ArrayList<>();
        c.add(20);
        c.add(30);
        assertTrue(customSet.removeAll(c));
        assertTrue(customSet.contains(10));
        assertFalse(customSet.contains(20));
        assertFalse(customSet.contains(30));
        assertTrue(customSet.contains(40));
        assertTrue(customSet.contains(50));
    }

    @Test
    public void givenSetOfValue_10_20_30_onContainsAllForCollection_20_30_returns_true() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);
        customSet.add(30);
        Collection<Integer> c = new ArrayList<>();
        c.add(20);
        c.add(30);
        assertTrue(customSet.containsAll(c));
    }

    @Test
    public void givenSetOfValue_10_20_30_onAddAllForCollectionOf_10_20_returns_false() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);
        customSet.add(30);
        Collection<Integer> c = new ArrayList<>();
        c.add(10);
        c.add(20);
        assertFalse(customSet.addAll(c));
    }

    @Test
    public void givenSetOfValue_10_20_30_onAddAllForCollectionOf_40_50_returns_true() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);
        customSet.add(30);
        Collection<Integer> c = new ArrayList<>();
        c.add(40);
        c.add(50);
        assertTrue(customSet.addAll(c));
    }

    @Test
    public void onAddingToSet_50_items_andClearingSet_returns_newSet() {
        CustomSet<Integer> customSet = createDynamicSet(50);
        assertEquals(50, customSet.size());
        customSet.clear();
        assertEquals(0, customSet.size());
    }

    @Test
    public void onConstructingEmptySet_returnsEmptyCurlyBracket_on_toString() {
        CustomSet<Integer> customSet = new CustomSet<>();
        assertEquals("{}", customSet.toString());
    }

    @Test
    public void onConstructingSet_withCollectionOfFiveItems_returnsCorrect_toString() {
        Collection<Integer> collection = IntStream.iterate(0, i -> i <= 50, i -> i + 10).boxed().collect(Collectors.toList());
        CustomSet<Integer> customSet = new CustomSet<>(collection);
        String setAsString = customSet.toString();
        assertTrue(setAsString.contains("10"));
        assertTrue(setAsString.contains("20"));
        assertTrue(setAsString.contains("30"));
        assertTrue(setAsString.contains("40"));
        assertTrue(setAsString.contains("50"));
        String pattern = "\\{[0-9]+(, [0-9]+)+}";
        System.out.println();
        assertTrue(setAsString.matches(pattern));
    }

    @Test
    public void givenEmptySet_onToArray_returns_emptyArray() {
        CustomSet<String> customSet = new CustomSet<>();
        Object[] values = customSet.toArray();
        Object[] expected = new Object[] { };
        assertEquals(0, values.length);
        assertArrayEquals(expected, values);
    }

    @Test
    public void onConstructingSet_withCollectionOfFiveItems_returnsCorrectArray() {
        CustomSet<String> customSet = new CustomSet<>();
        customSet.add("10");
        customSet.add("20");
        customSet.add("30");
        Object[] values = customSet.toArray();
        Arrays.sort(values); // sort so that we can compare correctly due to hashing indexing
        Object[] expected = new Object[] { "10", "20", "30" };
        assertEquals(3, values.length);
        assertArrayEquals(expected, values);
    }

    @Test
    public void onAdding_1_000_000_values_causesNoIndexOverwritingIssues_andAdds_1_000_000_values() {
        CustomSet<Long> customSet = new CustomSet<>();
        LongStream.range(0, 1_000_000).forEach(customSet::add);
        assertEquals(1_000_000, customSet.size());
    }

    @Test
    public void onAdding_2_000_000_values_causesNoIndexOverwritingIssues_andAdds_2_000_000_values() {
        CustomSet<Long> customSet = new CustomSet<>();
        LongStream.range(0, 2_000_000).forEach(customSet::add);
        assertEquals(2_000_000, customSet.size());
    }

    @Test
    public void onAdding_3_000_000_values_causesNoIndexOverwritingIssues_andAdds_3_000_000_values() {
        CustomSet<Long> customSet = new CustomSet<>();
        LongStream.range(0, 3_000_000).forEach(customSet::add);
        assertEquals(3_000_000, customSet.size());
    }

    @Test
    public void givenSetOfThreeItems_iterator_iteratesOverAllElements_andReturnsSizeOf_3() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(1);
        customSet.add(2);
        customSet.add(3);

        int count = 0;
        Iterator<Integer> iterator = customSet.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }

        assertEquals(3, count);
    }

    @Test
    public void givenSetOfThreeItems_iterator_remove_removesCurrentElement() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(1);
        customSet.add(2);
        customSet.add(3);

        Iterator<Integer> iterator = customSet.iterator();
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            if (value.equals(2)) {
                iterator.remove();
            }
        }

        assertFalse(customSet.contains(2));
        assertEquals(2, customSet.size());
    }

    @Test
    public void givenSetOf_100_items_spliterator_estimateSize_matchesSetSize() {
        CustomSet<Integer> customSet = createDynamicSet(100);
        assertEquals(100, customSet.spliterator().estimateSize());
    }

    @Test
    public void givenTwoIdenticalSet_onEquals_returnsTrue() {
        CustomSet<Integer> a = new CustomSet<>();
        a.add(1);
        a.add(2);
        CustomSet<Integer> b = new CustomSet<>();
        b.add(1);
        b.add(2);
        assertTrue(a.equals(b));
    }

    @Test
    public void givenTwoDifferentSets_onEquals_returnsFalse() {
        CustomSet<Integer> a = new CustomSet<>();
        a.add(1);
        a.add(2);
        CustomSet<Integer> b = new CustomSet<>();
        b.add(1);
        b.add(3);
        assertFalse(a.equals(b));
    }

    @Test
    public void givenTwoIdenticalSets_hashCode_IsTheSame() {
        CustomSet<Integer> a = new CustomSet<>();
        a.add(1);
        a.add(2);
        CustomSet<Integer> b = new CustomSet<>();
        b.add(1);
        b.add(2);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void givenTwoDifferentSets_hashCode_IsDifferent() {
        CustomSet<Integer> a = new CustomSet<>();
        a.add(1);
        a.add(2);
        CustomSet<Integer> b = new CustomSet<>();
        b.add(1);
        b.add(3);
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    private static CustomSet<Integer> createDynamicSet(int x) {
        CustomSet<Integer> customSet = new CustomSet<>();
        Random random = new Random();
        for (int i = 0; i < x; i++)
            customSet.add(random.nextInt());
        return customSet;
    }
}