Feature: Example
  Optional sadasdsa

  Background:
    Given I set UserEmail value in Data Scenario

  @test
  Scenario: Get sites
    Given I am in App main site
    Then I load the DOM Information spoty_registro.json
    And I Save text of Titulo as Scenario Context
    And I set Email with key value Titulo.text

  @test
  Scenario: Get Url
    Given I got to site http://www.google.com
    Then I load the DOM Information galicia.json

  @test
  Scenario: I do a click
    Given I got to site https://www.spotify.com/co/signup
    Then I load the DOM Information spoty_registro.json
    And I do a click in element Email
    And I set Email with text zairgomez88@gmail.com

  @test
  Scenario: Handle DropDown
    Given I am in App main site
    Then I load the DOM Information spoty_registro.json
    And I wait for element Mes de nacimiento to be present
    And I set text February in dropdown Mes de nacimiento
    And I set index 02 in dropdown Mes de nacimiento

  @test
  Scenario: I check state
    Given I am in App main site
    Then I load the DOM Information spoty_registro.json
    And I set Email with text mervindiazlugo@gmail.com
    #And I do a click in element Confirmacion_Email
    Then I check if Email error error message is false

  @frame
  Scenario: Handle various functions
    Given I got to site https://chercher.tech/practice/frames-example-selenium-webdriver
    Then I load the DOM Information frames.json
    And I switch to Frame: Frame2
    And I set text Avatar in dropdown Frame2 select
    And I switch to Parent Frame
    And I switch to Frame: Frame1
    And I set Frame1 input with text Hola mundo
    And I switch to Frame: Frame3
    When I check the checkbox having Frame3 checkbox


  @CheckBoxes
  Scenario: Click on checkbuttons
    Given I am in App main site
    Then I load the DOM Information spoty_registro.json
    When I check the checkbox having Male
    When I check the checkbox having Female
    When I check the checkbox having NoBinary
    When I check the checkbox having Marketing

  @tests_JS
  Scenario: Scroll to elements
    Given I got to site https://www.amazon.es/
    Then I load the DOM Information Amazon.json
    #And I do a click in element Mi Cuenta
    #And I click in JS element Mi Cuenta
    #And I wait for element Mis Pedidos to be present
    And I scroll to element Sobre Amazon
    And I scroll to top of page
    And I scroll to end of page
    #And I do a click in element Sobre Amazon
    And I wait for element Sobre Amazon to be present


  @test
  Scenario: Open New Tab
    Given I got to site https://www.amazon.es/
    And I open new tab with URL https://www.youtube.com/
    And I go to Youtube window
    And I go to Principal window
    
  @testAlertas
  Scenario: Handle Alerts
    Given I got to site https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_alert
    And I load the DOM Information frames.json
    And I switch to Frame: FrameAlerta
    And I do a click in element BotonAlerta
    And I wait 5 seconds
    Then I accept alert

  @ScreenShots
  Scenario: I take a screenshot
    Given I am in App main site
    And I wait 5 seconds
    And I take screenshot: HolyScreen

    

