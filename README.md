# HackathonNest

This project for transfer money through mt103 message and convert int MX message
## Architecture
![My First Board (3)](https://user-images.githubusercontent.com/62478714/187073017-0f4008c2-c268-4234-b4de-c279db28baa4.jpg)

## Database
Mysql Db name: mtbank

## kafka Topic Crea

kafka-topics.sh --create --topic mtmessage --bootstrap-server localhost:9092

kafka-topics.sh --create --topic mxmessage --bootstrap-server localhost:9092 

## Listen Kafka Messages

kafka-console-consumer.sh --topic mtmessage  --from-beginning --bootstrap-server localhost:9092


kafka-console-consumer.sh --topic mxmessage  --from-beginning --bootstrap-server localhost:9092


### Then Run the 4 springboot application
