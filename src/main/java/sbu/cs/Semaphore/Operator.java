package sbu.cs.Semaphore;

import java.util.concurrent.Semaphore;

public class Operator extends Thread {
    static int count = 0;

    Semaphore semaphore;
    //variable for storing the thread name
    String threadName;
    public Operator(String name,Semaphore semaphore) {
        super(name);
        this.semaphore = semaphore;
        this.threadName = name;
    }

    @Override
    public void run() {

        System.out.println("Starting thread " + threadName);
        try {
            semaphore.acquire();
            System.out.println(threadName + " gets a permit.");

            for (int i = 0; i < 10; i++)
            {
                Resource.accessResource();         // critical section - a Maximum of 2 operators can access the resource concurrently
                count++;
                System.out.println(threadName + ": " + count);
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Thread " + threadName + " releases the permit.");
            semaphore.release();

        }catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
