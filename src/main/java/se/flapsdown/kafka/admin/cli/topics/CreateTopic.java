package se.flapsdown.kafka.admin.cli.topics;

import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.concurrent.Callable;

@CommandLine.Command(description = "Create Topic",  name = "CreateTopic")
public class CreateTopic implements Callable<Void> {

    @CommandLine.ParentCommand
    private Topics topics;

    @CommandLine.Option(names = {"-n", "--name"}, description = "Topic name", required = true)
    private String name;

    @CommandLine.Option(names = {"-p", "--num-partitions"}, description = "Number of partitions in topic", required = true)
    private int numPartitions;

    @CommandLine.Option(names = {"-r", "--replication-factor"}, description = "Replication factor for topic", required = true)
    private short replicationFactor;

    @Override
    public Void call() throws Exception {
        CreateTopicsResult result = this.topics.cli.adminClient().createTopics(Arrays.asList(new NewTopic(name, numPartitions, replicationFactor)));
        topics.cli.responseHandler().waitForEmptyResponse(result.all());
        return null;
    }
}
