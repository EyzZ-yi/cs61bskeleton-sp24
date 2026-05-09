package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private Node sentinel;
    int size=0;
    private class Node{
        Node qiannode;
        Node hounode;
        T item;
        public Node(T x,Node qian,Node hou){
            item=x;
            qiannode=qian;
            hounode=hou;
        }
    }
    public LinkedListDeque61B() {
        sentinel=new Node(null,null,null);
    }

    @Override
    public void addFirst(T x) {
        size++;
        if(sentinel.hounode!=null){
            Node p;
            p=sentinel.hounode;
            sentinel.hounode=new Node(x,sentinel,p);
            p.qiannode=sentinel.hounode;}
        else {sentinel.hounode=new Node(x,sentinel,null);
            sentinel.qiannode=sentinel.hounode;}

    }

    @Override
    public void addLast(T x) {
        size++;
        if(sentinel.hounode==null){//空链表
            sentinel.hounode=new Node(x,sentinel,null);
            sentinel.qiannode=sentinel.hounode;
        }
        else {Node p=sentinel.qiannode;
            sentinel.qiannode=new Node(x,p,null);
            p.hounode=sentinel.qiannode;}

    }

    @Override
    public List<T> toList() {
        Node p=sentinel;
        ArrayList<T> list=new ArrayList<>();
        while (p!=null){
            if(p!=sentinel)list.add(p.item);
            p=p.hounode;
        }
        return list;
    }

    @Override
    public boolean isEmpty() {
        if(sentinel.hounode==null) return false;
        else return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if(sentinel.hounode!=null){
            Node p=sentinel.hounode;
            sentinel.hounode=sentinel.hounode.hounode;
            p.hounode.qiannode=sentinel;
            return p.item;
        }
        else return null;
    }

    @Override
    public T removeLast() {
        if(sentinel.hounode!=null){
            Node p=sentinel.qiannode;
            sentinel.qiannode=p.qiannode;
            p.qiannode.hounode=null;
            p.qiannode=null;
            return p.item;
        }
        else return null;
    }

    @Override
    public T get(int index) {
        if(index==size && size>0)return sentinel.qiannode.item;
        if(index<size && index>0){
            if(index>size/2){
                int j=size;
                Node p=sentinel.qiannode;
                while (j!=index){
                    p=p.qiannode;
                    j--;
                }
                return p.item;
            }
            if(index<size/2){
                int j=1;
                Node p=sentinel.hounode;
                while (j!=index){
                    p=p.hounode;
                    j++;
                }
                 return p.item;
            }
        }
        return null;
    }
    Node pointer=sentinel;
    @Override
    public T getRecursive(int index) {
//        if(size==0 || index>size) return null;
//        if(index<size+1){
//
//            pointer=pointer.hounode;
//            return getRecursive(index--);
//        }
//        else return pointer.item;
        return null;

}

    @Override
    public Iterator<T> iterator() {
        return new observer<T>();
    }
    private class observer<T> implements Iterator<T>{
        Node Observer=sentinel.hounode;
        @Override
        public boolean hasNext() {
            if(Observer!=sentinel &&Observer!=null){
                return true;
            }
            else return false;
        }

        @Override
        public T next() {
           // if(hasNext()==true){
            T n=(T)Observer.item;
            Observer=Observer.hounode;
            return n;
            //else return null;

        }
    }
    @Override
    public boolean equals(Object x){
        if(x instanceof LinkedListDeque61B<?> otherLink){
            if(size==otherLink.size()){
                Node point=sentinel;
                int j=0;
                while (j<=this.size){
                    if(point.item==otherLink.get(j)){
                    point=point.hounode;
                    j++;}
                    else return false;
                    if(j==size+1) return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
    @Override
    public String ToString() {
        StringBuilder x=new StringBuilder();
        int n=0;
        x.append("[");
        for(T j:this){
            x.append(j);
            n++;
           if(n<size()) x.append(",");
        }
        x.append("]");
        return x.toString();
    }

    static void main() {
        LinkedListDeque61B j=new LinkedListDeque61B();
        j.addFirst(1);
        j.addLast(12);
        j.addFirst("jj");
        System.out.println(j.ToString());
    }
}

