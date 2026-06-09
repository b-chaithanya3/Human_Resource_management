package p1;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Random;

public class HRM1{
    public static void main( String[] args ) throws IOException {

        try {

            FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "\\testdata\\myfile.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet("data");
            String test = sheet.getRow(0).getCell(0).toString() + new Random().nextInt(100);

            WebDriver driver = new ChromeDriver();
            driver.manage().window().maximize();
            //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("Admin");
            driver.findElement(By.name("password")).sendKeys("admin123");
            WebElement login = driver.findElement(By.tagName("button"));
            if (login.isEnabled()) {
                System.out.println("login is enabled");
            } else {
                System.out.println("login is disabled");
            }
            login.click();
            String current = driver.getCurrentUrl();
            System.out.println("URL of current Window:" + current);
            if (current.contains("dashboard")) {
                System.out.println("Login successful");
            } else {
                System.out.println("Login fail");
            }
            wait.until(ExpectedConditions.elementToBeClickable(By.tagName("span"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Job']"))).click();
            WebElement a = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[text()='Job Titles']"))));
            System.out.println("Job:" + a.getText());
            if (a.isDisplayed()) {
                System.out.println("job Titles is present");
            } else {
                System.out.println("job Titles is not present");
            }
            wait.until(ExpectedConditions.elementToBeClickable(a)).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".oxd-icon.bi-plus.oxd-button-icon"))).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input[@class='oxd-input oxd-input--active']"))).sendKeys(test);
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']"))).click();
            //List<WebElement> element=wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.oxd-table-cell.oxd-padding-cell[style='flex: 2 1 0%;']")));
            List<WebElement> element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div.oxd-table-cell[style='flex: 2 1 0%;']")));
            int sz = element.size();
            System.out.println("Total available jobTitles after adding:"+sz);
            boolean f = false;
            for (WebElement e : element) {
                String s = e.getText();
                if (s.equals(test)) {
                    f = true;
                    break;
                }
            }
            if (f == true) {
                System.out.println("test passed");
            } else {
                System.out.println("test failed");
            }
            /*
            FileOutputStream file = new FileOutputStream(System.getProperty("user.dir") + "\\testdata\\myfile.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("data");
            Thread.sleep(1000);
            for (int r = 0; r <sz; r++) {
                XSSFRow currentRow = sheet.createRow(r);
                XSSFCell cell = currentRow.createCell(0);
                cell.setCellValue(element.get(r).getText());
            }
            workbook.write(file);
            workbook.close();
            file.close();
            Thread.sleep(1000);

            System.out.println("File is creataed.....");

             */

            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".oxd-icon.bi-caret-down-fill.oxd-userdropdown-icon"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Logout']"))).click();
            driver.quit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
