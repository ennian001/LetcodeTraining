package study.algorithm.letcode.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * #136
 */
public class SigngleNumber {

    public static void main(String[] args) {
        int[] nums = {4,1,2,1,2};
        SigngleNumber singleNumber = new SigngleNumber();
        System.out.println(singleNumber.singleNumber(nums));
    }
    //空间复杂度 O（n） 时间复杂度 O（n²）
    // 方法 1： 暴力法
    public int singleNumber1(int[] nums){
        // 定义一个列表 ， 保存当前保存所有出现过一次的元素
        ArrayList<Integer> singleNumList = new ArrayList<>() ;
        // 遍历所有元素
        for(Integer num:nums){
            if (singleNumList.contains(num)){
                //如果已经出现过，删除列表中的元素
                singleNumList.remove(num);
            }else {
                //没有出现过直接保存
                singleNumList.add(num) ;
            }
        }
        return singleNumList.get(0) ;
    }
   // 方法二：保存单独的元素到hashmap中
   public int singleNumber2(int[] nums){
       HashMap<Integer,Integer> singleNumMap = new HashMap<>() ;
       for (Integer num:nums){
           if (singleNumMap.get(num)!=null){
               singleNumMap.remove(num) ;
           }else {
               singleNumMap.put(num,1) ;
           }
       }
       Set<Integer> integers = singleNumMap.keySet();
       return integers.iterator().next() ;
   }

   //方法三：用set去重，a = 2*（a+b+c） - (a+b+c+c+b)
    public int singleNumber3(int[] nums){
        //定义一个HashSet去重
        HashSet<Integer> set = new HashSet<>() ;
        int arraySum = 0 ;
        int setSum = 0 ;
        //遍历数组元素，保存到set，并直接求和
        for (int num:nums) {
            set.add(num);
            arraySum = arraySum+num ;
        }
        //集合中所有元素求和
        for (int num:set)
            setSum+=num ;
        return setSum*2 - arraySum ;
    }
    //方法四：数学方法（做异或）
    //异或满足结合律、交换律
    /**
     *  与0异或后就是自己     与自己异或后等于0
     *  1011                1011
     *  0000                1011
     *  1011                0000
     *
     * **/
    public int singleNumber(int[] nums){
        int result = 0 ;
        //遍历所有数据 ， 按位做异或
        for (int num: nums)
            result ^= num ;

        return result ;
    }

}
