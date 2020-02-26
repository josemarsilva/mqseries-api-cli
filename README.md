# README - mqseries-api-cli

## 1. Introdução

Este é o repositório do projeto **mqseries-api-cli**.

## 2. Documentação

### 2.1. Use Case Diagram

![UseCaseDiagram-Context.png](./doc/UseCaseDiagram-Context.png) 


### 2.2. Deploy Diagram

![DeployDiagram-Context.png](./doc/DeployDiagram-Context.png) 


## 3. Project

### 3.1. Preconditions

* Java JDK 1.8 (jar file includes runnable)
* Eclipse (version Neon recommended)
* Apache Maven 3.6 (recommended > 3.3)
* `pom.xml`:
  * properties:
    * `<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>`
  * dependencies:
    * https://mvnrepository.com/artifact/commons-cli/commons-cli
	* https://mvnrepository.com/artifact/com.ibm.mq/com.ibm.mq.allclient
	* https://mvnrepository.com/artifact/javax/javaee-api/8.0
  * build:
    * `<finalName>mqseries-api-cli</finalName>`
	* `<plugin> <artifactId>maven-assembly-plugin</artifactId> </plugin>`
* [MQSeries instalado - Docker Container](https://github.com/josemarsilva/eval-virtualbox-vm-ubuntu-server#412-docker---mq-series)


### 3.2. Guide for Developers

* Clone source code using "git clone". Use branch "master" if branch "develop" is not available.
* Read, if avaliable, "2. Documentation"  and "3.X. Guides, Patterns, Standard, Conventions and Best Practices" to understand patterns, standards, conventions and best practices used in this project.
* Make your changes, commit and push on "develop" branch. Use branch "master" if branch "develop" is not available. Ask me permission, if not available permission to push.


### 3.3. Guide for Configuration

* n/a


### 3.4. Guide for Test

* n/a

### 3.5. Guide for Deploy

* n/a

### 3.6. Guide for Demonstration

* n/a


### 3.7. Patterns, Standard, Conventions and Best Practices

* n/a


## I - References

* https://github.com/josemarsilva/eval-java-mqseries
* [Point to point with JMS MQSeries Tutorial](https://developer.ibm.com/messaging/learn-mq/mq-tutorials/develop-mq-jms/)
* [Developing Java applications for MQ just got easier with Maven](https://developer.ibm.com/messaging/2018/01/09/developing-mq-java-applications-maven/)
* [https://mvnrepository.com/artifact/com.ibm.mq/com.ibm.mq.allclient/9.1.4.0](https://mvnrepository.com/artifact/com.ibm.mq/com.ibm.mq.allclient/9.1.4.0)
