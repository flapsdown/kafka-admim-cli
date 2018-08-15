package se.flapsdown.kafka.admin.cli.topics;

import org.apache.kafka.clients.admin.DeleteTopicsResult;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.concurrent.Callable;

@CommandLine.Command(description = "Delete topic",  name = "DeleteTopic")
public class DeleteTopic implements Callable<Void> {

    @CommandLine.ParentCommand
    private Topics topics;

    @CommandLine.Option(names = {"-n", "--name"}, description = "Topic name", required = true)
    private String name;

    @Override
    public Void call() throws Exception {
        DeleteTopicsResult result = this.topics.cli.adminClient().deleteTopics(Arrays.asList(name));
        topics.cli.responseHandler().waitForEmptyResponse(result.all());
        return null;
    }
}
