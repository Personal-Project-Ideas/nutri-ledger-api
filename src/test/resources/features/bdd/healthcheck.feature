Feature: Health Check
  As a user or monitoring system
  I want to verify that the application is running and healthy
  So that I can be sure the service is available

  Scenario: Application health endpoint returns status UP
    When I access the health endpoint
    Then the response status should be 200
    And the response should contain "status" with value "UP"

