Feature: SS1 Verify the Login Section

  Scenario: Enter valid Userid & Password
    When I am on http://www.demo.guru99.com/V4/
    And I enter valid UserId
    And I enter valid Password
    And Click Login
    Then Login successful

#    Examples:
#      user       | password |
#      mngr117533 | dagatem  |
