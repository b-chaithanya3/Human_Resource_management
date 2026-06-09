package project;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HRM {
    public static void main( String[] args ) {

        try {
            WebDriver driver=new ChromeDriver();
            driver.manage().window().maximize();
            //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(10));
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("Admin");
            driver.findElement(By.name("password")).sendKeys("admin123");
            WebElement login=driver.findElement(By.tagName("button"));
            if(login.isEnabled()){
                System.out.println("login is enabled");
            }
            else{
                System.out.println("login is disabled");
            }
            login.click();
            String current=driver.getCurrentUrl();
            System.out.println("URL of current Window:"+current);
            if(current.contains("dashboard")){
                System.out.println("Login successful");
            }
            else{
                System.out.println("Login fail");
            }
            wait.until(ExpectedConditions.elementToBeClickable(By.tagName("span"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.oxd-icon.bi-chevron-down[with-container='false'][css='1']"))).click();
            WebElement a=driver.findElement(By.xpath("//a[text()='Job Titles']"));
            System.out.println(a.getText());
            if(a.isDisplayed()){
                System.out.println("job Titles is present");
            }
            else{
                System.out.println("job Titles is not present");
            }
            wait.until(ExpectedConditions.elementToBeClickable(a)).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.oxd-icon.bi-plus.oxd-button-icon"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input.oxd-input.oxd-input--active[css='2']"))).sendKeys("Automation Tester");
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.oxd-icon.bi-caret-down-fill.oxd-userdropdown-icon"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.oxd-userdropdown-link[role='menuitem'][css='4']"))).click();
            driver.quit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
