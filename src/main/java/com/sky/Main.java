package com.sky;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        StringListImp stringList = new StringListImp(5);

        stringList.add("1");
        stringList.add("2");
        stringList.add("4");
        stringList.add("5");
        stringList.add("6");
        stringList.add("7");
        stringList.print();

        stringList.add(2, "3");
        stringList.print();

        stringList.add("5");
        stringList.print();

        stringList.set(1,"7");
        stringList.print();

        stringList.remove("3");
        stringList.print();

        stringList.remove(1);
        stringList.print();

        System.out.println(stringList.contains("5"));

        System.out.println(stringList.indexOf("4"));

        System.out.println(stringList.lastIndexOf("5"));

        System.out.println(stringList.get(0));

        StringListImp stringList2 = new StringListImp(5);

        stringList2.add("1");
        stringList2.add("4");
        stringList2.add("5");


        System.out.println(stringList.equals(stringList2));

        System.out.println(stringList.size());

        System.out.println(stringList.isEmpty());

        stringList2.clear();
        stringList2.print();
        System.out.println(stringList2.size());

        System.out.println(Arrays.toString(stringList.toArray()));


    }
}