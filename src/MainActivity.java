import com.sun.security.jgss.GSSUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

public class MainActivity {

    public static void main(String[] args) throws InterruptedException {

        WebDriver driver =  init();
        login(driver);
        System.out.println("successfully signed in ");
        System.out.println("Multifactor authentication login");



    }



    public static void login(WebDriver driver) throws InterruptedException {
        // open page
        driver.get("https://hud-stage.hudnsc.org/evars/index.cfm?login=true&appswitch=switch=login~~AdditionalText=Invalid%20username%20or%20password%2E%20Please%20re%2Denter%20your%20username%20and%20password%2E");
        Thread.sleep(500);
        WebElement login_btn = driver.findElement(By.id("main_dsp-login-btn"));
        Thread.sleep(500);
        if (login_btn != null) login_btn.click();

        WebElement login_email_field = driver.findElement(By.id("loginEmail"));
        Thread.sleep(1000);
        login_email_field.sendKeys("freddy.martinez@hudnsc.org");

        WebElement login_password_field = driver.findElement(By.id("loginPassword"));
        Thread.sleep(1000);
        login_password_field.sendKeys("Tara!234");

        driver.findElement(By.id("loginSubmit")).click();
        // TODO: 1/19/2022 if fields are not null then submit
        Thread.sleep(1000);

        Set<String> windows = driver.getWindowHandles();
        System.out.println("Number of windows: "  + windows.size());



    }





    public static WebDriver init(){

        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // browser settings
        driver.manage().window().maximize();
        return driver;

    }
}
