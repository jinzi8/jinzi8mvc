package com.jinzi8.framework.util;

/**
 * @author 博哥
 * @create 2019-03-26 11:04
 */
public class SequenceB implements  Sequence {
    private static ThreadLocal<Integer> numberContainer = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue(){
            return 0;
        }
    };

    public int getNumber() {
        numberContainer.set(numberContainer.get()+1);
        return numberContainer.get();
    }

    public static void main(String[] args) {
        SequenceB SequenceB = new SequenceB();
        ClientThread thread1 = new ClientThread(SequenceB);
        ClientThread thread2 = new ClientThread(SequenceB);
        ClientThread thread3 = new ClientThread(SequenceB);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
