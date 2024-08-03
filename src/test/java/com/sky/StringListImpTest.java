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

public class StringListImpTest {

    private StringList out;

    private String item1;

    @BeforeEach
    public void setUp() {

        out = new StringListImp(5);

        item1 = out.add("0");
        out.add("1");
        out.add("2");


    }

    @Test
    public void addItemTesting() {

        assertEquals("0", item1);
        assertEquals("0", out.get(0));

    }

    @Test
    public void ExceptionWhenItemNullTesting() {
        Exception exception = assertThrows(NullException.class,
                () -> out.add(null));

        assertEquals("Передан null!", exception.getMessage());
    }

    @Test
    public void addItemByIndexTesting() {

        String actual = out.add(1, "1");

        assertEquals("1", actual);
        assertEquals("1", out.get(1));
        assertEquals(4, out.size());

    }
    @ParameterizedTest
    @ValueSource(ints = {-1, 5})
    public void ExceptionWhenInvalidIndexTesting(int index) {
        Exception exception = assertThrows(OutOFBondsException.class,
                () -> out.add(index, "3"));

        assertEquals("Индекс за пределами границ списка!", exception.getMessage());
    }

    @Test
    public void growArrayWhenAddItemTesting() {
        out.add("3");
        out.add("4");

        String actual = out.add("5");

        String expected = "5";

        assertEquals(expected, actual);
        assertEquals("5", out.get(5));
        assertEquals(6, out.size());

    }

    @Test
    public void growArrayWhenAddItemByIndexTesting() {

        out.add("4");
        out.add("5");

        String actual = out.add(2,"2");

        String expected = "2";

        assertEquals(expected, actual);
        assertEquals("2", out.get(2));
        assertEquals(6, out.size());

    }

    @Test
    public void setItemTesting() {

        String actual = out.set(0,"1");

        assertEquals("1", actual);
        assertEquals("1", out.get(0));
        assertEquals(3, out.size());

    }
    @Test
    public void removeItemTesting() {

        String actual = out.remove("2");

        assertEquals("2", actual);
        assertEquals(2, out.size());

    }
    @Test
    public void ExceptionWhenRemoveNotExistingItemTesting() {

        Exception exception = assertThrows(NotFoundException.class,
                () -> out.remove("3"));

        assertEquals("Такого элемента нет!", exception.getMessage());
    }
    @Test
    public void removeItemByIndexTesting() {

        String actual = out.remove(1);

        assertEquals("1", actual);
        assertEquals(2, out.size());

    }

    @Test
    public void containsItemByIndexTesting() {

        assertTrue(out.contains("2"));

    }

    @ParameterizedTest
    @MethodSource("IndexForTests")
    public void indexOfItemTesting(String item, int expected) {

        int actual = out.indexOf(item);

        assertEquals(expected, actual);

    }

    @ParameterizedTest
    @MethodSource("IndexForTests")
    public void lastIndexOfItemTesting(String item, int expected) {

        int actual = out.lastIndexOf(item);

        assertEquals(expected, actual);

    }

    @Test
    public void getItemTesting() {

        String actual = out.get(2);

        assertEquals("2", actual);

    }

    @Test
    public void equalsStringListsWhenReturnTrueTesting() {

        StringListImp stringList2 = new StringListImp(5);

        stringList2.add("0");
        stringList2.add("1");
        stringList2.add("2");

        assertTrue(out.equals(stringList2));

    }

    @Test
    public void equalsStringListsWhenReturnFalseTesting() {

        StringListImp stringList2 = new StringListImp(5);

        stringList2.add("0");
        stringList2.add("1");
        stringList2.add("2");
        stringList2.add("3");

        assertFalse(out.equals(stringList2));

    }

    @Test
    public void ExceptionWhenOtherStringListNullTesting() {

        StringListImp stringList2 = null;

        Exception exception = assertThrows(NullException.class,
                () -> out.equals(stringList2));

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

        String[] expected = {"0","1", "2"};

        assertArrayEquals(expected, out.toArray());
    }

    public static Stream<Arguments> IndexForTests() {
        return Stream.of(
                Arguments.of("2", 2),
                Arguments.of("3", -1)
        );
    }

}
