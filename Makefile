mak.PHONY: run run-local clean test cucumber-test

# Spring test profile + H2 from .env (application-test.yaml imports optional:file:.env)
TEST_MVN_ARGS := -Dspring.profiles.active=test
TEST_ENV := SPRING_PROFILES_ACTIVE=test

clean:
	./mvnw clean

run:
	./mvnw spring-boot:run

run-local:
	SPRING_PROFILES_ACTIVE=local ./mvnw clean spring-boot:run

# Usage: make test | make test TEST_CLASS=JwtUtilTest
test:
	@if [ -n "$(TEST_CLASS)" ]; then \
		$(TEST_ENV) ./mvnw $(TEST_MVN_ARGS) -Dtest=$(TEST_CLASS) test; \
	else \
		$(TEST_ENV) ./mvnw $(TEST_MVN_ARGS) test; \
	fi

cucumber-test:
	$(TEST_ENV) ./mvnw $(TEST_MVN_ARGS) -Dtest=io.github.pratesjr.nutriledgerapi.bdd.HealthCheckIT test
