# Simple Spring Boot REST API
Um exemplo de implementação spring boot rest java com CQRS

Depêndecias do projeto: 

- Axon (Spring Boot starter)
- Spring Data JPA
- Freemarker
- Web
- Reactor
- Mysql
- Spring Boot Test
- Axon Test

Como executar

```docker
$ docker pull mysql
$ docker pull axoniq/axonserver
$ docker run --name galaxias_db -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=chat_db -e MYSQL_USER=root -e MYSQL_PASSWORD=root --publish 3306:3306 -d mysql/mysql-server:latest
$ docker run -d -p 8024:8024 -p 8124:8124 -p 8224:8224 --name axonserver axoniq/axonserver
```