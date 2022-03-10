import com.sun.security.jgss.GSSUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Set;



public class MainActivity {
    public static final String STAGE_ENV = "https://hud-stage.hudnsc.org/evars/index.cfm?";
    public static final String PROD_ENV = "https://hud.hudnsc.org/index.cfm?";



    public static void main(String[] args) throws InterruptedException {
        Dashboard dashboard = new Dashboard(init());
        dashboard.driver.get(STAGE_ENV);

        Thread.sleep(500);

        System.out.println( dashboard.driver.getCurrentUrl());
        System.out.println( dashboard.driver.getTitle());
        // browser settings
        ;
        if (dashboard.login()){
            System.out.println("[+]Multifactor authentication login");
        }

        dashboard.search(); // todo should return number of results
        var users = dashboard.pending_users(); //todo should return number of results
        System.out.println("[+]Number of pending users: " + users.size());
        System.out.println("[+] Ready for processing:  ");
        for (var user : users){
            System.out.println("\t" + user);
        }
    }

    public static WebDriver init(){
        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        return driver;

    }
}
