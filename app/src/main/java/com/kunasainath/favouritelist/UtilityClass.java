package com.kunasainath.favouritelist;

import java.util.Comparator;

public class UtilityClass {
    public static Comparator<Category> getSorter(){
        Comparator<Category> sorter = new Comparator<Category>() {
            @Override
            public int compare(Category a, Category b) {
                if(a.getCategory().compareTo(b.getCategory()) > 0){
                    return 1;
                }return -1;
            }
        };
        return sorter;
    }

    public static Comparator<String> getStringSorter(){
        Comparator<String> stringSorter = new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                if(a.compareTo(b) > 0){
                    return 1;
                }return -1;
            }
        };
        return stringSorter;
    }
}
