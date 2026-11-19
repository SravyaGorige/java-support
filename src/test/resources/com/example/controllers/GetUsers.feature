Feature: Get all users

  Background:
    * url 'http://localhost:8083/api/v1'

    @ts1
  Scenario: Fetch all users successfully
    Given path 'users'
    When method get
    Then status 200
    * print response

      @ts2
  Scenario: Save and Get Users

    Given path 'saveall'
    And request
    """
    [
      {
        "name":"Harish",
        "rollNo":101
      }
    ]
    """
    When method post
    Then status 200

    Given path 'users'
    When method get
    Then status 200

    * print karate.pretty(response)

    And match response[*].name contains 'Harish'
   # And match response == '#[0]'   # response should be a non‑empty array

    # Validate structure of first user
    #And match response[0].name == '#string'
   # And match response[0].rollNo == '#number'
