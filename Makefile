.PHONY: run run-local test

run:
	./mvnw spring-boot:run

run-local:
	SPRING_PROFILES_ACTIVE=local ./mvnw spring-boot:run

test:
	./mvnw test

