import com.sun.security.jgss.GSSUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

    public List<String> check_lender_admin(List<String> list) throws InterruptedException {
        for (int i = 0; i <  list.size(); i++){
            System.out.print("Checking: \t" + i);
            System.out.println(list.get(i));

            System.out.println("Windows:" + driver.getWindowHandles().size());
            List<String> new_list = new ArrayList<>();

            Set<String> windows = driver.getWindowHandles();
            Iterator<String> it = windows.iterator();
            var parent = it.next();

            while (it.hasNext()){
                System.out.println("Switching windows ");
                driver.switchTo().window(it.next());
                Thread.sleep(5000);
                var lender_admin_button = driver.findElement(By.cssSelector("#lenderAdminDropdown"));

                if (lender_admin_button.isDisplayed()){
                    System.out.println("this account has a lender admin, SKIP");
                    break;
                } else {
                    System.out.println("this account does not have a lender admin, DO NOT SKIP");
                    System.out.println("adding to list");
                    new_list.add(list.get(i));
                }
            }
            return new_list;
        }
        return null;
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
    }


    public void down(){
        driver.close();
    }

    public boolean handle_multifactor_authentication() throws InterruptedException {
        // open duo iframe
        var duo_iframe = driver.findElement(By.id("duo_iframe"));
        driver.switchTo().frame(duo_iframe);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"passcode\"]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"message\"]")).click();
        Thread.sleep(2000);
        System.out.println("Please provide the code.");

        Scanner sc = new Scanner(System.in);
        String mfa_code = sc.nextLine();

        Thread.sleep(3000);
        driver.findElement(By.cssSelector("#auth_methods > fieldset > div.passcode-label.row-label > div > input")).sendKeys(mfa_code);

        Thread.sleep(1000);
        driver.findElement(By.cssSelector("#passcode")).click();
        return true;
    }

    public void pending_users() throws InterruptedException {
        Thread.sleep(500);
        // #keyword
        driver.findElement(By.cssSelector("#keyword")).sendKeys("pending");
        Thread.sleep(500);

        driver.findElement(By.id("traineesSearchSubmit")).click();
        Thread.sleep(500);
        WebElement table =  driver.findElement(By.xpath("//*[@id=\"trainees_dt\"]"));
        int row_count = table.findElements(By.tagName("tr")).size();
        var rows =  table.findElements(By.tagName("tr"));
        var cols  = table.findElements(By.tagName("td"));
        System.out.println("Rows: " + row_count);
        int col_count = table.findElements(By.tagName("td")).size();
        List<String> pending_account_names = new ArrayList<String>();
        List<WebElement> row_data = null;

        for (int i = 1; i < col_count; i++){
            row_data =  rows.get(i).findElements(By.tagName("td"));
            String cell_text_value = row_data.get(2).getText().toLowerCase();
            Thread.sleep(1000);

            Thread.sleep(1000);
            pending_account_names.add(cell_text_value);
            System.out.println();
        }
        System.out.println("All accounts have been opened in new tabs.\t Checking wil now begin...");

        var control_click = Keys.chord(Keys.CONTROL, ENTER);
        System.out.println(driver.getTitle());
        System.out.println(row_data.get(10).findElement(By.tagName("a")).getText());
        row_data.get(10).findElement(By.tagName("a")).sendKeys(control_click);
        Thread.sleep(500);
        System.out.println();

        List<String> pending_users =  check_lender_admin(pending_account_names);
        for (var u : pending_users) {
            System.out.println(u);
        }
    }

    public void search() throws InterruptedException {
        Thread.sleep(500);
        // #keyword
        driver.findElement(By.cssSelector("#keyword")).sendKeys("pending");
        Thread.sleep(500);
        driver.findElement(By.id("traineesSearchSubmit")).click();
        Thread.sleep(500);
    }
}


