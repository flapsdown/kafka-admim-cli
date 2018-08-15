# kafka-admin-cli

## Why?

To automate kafka management we need structured output while all kafka-cli return human readable output only.
This tool will output everything as json so you can use scripts, jq and any other tool that you need.

## How

Kafka AdminClient documentation is found at https://kafka.apache.org/documentation/#adminclientconfigs.


## Usage

This cli tries to mimic the aws-cli and perhaps also git in that we supply commands and subcommands. I.e topics is
a command with subcommands list, create, remove.



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

### Create topic

    kafka topics create --name topic_name --num-partitions 2 --replication-factor 3


### Delete topic

    kafka topics delete --name topic_name