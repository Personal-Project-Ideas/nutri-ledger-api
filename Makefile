.PHONY: run run-local test cucumber-test

run:
	./mvnw spring-boot:run

run-local:
	SPRING_PROFILES_ACTIVE=local ./mvnw spring-boot:run

test:
	@if [ -n "$(TEST_CLASS)" ]; then \
		./mvnw -Dtest=$(TEST_CLASS) test; \
	else \
		./mvnw test; \
	fi

cucumber-test:
	./mvnw -Dtest=io.github.pratesjr.nutriledgerapi.bdd.HealthCheckIT test
