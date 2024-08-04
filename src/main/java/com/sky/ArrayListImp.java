package com.sky;

import com.sky.exception.NotFoundException;
import com.sky.exception.NullException;
import com.sky.exception.OutOFBondsException;

import java.util.Arrays;

public class ArrayListImp implements ArrayList {

    private Integer[] array;

    private int size;

    public ArrayListImp() {
        array = new Integer[10];
    }

    public ArrayListImp(int length) {
        array = new Integer[length];
    }

    @Override
    public Integer add(Integer item) {
        validateItem(item);
        validateSize();
        array[size++] = item;
        return item;
    }

    @Override
    public Integer add(int index, Integer item) {
        validateItem(item);
        validateIndex(index);
        validateSize();
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
        quickSort(arrayBySize, 0, arrayBySize.length - 1);
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

    private void validateSize() {
        if (size == array.length) {
            array = grow();
        }
    }

    private Integer[] grow() {
        return Arrays.copyOf(array, size + size / 2);
    }

    private void swapElements(Integer[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
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

    public void quickSort(Integer[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                swapElements(arr, i, j);
            }
        }

        swapElements(arr, i + 1, end);
        return i + 1;
    }

    public void mergeSort(Integer[] arr) {
        if (arr.length < 2) {
            return;
        }
        int mid = arr.length / 2;
        Integer[] left = new Integer[mid];
        Integer[] right = new Integer[arr.length - mid];

        for (int i = 0; i < left.length; i++) {
            left[i] = arr[i];
        }

        for (int i = 0; i < right.length; i++) {
            right[i] = arr[mid + i];
        }

        mergeSort(left);
        mergeSort(right);

        merge(arr, left, right);
    }

    private void merge(Integer[] arr, Integer[] left, Integer[] right) {

        int mainP = 0;
        int leftP = 0;
        int rightP = 0;
        while (leftP < left.length && rightP < right.length) {
            if (left[leftP] <= right[rightP]) {
                arr[mainP++] = left[leftP++];
            } else {
                arr[mainP++] = right[rightP++];
            }
        }
        while (leftP < left.length) {
            arr[mainP++] = left[leftP++];
        }
        while (rightP < right.length) {
            arr[mainP++] = right[rightP++];
        }
    }
}
