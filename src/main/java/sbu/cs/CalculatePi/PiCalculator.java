package sbu.cs.CalculatePi;

import java.math.BigDecimal;
import java.math.MathContext;

public class PiCalculator {

    /**
     * Calculate pi and represent it as a BigDecimal object with the given floating point number (digits after . )
     * There are several algorithms designed for calculating pi, it's up to you to decide which one to implement.
     Experiment with different algorithms to find accurate results.

     * You must design a multithreaded program to calculate pi. Creating a thread pool is recommended.
     * Create as many classes and threads as you need.
     * Your code must pass all of the test cases provided in the test folder.

     * @param floatingPoint the exact number of digits after the floating point
     * @return pi in string format (the string representation of the BigDecimal object)
     */

    public static final int MAX = 10000000;
    public static final int STEP = 1000000;
    public static final int THREADS_COUNT = 5;
    public static int current = 0;
    public static BigDecimal[] numbers = new BigDecimal[MAX];

    private static class ComputationThread extends Thread {
        int start;

        public ComputationThread(int start) {
            this.start = start;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    compute();
                    start = increment();
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
        }

        private synchronized int increment() {
            current += STEP;
            return current;
        }

        private void compute() {
            BigDecimal half = new BigDecimal("0.5");
            MathContext mc = new MathContext(100);
            BigDecimal one = new BigDecimal("1");
            BigDecimal four = new BigDecimal("4");
            for (int i = start; i < start + STEP & i < MAX; i++) {
                BigDecimal counter=new BigDecimal(i);
                BigDecimal s=counter.add(half,mc);
                BigDecimal max1=new BigDecimal(MAX,mc);
                BigDecimal x = (s.divide(max1,mc));
                x=x.pow(2,mc);
                BigDecimal s2=one.add(x,mc);
                BigDecimal multi=s2.multiply(max1,mc);
                numbers[i] = four.divide(multi,mc);
                if (i + 1 == MAX) throw new IndexOutOfBoundsException();
            }
        }
    }

    public String calculate(int floatingPoint)
    {
        // TODO
        MathContext mc = new MathContext(floatingPoint);
        Thread[] threads = new Thread[THREADS_COUNT];
        // create thread pool
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new ComputationThread(i * current);
        }
        // start threads
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i].start();
        }
        // wait end of computation
        for (int i = 0; i < THREADS_COUNT; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        BigDecimal sum =new BigDecimal("0");
        for (int i = 0; i < MAX; i++) {
            sum =sum.add( numbers[i],mc);
        }
        return sum.toString();

    }


}
