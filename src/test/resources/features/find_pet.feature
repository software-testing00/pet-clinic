Feature: Find Pet

  Scenario: Pet is found
    Given There is a pet owner
    And The owner has a pet called "StrawBerry"
    When I want to find the pet with its ID
    Then The pet should be found successfully

#  Scenario: Owner is not found
#    Given There is not a pet owner with ID "222"
#    When I want to find an owner with ID "222"
#    Then The owner should not be found successfully
