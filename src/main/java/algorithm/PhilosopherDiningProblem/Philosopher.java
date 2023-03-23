package algorithm.PhilosopherDiningProblem;

import algorithm.SleepHelper;

/**
 * 哲学家
 */
public class Philosopher extends Thread {
    private Chopstick left, right;  //筷子
    private int index;  //位置

    public Philosopher(String name, Chopstick left, Chopstick right, int index) {
        this.setName(name + index);
        this.left = left;
        this.right = right;
        this.index = index;
    }

    @Override
    public void run() {
        if(this.index <= 1) {
            synchronized (this.left) {
                SleepHelper.sleepSeconds(1);
                synchronized (this.right) {
                    SleepHelper.sleepSeconds(1);
                }
            }
        } else {
            synchronized (right) {
                SleepHelper.sleepSeconds(1);
                synchronized (this.left){
                    SleepHelper.sleepSeconds(1);
                }
            }
        }
        System.out.println(this.getName()+"号 已经吃完");
    }

}
