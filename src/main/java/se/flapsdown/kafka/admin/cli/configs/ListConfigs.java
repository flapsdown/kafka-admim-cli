package se.flapsdown.kafka.admin.cli.configs;

import org.apache.kafka.clients.admin.Config;
import org.apache.kafka.common.acl.*;
import org.apache.kafka.common.config.ConfigResource;
import org.apache.kafka.common.resource.ResourceFilter;
import org.apache.kafka.common.resource.ResourceType;
import picocli.CommandLine;
import se.flapsdown.kafka.admin.cli.acl.ListAcls;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@CommandLine.Command(description = "List configs",  name = "ListAcls")
public class ListConfigs implements Callable<Void> {

    @CommandLine.ParentCommand
    public Configs configs;

    @CommandLine.Option(names = {"-n", "--name"}, description = "Resource name (required)", required = true)
    private String name;

    @CommandLine.Option(names = {"-t", "--type"}, description = "Resource type [any,topic, group, cluster, transaction_id, delegation_token, unknown] (defaults to ANY)")
    private String type = "any";


    public static final AccessControlEntryFilter ANY =
            new AccessControlEntryFilter(null, null, AclOperation.ANY, AclPermissionType.ANY);

    @Override
    public Void call() {

        // Kafka 2.0
        //AclBindingFilter filter =
        //        new AclBindingFilter(new ResourcePatternFilter(ResourceType.TOPIC, name, PatternType.MATCH), ListAcls.ANY);



        List<ConfigResource> collect = Arrays.asList(name.split(","))
                .stream()
                .map(s -> new ConfigResource(ConfigResource.Type.valueOf(type), s))
                .collect(Collectors.toList());

        try {
            Map<ConfigResource, Config> configResourceConfigMap = configs.cli.adminClient().describeConfigs(
                    collect).all().get();

            Map<String, Config> newMap = new HashMap<>();

            for (ConfigResource res : configResourceConfigMap.keySet()) {
                Config config = configResourceConfigMap.get(res);
                newMap.put(res.name(), config);
            }

            configs.cli.print(newMap);


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        return null;
    }
}
