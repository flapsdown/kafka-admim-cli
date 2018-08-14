package se.flapsdown.kafka.admin;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.acl.AclBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    static short apa = 1;

    static Map map = new HashMap<String, Object>(){{ put("bootstrap.servers","localhost:9092");}};


    private final AdminClient client =AdminClient.create(map);

    public static void main( String[] args ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        NewTopic topic = new NewTopic(args[0],1, apa);

        CreateTopicsResult topics = new App().client.createTopics(Arrays.asList(topic));
        KafkaFuture<Void> all = topics.all();

        try {
            all.get();
            System.out.println(mapper.writeValueAsString(topic));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            String s = mapper.writeValueAsString(e.getCause());
            //System.out.println(s);
            e.printStackTrace();
        }


    }
}
