import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.swing.*;
import java.util.*;

import static org.openqa.selenium.Keys.ENTER;

public class Dashboard {
    /**
     * Login to stage EVARS with username and password
     *
     * @throws InterruptedException login may not be successful due to MFA
     */


    public WebDriver driver;

    int MFA_CODE = 0;

    public Dashboard(WebDriver driver){
        this.driver = driver;

    }




    public boolean login() throws InterruptedException {
        Agent user = new Agent();
        user.email = "freddy.martinez@hudnsc.org";
        user.password = "Tara!234";

        WebElement login_btn = driver.findElement(By.id("main_dsp-login-btn"));
        Thread.sleep(500);

        if (login_btn == null) return false;
        login_btn.click();
        WebElement login_email_field = driver.findElement(By.id("loginEmail"));
        Thread.sleep(500);

        login_email_field.sendKeys(user.email);
        WebElement login_password_field = driver.findElement(By.id("loginPassword"));
        Thread.sleep(500);

        login_password_field.sendKeys(user.password);
        driver.findElement(By.id("loginSubmit")).click();
        Thread.sleep(2000);

        System.out.println("Successfully enter credentials");

        System.out.println("Processing MFA");
        Thread.sleep(1000);
        boolean mfa_auth =  handle_multifactor_authentication();

        Thread.sleep(1000);

        if (!mfa_auth){
            System.out.println("ERROR! Multi-factor authentication failed. Please try again.");
        } else {
            Thread.sleep(2000);
            var requestors = driver.findElement(By.cssSelector("#navbar > ul > li:nth-child(3) > a"));
            requestors.click();
            Thread.sleep(2000);
            driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul/li[3]/ul/a[3]/li")).click();
        }
        return true;
    }
    public void down(){
        driver.close();
    }

    public boolean handle_multifactor_authentication() throws InterruptedException {
        // open duo iframe
        var duo_iframe = driver.findElement(By.id("duo_iframe"));
        Thread.sleep(1000);
        System.out.println("Switching to duo frame");
        driver.switchTo().frame(duo_iframe);
        Thread.sleep(2000);
        var mfa_passcode = driver.findElement(By.xpath("//*[@id='passcode']")); //*[@id="passcode"]
        Thread.sleep(2000);
        mfa_passcode.click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"message\"]")).click();
        Thread.sleep(2000);
        System.out.print("Please provide the code.");
        Scanner sc = new Scanner(System.in);
        String mfa_code = sc.nextLine();
        System.out.println("Dashboard.handle_multi factor_authentication");
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("#auth_methods > fieldset > div.passcode-label.row-label > div > input")).sendKeys(mfa_code);

        Thread.sleep(1000);
        driver.findElement(By.cssSelector("#passcode")).click();
        return true;
    }

    public List<String> pending_users() throws InterruptedException {
        WebElement table =  driver.findElement(By.xpath("//*[@id=\"trainees_dt\"]"));
        List<WebElement> row_data;
        var rows =  table.findElements(By.tagName("tr"));
        int row_count = rows.size();
        var table_data  = table.findElements(By.tagName("td"));
        Thread.sleep(500);
        System.out.println("Accounts: " + row_count);
        Thread.sleep(500);
        int col_count = table.findElements(By.tagName("td")).size();
        System.out.println("Table Data Cells: "  + col_count);
        Thread.sleep(500);
        List<String> full_list = new ArrayList<String>();

        String user_name = " ";
        for (int i = 1; i < row_count; i++) {
            Thread.sleep(500);
            row_data = rows.get(i).findElements(By.tagName("td"));
            user_name = row_data.get(2).getText().toLowerCase();
            var control_click = Keys.chord(Keys.CONTROL, ENTER);
            row_data.get(10).findElement(By.tagName("a")).sendKeys(control_click);
            Thread.sleep(500);
            full_list.add(user_name);
        }
        System.out.println("All Users: ");
        for (var u : full_list) {
            System.out.println("\t"+ u);
        }

        System.out.println("Full List size: "  + full_list.size());

        Set<String> windows = driver.getWindowHandles();
        System.out.println("Accounts: " + driver.getWindowHandles().size());
        Iterator<String> it = windows.iterator();
        List<String> filtered_list = new ArrayList<>();
        var parent = it.next();
        int index = full_list.size();
        while (it.hasNext()){
//            System.out.println("Switching window to it.next().... ");
            driver.switchTo().window(it.next());
            var admin =  driver.findElements(By.id("lenderAdminInfoWrapper"));
            index--;
            if (admin.size() != 1) {
                filtered_list.add(full_list.get(index));
            } else {
                System.out.println("lender admin present.");
            }
        }
        return filtered_list;
    }


    public void search() throws InterruptedException {
        Thread.sleep(500); // #keyword
        driver.findElement(By.cssSelector("#keyword")).sendKeys("pending");
        Thread.sleep(500);
        driver.findElement(By.id("traineesSearchSubmit")).click();
        Thread.sleep(500);
    }
}


