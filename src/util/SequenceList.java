package util;

import java.util.Iterator;

/*
线性表的设计：

成员变量：
private T[] elements:存储元素的数组
private int N:当前线性表的长度

构造方法：
public SequenceList(int capacity):创建容量为capacity的SequenceList对象
public SequenceList():创建初始容量为10的SequenceList对象

：成员方法：
public void clear():空置线性表
public boolean isEmpty():判断线性表是否为空，是则返回true，反之返回false
public int length():获取线性表中元素的个数
public T get(int i):读取并返回线性表中的第i个元素的值
public void insert(int i,T t):在线性表中的第i个元素之前插入一个值为t的元素
public void insert(T t):向线性表末尾添加一个元素t
public T remove(int i):删除并返回线性表中第i个元素
public int indexOf(T t):返回线性表中首次出现的指定的数据元素的序号，若不存在，则返回-1

 */
public class SequenceList<T> implements Iterable<T>{
    private T[] elements;
    private int N;
    private final int capacityDefault = 10;
    public SequenceList(int capacity){
        this.elements = (T[]) new Object[capacity];
        this.N = 0;

    }
    public SequenceList(){
        this.elements = (T[]) new Object[capacityDefault];
        this.N = 0;

    }

    public void clear(){
        this.N=0;
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public int length(){
        return this.N;
    }
    //读取并返回线性表中第i个元素的值
    public T get(int i){
        return this.elements[i];
    }
    //在线性表中第i个元素之前插入一个值为t的元素
    public void insert(int i,T t){
        isSafety();
        //将索引为i到N-1的所有元素往后移动一格
        for (int j = N-1; j >=i ; j--) {
            elements[N-1] = elements[N];
        }
        //将待插入的数据添加到第i个
        elements[i]=t;
        N++;
    }
    //向线性表末尾添加一个元素t
    public void insert(T t){
        isSafety();
        elements[N++]=t;
    }
    //删除并返回线性表中第i个元素
    public T remove(int i){
        T elementDel = elements[i-1];
        //将i索引到N-1索引的元素全部向左移动一格
        for (int j = i; j <=N-1 ; j++) {
            elements[i-1]=elements[i];
        }
        return elementDel;
    }
    //返回线性表中首次出现的指定的数据元素的序号，若不存在，则返回-1
    public int indexOf(T t){
        int index=0;
        while (index<N){
            if (elements[index]==t) {
                return index+1;
            }
            index++;
        }
        return -1;
    }
    //检验当前数组长度是否安全,如果不安全，则扩容
    public void isSafety(){
        if(elements.length==N){
            //创建一个辅助数组，保存当前数组的数据
            T[] assists = (T[]) new Object[elements.length];
            for (int i = 0; i < elements.length; i++) {
                assists[i]=elements[i];
            }
            //创建一个新的数组，并且增加长度至原来的二倍
            elements = (T[]) new Object[elements.length * 2];
            //将辅助数组的数据复制到新数组
            for (int i = 0; i < assists.length; i++) {
                elements[i]=assists[i];
            }
        }
    }

    @Override
    public Iterator iterator() {
        return new myIter();
    }
    private class myIter implements Iterator {
        private int cusor;
        public myIter(){
            this.cusor = 0;
        }
        @Override
        public boolean hasNext() {
            return cusor<N;
        }

        @Override
        public Object next() {
            return elements[cusor++];
        }
    }
}