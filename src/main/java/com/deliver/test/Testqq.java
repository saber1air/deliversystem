package com.deliver.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by pdl on 2019/1/14.
 */
public class Testqq {
    public static void main(String[] args) {
        List l1 = new ArrayList();
        List l2 = new ArrayList();
        l1.add("3");
        l1.add("1");
        l1.add("2");
        l1.add("11");
        Collections.sort(l1, new Comparator< Object >() {
            public int compare(Object o1, Object o2) {
                String a = (String)o1;
                String b = (String)o2;
                int aa = Integer.parseInt(a);
                int bb = Integer.parseInt(b);
                if(aa>bb) return -1;
                else if(aa<bb) return 1;
                else return 0;
            }
        });
        l2.add(l1);
        System.out.println(l1);
        System.out.println(l2);
    }
}
