package org.example;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import static junit.framework.Assert.assertEquals;

public class testng {
    String test;
    WebDriver driver;
    WebDriverWait wait;
    @BeforeClass
    @Parameters({"browser","url"})
    void setup(String br,String url) throws InterruptedException {
        switch(br.toLowerCase()){
            case "chrome": driver=new ChromeDriver(); break;
            case "edge": driver=new EdgeDriver(); break;
            case "firefox": driver=new FirefoxDriver(); break;
            default: System.out.println("invalid browser"); return;
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(url);
        driver.manage().window().maximize();
        Thread.sleep(1000);
    }
    @Test(priority =0)
    void gettestdata_excel() throws IOException {
        FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "\\testdata\\myfile.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet("data");
        test = sheet.getRow(0).getCell(0).toString() + new Random().nextInt(100);

    }
    @Test(priority = 1)
    void login(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("Admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        WebElement login=driver.findElement(By.tagName("button"));
        Assert.assertTrue(login.isEnabled(),"Login button is not Enabled");
        login.click();
    }
    @Test(priority = 2,dependsOnMethods = {"login"})
    void Test_url(){
        String current=driver.getCurrentUrl();
        System.out.println("URL of current Window:"+current);
        Assert.assertEquals(current,"https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
        if(current.contains("dashboard")){
            System.out.println("Login successful");
        }
        else{
            System.out.println("Login fail");
        }
    }
    @Test(priority=3,dependsOnMethods = {"Test_url"})
    void Verify_jobTitle(){
        wait.until(ExpectedConditions.elementToBeClickable(By.tagName("span"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Job']"))).click();
        WebElement a=wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[text()='Job Titles']"))));
        String se=a.getText();
        //System.out.println(a.isDisplayed());
        if(se.equals("Job Titles")){
            System.out.println("job Titles is present");
        }
        else{
            System.out.println("job Titles is not present");
        }
        wait.until(ExpectedConditions.elementToBeClickable(a)).click();
    }
    @Test(priority=4,dependsOnMethods = {"Verify_jobTitle"})
    void Available_jobs(){
        List<WebElement> element1=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.oxd-table-cell[style='flex: 2 1 0%;']")));
        System.out.println("Total Available Jobs Before adding:"+element1.size());
        for(WebElement e1:element1){
            String s1=e1.getText();
            System.out.println(s1);
        }
    }
    @Test(priority = 5,dependsOnMethods = {"gettestdata_excel"})
    void Add_New_JobTitle(){
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".oxd-icon.bi-plus.oxd-button-icon"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input[@class='oxd-input oxd-input--active']"))).sendKeys(test);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();
    }

    @Test(priority=6,dependsOnMethods = {"Add_New_JobTitle"})
    void Check_New_JobTitle_Status(){
        List<WebElement> element=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.oxd-table-cell[style='flex: 2 1 0%;']")));
        System.out.println("Total Available jobs after adding new job:"+element.size());
        boolean f=false;
        for(WebElement e:element){
            String s = e.getText();
            if(s.equals(test)){
                f=true;
                break;
            }
        }
        if (f==true){
            System.out.println("test passed");
        }
        else {
            System.out.println("test failed");
        }
    }
    @Test(priority=7,dependsOnMethods = {"Test_url"})
    void logout(){
        System.out.println("logout");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".oxd-icon.bi-caret-down-fill.oxd-userdropdown-icon"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Logout']"))).click();
    }
    @AfterClass
    void close_browser(){
        driver.quit();
    }

}
