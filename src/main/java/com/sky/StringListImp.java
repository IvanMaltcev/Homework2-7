package com.sky;

import com.sky.exception.NotFoundException;
import com.sky.exception.NullException;
import com.sky.exception.OutOFBondsException;

import java.util.Arrays;

public class StringListImp implements StringList {

    private String[] array;

    private int size;

    private int length;

    public StringListImp() {
        this.length = 10;
        array = new String[10];
    }

    public StringListImp(int length) {
        this.length = length;
        array = new String[length];
    }

    @Override
    public String add(String item) {
        validateItem(item);
        if (size == array.length) {
            array = grow();
        }
        array[size++] = item;
        return item;
    }

    @Override
    public String add(int index, String item) {
        validateItem(item);
        validateIndex(index);
        if (size == array.length) {
           array = grow();
        }
        for (int i = size; i >= index; i--) {
            array[i] = array[i - 1];
        }
        size++;
        array[index] = item;
        return item;
    }

    @Override
    public String set(int index, String item) {
        validateItem(item);
        validateIndex(index);
        array[index] = item;
        return item;
    }

    @Override
    public String remove(String item) {
        validateItem(item);
        boolean isItemNotFound = true;
        for (int i = 0; i < size; i++) {
            if (array[i].equals(item)) {
                for (int j = i; j < size - 1; j++) {
                    array[j] = array[j + 1];
                }
                array[size - 1] = null;
                size--;
                isItemNotFound = false;
                break;
            }
        }
        if (isItemNotFound) {
            throw new NotFoundException("Такого элемента нет!");
        }
        return item;
    }

    @Override
    public String remove(int index) {
        validateIndex(index);
        String item = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null;
        size--;
        return item;
    }

    @Override
    public boolean contains(String item) {
        validateItem(item);
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(String item) {
        validateItem(item);
        for (int i = 0; i < size; i++) {
            if (array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {
        validateItem(item);
        for (int i = size - 1; i >= 0; i--) {
            if (array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String get(int index) {
        validateIndex(index);
        return array[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        if (otherList == null) {
            throw new NullException("Передан null!");
        }
        return Arrays.equals(this.toArray(), otherList.toArray());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public String[] toArray() {
        return Arrays.copyOf(array, size);
    }

    public void print() {
        System.out.println(Arrays.toString(array));
    }

    private void validateItem(String item) {
        if (item == null) {
            throw new NullException("Передан null!");
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new OutOFBondsException("Индекс за пределами границ списка!");
        }
    }

    private String[] grow() {
        length++;
        return Arrays.copyOf(array, length + 1);
    }

}
