clean:
	mvnw clean

run-local:
	mvnw spring-boot:run -Dspring-boot.run.profiles=default,local

compile-skiptest:
	mvnw clean package -Dmaven.test.skip=true

stack-up:
	docker-compose build
	docker-compose up

stack-logs:
	docker-compose logs -f
