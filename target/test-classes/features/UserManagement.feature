Feature: OrangeHRM User Management

  Scenario: Add and Delete a User in OrangeHRM
    Given I navigate to the OrangeHRM login page
    When I enter "Admin" as username and "admin123" as password
    And I click the login button
    Then I should be redirected to the dashboard
    

    When I navigate to Admin section
   	And I get the current number of users
    And I click on the add button
    And I fill in the new user details
    And I click save
    Then I verify the number of users increased by 1

    When I search for the new user
    And I delete the new user
    Then I verify the number of users decreased by 1
