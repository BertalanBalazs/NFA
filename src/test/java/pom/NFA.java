package pom;

import com.codecool.bertalan.nfa.BasePage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;


class NFA {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static List<Integer> coordsFirst = new ArrayList<>();
    private static List<Integer> coordsSecond = new ArrayList<>();
    private static BasePage basePage;

    @BeforeAll
    static void setUp() {
        System.setProperty("webdriver.chrome.driver", System.getenv("webdriverPath"));
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        basePage = new BasePage(driver);
    }

    @AfterAll
    static void tearDown() {
        System.out.println(coordsFirst);
        System.out.println(coordsSecond);
        driver.close();
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/nfa.csv", numLinesToSkip = 1)
    void getAllEovCoordinate(String cityName, String localNumber) {
        basePage.navigate();
        driver.findElement(By.xpath("//select[@id='megye']")).click();
        driver.findElement(By.xpath("//*[@id=\"megye\"]/option[5]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='telep']")));
        driver.findElement(By.xpath("//select[@id='telep']")).click();
        driver.findElement(By.xpath("//select[@id='telep']")).sendKeys(cityName);
        driver.findElement(By.xpath("//select[@id='telep']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='hrsz']")));
        driver.findElement(By.xpath("//input[@id='hrsz']")).click();
        driver.findElement(By.xpath("//input[@id='hrsz']")).sendKeys(localNumber);
        driver.findElement(By.xpath("//form[@id='hrszform']/input[2 and @value='keres' and @type='submit']")).click();
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("document.body.setAttribute(\"sessionId\", $(\"a.title.first-kataszter\").data('point'))");

        String eovNum = driver.findElement(By.tagName("body")).getAttribute("sessionId");
        String[] eovNumList = eovNum.replaceAll("[^\\d.]"," ").split(" ");
        double f = Double.parseDouble(eovNumList[6]);
        double s = Double.parseDouble(eovNumList[7]);
        coordsFirst.add((int) f);
        coordsSecond.add((int) s);
    }
}