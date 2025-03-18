kafka:
	sudo systemctl start zookeeper && sudo systemctl start kafka

kafka-stop:
	sudo systemctl stop kafka && sudo systemctl stop zookeeper

kafka-status:
	sudo systemctl status kafka

zookeeper-status:
	sudo systemctl status zookeeper
