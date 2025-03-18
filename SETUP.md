# The Setup Guide

## Install necessary software/depedencies

1. vsftpd
2. Java 17+
3. Kafka

## Setup vsftpd

1. Install vsftpd
```bash
sudo apt-get install vsftpd
```

2. Configure vsftpd
```bash
sudo nano /etc/vsftpd.conf
```

3. Restart vsftpd
```bash
sudo systemctl restart vsftpd
```

## Setup Java

1. Install Java 17
```bash
sudo apt-get install openjdk-17-jdk
```

2. Verify Java installation
```bash
java -version
```

## Setup Kafka

Download Kafka from [https://kafka.apache.org/downloads](https://kafka.apache.org/downloads)

1. Extract the downloaded file
```bash
tar -xzf kafka_2.13-<version>.tgz
cd kafka_2.13-<version>
```

2. Start Zookeeper
```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
```

3. Start Kafka
```bash
bin/kafka-server-start.sh config/server.properties
```

4. Check if Kafka is running
```bash
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

## Setup the Project

1. Configure application.properties according to your environment/server.
2. Run the project jar after downloading using the following command
```bash
java -jar <project-jar-name>.jar
```

## Usage

1. Open the web application in a browser
2. Upload a document
3. Verify the document
4. View the status of the document

## For Configuration check [CONFIGURATION.md](CONFIGURATION.md)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Blockchain](https://en.wikipedia.org/wiki/Blockchain)
- [Java](https://www.java.com/)