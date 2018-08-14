package se.flapsdown.kafka.admin;


import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.acl.AclBinding;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    static short apa = 1;

    static Map map = new HashMap<String, Object>(){{ put("bootstrap.servers","localhost:9200");}};
    static NewTopic topic = new NewTopic("topic",1, apa);

    private final AdminClient client =AdminClient.create(map);

    public static void main( String[] args )
    {

        new App().client.createTopics(Arrays.asList(topic));

    }
}
