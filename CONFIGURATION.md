# Configurations Regarding installation of the various tools and dependencies required for the project.

1. vsftpd
```
-- Allow vsftpd
sudo ufw allow 21/tcp

--- Allow read/write access to the directory through config file
sudo nano /etc/vsftpd.conf

--- Add the following lines to the config file
write_enable=YES
local_root=/path/to/your/directory
local_umask=022

--- Restart vsftpd
sudo systemctl restart vsftpd
```

2. Kafka
```
--- Allow kafka port
sudo ufw allow 9092/tcp

--- Allow zookeeper port
sudo ufw allow 2181/tcp

--- Add IP in server.properties (/<your-kafka-location>/kafka/config/server.properties)
listeners=PLAINTEXT://<IP>:9092
```

3. (Optional but recommended) Make Kafka and Zookeeper systemd services : [SERVICES.md](SERVICES.md)
