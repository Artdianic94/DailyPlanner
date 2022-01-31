import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.DateFormatSymbols;
import java.time.Month;
import java.util.*;

public class PageTest {
    WebDriver driver;
    private static final String path = "file:///Users/da/IdeaProjects/DailyPlanner/src/test/resources/index.html";

    @BeforeTest
    public void setProperties() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        driver = new ChromeDriver();
        driver.get(path);
    }

    @Test
    public void welcomeOnThePageTest() {
        String actualResult = driver.findElement(By.xpath("//h1[@id='welcome']")).getText();
        Assert.assertEquals(actualResult, "WELCOME", "Greeting not found");
    }

    @Test
    public void listOnThePageTest() {
        List<String> expectedList = new ArrayList<>();
        String[] months = new DateFormatSymbols(Locale.ENGLISH).getMonths();
        for (int m = 0; m < Month.values().length; m++) {
            expectedList.add(months[m]);
        }
        List<String> actualList = new ArrayList<>();
        for (int i = 0; i < expectedList.size(); i++) {
            actualList.add(driver.findElements(By.xpath("//option")).get(i).getText());
        }
        Assert.assertEquals(expectedList, actualList, "Month lists are not identical");
    }

    @Test
    public void buttonOnThePageTest() {
        driver.findElement(By.xpath("//button[@id='button']")).click();
        String actualAlertMessage = driver.switchTo().alert().getText();
        Assert.assertEquals("Bye", actualAlertMessage, "Error message");
    }

    @Test
    public void pageContainsMyNameTest() {
        boolean isContain = driver.findElement(By.xpath("//p[@id='hi']")).getText().contains("Diana");
        Assert.assertTrue(isContain, "The page doesn't contain my name");
    }

    @Test
    public void pageContainsLinkTest() {
        driver.findElement(By.xpath("//a[@id='page']")).click();
        boolean isOpened = driver.findElement(By.xpath("//a[@href='http://justnotepad.com/ru/']")).isDisplayed();
        Assert.assertTrue(isOpened, "The link doesn't opened");
    }

    @Test
    public void pictureHasAlternativeTextTest() {
        String containText = driver.findElement(By.xpath("//img[@alt='Ooops']")).getAttribute("alt");
        Assert.assertEquals(containText, "Ooops", "The picture doesn't contain alternative text");
    }

    @AfterTest
    public void quit() {
        driver.quit();

    }
}
