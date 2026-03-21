package io.github.pratesjr.nutriledgerapi.bdd.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

public class HealthCheckSteps {
    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();
    private ResponseEntity<String> response;

    @When("I access the health endpoint")
    public void i_access_the_health_endpoint() {
        String url = "http://localhost:" + port + "/nutri-ledger/api/health";
        response = restTemplate.getForEntity(url, String.class);
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(int statusCode) {
        assertNotNull(response, "Response should not be null");
        assertEquals(statusCode, response.getStatusCode().value());
    }

    @Then("the response should contain {string} with value {string}")
    public void the_response_should_contain_with_value(String key, String value) {
        assertNotNull(response, "Response should not be null");
        assertNotNull(response.getBody(), "Response body should not be null");
        assertTrue(response.getBody().contains("\"" + key + "\""), "Response should contain key: " + key);
        assertTrue(response.getBody().contains("\"" + value + "\""), "Response should contain value: " + value);
    }
}
