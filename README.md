# kafka-admin-cli

## Why?

To automate kafka management we need structured output while all kafka-cli return human readable output only.
This tool will output everything as json so you can use scripts, jq and any other tool that you need.

## How

Kafka AdminClient documentation is found at https://kafka.apache.org/documentation/#adminclientconfigs.


## Usage

This cli tries to mimic the aws-cli and perhaps also git in that we supply commands and subcommands. I.e topics is
a command with subcommands list, create, remove.


### Topics

### List topics

    kafka -s broker1:9092 -c property_file topics list

    [
      {
        "name": "topic_1",
        "internal": false
      },
      {
        "name": "topic_2",
        "internal": false
      }
    ]

#### Create topic

    kafka topics create --name topic_name --num-partitions 2 --replication-factor 3


#### Delete topic

    kafka topics delete --name topic_name

#### Describe topic

### Configs

#### List configs

*Currently we can only list a single resource at a time*

    ./kafka configs list -t [topic|broker] -n <name_of_resource>

Print human readable summary

    ./kafka configs list -t topic -n topic_name | jq --raw-output '.[].entries[] | .name + ": " + .value + " || source: " + .source '

    compression.type: producer || source: DEFAULT_CONFIG
    message.format.version: 1.0-IV0 || source: STATIC_BROKER_CONFIG
    file.delete.delay.ms: 60000 || source: DEFAULT_CONFIG
    leader.replication.throttled.replicas:  || source: DEFAULT_CONFIG
    max.message.bytes: 1000012 || source: DEFAULT_CONFIG
    ...

Print customized(altered) configs only

     ./kafka configs list -t topic -n topic_name | jq --raw-output | jq '.[].entries[] | select(.source == "DYNAMIC_TOPIC_CONFIG")'

     {
       "name": "segment.ms",
       "value": "1800000",
       "source": "DYNAMIC_TOPIC_CONFIG",
       "isSensitive": false,
       "isReadOnly": false,
       "synonyms": []
     }
     {
       "name": "retention.ms",
       "value": "1800000",
       "source": "DYNAMIC_TOPIC_CONFIG",
       "isSensitive": false,
       "isReadOnly": false,
       "synonyms": []
     }

Print configs in "original cli" format

    ./kafka configs list -t topic -n topic_name | \
        jq --raw-output \
        '.[].entries[] | select(.source == "DYNAMIC_TOPIC_CONFIG") | .name + "=" + .value'

    segment.ms=1800000
    retention.ms=1800000