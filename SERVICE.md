## Steps to create zookeeper as system service

```
sudo nano /etc/systemd/system/zookeeper.service
```

Add the following lines to the file

```
[Unit]
Description=Apache Zookeeper Service
After=network.target

[Service]
Type=simple
ExecStart=/usr/local/kafka/bin/zookeeper-server-start.sh /usr/local/kafka/config/zookeeper.properties
ExecStop=/usr/local/kafka/bin/zookeeper-server-stop.sh
Restart=on-abnormal

[Install]
WantedBy=multi-user.target
```

**Note** : Change the path of `zookeeper-server-start.sh` and `zookeeper-server-stop.sh` according to your installation path.



## Steps to create kafka as system service

```
sudo nano /etc/systemd/system/kafka.service
```

Add the following lines to the file

```
[Unit]
Description=Apache Kafka
After=network.target zookeeper.service

[Service]
User=sub-escanor
Group=sub-escanor
ExecStart=/usr/local/kafka/kafka_2.13-3.9.0/bin/kafka-server-start.sh /usr/local/kafka/config/server.properties
ExecStop=/usr/local/kafka/kafka_2.13-3.9.0/bin/kafka-server-stop.sh
Restart=on-failure

[Install]
WantedBy=multi-user.target

```

**Note** : Change the path of `kafka-server-start.sh` and `kafka-server-stop.sh` according to your installation path.

### At the end

```
sudo systemctl daemon-reload
sudo systemctl enable zookeeper
sudo systemctl enable kafka
sudo systemctl start zookeeper
sudo systemctl start kafka
```

