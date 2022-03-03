import com.sun.security.jgss.GSSUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

public class MainActivity {

    public static void main(String[] args) throws InterruptedException {
        Dashboard dashboard = new Dashboard(init());
        dashboard.login();
        System.out.println(dashboard.driver.getCurrentUrl());
        System.out.println("successfully signed in ");
        System.out.println("Multifactor authentication login");
    }

    public static WebDriver init(){

        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // browser settings
        driver.manage().window().maximize();
        return driver;

    }
}
