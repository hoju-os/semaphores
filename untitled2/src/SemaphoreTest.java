import java.util.concurrent.Semaphore;

public class SemaphoreTest {

    private static Semaphore semA = new Semaphore(1);
    private static Semaphore semB = new Semaphore(0);
    private static Semaphore semC = new Semaphore(0);
    private static Semaphore mutex = new Semaphore(1);
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {

        while (true){
            Thread A = new Thread(new A());
            Thread B = new Thread(new B());
            Thread C = new Thread(new C());
            A.start();
            B.start();
            C.start();
            Thread.sleep(1000);
        }

    }

    private static class A implements Runnable{
        @Override
        public void run() {
//            System.out.println("A");

            try {
                semA.acquire();
                mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("A");
            count++;
            semB.release();
            mutex.release();
        }
    }

    private static class B implements Runnable{
        @Override
        public void run() {
            try {
                semB.acquire();
                mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B");
            count++;
            if (count == 4){
                semC.release();
                count = 0;
            }
            else
                semA.release();
            mutex.release();
        }
    }

    private static class C implements Runnable{
        @Override
        public void run() {
            try {
                semC.acquire();
                mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("C");
            semA.release();
            mutex.release();

        }
    }

}


