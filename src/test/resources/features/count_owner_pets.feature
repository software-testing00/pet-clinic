Feature: Count Owner Pets

  Scenario: Owner has one pet
    Given There is a pet owner with one pet
    When I want to find the number of the owners' pets
    Then The result should be one
