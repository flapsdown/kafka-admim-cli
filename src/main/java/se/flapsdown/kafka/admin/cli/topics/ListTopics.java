package se.flapsdown.kafka.admin.cli.topics;

import org.apache.kafka.clients.admin.ListTopicsResult;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(description = "List topics",  name = "ListTopics")
public class ListTopics implements Callable<Void> {

    @CommandLine.ParentCommand
    public Topics topics;

    @Override
    public Void call() throws Exception {

        ListTopicsResult listTopicsResult = topics.cli.adminClient().listTopics();

        topics.cli.responseHandler().waitForResponse(listTopicsResult.listings());

        return null;
    }
}
