package se.flapsdown.kafka.admin.cli.configs;

import picocli.CommandLine;
import se.flapsdown.kafka.admin.cli.Cli;

import java.util.concurrent.Callable;

@CommandLine.Command(description = "Grouping of actions based on configs",
        name = "Configs", mixinStandardHelpOptions = true)
public class Configs implements Callable<Void> {

    @CommandLine.ParentCommand
    public Cli cli;

    @Override
    public Void call() throws Exception {

        return null;
    }

}
