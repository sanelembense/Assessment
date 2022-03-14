# Description:The following User Scenarios are bases off a online shopping store, the user is instructed to do.
  #    this test feature file executes the following:
  #    - User details input to registration page
  #    - User registration complete check/validation and submit
  #    The data / variables for this test is predefined in the Test steps class in this project

@Registration
Feature: User Online Registration
  #  The following feature outlines th test cases and test scenarios that are related to the online registration
  #  conducted on a online ordering store

    Background:
      //Given The user enters an already existing email
      Given The user attempts to register with an existing account
    # user needs to scroll to the top of the registration form
    # find create an account string, Validate the correct url, Validate the form title, scroll and validate register button
    # Click register button, scroll to the top and verify validation message


    Scenario Outline:
      Given the user enters their <username> and clicks create
      And Error message is displayed on clicking create
      When tries logging in with a <username> and <password>
      Then the user is presented with the correct error message

      Examples:
        | username         | password     |
        ##@externaldata@./src/main/resources/TestData/TestData.xlsx@Credentials


  Scenario: This is a scenario to test datatable test on Cucumber JVM.
    Then UserName and Password shows on console from datatable.
      | UserName | Password |
      ##@externaldata@./src/main/resources/TestData/TestData.xlsx@Credentials

  Scenario: As a User I want to add a Category item to the Cart
    Given An Existing user Logins in and searches the Tshirt Category
    When User Hovers over Product item to reveal and click the add to cart button
    And user is taken through checkout for validation and summary
    And Order, shipping and Payment confirmations are successful
    Then the User is Returned to the Home Page