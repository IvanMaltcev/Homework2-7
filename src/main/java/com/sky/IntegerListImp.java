package com.sky;

import com.sky.exception.NotFoundException;
import com.sky.exception.NullException;
import com.sky.exception.OutOFBondsException;

import java.util.Arrays;

public class IntegerListImp implements IntegerList {

    private Integer[] array;

    private int size;

    private int length;

    public IntegerListImp() {
        this.length = 10;
        array = new Integer[10];
    }

    public IntegerListImp(int length) {
        this.length = length;
        array = new Integer[length];
    }

    @Override
    public Integer add(Integer item) {
        validateItem(item);
        if (size == array.length) {
            array = grow();
        }
        array[size++] = item;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
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
    public Integer set(int index, Integer item) {
        validateItem(item);
        validateIndex(index);
        array[index] = item;
        return item;
    }

    @Override
    public Integer remove(Integer item) {
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
    public Integer remove(int index) {
        validateIndex(index);
        Integer item = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        array[size - 1] = null;
        size--;
        return item;
    }

    @Override
    public boolean contains(Integer item) {
        validateItem(item);
        Integer[] arrayBySize = toArray();
        sortSelection(arrayBySize);
        return binarySearch(arrayBySize, item) != -1;
    }

    @Override
    public int indexOf(Integer item) {
        validateItem(item);
        for (int i = 0; i < size; i++) {
            if (array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        validateItem(item);
        for (int i = size - 1; i >= 0; i--) {
            if (array[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        validateIndex(index);
        return array[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
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
    public Integer[] toArray() {
        return Arrays.copyOf(array, size);
    }

    public void print() {
        System.out.println(Arrays.toString(array));
    }

    private void validateItem(Integer item) {
        if (item == null) {
            throw new NullException("Передан null!");
        }
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= size) {
            throw new OutOFBondsException("Индекс за пределами границ списка!");
        }
    }

    private Integer[] grow() {
        length++;
        return Arrays.copyOf(array, length + 1);
    }

    private void swapElements(Integer[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
    }

    public void sortBubble(Integer[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swapElements(arr, j, j + 1);
                }
            }
        }
    }

    public void sortSelection(Integer[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minElementIndex]) {
                    minElementIndex = j;
                }
            }
            swapElements(arr, i, minElementIndex);
        }
    }

    public void sortInsertion(Integer[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] >= temp) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
    }

    private int binarySearch(Integer[] sortedArray, int item) {
        var left = 0;
        var right = sortedArray.length - 1;
        while (left <= right) {
            var middle = (left + right) / 2;
            var current = sortedArray[middle];

            if (current == item) {
                return middle;
            } else if (current < item) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return -1;
    }
}
