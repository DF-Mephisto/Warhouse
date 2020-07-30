package my.project;

import my.project.entity.Customer;
import my.project.entity.Warehouse;

import java.util.concurrent.CyclicBarrier;

public class Main {
    public static void main(String ... args)
    {
        if (args.length != 1)
            System.out.println("Run parameters should be: [NumberOfCustomers]");
        else
        {
            try
            {
                Integer customersNum = Integer.valueOf(args[0]);
                process(customersNum);
            }
            catch(NumberFormatException e)
            {
                System.out.println("Wrong number format: " + args[0]);
            }
        }
    }

    private static void process(Integer customersNum)
    {
        Warehouse wh = new Warehouse(1000);
        CyclicBarrier barrier = new CyclicBarrier(customersNum);

        for (Integer i = 1; i <= customersNum; i++)
            new Customer(i.toString(), wh, barrier).startShopping();
    }
}