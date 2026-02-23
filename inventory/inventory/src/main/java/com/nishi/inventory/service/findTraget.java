//package com.nishi.inventory.service;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class findTraget {
//
//    public static void main(String[] args) {
//
//        List<Integer> list =  Arrays.asList(1,2,3,4,5,6,7,8,9,10);
//
//
//        int target=7;
//
//        List<List<Integer>> ans = new ArrayList<>();
//
//
//        ans =list.stream().map((Integer i) ->{
//            List<List<Integer>> temp = new ArrayList<>();
//            int a=list.get(i);
//            int sumResidual=target-a;
//            if(sumResidual>=1 && sumResidual<=10)
//            {
//                temp.add(Arrays.asList(a, sumResidual));
//            }
//            int subResidual=target+a;
//            if(subResidual>=1 && subResidual<=10)
//            {
//                temp.add(Arrays.asList(a, subResidual));
//            }
//            return temp;
//        }).collect(Collectors.toCollection());
//
//        for(int i=0;i<ans.size();i++){
//
//            System.out.println(ans.get(i));
//
//        }
//
//
//
//    }
//}
