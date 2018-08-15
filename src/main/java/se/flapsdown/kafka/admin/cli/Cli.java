package se.flapsdown.kafka.admin.cli;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.AdminClient;
import se.flapsdown.kafka.admin.cli.topics.*;
import picocli.CommandLine;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.Callable;

@CommandLine.Command(description = "",
        name = "checksum", mixinStandardHelpOptions = true, version = "checksum 3.0")
public class Cli implements Callable<Void>{

    @CommandLine.Option(names = {"-c", "--config"}, description = "Configuration file")
    public String config = "path";


    @CommandLine.Option(names = {"-s","--bootstrap-servers"})
    private String bootstrapServers = null;

    private AdminClient client;

    private ObjectMapper mapper = new ObjectMapper();

    private ResponseHandler responseHandler;

    public Cli() {
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }


    public AdminClient adminClient() {
        return client;
    }


    public ResponseHandler responseHandler() {
        return responseHandler;
    }


    public static void main(String args[]) {
        CommandLine cl = new CommandLine(new Cli())
                .addSubcommand("topics", new CommandLine(new Topics())
                    .addSubcommand("create", new CreateTopic())
                    .addSubcommand("list", new ListTopics())
                    .addSubcommand("describe", new DescribeTopic())
                    .addSubcommand("delete", new DeleteTopic()));
        cl.parseWithHandler(new CommandLine.RunAll(), args);
    }


    @Override
    public Void call() throws Exception {

        File f = new File(config);
        if (!f.exists()) {
            exit1("File " + config + " does not exist");
        }
        Properties props = new Properties();
        props.load(new FileInputStream(f));
        if (bootstrapServers != null ) {
            props.put("bootstrap.servers", bootstrapServers);
        }
        client  = AdminClient.create(props);
        responseHandler = new ResponseHandler(this);
        return null;
    }


    public void print(Object o) {
        try {
            System.out.println(mapper.writeValueAsString(o));
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    public static void exit1(String msg) {
        System.out.println(msg);
        System.exit(1);
    }
}
