## CI/CD 
### CI/CD using Jenkins (Self-hosted tool)
Jenkinsfile
agent {
docker {
image 'maven:3.8.1-adoptopenjdk-11'
args '-v $HOME/.m2:/root/.m2'
}
}
### CI/CD AWS


#### Book library is app that should be deployed to Tomcat on EC2 instance
#### Build and Run (for more see [Creating a Multi Module Project](https://github.com/spring-guides/gs-multi-module)):
```
cd demo-pipeline-jenkins
$ ./mvnw clean install
```
#### URL
[http://localhost:9094](http://localhost:9094)
#### H2 console
```
http://localhost:9091/h2-console/
the default jdbc url : jdbc:h2:mem:testdb
username: sa
password leave empty
```

#### HTTP client in IntelliJ IDEA code editor

Open file `https/book.http` and test REST web-service.
