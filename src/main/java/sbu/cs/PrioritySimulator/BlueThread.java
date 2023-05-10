package sbu.cs.PrioritySimulator;

import java.util.concurrent.CountDownLatch;

public class BlueThread extends ColorThread {

    private static final String MESSAGE = "hi finished blacks, hi whites!";
    private int delay;
    private CountDownLatch latch;

    public BlueThread(int delay,CountDownLatch latch){
        this.delay = delay;
        this.latch = latch;
    }

    void printMessage() {
        super.printMessage(new Message(this.getClass().getName(), getMessage()));
    }

    @Override
    String getMessage() {
        return MESSAGE;
    }

    @Override
    public void run() {
        // TODO call printMessage
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        latch.countDown();
        printMessage();
    }
}
