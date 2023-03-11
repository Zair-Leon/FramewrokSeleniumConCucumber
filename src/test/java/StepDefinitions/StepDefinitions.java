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
    public void iAmInAppMainSite() throws IOException {
        String url = functions.readProperties("MainAppUrlBase");
        log.info("Navigate to: " + url);
        driver.get(url);
        functions.page_has_loaded();
    }

    /**
     * Step que realiza la navegación a una URL.
     *
     * @param URL Parámetro que define la URL a la que se debe navegar.
     * @throws Exception
     */
    @Given("^I got to site (.*)")
    public void iGotToSite(String URL) throws Exception {
        log.info("Navigate to: " + URL);
        driver.get(URL);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        functions.page_has_loaded();
        //functions.WindowsHandle("Principal");
    }

    /**
     * Step que realiza la carga de un archivo .JSON
     *
     * @param json Parámetro que define el nombre del archivo .JSON
     * @throws Exception
     */
    @Then("I load the DOM Information (.*)$")
    public void iLoadTheDOMInformation(String json) throws Exception {
        SeleniumFunctions.FileName = json;
        SeleniumFunctions.readJson();
        log.info("initialize file: " + json);
    }

    /**
     * Step que realiza clic en un elemento de la pantalla.
     *
     * @param element Parámetro que define el elemento al que se le debe hacer clic.
     * @throws Exception
     */
    @And("I do a click in element (.*)$")
    public void iDoAClickInElement(String element) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        functions.waitForElementClic(element);
        driver.findElement(SeleniumElement).click();
        log.info("Click on element by " + element);
    }

    /**
     * Step que realiza la escritura de un texto en un elemento de la pantalla.
     *
     * @param element Parámetro que define el elemento al que se le debe ingresar el texto.
     * @param text    Parámetro que define el texto a ingresar en el elemento.
     * @throws Exception
     */
    @And("I set (.*) with text (.*)$")
    public void iSetWithText(String element, String text) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        driver.findElement(SeleniumElement).sendKeys(text);
        log.info("Send text: " + text + " to element " + element);
    }

    /**
     * Step
     *
     * @param parameter
     * @throws IOException
     */
    @Given("I set (.*) value in Data Scenario")
    public void iSetUserEmailValueInDataScenario(String parameter) throws IOException {

        functions.RetriveTestData(parameter);
    }

    /**
     * @param element
     * @throws Exception
     */
    @And("I Save text of (.*) as Scenario Context$")
    public void iSaveTextOfElementAsScenarioContext(String element) throws Exception {
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        String ScenarioElementText = driver.findElement(SeleniumElement).getText();
        functions.SaveInScenario(element + ".text", ScenarioElementText);
    }

    /**
     * @param element
     * @param key
     * @throws Exception
     */
    @And("I set (.*) with key value (.*)$")
    public void iSetWithKeyValue(String element, String key) throws Exception {
        functions.iSetElementWithKeyValue(element, key);
    }

    /**
     * Step que realiza la selección de una opción de una lista, al ingresar el nombre de la opción
     *
     * @param option  Parámetro que define la opción a elegir de la lista.
     *                <b><br>Ejemplo:</br>
     *                <li>Enero</li><li>Febrero</li><li>...</li></b>
     * @param element Parámetro que define el elemento tipo lista (Select)
     * @throws Exception
     */
    @And("I set text (.*) in dropdown (.*)$")
    public void iSetTextInDropdown(String option, String element) throws Exception {

        functions.selectOptionDropdownByText(option, element);

    }

    /**
     * Step que realiza la selección de una opción de una lista, al ingresar el valor en el que esten ordenadas
     * @param option Parámetro que define el valor o index de la opción
     *               <br><b>Ejemplo:</b></br>
     *               <li>1</li>
     *               <li>2</li>
     *               <li>...</li>
     * @param element Paŕametro que define el elemento tipo lista (Select)
     * @throws Exception
     */
    @And("I set index (.*) in dropdown (.*)$")
    public void iSetIndexInDropdown(int option, String element) throws Exception {
        Select opt = (Select) functions.selectOption(element);
        opt.selectByIndex(option);
    }

    /**
     * Step que espera X segundos a que un elemento se encuentre presente en la pantalla.
     * @param element Parámetro que define el elemento a esperar.
     * @throws Exception
     */
    @Then("I wait for element (.*) to be present$")
    public void waitForElemetPresent(String element) throws Exception{
        functions.waitForElementPresent(element);
    }

    /**
     * Step que espera X segundos a que un elemento se encuentre visible en la pantalla.
     * @param element Parámetro que define el elemento a esperar.
     * @throws Exception
     */
    @Then("I wait for element (.*) to be visible$")
    public void waitForElemetVisible(String element) throws Exception{
        functions.waitForElementVisible(element);
    }

    /**
     * Step que compara el texto de un mensaje con otro texto ingresado por el usuario.
     * @param element Parámetro que define el elemento a comparar (Mensaje)
     * @param state Parámetro que define el texto a comparar
     * @throws Exception
     */
    @Then("I check if (.*) error message is (.*)$")
    public void iCheckIfErrorMessageIs(String element, String state) throws Exception {
        boolean Actual = functions.isElementDisplayed(element);
        System.out.println("Valores de variables \n"+Actual + "\n"+state);
        Assert.assertEquals("El estado es diferente al esperado", Actual, Boolean.valueOf(state));
    }

    /**
     * Step que realiza un SwitchTo a un frame determinado.
     * @param Frame Parámetro que define el frame en el que se posicionará en driver.
     * @throws Exception
     */
    @And("I switch to Frame: (.*)$")
    public void iSwitchToFrame(String Frame) throws Exception {

        functions.switchToFrame(Frame);
    }

    /**
     * Step que realiza un SwitchTo al frame "Padre o principal".
     */
    @And("I switch to Parent Frame")
    public void iSwitchToParentFrame() {

        functions.switchToParentFrame();
    }

    /**
     * Step que selecciona un checkbutton de la pantalla.
     * @param element Parámetro que define el checkbutton a seleccionar.
     * @throws Exception
     */
    @When("I check the checkbox having (.*)$")
    public void checkCheckBox(String element) throws Exception{
        functions.checkCheckBox(element);
    }

    /**
     * Step que desmarca un checkbutton de la pantalla
     * @param element Parámetro que define el checkbutton a desmarcar.
     * @throws Exception
     */
    @When("I Uncheck the checkbox having (.*)$")
    public void UncheckCheckBox(String element) throws Exception{
        functions.UncheckCheckBox(element);
    }

    /**
     * Step que da clic en un elemento a través de un script (No es necesario que el elemento
     * se encuentre visible en la pantalla)
     * @param element Parámetro que define el elemento al que se le debe hacer clic.
     * @throws Exception
     */
    @And("I click in JS element (.*)$")
    public void ClickJSElement(String element) throws Exception{
        functions.ClickJSElement(element);
    }

    /**
     * Step que realiza un scroll o "focus" en un elemento determinado.
     * @param element Parámetro que define el elemento al que se le debe hacer scroll.
     * @throws Exception
     */
    @And("I scroll to element (.*)$")
    public void ScrollToElement(String element) throws Exception{
        functions.scrollToElement(element);
    }

    /**
     * Step que realiza un scroll o "focus" según unas coordenadas de la pantalla.
     * #####REALIZAR UN ARREGLO EN ESA FUNCIÓN PARA QUE PUEDA SER PARAMETRIZABLE LAS COORDENADAS#####
     * @param to Parámetro que define una opcion |top o end| donde se realizará el scroll.
     * @throws Exception
     */
    @And("I scroll to (.*) of page$")
    public void ScrollPage(String to) throws Exception{
        functions.scrollPage(to);
    }

    /**
     * Step que abre una nueva ventana con otra URL
     * @param URL Parámetro que define la URL para la nueva ventana.
     * @throws Exception
     */
    @Given("I open new tab with URL (.*)$")
    public void OpenNewTabWithURL(String URL) throws Exception{
        functions.OpenNewTabWithURL(URL);
    }

    /**
     * Step que realiza la navegación hacia la nueva ventana.
     */
    @When("I switch to new window")
    public void switchToNewWindow(){
        System.out.println(driver.getWindowHandles());
        for (String winHandle : driver.getWindowHandles()){
            System.out.println(winHandle);
            log.info("Switching to new windows");
            driver.switchTo().window(winHandle);
        }
    }

    /**
     * Step que realiza la navegación a través del nombre de las ventanas.
     * @param WindowsName Parámetro que define el nombre de la ventana.
     * @throws Exception
     */
    @When("I go to (.*) window$")
    public void switchNewNamedWindow(String WindowsName) throws Exception{
        functions.WindowsHandle(WindowsName);
    }

    /**
     * Step que espera los segundos establecidos en el parámetro.
     * @param seconds Parámetro que define los segundos a esperar.
     * @throws InterruptedException
     */
    @And("I wait (.*) seconds$")
    public void IWaitSeconds(int seconds) throws InterruptedException{
        int secs = seconds * 1000;
        Thread.sleep(secs);
    }

    /**
     * Step que acepta o rechaza la acción de una alerta del navegador.
     * @param option Parámetro que define la opción a elegir por el usuario <b>|accept o dismiss|</b>.
     */
    @Then("I (accept|dismiss) alert$")
    public void AcceptAlert (String option){
        functions.AcceptAlert(option);
    }

    /**
     * Step que realiza un ScreenShot de la pantalla en la que se encuentre el driver.
     * @param TestCaptura Parámetro que define el nombre de la imagen tomada.
     * @throws IOException
     */
    @And("I take screenshot: (.*)$")
    public void iTakeScreenshot(String TestCaptura) throws IOException {
        functions.ScreenShot(TestCaptura);
    }

    /**
     * Step que compara si un elemento contiene un texto definido.
     * @param element Parámetro que define el elemento a comparar.
     * @param text Parámetro que define el texto a comparar.
     * @throws Exception
     */
    @Then("Assert if (.*) contains text (.*)$")
    public void assertIfContainsText(String element, String text) throws Exception{
        functions.checkPartialTextElementPresent(element, text);
    }

    /**
     * Step que compara si un texto es igual al que esta contenido en un elemento.
     * @param element Parámetro que define el elemento a comparar.
     * @param text Parámetro que define el texto a comparar.
     * @throws Exception
     */
    @Then("Assert if (.*) is equals to (.*)$")
    public void assertIfIsEqualsTo(String element, String text) throws Exception{
        functions.checkTextElementIqualTo(element, text);
    }

    /**
     * Step que valida si un elemento esta desplegado.
     * @param element Parámetro que define el elemento a validar.
     * @throws Exception
     */
    @Then("Check if (.*) is Displayed$")
    public void checkIfElementIsPresent(String element) throws Exception{

        boolean isDisplayed = functions.isElementDisplayed(element);
        Assert.assertTrue("Element is not present: "+ element, isDisplayed);
    }

    /**
     * Step que valida si un elemento <b>NO</b> esta desplegado.
     * @param element Parámetro que define el elemento a validar.
     * @throws Exception
     */
    @Then("Check if (.*) is NOT Displayed$")
    public void checkIfElementNotIsPresent(String element) throws Exception{

        boolean isDisplayed = functions.isElementDisplayed(element);
        Assert.assertFalse("Element is present: "+ element, isDisplayed);
    }


    /*Step Definitions personalizados para WS-GUARDIAN*/

    /**
     * Step que realiza el consumo de un servicio <b>REST</b> de <b>WS-Guardian</b>
     * @param servicio Parámetro que define la URL del servicio registrado en WS-Guardian.
     * @throws IOException
     */
    @And("I consume (.*) service REST$")
    public void consumeServiceREST(String servicio) throws IOException{
        functions.consumeServiceREST(servicio);
    }
}
