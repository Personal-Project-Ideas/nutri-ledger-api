.PHONY: run run-local test cucumber-test

run:
	./mvnw spring-boot:run

run-local:
	SPRING_PROFILES_ACTIVE=local ./mvnw spring-boot:run

test:
	./mvnw test

cucumber-test:
	./mvnw -Dtest=io.github.pratesjr.nutriledgerapi.bdd.HealthCheckIT test
