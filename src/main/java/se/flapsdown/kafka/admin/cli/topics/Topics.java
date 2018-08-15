package se.flapsdown.kafka.admin.cli.topics;

import picocli.CommandLine;
import se.flapsdown.kafka.admin.cli.Cli;

import java.util.concurrent.Callable;

@CommandLine.Command(description = "Grouping of actions based on topics",
        name = "Topics", mixinStandardHelpOptions = true)
public class Topics implements Callable<Void> {

    @CommandLine.ParentCommand
    public Cli cli;

    @Override
    public Void call() throws Exception {
        return null;
    }
}
