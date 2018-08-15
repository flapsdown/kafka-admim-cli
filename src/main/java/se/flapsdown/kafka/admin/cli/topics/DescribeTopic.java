package se.flapsdown.kafka.admin.cli.topics;

import org.apache.kafka.clients.admin.DescribeTopicsResult;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.concurrent.Callable;

@CommandLine.Command(description = "Describe topics",  name = "DescribeTopics")
public class DescribeTopic implements Callable<Void> {

    @CommandLine.ParentCommand
    public Topics topics;


    @CommandLine.Option(names = {"-n", "--name"}, description = "Topic name", required = true)
    private String name;

    @Override
    public Void call() throws Exception {

        DescribeTopicsResult listTopicsResult = topics.cli.adminClient().describeTopics(Arrays.asList(name));

        topics.cli.responseHandler().waitForResponse(listTopicsResult.all());

        return null;
    }
}
