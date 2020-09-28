import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.Keys.ENTER;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class BBC1 {
    private WebDriver driver;

    @BeforeTest
    public void profileSetUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe"); // set up path to the chromedriver
    }

    @BeforeMethod
    public void checkSetUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.bbc.com");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement element = driver.findElement(By.xpath("//nav//a[contains(text(),'News')]"));
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/button[contains(text(),'Maybe later')]"))).click();
    }

    @Test(priority = 1)
    public void compareStringWithFoundElement() {
        String actualResult = driver.findElement(xpath("//a[contains(@href,'54332956')]/h3[@class]")).getText();
        Assert.assertEquals("Trump taxes are 'national security issue' - Pelosi", actualResult, "string “Trump taxes are 'national security issue' - Pelosi” is not equal to variable actualResult");  // Checks that string “text” is equal to variable actualResult, and throws exception if not. Use for checks that must succeed for the test to pass (i.e. the checks that are the purpose of the test).
    }

    @Test(priority = 2)
    public void checkIfFoundElements() {
        Boolean rightElement = driver.findElement(xpath("//h3[contains(text(),'Quick Covid-19 test to roll out in 133 nations')]")).isDisplayed();
        Boolean belowElement = driver.findElement(xpath("//h3[contains(text(),'New rules in Netherlands to cope with virus surge')]")).isDisplayed();
        assertTrue(rightElement);
        assertTrue(belowElement);
    }

    @Test(priority = 3)
    public void CheckNameOfTheFirstArticle() {
        String categoryLinkElement = driver.findElement(xpath("//div[contains(@class,'gs-c-promo-body gs-u-display-none gs-u-display-inline-block')]//a/span[@aria-hidden]")).getText();
        driver.findElement(xpath("//input[@id='orb-search-q']")).sendKeys(categoryLinkElement, ENTER);
        String firstArticle = driver.findElement(xpath("//div[@id='root']//a[contains(@href,'b0132mxz')]/span[@aria-hidden]")).getText();
        Boolean checkFirstArticle = firstArticle.contains("Business");
        assertTrue(checkFirstArticle);
    }

    @AfterMethod
    public void tearDown() {
        driver.close();
    }
}