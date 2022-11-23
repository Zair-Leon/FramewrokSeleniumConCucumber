package StepDefinitions;

import Functions.SeleniumFunctions;
import Functions.CreateDriver;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class StepDefinitions {

    WebDriver driver;
    SeleniumFunctions functions = new SeleniumFunctions();

    public static boolean Actual = Boolean.parseBoolean(null);

    private static Logger log = Logger.getLogger(StepDefinitions.class);

    public StepDefinitions() {
        driver = Hooks.driver;
    }
    @Given("^I am in App main site")
    public void iAmInAppMainSite() throws IOException{
        String url = functions.readProperties("MainAppUrlBase");
        log.info("Navigate to: "+url);
        driver.get(url);
        functions.page_has_loaded();
    }
    @Given("^I got to site (.*)")
    public void iGotToSite(String URL) throws Exception{
        log.info("Navigate to: "+ URL);
        driver.get(URL);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        functions.page_has_loaded();
        functions.WindowsHandle("Principal");
    }

    @Then("I load the DOM Information (.*)$")
    public void iLoadTheDOMInformation(String json) throws Exception {
        SeleniumFunctions.FileName = json;
        SeleniumFunctions.readJson();
        log.info("initialize file: "+ json);
    }


    @And("I do a click in element (.*)$")
    public void iDoAClickInElement(String element) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        functions.waitForElementClic(element);
        driver.findElement(SeleniumElement).click();
        log.info("Click on element by "+element);
    }

    @And("I set (.*) with text (.*)$")
    public void iSetWithText(String element, String text) throws Exception{
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        driver.findElement(SeleniumElement).sendKeys(text);
        log.info("Send text: "+text+" to element "+element);
    }


    @Given("I set (.*) value in Data Scenario")
    public void iSetUserEmailValueInDataScenario(String parameter) throws IOException {

        functions.RetriveTestData(parameter);
    }

    @And("I Save text of (.*) as Scenario Context$")
    public void iSaveTextOfElementAsScenarioContext(String element) throws Exception{
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        String ScenarioElementText = driver.findElement(SeleniumElement).getText();
        functions.SaveInScenario(element+".text", ScenarioElementText);
    }

    @And("I set (.*) with key value (.*)$")
    public void iSetWithKeyValue(String element, String key) throws Exception {
        functions.iSetElementWithKeyValue(element, key);
    }

    @And("I set text (.*) in dropdown (.*)$")
    public void iSetTextInDropdown(String option, String element) throws Exception {

        functions.selectOptionDropdownByText(option, element);

    }

    @And("I set index (.*) in dropdown (.*)$")
    public void iSetIndexInDropdown(int option, String element) throws Exception {
        Select opt = (Select) functions.selectOption(element);
        opt.selectByIndex(option);
    }

    @Then("I wait for element (.*) to be present$")
    public void waitForElemetPresent(String element) throws Exception{
        functions.waitForElementPresent(element);
    }

    @Then("I wait for element (.*) to be visible$")
    public void waitForElemetVisible(String element) throws Exception{
        functions.waitForElementVisible(element);
    }

    @Then("I check if (.*) error message is (.*)$")
    public void iCheckIfErrorMessageIs(String element, String state) throws Exception {
        boolean Actual = functions.isElementDisplayed(element);
        System.out.println("Valores de variables \n"+Actual + "\n"+state);
        Assert.assertEquals("El estado es diferente al esperado", Actual, Boolean.valueOf(state));
    }

    @And("I switch to Frame: (.*)$")
    public void iSwitchToFrame(String Frame) throws Exception {

        functions.switchToFrame(Frame);
    }

    @And("I switch to Parent Frame")
    public void iSwitchToParentFrame() {

        functions.switchToParentFrame();
    }

    @When("I check the checkbox having (.*)$")
    public void checkCheckBox(String element) throws Exception{
        functions.checkCheckBox(element);
    }

    @When("I Uncheck the checkbox having (.*)$")
    public void UncheckCheckBox(String element) throws Exception{
        functions.UncheckCheckBox(element);
    }

    @And("I click in JS element (.*)$")
    public void ClickJSElement(String element) throws Exception{
        functions.ClickJSElement(element);
    }

    @And("I scroll to element (.*)$")
    public void ScrollToElement(String element) throws Exception{
        functions.scrollToElement(element);
    }

    @And("I scroll to (.*) of page$")
    public void ScrollPage(String to) throws Exception{
        functions.scrollPage(to);
    }

    @Given("I open new tab with URL (.*)$")
    public void OpenNewTabWithURL(String URL) throws Exception{
        functions.OpenNewTabWithURL(URL);
    }

    @When("I switch to new window")
    public void switchToNewWindow(){
        System.out.println(driver.getWindowHandles());
        for (String winHandle : driver.getWindowHandles()){
            System.out.println(winHandle);
            log.info("Switching to new windows");
            driver.switchTo().window(winHandle);
        }
    }

    @When("I go to (.*) window$")
    public void switchNewNamedWindow(String WindowsName) throws Exception{
        functions.WindowsHandle(WindowsName);
    }

    @And("I wait (.*) seconds$")
    public void IWaitSeconds(int seconds) throws InterruptedException{
        int secs = seconds * 1000;
        Thread.sleep(secs);
    }

    @Then("I (accept|dismiss) alert$")
    public void AcceptAlert (String option){
        functions.AcceptAlert(option);
    }

    @And("I take screenshot: (.*)$")
    public void iTakeScreenshot(String TestCaptura) throws IOException {
        functions.ScreenShot(TestCaptura);
    }

    @Then("Assert if (.*) contains text (.*)$")
    public void assertIfContainsText(String element, String text) throws Exception{
        functions.checkPartialTextElementPresent(element, text);
    }

    @Then("Assert if (.*) is equals to (.*)$")
    public void assertIfIsEqualsTo(String element, String text) throws Exception{
        functions.checkTextElementIqualTo(element, text);
    }

    @Then("Check if (.*) is Displayed$")
    public void checkIfElementIsPresent(String element) throws Exception{

        boolean isDisplayed = functions.isElementDisplayed(element);
        Assert.assertTrue("Element is not present: "+ element, isDisplayed);
    }

    @Then("Check if (.*) is NOT Displayed$")
    public void checkIfElementNotIsPresent(String element) throws Exception{

        boolean isDisplayed = functions.isElementDisplayed(element);
        Assert.assertFalse("Element is present: "+ element, isDisplayed);
    }
}
