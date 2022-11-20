package Functions;


import StepDefinitions.Hooks;
import cucumber.api.java.eo.Se;
import org.apache.commons.io.FileUtils;
import org.json.simple.parser.*;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SeleniumFunctions {

    WebDriver driver;
    public SeleniumFunctions() { driver = Hooks.driver; }

    public static String PagesFilePath = "src/test/resources/Pages/";
    private static Logger log = Logger.getLogger(SeleniumFunctions.class);

    public static Map<String, String> ScenaryData = new HashMap<>();
    public static Map<String, String> HandleMyWindows = new HashMap<>();
    public static String Environment = "";

    public static Properties prop = new Properties();
    public static InputStream in = CreateDriver.class.getResourceAsStream("../test.properties");

    public static String FileName = "";
    public static String GetFielBy = "";
    public static String ValueToFind = "";

    public static final int EXPLICIT_TIMEOUT = 5;
    public static Object readJson() throws Exception{
        FileReader reader = new FileReader(PagesFilePath + FileName);
        try {

            if (reader != null){
                JSONParser jsonParser = new JSONParser();
                return jsonParser.parse(reader);
            }else {
                return null;
            }
        }catch (NullPointerException e){
            log.error("ReadEntity: No existe el archivo "+FileName);
            throw new IllegalStateException("ReadEntity: No existe el archivo: "+FileName, e);
        }
    }

    public static JSONObject ReadEntity(String element) throws Exception{
        JSONObject Entity = null;

        JSONObject jsonObject = (JSONObject) readJson();
        Entity = (JSONObject) jsonObject.get(element);
        log.info(Entity.toJSONString());
        //System.out.println("Entidad: "+Entity);
        return Entity;
    }

    public static By getCompleteElement(String element) throws Exception {
        By result = null;
        JSONObject Entity = ReadEntity(element);

        GetFielBy = (String) Entity.get("GetFieldBy");
        ValueToFind = (String) Entity.get("ValueToFind");

        if ("className".equalsIgnoreCase(GetFielBy)) {
            result = By.className(ValueToFind);
        } else if ("cssSelector".equalsIgnoreCase(GetFielBy)) {
            result = By.cssSelector(ValueToFind);
        } else if ("id".equalsIgnoreCase(GetFielBy)) {
            result = By.id(ValueToFind);
        } else if ("linkText".equalsIgnoreCase(GetFielBy)) {
            result = By.linkText(ValueToFind);
        } else if ("name".equalsIgnoreCase(GetFielBy)) {
            result = By.name(ValueToFind);
        } else if ("link".equalsIgnoreCase(GetFielBy)) {
            result = By.partialLinkText(ValueToFind);
        } else if ("tagName".equalsIgnoreCase(GetFielBy)) {
            result = By.tagName(ValueToFind);
        } else if ("xpath".equalsIgnoreCase(GetFielBy)) {
            result = By.xpath(ValueToFind);
        }
        return result;
    }

    public String readProperties(String property) throws IOException{

        prop.load(in);
        return prop.getProperty(property);
    }

    public void SaveInScenario(String key, String text) {
        if (!this.ScenaryData.containsKey(key)){
            this.ScenaryData.put(key,text);
            log.info(String.format("Save as Scenario contect key %s with value: ", key,text));
            System.out.println(String.format("Save as Scenario contect key %s with value: %s", key,text));
        }else{
            this.ScenaryData.replace(key,text);
            log.info(String.format("Update scenario contect key %s with value %s", key,text));
            System.out.println(String.format("Update scenario context key %s with value %s", key,text));
        }
    }

    public void RetriveTestData (String parameter) throws IOException{
        Environment = readProperties("Environment");
        try {
            SaveInScenario(parameter, readProperties(parameter+"."+Environment));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void iSetElementWithKeyValue(String element, String key) throws Exception{
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        boolean exist = this.ScenaryData.containsKey(key);
        if (exist){
            String text = this.ScenaryData.get(key);
            driver.findElement(SeleniumElement).sendKeys(text);
            log.info(String.format("Set on element %s with text %s", element, text));
            System.out.println(String.format("Set on element %s with text %s", element, text));
        }else {
            Assert.assertTrue(String.format("The given key %s do not exist in Context", key), this.ScenaryData.containsKey(key));
        }
    }

    public void selectOptionDropdownByText(String option, String element) throws Exception{

        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        log.info(String.format("Waiting element: %s", element));

        Select opt = new Select(driver.findElement(SeleniumElement));
        log.info("Select option:  "+option+ " by text");
        opt.selectByVisibleText(option);

    }

    public Select selectOption(String element) throws Exception{

        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        log.info(String.format("Waiting element: %s", element));
        Select opt = new Select(driver.findElement(SeleniumElement));
        return opt;
    }


    public void waitForElementPresent(String element) throws Exception{

        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
        log.info("Waiting for the element: "+element+" to be present");
        wait.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement));
    }


    public void waitForElementVisible(String element) throws Exception{

        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
        log.info("Waiting for the element: "+element+" to be present");
        wait.until(ExpectedConditions.visibilityOfElementLocated(SeleniumElement));
    }


    public void waitForElementClic(String element) throws Exception{

        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
        log.info("Waiting for the element: "+element+" to be present");
        wait.until(ExpectedConditions.elementToBeClickable(SeleniumElement));
    }


    public boolean isElementDisplayed(String element) throws Exception{
        boolean isDisplayed = true;
        try {
            By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
            log.info(String.format("Waiting element: %s", element));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
            isDisplayed = wait.until(ExpectedConditions.presenceOfElementLocated(SeleniumElement)).isDisplayed();
        }catch (NoSuchElementException | TimeoutException e){
            isDisplayed = false;
            System.out.println("Message: \n"+e);
        }
        log.info(String.format("%s visibility is: %s", element, isDisplayed));
        return isDisplayed;
    }


    public void switchToFrame(String Frame) throws Exception{

        By SeleniumElement = SeleniumFunctions.getCompleteElement(Frame);
        log.info("Switching to frame: "+ Frame);
        driver.switchTo().frame(driver.findElement(SeleniumElement));
    }

    public void switchToParentFrame(){
        log.info("Switching to parent frame: ");
        driver.switchTo().parentFrame();
    }

    public void checkCheckBox(String element) throws Exception{

        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        boolean isChecked = driver.findElement(SeleniumElement).isSelected();
        if (!isChecked){
            log.info("Clicking on the checkbox to select: "+ element);
            driver.findElement(SeleniumElement).click();
        }
    }

    public void UncheckCheckBox(String element) throws Exception{

        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        boolean isChecked = driver.findElement(SeleniumElement).isSelected();
        if (isChecked){
            log.info("Clicking on the checkbox to select: "+ element);
            driver.findElement(SeleniumElement).click();
        }
    }
    //JAVASCRIPT ACTIONS//
    public void ClickJSElement(String element) throws Exception{
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        log.info("Click to element with JavaScript: "+ element);
        jse.executeScript("arguments[0].click()", driver.findElement(SeleniumElement));
    }


    public void scrollToElement(String element) throws Exception{
        By SeleniumElement = SeleniumFunctions.getCompleteElement(element);
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        log.info("Click to element with JavaScript: "+ element);
        jse.executeScript("arguments[0].scrollIntoView()", driver.findElement(SeleniumElement));
        TimeUnit.SECONDS.sleep(5);
    }


    public void scrollPage(String to) throws Exception{
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        if (to.equals("top")){
            log.info("Scrolling to the top of the page");
            jse.executeScript("scroll(0, -250)");
            TimeUnit.SECONDS.sleep(3);
        } else if (to.equals("end")) {
            log.info("Scrolling to the end of the page");
            jse.executeScript("scroll(0, 250)");
            TimeUnit.SECONDS.sleep(3);
        }
    }

    public void page_has_loaded(){
        String GetActual = driver.getCurrentUrl();
        System.out.println(String.format("Checking if %s page is loaded.", GetActual));
        log.info(String.format("Checking if %s page is loaded.", GetActual));
        new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public void OpenNewTabWithURL(String URL) throws Exception {
        log.info("Open New Tab with URL: " + URL);
        System.out.println("Open new tab with URL: " + URL);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(String.format("window.open('%s', '_blank');", URL));
        TimeUnit.SECONDS.sleep(3);

    }


    public void WindowsHandle(String WindowsName) throws Exception{
        if (this.HandleMyWindows.containsKey(WindowsName)){
            driver.switchTo().window(this.HandleMyWindows.get(WindowsName));
            log.info(String.format("I go to windows: %s with value: %s", WindowsName, this.HandleMyWindows.get(WindowsName)));

        }else {
            for (String winHandle : driver.getWindowHandles()) {
                this.HandleMyWindows.put(WindowsName,winHandle);
                System.out.println("The New Window "+ WindowsName + " is saved in scenario with value " + this.HandleMyWindows);
                log.info("The New Window "+ WindowsName + " is saved in scenario with value " + this.HandleMyWindows.get(WindowsName));
                driver.switchTo().window(winHandle);
                TimeUnit.SECONDS.sleep(3);
            }
        }
    }

    public void AcceptAlert(String option){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            System.out.println(alert.getText());
            if (option == "accept") {
                alert.accept();
            }else {
                alert.dismiss();
            }
            log.info("The alert was accepted successfully");
        }catch (Throwable e){
            log.error("Error came while waiting for the alert pop-up "+e.getMessage());
        }
    }

    public void ScreenShot(String TestCaptura) throws IOException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmm");
        String screenShotName = readProperties("ScreenShotPath")+"\\"+readProperties("browser") + "\\" + TestCaptura + "_(" + dateFormat.format(GregorianCalendar.getInstance().getTime()) + ")";
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        log.info("ScreenShot saved as: "+screenShotName);
        FileUtils.copyFile(scrFile, new File(String.format("%s.png", screenShotName)));
    }


}
