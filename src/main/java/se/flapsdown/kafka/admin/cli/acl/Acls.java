package se.flapsdown.kafka.admin.cli.acl;

import picocli.CommandLine;
import se.flapsdown.kafka.admin.cli.Cli;

import java.util.concurrent.Callable;

@CommandLine.Command(description = "Grouping of actions based on acl",
        name = "Acls", mixinStandardHelpOptions = true)
public class Acls implements Callable<Void> {

    @CommandLine.ParentCommand
    public Cli cli;

    @Override
    public Void call() throws Exception {
        return null;
    }

}
