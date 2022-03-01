import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.function.Function;

public class Dashboard {
    /**
     * Login to stage EVARS with username and password
     *
     * @throws InterruptedException login may not be successful due to MFA
     */


    public Dashboard(){

    }


    public void login() throws InterruptedException {


        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // browser settings
        driver.manage().window().maximize();

        // open page
        driver.get("https://hud-stage.hudnsc.org/eclass/index.cfm?login=true");
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

        var trainees = driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul/li[3]/a"));
        Thread.sleep(1000);
        if (trainees.getText().length() > 0){
            System.out.println("trainees clicked");
            trainees.click();
            Thread.sleep(1000);
            trainees.findElement(By.cssSelector("#navbar > ul > li.nav-item.dropdown.show > ul > a:nth-child(3) > li")).click();
            System.out.println("search clicked");

            var container = driver.findElement(By.className("container"));
            System.out.println();







            // TODO: 1/31/2022 create variable for the table that holds the fields


        }

        // TODO: 1/31/2022 find search link and click

        // FIXME: 1/19/2022 manually authenticate with DUO
//        driver.close();

//        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(3)).ignoring(NoSuchElementException.class);
//        wait.until(new Function<WebDriver, WebElement>() {
//            @Override
//            public WebElement apply(WebDriver webDriver) {
//                return driver.findElement(By.cssSelector("[id=finish] h4"));
//            }
//        });
}

}


