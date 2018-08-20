# Microservices best practices

This project was initiated to apply best practices and patterns for microservices using Spring Boot technology.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system, for my example i am using Linux CentOS 7, Other OS should have similar steps.

### Prerequisites

* [Java 8+](http://openjdk.java.net/install/) - Programming language
* [Maven](https://maven.apache.org/) - Dependency Management 
* [Docker](https://www.docker.com/) - Containerization tool
* [Docker-Compose](https://docs.docker.com/compose/install/) - Container Orchestration tool
* [Dockerize](https://github.com/jwilder/dockerize) - Used in docker-compose for service dependencies
* [Postgresql](https://www.postgresql.org/) - RDBMS (or you can choose any DB that you are comfortable with)

### Before you start

Take a good look on docker and docker-compose, try to build your docker image, deploy it to docker and run it, and try using docker-compose to run your image with other data image (ex: db image).

Since we are learning and practicing microservices, it is highly recommended to know about docker, thats why i am asking you to do the above.  

### Installing

#### Installing Java
First you need to check what java version installed on your machine, in your terminal type :
```
java -version
```

if output contains 'jre' keywork then you need to install java jdk version, you can follow this site
for java installation [How to install java on linux](https://tecadmin.net/install-java-8-on-centos-rhel-and-fedora/).

#### Installing Maven
After Java JDK version is being installed, we need to install Maven from Apache site, in your terminal type :

```
cd /usr/local
wget http://www-eu.apache.org/dist/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz
```

Now Extract your downloaded file :

```
sudo tar xzf apache-maven-3.5.4-bin.tar.gz
sudo ln -s apache-maven-3.5.4 maven
```
Once your extraction is finished successfully we'll set environment variables for maven :

```
sudo vi /etc/profile.d/maven.sh
```

after opening file using vim tool (vi), click on 'i' keyboard, this will activate vi in editing mode, 
navigate to the end of your file and type :

```
export M2_HOME=/usr/local/maven
export PATH=${M2_HOME}/bin:${PATH}
```
then click on 'esc' button and type ' :wq ', it is a command shortcut to write and quit.

reload your environment variables with this command :

```
source /etc/profile.d/maven.sh
```

check your installation by typing 'mvn -version', you should see output similar to the below  :

```
mvn -version
Apache Maven 3.5.0 (ff8f5e7444045639af65f6095c62210b5713f426; 2017-04-03T23:39:06+04:00)
Maven home: /opt/maven
Java version: 1.8.0_181, vendor: Oracle Corporation
Java home: /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.181-3.b13.el7_5.x86_64/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "3.10.0-862.9.1.el7.x86_64", arch: "amd64", family: "unix"
```

#### Installing Docker
For installing docker, follow docker documentation on their official [website](https://docs.docker.com/install/linux/docker-ce/centos/), and choose your OS from left menu.

#### Installing Docker Compose
In your terminal run the below command :

```
sudo curl -L https://github.com/docker/compose/releases/download/1.22.0/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose
```

Then add executable permissions to your binary :

```
sudo chmod +x /usr/local/bin/docker-compose
```

Test your installation :

```
docker-compose --version
```

## Project structure

Project is consisted of main project (microservices) and it contain each service as a sub module also there is <b>confsrv</b> which will be used as a centralized config service provider.

Microservices main project contains pom.xml which includes sub modules definition, common properties, dependencies and plugins.

Common plugins in main projects are :
1- <b>Maven Check style</b> : it is static code analysis for your java code sytles & indents, you can run it by navigate to your folder and execute the below maven target :

```
mvn checkstyle:checkstyle
```

if you run this command on microservices project folder it will run against all sub modules and this will apply to the rest of plugins.

2- <b>Maven find bugs</b> : static code analysis to determine common mistakes by developers like static encoding and using null references, it can be run using the below target :

```
mvn findbugs:check
```

3- <b>Maven Dockerfile plugin</b> : this plugin reads Dockerfile in your project class path and it can build & deploy docker images, this plugin is not included in the main project, it is included in each service of your project, to build and deploy docker image navigate to your service (ex:confsrv) then run the below where Dockerfile & pom.xml exists :

```
mvn package
```

### Running services locally (without docker)
1-First you need to run configuration server, navigate to /confsrv folder and run :

```
mvn spring-boot:run
```
for more information about configuration server check confsrv [README.md](https://github.com/iabughosh/microservices/tree/master/confsrv).

2-Then you can run any service after that with the same way

### Running services with docker
1-Make sure that you've created and deployed your image successfully to docker using mvn deploy command.
2-At the root main project (microservices) you'll find docker-compose.yml file, in your command line type :

```
docker-compose up
```
This command will create docker network adapters between services inside and start them together, then services can integrate with each other internally by name (licensesrv,confsrv & db). 

examine service "licensesrv" attributes and check attribute <b>depends_on</b>, in this attribute you define services that licensesrv needs before starting up,it will fire db & confsrv services then starts licensesrv.

Seems fine now, we are assuring that licensesrv will start after db & confsrv is up, but is that enough !!!.

Actually no it is not, service confsrv will really starts before licensesrv, but the problem that confsrv needs 5-15 second to completely finish it start up process and if licensesrv try to boot while confsrv is not ready yet licensesrv will shutdown with error code because confsrv is not ready yet, so we are in trouble here. 

licensesrv needs confsrv up and running and in a healthy state, how can we assure such check before really starts licensesrv !!!.

"dockerize" tool to the rescue, check again docker-compse file 'licensesrv -> command' section, you will find 

```
dockerize -wait http://confsrv:8888/actuator/health -timeout 60s -wait-retry-interval 15s /run.sh
```
Here i am overriding licensesrv's image command with dockerized command, i am waiting when serving is up by checking confsrv health service (/actuator/health) provided by spring actuator, when it is up then licensesrv will run "/run.sh".

## Building tools

* [Maven](https://maven.apache.org/) - Dependency Management

## Contributing

Participating in project are most welcome, but no direct push to master, just a reviewed pull request will be merged into master,
committed code should be well formatted and documented.

## Authors

* **Ibrahim Abu Ghosh** - *Initial work* - [iabughosh](https://github.com/iabughosh)

See also the list of [contributors](https://github.com/iabughosh/microservices/graphs/contributors) who participated in this project.

## License

This project is licensed under the GPL License - see the [LICENSE.md](https://github.com/iabughosh/microservices/blob/master/LICENSE) file for details

## Acknowledgments

* Special thanks to [John Carnell](https://github.com/carnellj) author of book microservices in action.