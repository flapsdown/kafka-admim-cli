package se.flapsdown.kafka.admin.cli.acl;

import org.apache.kafka.common.acl.*;
import org.apache.kafka.common.resource.*;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(description = "List acls",  name = "ListAcls")
public class ListAcls implements Callable<Void> {

    @CommandLine.ParentCommand
    public Acls acls;

    @CommandLine.Option(names = {"-t", "--topic"}, description = "Topic name", required = true)
    private String name;


    public static final AccessControlEntryFilter ANY =
            new AccessControlEntryFilter(null, null, AclOperation.ANY, AclPermissionType.ANY);

    @Override
    public Void call() {

        AclBindingFilter filter =
                new AclBindingFilter(new ResourcePatternFilter(ResourceType.TOPIC, name, PatternType.MATCH), ListAcls.ANY);

        acls.cli.responseHandler().waitForResponse(acls.cli.adminClient().describeAcls(filter).values());

        return null;
    }
}
