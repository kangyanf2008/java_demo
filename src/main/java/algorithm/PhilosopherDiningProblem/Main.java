package algorithm.PhilosopherDiningProblem;

public class Main {
    public static void main(String[] args) {
        //筷子
        Chopstick c1 = new Chopstick();
        Chopstick c2 = new Chopstick();
        Chopstick c3 = new Chopstick();
        Chopstick c4 = new Chopstick();
        Chopstick c5 = new Chopstick();

        Philosopher p1 = new Philosopher("哲学家", c1, c2, 1);
        Philosopher p2 = new Philosopher("哲学家", c2, c3, 2);
        Philosopher p3 = new Philosopher("哲学家", c3, c4, 3);
        Philosopher p4 = new Philosopher("哲学家", c4, c5, 4);
        Philosopher p5 = new Philosopher("哲学家", c5, c1, 5);

        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();

    }
}
