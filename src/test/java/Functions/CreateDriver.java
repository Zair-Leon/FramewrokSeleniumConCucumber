package Functions;

import StepDefinitions.Hooks;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverInfo;
//import org.openqa.selenium.support.decorators.WebDriverDecorator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CreateDriver {

    private static String browser;
    private static String os;
    private static String logLevel;

    private static Properties prop = new Properties();
    private static InputStream in = CreateDriver.class.getResourceAsStream("../test.properties");

    private static Logger log = Logger.getLogger(CreateDriver.class);

    private CreateDriver() throws IOException{
        CreateDriver.initConfig();
    }
    public static WebDriver initConfig() throws IOException {
        WebDriver driver;

        try{
            log.info("************************************************************************************");
            log.info("[ POM Configuration ] - Read the basic properties configuration from: ../test.properties");
            prop.load(in);
            browser = prop.getProperty("browser");
            os = prop.getProperty("os");
            logLevel = prop.getProperty("logLevel");

        }catch (IOException e){
            log.error("initConfig Error", e);
        }

        /************ Informacion POM *************/
        log.info("[ POM Configuration ] - OS: "+ os + " | Browser: " + browser + " |");
        log.info("[ POM Configuration ] - Logger Level: "+ logLevel);
        log.info("******************************************************************************************");
        /************ Carga del driver ************/
        driver = WebDriverFactory.createNewWebDriver(browser,os);

        return driver;
    }
}
