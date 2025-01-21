
### Before running the project
- Install JDK 17
- Install MongoDB Community Edition
- Install Docker Desktop
- Install Postman

---

### How to build the project and run automated tests

Make sure local MongoDB is running

./mvnw clean package

### How to run the project in dev mode from command line

./mvnw spring-boot:run -Dspring.profiles.active=dev

### How to stop the project in dev mode from command line

On the same console where project was started: ctrl + c

---
### How to run the project in test mode by creating and running containers

Make sure Docker Desktop is running and the local MongoDB is stopped

./mvnw clean package -DskipTests

docker compose up -d

### How to check logs in test mode

docker compose logs

### How to stop the project in test mode

docker compose stop

### How to restart the project in test mode

docker compose start

### How to stop the project in test mode and removing containers

docker compose down

---
### List all containers
docker ps -a

### Remove all stopped containers
docker rm $(docker ps --filter status=exited -q)

### List volumes
docker volume ls

### Remove all unused local volumes
docker volume prune

### List images
docker images

### Remove image by Id
docker rmi ${imageId}

### Run docker exec on a running container
docker exec -it ${mycontainer} sh

---
### Resources
- [Spring Data MongoDB - Reference Documentation] (https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/)
- [Building REST services with Spring] (https://spring.io/guides/tutorials/rest/)
- [Docker Compose CLI] (https://docs.docker.com/compose/reference/)
- [Docker CLI] (https://docs.docker.com/engine/reference/commandline/cli/)

### Postman Resources
- [Getting Started] (https://learning.postman.com/docs/getting-started/overview/)
- [Using Variables] (https://learning.postman.com/docs/sending-requests/variables/)
- [Writing Scripts] (https://learning.postman.com/docs/writing-scripts/intro-to-scripts/)

