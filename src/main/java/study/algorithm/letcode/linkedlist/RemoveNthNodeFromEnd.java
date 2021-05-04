package study.algorithm.letcode.linkedlist;

import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
 *
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 *
 * 进阶：你能尝试使用一趟扫描实现吗？
 *
 * @author ennian
 */
public class RemoveNthNodeFromEnd {

    // 定义一个计算链表长度的方法
    public  static  int getLength(ListNode node){
        int length = 0  ;
        while (node!=null){
            length++ ;
            node = node.next ;
        }
        return length;
    }

    // 方法一 ： 计算链表长度
    public ListNode removeNthFromEnd1(ListNode head , int n ){
        // 1. 遍历链表得到链表长度
        int length = getLength(head);
        // 2. 从头到后继续遍历,找到正数第 length - n +1 个元素
        // 3. 定义一个哨兵节点 , next 指向头节点
        ListNode sentinel = new ListNode(-1,head) ;

        ListNode curr = sentinel ;
        for (int i = 0; i < length - n ; i++) {
            curr = curr.next;
        }
        // 4. 找到第length - n 个节点
        // 5. 跳过第length - n +1 个节点
        curr.next = curr.next.next ;
        return sentinel.next ;
    }
    //方法二：使用栈
    public ListNode removeNthFromEnd2(ListNode head , int n){
        //定义一个烧饼节点 ， next 指向头节点
        ListNode sentinel  = new ListNode(-1,head) ;
        ListNode curr = sentinel ;
        //定义一个栈
        Stack<ListNode> stack = new Stack<>() ;
        //1.将所有节点入栈
        while (curr!=null){
            stack.push(curr) ;
            curr = curr.next ;
        }
        //2.弹栈n次
        for (int i = 0; i < n; i++) {
            stack.pop() ;
        }
        stack.peek().next = stack.peek().next.next ;
        return sentinel.next ;
    }
    //方法三 ： 双指针
    public ListNode removeNthFromEnd(ListNode head , int n){
        //定义一个哨兵节点 ， next指向头节点
        ListNode sentinel = new ListNode(-1, head) ;
        //定义前后双指针
        ListNode first = sentinel ,second = sentinel ;
        //1.first先走n步
        for (int i = 0; i < n+1; i++) {
            first = first.next ;
        }
        //2.first,second 同时前进，当first变为null，second就是倒数第n+1个节点
        while (first!=null){
            first  = first.next ;
            second = second.next ;
        }
        //3. 删除倒数第n个节点
        second.next = second.next.next ;
        return sentinel.next ;
    }


    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode4 = new ListNode(4);
        ListNode listNode5 = new ListNode(5);


        listNode1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;
        listNode5.next = null;

        TestLinkedList.printList(listNode1);

        RemoveNthNodeFromEnd removeNthNodeFromEnd = new RemoveNthNodeFromEnd();

        TestLinkedList.printList(removeNthNodeFromEnd.removeNthFromEnd1(listNode1, 2));
    }

}
