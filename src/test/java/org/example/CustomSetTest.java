package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomSetTest {

    @Test
    public void onConstructingSet_returnsSetSizeOf_10() {
        CustomSet<Integer> customSet = new CustomSet<>();
        assertEquals(10, customSet.getSetSize());
    }

    @Test
    public void onConstructingSet_withCollectionOfFiveItems_returnsSetSizeOf_10_andSizeOf_5() {
        Collection<Integer> collection = new ArrayList<Integer>();
        collection.add(10);
        collection.add(20);
        collection.add(30);
        collection.add(40);
        collection.add(50);
        CustomSet<Integer> customSet = new CustomSet<>(collection);
        assertEquals(5, customSet.getSize());
        assertEquals(10, customSet.getSetSize());
        assertTrue(customSet.contains(10));
        assertTrue(customSet.contains(20));
        assertTrue(customSet.contains(30));
        assertTrue(customSet.contains(40));
        assertTrue(customSet.contains(50));
        assertFalse(customSet.contains(100));
    }

    @Test
    public void onConstructingSet_withSizeOf_5_returnsSetSizeOf_10() {
        CustomSet<Integer> customSet = new CustomSet<>(5);
        assertEquals(10, customSet.getSetSize());
    }

    @Test
    public void onAddingToSet_10_returns_true_and_sizeOf_1() {
        CustomSet<Integer> customSet = new CustomSet<>();
        assertTrue(customSet.add(10));
        assertEquals(1, customSet.getSize());
    }

    @Test
    public void onAddingToSet_10_20_returns_true_and_sizeOf_2() {
        CustomSet<Integer> customSet = new CustomSet<>();
        Random random = new Random();
        for(int i = 0; i < 2; i++) {
            customSet.add(random.nextInt());
        }

        assertEquals(2, customSet.getSize());
    }

    @Test
    public void onAddingToSet_10_items_returns_true_andSizeOf_10() {
        CustomSet<Integer> customSet = new CustomSet<>();
        Random random = new Random();
        //create the set dynamically
        for(int i = 0; i < 10; i++) {
            customSet.add(random.nextInt());
        }

        assertEquals(10, customSet.getSize());
    }

    @Test
    public void onAddingToSet_twoIdenticalNumbersToSet_onlyAddsOne() {
        CustomSet<Integer> customSet = new CustomSet<>();
        assertTrue(customSet.add(10));
        assertEquals(1, customSet.getSize());
        assertFalse(customSet.add(10));
        assertEquals(1, customSet.getSize());
    }

    @Test
    public void onAddingToSet_50_items_returns_true_andSizeOf_50_andSetSizeOf_40() {
        CustomSet<Integer> customSet = new CustomSet<>();
        Random random = new Random();
        //create the set dynamically
        for(int i = 0; i < 50; i++) {
            customSet.add(random.nextInt());
        }

        assertEquals(50, customSet.getSize());
        assertEquals(160, customSet.getSetSize());
    }

    @Test
    public void onAddingToSet_twoSameValues_returns_sizeOf_1() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(1);
        customSet.add(1);

        assertEquals(1, customSet.getSize());
        System.out.println(customSet);
        assertEquals(10, customSet.getSetSize());
    }

    @Test
    public void onAddingToSet_twoSameValues_andOneUnique_returns_sizeOf_2() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(1);
        customSet.add(1);
        customSet.add(2);

        assertEquals(2, customSet.getSize());
        System.out.println(customSet);
        assertEquals(10, customSet.getSetSize());
    }

    @Test
    public void onAddingToSet_twoValuesOf_10_20_onRemove_30_returns_false() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);

        assertEquals(2, customSet.getSize());
        assertFalse(customSet.remove(30));
        assertEquals(10, customSet.getSetSize());
    }

    @Test
    public void onAddingToSet_twoValuesOf_10_20_onRemove_null_returns_false() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);

        assertEquals(2, customSet.getSize());
        assertFalse(customSet.remove(null));
        assertEquals(2, customSet.getSize());
        assertEquals(10, customSet.getSetSize());
    }

    @Test
    public void onAddingToSet_twoValuesOf_10_20_onRemove_10_returns_true() {
        CustomSet<Integer> customSet = new CustomSet<>();
        customSet.add(10);
        customSet.add(20);

        assertEquals(2, customSet.getSize());
        assertTrue(customSet.remove(10));
        assertEquals(1, customSet.getSize());
        assertEquals(10, customSet.getSetSize());
    }

    @Test
    public void onAddingToSet_50_items_andClearingSet_returns_newSet() {
        CustomSet<Integer> customSet = new CustomSet<>();
        Random random = new Random();
        //create the set dynamically
        for(int i = 0; i < 50; i++) {
            customSet.add(random.nextInt());
        }

        assertEquals(50, customSet.getSize());
        assertEquals(160, customSet.getSetSize());
        customSet.clear();
        assertEquals(0, customSet.getSize());
        assertEquals(10, customSet.getSetSize());
    }
}