package Functions;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WebDriverFactory {

    private static Properties prop = new Properties();
    private static InputStream in = CreateDriver.class.getResourceAsStream("../test.properties");
    private static String resourceFolder;

    /****** Logs ******/
    private static Logger log = Logger.getLogger(WebDriverFactory.class);

    private static WebDriverFactory instance = null;

    public static WebDriver createNewWebDriver(String browser, String os) throws IOException {
        WebDriver driver;
        prop.load(in);
        resourceFolder = prop.getProperty("resourceFolder");

        /************** Driver FireFox **************/
        if ("FIREFOX".equalsIgnoreCase(browser)){
            if ("LINUX".equalsIgnoreCase(os)){
                System.setProperty("webdriver.gecko.driver", resourceFolder +os+"/geckodriver");
            }
            driver = new FirefoxDriver();
        }

        /************** Driver Chrome **************/
        if ("CHROME".equalsIgnoreCase(browser)){
            if ("LINUX".equalsIgnoreCase(os)){
                System.setProperty("webdriver.chrome.driver", resourceFolder +os+"/chromedriver");
            }
            driver = new ChromeDriver();
        }
        /************** Driver PhantomJS **************/

        /*if ("PHANTOM".equalsIgnoreCase(browser)){
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setJavascriptEnabled(true);
            if ("LINUX".equalsIgnoreCase(os)){
                caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "src/test/java/Software/linux/phantomjs");
            }
            driver = new PhantomJSDriver(caps);
        }*/
        /********** Driver no esta seleccionado ***********/
        else {
            log.error("El driver no esta seleccionado correctamente, nombre invalido: "+ browser +", "+os);
            return null;
        }

        driver.manage().window().maximize();
        return driver;
    }
}

