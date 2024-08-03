package com.sky;

import com.sky.exception.NotFoundException;
import com.sky.exception.NullException;
import com.sky.exception.OutOFBondsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class IntegerListImpTest {

    private IntegerList out;

    private Integer item1;

    @BeforeEach
    public void setUp() {

        out = new IntegerListImp(5);

        item1 = out.add(0);
        out.add(1);
        out.add(2);


    }

    @Test
    public void addItemTesting() {

        assertEquals(0, item1);
        assertEquals(0, out.get(0));

    }

    @Test
    public void ExceptionWhenItemNullTesting() {
        Exception exception = assertThrows(NullException.class,
                () -> out.add(null));

        assertEquals("Передан null!", exception.getMessage());
    }

    @Test
    public void addItemByIndexTesting() {

        Integer actual = out.add(1, 1);

        assertEquals(1, actual);
        assertEquals(1, out.get(1));
        assertEquals(4, out.size());

    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 5})
    public void ExceptionWhenInvalidIndexTesting(int index) {
        Exception exception = assertThrows(OutOFBondsException.class,
                () -> out.add(index, 3));

        assertEquals("Индекс за пределами границ списка!", exception.getMessage());
    }

    @Test
    public void growArrayWhenAddItemTesting() {
        out.add(3);
        out.add(4);

        Integer actual = out.add(5);

        Integer expected = 5;

        assertEquals(expected, actual);
        assertEquals(5, out.get(5));
        assertEquals(6, out.size());

    }

    @Test
    public void growArrayWhenAddItemByIndexTesting() {

        out.add(4);
        out.add(5);

        Integer actual = out.add(2, 2);

        Integer expected = 2;

        assertEquals(expected, actual);
        assertEquals(2, out.get(2));
        assertEquals(6, out.size());

    }

    @Test
    public void setItemTesting() {

        Integer actual = out.set(0, 1);

        assertEquals(1, actual);
        assertEquals(1, out.get(0));
        assertEquals(3, out.size());

    }

    @Test
    public void removeItemTesting() {

        Integer actual = out.remove(2);

        assertEquals(2, actual);
        assertEquals(2, out.size());

    }

    @Test
    public void ExceptionWhenRemoveNotExistingItemTesting() {

        Integer item = 3;

        Exception exception = assertThrows(NotFoundException.class,
                () -> out.remove(item));

        assertEquals("Такого элемента нет!", exception.getMessage());
    }

    @Test
    public void removeItemByIndexTesting() {

        Integer actual = out.remove(1);

        assertEquals(1, actual);
        assertEquals(2, out.size());

    }

    @Test
    public void containsItemByIndexTesting() {

        assertTrue(out.contains(1));

    }

    @ParameterizedTest
    @MethodSource("IndexForTests")
    public void indexOfItemTesting(Integer item, int expected) {

        int actual = out.indexOf(item);

        assertEquals(expected, actual);

    }

    @ParameterizedTest
    @MethodSource("IndexForTests")
    public void lastIndexOfItemTesting(Integer item, int expected) {

        int actual = out.lastIndexOf(item);

        assertEquals(expected, actual);

    }

    @Test
    public void getItemTesting() {

        Integer actual = out.get(2);

        assertEquals(2, actual);

    }

    @Test
    public void equalsIntegerListsWhenReturnTrueTesting() {

        IntegerListImp integerList2 = new IntegerListImp(5);

        integerList2.add(0);
        integerList2.add(1);
        integerList2.add(2);

        assertTrue(out.equals(integerList2));

    }

    @Test
    public void equalsIntegerListsWhenReturnFalseTesting() {

        IntegerListImp integerList2 = new IntegerListImp(5);

        integerList2.add(0);
        integerList2.add(1);
        integerList2.add(2);
        integerList2.add(3);

        assertFalse(out.equals(integerList2));

    }

    @Test
    public void ExceptionWhenOtherIntegerListNullTesting() {

        IntegerListImp integerList2 = null;

        Exception exception = assertThrows(NullException.class,
                () -> out.equals(integerList2));

        assertEquals("Передан null!", exception.getMessage());
    }

    @Test
    public void sizeTesting() {

        assertEquals(3, out.size());
    }

    @Test
    public void isEmptyFalseTesting() {

        assertFalse(out.isEmpty());
    }

    @Test
    public void isEmptyTrueAndClearTesting() {

        out.clear();

        assertTrue(out.isEmpty());
        assertEquals(0, out.size());
    }

    @Test
    public void toArrayTesting() {

        Integer[] expected = {0, 1, 2};

        assertArrayEquals(expected, out.toArray());
    }

    public static Stream<Arguments> IndexForTests() {
        return Stream.of(
                Arguments.of(2, 2),
                Arguments.of(3, -1)
        );
    }
}
