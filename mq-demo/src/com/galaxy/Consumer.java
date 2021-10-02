package com.galaxy;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * @author lane
 * @date 2021年08月15日 下午3:00
 */
public class Consumer implements Runnable {

    private BlockingQueue<Mask> queue;

    public Consumer(BlockingQueue<Mask> queue){
        this.queue = queue;
    }


    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(200);
                Mask mask = queue.take();
                System.out.println("正在出售口罩的ID"+mask.getId()+"口罩的类型"+mask.getType());


            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
