#!/usr/bin/env bash
#
# 워커노드의 패키지를 설치한다.
# @author : Sang Wook, Song
#
# @param 1 : zookeeper address		zk://192.168.2.44:2181,192.168.2.45:2181,192.168.2.46:2181/mesos
# @param 2 : mesos slave public ip
# @param 3 : mesos slave private ip
# @param 4 : mesos container. Comma separated list of containerizer. "docker" for docker, "mesos" for mesos
# @param 5 : docker registry address        192.168.2.40:5000
#

#zk
echo $1 | sudo tee /etc/mesos/zk

#public IP
echo $2 | sudo tee /etc/mesos-slave/hostname

#private IP
echo $3 | sudo tee /etc/mesos-slave/ip

#containerizers
echo $4 | sudo tee /etc/mesos-slave/containerizers

#docker configuration
echo DOCKER_OPTS=\"\$DOCKER_OPTS --insecure-registry $5:5000\" | sudo tee -a /etc/default/docker
