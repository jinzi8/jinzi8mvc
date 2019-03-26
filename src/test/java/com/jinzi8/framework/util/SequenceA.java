package com.jinzi8.framework.util;

/**
 * @author 博哥
 * @create 2019-03-26 11:04
 */
public class SequenceA implements  Sequence {
    private static int number = 0 ;
    public int getNumber() {
        number = number+1;
        return number;
    }

    public static void main(String[] args) {
        SequenceA sequenceA = new SequenceA();
        ClientThread thread1 = new ClientThread(sequenceA);
        ClientThread thread2 = new ClientThread(sequenceA);
        ClientThread thread3 = new ClientThread(sequenceA);

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
