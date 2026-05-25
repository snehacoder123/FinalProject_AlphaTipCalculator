Feature: Tip Calculator Application

  As a user
  I want to calculate tips
  So that I can split bills correctly

  Background:
    Given user is on Tip Calculator page


  Scenario: Valid bill calculation
    When user enters bill "1000"
    And user selects currency "INR"
    And user enters people "2"
    And user selects rating "5"
    And user submits the form
    Then tip amount should be displayed

 
  Scenario: Invalid bill input
    When user enters bill "abc"
    And user submits the form
    Then invalid bill should be handled

 
  Scenario: Invalid people input
    When user enters bill "1000"
    And user enters people "0"
    And user submits the form
    Then invalid people should be handled

 
  Scenario: Reset functionality
    When user enters bill "1000"
    And user enters people "2"
    And user resets the form
    Then all fields should be cleared

  
  Scenario: Theme toggle
    When user toggles theme
    Then theme should change
