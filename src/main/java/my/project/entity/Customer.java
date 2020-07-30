package my.project.entity;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Customer implements Runnable {

    private String name;
    private Warehouse wh;
    private CyclicBarrier barrier;

    private int totalGoods;
    private int transCount;

    public Customer(String name, Warehouse wh, CyclicBarrier barrier)
    {
        this.name = name;
        this.wh = wh;
        this.barrier = barrier;

        totalGoods = 0;
        transCount = 0;
    }

    public Warehouse getWh() {
        return wh;
    }

    public String getName() {
        return name;
    }

    public int getTotalGoods() {
        return totalGoods;
    }

    public int getTransCount() {
        return transCount;
    }

    public void run()
    {
        Random rand = new Random();
        while(!wh.isEmpty())
        {
            int count = rand.nextInt(10) + 1;
            int boughtGoods = wh.sellGoods(count);

            if (boughtGoods > 0)
            {
                totalGoods += boughtGoods;
                transCount++;

                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    System.out.println("Customer " + name + "'s shopping was interrupted");
                    printResults();
                    return;
                } catch (BrokenBarrierException e) {
                    break;
                }
            }
        }

        if (!barrier.isBroken())
            barrier.reset();

        printResults();
    }

    public void startShopping()
    {
        new Thread(this).start();
    }

    private void printResults()
    {
        System.out.println("Customer " + name + " has bought " + totalGoods +
                " products in " + transCount + " transactions");
    }
}
