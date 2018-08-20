# Configuration server

People who works in microservices project especially if there projects are hosted in containers platforms such as docker have a problem where to put there service configuration and how to make it dynamic, remember that after building docker image and pushing this image to some kind of docker image repository it become immutable and you can't modify it, so what can we do to achieve such goal.

Spring provides a solution for this with module called "Spring Boot Cloud Server", it allows you to encapsulate all your configuration files for all your services and put it in a centralized place, and provide clients for your services to communicate with configuration server.

### Configuration server config files setup
In this repository i am using file system module for development only, if you want to run Configuration server in production it is recommended to do this through GIT which by default gives you versions feature for your configuration file.

Check application.yml resource file, I used classpath files as configuration files for my project.

### Encryption support setup
if you check configuration files you will find some properties values starts with "{encrypt}", these properties are encrypted using spring cloud encryption services, to enable this feature you need to do the below on confsrv :

#### Configuration server setup
1-Set environment variable "ENCRYPT_KEY" to a value which will represents your encryption key.
2-Start your configuration server and check server logs, you will find URLS "/encrypt [POST]" & "/decrypt [POST]", you can encrypt your properties using these urls.
3-In bootstrap.yml file set property 

```
spring:
  cloud:
     config:
       server:
           encrypt:
              enabled: false
```

if you leave it as its default value "true" then when you call configuration url "http://host:port/{service}/{profile}" it will return property decrypted which is a behavior that we don't want it on production for sure.

#### Consumer server setup (ex:licensesrv)
1-Set environment variable "ENCRYPT_KEY" to same key value as Configuration server.
2-Add the below RSA dependencies to enable properties decryption on server side :

```
	<dependency>
	    <groupId>org.springframework.security</groupId>
	    <artifactId>spring-security-rsa</artifactId>
	    <version>1.0.7.RELEASE</version>
	</dependency>
```