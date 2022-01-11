public class PingPong implements Runnable {
    private static class Ball {
        protected synchronized void hit(String name, int delay) {
            System.out.print(name + " -> ");
            try {
                notify();
                wait();
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + name);
            }
        }
    }

    private String name;
    private int delay;
    private static Ball ball;

    public PingPong(String name, int delay) {
        this.name = name;
        this.delay = delay;
    }

    public static void main(String[] args) {
        ball = new Ball();
        PingPong gamer1 = new PingPong("Ping", 250);
        PingPong gamer2 = new PingPong("Pong", 500);
        System.out.println("Start game...");
        Thread t1 = new Thread(gamer1);
        Thread t2 = new Thread(gamer2);
        t1.start();
        t2.start();
        try {
            t1.join((gamer1.delay + gamer2.delay) * 12);
            t2.join((gamer1.delay + gamer2.delay) * 12);
            synchronized (ball) {
                ball.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nGame over");
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            ball.hit(name, delay);
        }
    }
}
