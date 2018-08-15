package se.flapsdown.kafka.admin.cli;

import org.apache.kafka.common.KafkaFuture;

import java.util.concurrent.ExecutionException;

public class ResponseHandler {

    private final Cli cli;

    public ResponseHandler(Cli cli) {
        this.cli = cli;
    }

    public void waitForResponse(KafkaFuture<?> future) {
        try {
            Object o = future.get();
            cli.print(o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            cli.print(new Failed(e.getCause().getMessage()));
        }
    }

    public void waitForEmptyResponse(KafkaFuture<?> future) {
        try {
            Object o = future.get();
            cli.print(new Ok());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            cli.print(new Failed(e.getCause().getMessage()));
        }
    }

    private static class Ok {
        boolean success = true;
    }

    private static class Failed {

        boolean success = false;
        final String message;

        Failed(String message) {
            this.message = message;
        }
    }
}
