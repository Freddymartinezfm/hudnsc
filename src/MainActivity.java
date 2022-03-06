import com.sun.security.jgss.GSSUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

public class MainActivity {

    public static void main(String[] args) throws InterruptedException {
        Dashboard dashboard = new Dashboard(init());
        if (dashboard.login()){
            System.out.println("Multifactor authentication login");
        }

        dashboard.search();






    }

    public static WebDriver init(){
        System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        return driver;

    }
}
