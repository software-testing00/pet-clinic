Feature: Find Owner

  Scenario: Owner is found
    Given There is a pet owner called "Zahra"
    When I want to find the owner with his ID
    Then The owner should be found successfully

  Scenario: Owner is not found
    Given There is not a pet owner with ID "222"
    When I want to find an owner with ID "222"
    Then The owner should not be found successfully
