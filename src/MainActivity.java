import com.sun.security.jgss.GSSUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Set;



public class MainActivity {
    public static final String STAGE_ENV = "https://hud-stage.hudnsc.org/evars/index.cfm?";
    public static final String PROD_ENV = "https://hud.hudnsc.org/";



    public static void main(String[] args) throws InterruptedException {
        Dashboard dashboard = new Dashboard(init());
        dashboard.driver.get(PROD_ENV);

        Thread.sleep(500);

        System.out.println( dashboard.driver.getCurrentUrl());
        System.out.println( dashboard.driver.getTitle());
        // browser settings
        dashboard.driver.manage().window().maximize();
        ;
        if (dashboard.login()){
            System.out.println("Multifactor authentication login");
        }

        dashboard.search(); // todo should return number of results
        dashboard.pending_users(); //todo should return number of results
//        Set<String> real_pending_users =  dashboard.check_lender_admin();

    }

    public static WebDriver init(){
        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        return driver;

    }
}
