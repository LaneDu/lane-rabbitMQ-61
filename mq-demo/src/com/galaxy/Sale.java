package com.galaxy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * @author lane
 * @date 2021年08月15日 下午4:00
 */
public class Sale {
    public static void main(String[] args) {
        BlockingQueue<Mask> queue = new ArrayBlockingQueue<>(20);

        new Thread(new Provider(queue)).start();

        new Thread(new Consumer(queue)).start();

    }


}
