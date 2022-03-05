import com.sun.security.jgss.GSSUtil;
import net.jodah.failsafe.Fallback;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.security.cert.TrustAnchor;
import java.sql.Struct;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class Dashboard {
    /**
     * Login to stage EVARS with username and password
     *
     * @throws InterruptedException login may not be successful due to MFA
     */


    public WebDriver driver;
    public Dashboard(WebDriver driver){
        this.driver = driver;

    }




    public boolean login() throws InterruptedException {
        driver.get("https://hud-stage.hudnsc.org/evars/index.cfm?");
        Thread.sleep(500);

        WebElement login_btn = driver.findElement(By.id("main_dsp-login-btn"));
        Thread.sleep(500);

        if (login_btn == null){
            return false;
        }
        login_btn.click();
        WebElement login_email_field = driver.findElement(By.id("loginEmail"));
        Thread.sleep(500);

        login_email_field.sendKeys("freddy.martinez@hudnsc.org");
        WebElement login_password_field = driver.findElement(By.id("loginPassword"));
        Thread.sleep(500);

        login_password_field.sendKeys("Tara!234");
        driver.findElement(By.id("loginSubmit")).click();
        Thread.sleep(2000);

        System.out.println("Successfully enter credentials");

        System.out.println("Processing MFA");
        boolean mfa_auth =  handle_multifactor_authentication();

        if (!mfa_auth){
            System.out.println("ERROR! Multi-factor authentication failed. Please try again.");
        } else {
            Thread.sleep(5000);
            var requestors = driver.findElement(By.cssSelector("#navbar > ul > li:nth-child(3) > a"));
            requestors.click();
            Thread.sleep(1000);
            driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul/li[3]/ul/a[3]/li")).click();

        }
        return true;


////*[@id="navbar"]/ul/li[3]/ul/a[3]/li
//        var trainees = driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul/li[3]/a"));
//        Thread.sleep(1000);
//        if (trainees.getText().length() > 0){
//            System.out.println("trainees clicked");
//            trainees.click();
//            Thread.sleep(1000);
//            trainees.findElement(By.cssSelector("#navbar > ul > li.nav-item.dropdown.show > ul > a:nth-child(3) > li")).click();
//            System.out.println("search clicked");
//
//            var container = driver.findElement(By.className("container"));
//            System.out.println();
//
    }


    public void down(){
        driver.close();
    }

    public boolean handle_multifactor_authentication() throws InterruptedException {
        // open duo iframe
        var duo_iframe = driver.findElement(By.id("duo_iframe"));
        driver.switchTo().frame(duo_iframe);
        driver.findElement(By.xpath("//*[@id=\"passcode\"]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"message\"]")).click();
        Thread.sleep(2000);
        System.out.println("Please provide the  code.  ");

        Scanner sc = new Scanner(System.in);
        String mfa_code = sc.nextLine();
//        System.out.println(mfa_code);

        Thread.sleep(3000);
        driver.findElement(By.cssSelector("#auth_methods > fieldset > div.passcode-label.row-label > div > input")).sendKeys(mfa_code);
        driver.findElement(By.cssSelector("#passcode")).click();

        return true;
    }

    public void search() throws InterruptedException {
        Thread.sleep(500);

        // #keyword
        driver.findElement(By.cssSelector("#keyword")).sendKeys("pending");
        Thread.sleep(500);

    }
}


