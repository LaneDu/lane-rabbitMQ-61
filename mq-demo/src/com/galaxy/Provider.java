package com.galaxy;

import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * @author lane
 * @date 2021年08月15日 下午3:00
 */
public class Provider implements Runnable {

    private BlockingQueue<Mask> queue;

    public Provider(BlockingQueue<Mask> queue){
        this.queue = queue;
    }

    private int index;

    @Override
    public void run() {
        while (true){

            try {
                Thread.sleep(500);
                if (queue.remainingCapacity()<=0){

                    System.out.println("口罩生产仓库已满！");
                }
                else {
                    Mask mask = new Mask();
                    mask.setId(index++);
                    mask.setType("N95");
                    System.out.println("正在生产口罩id: " + (index - 1));
                    queue.put(mask);
                    System.out.println("仓库口罩个数" + queue.size());
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
}
