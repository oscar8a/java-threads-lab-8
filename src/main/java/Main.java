import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future> futuresList = new ArrayList<>();

        IntStream.rangeClosed(1, 5)
                .forEach(i -> {
                    Future<Integer> futureInt = executor.submit(() -> {
                        try {
                            Thread.sleep(i * 1000L);
                            return i;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    futuresList.add(futureInt);
                });

        countFinishedFutures(futuresList);
        executor.shutdown();
    }

    public static int countFinishedFutures(List<Future> futures) throws InterruptedException {
        int count = 0;
        Thread.sleep(2500);
        for (Future future : futures) {
            System.out.println(future.toString());
            if(future.isDone()) count += 1;
        }
        return count;
    }
}