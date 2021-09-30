package com.herocuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class ExceptionsTests {

    private WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    private void setUp(@Optional("chrome") String browser) {

        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver");
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver","src/main/resources/geckodriver");
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Do not know how to start " + browser + ", starting chrome instead");
                System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver");
                driver = new ChromeDriver();
                break;
        }

        driver.manage().window().maximize();
    }


    @Test(priority = 1)
    public void notVisibleTest() {
        String url = "http://the-internet.herokuapp.com/dynamic_loading/1";
        driver.get(url);

        WebElement startButton = driver.findElement(By.xpath("//div[@id=\"start\"]//button"));
        startButton.click();

        WebElement finalText = driver.findElement(By.xpath("//div[@id=\"finish\"]//h4"));

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(finalText));

        String expectedFinalText = "Hello World!";
        String actualFinalText = finalText.getText();
        Assert.assertTrue(actualFinalText.contains(expectedFinalText));

    }


    @Test(priority = 2)
    public void timeoutTest() {
        String url = "http://the-internet.herokuapp.com/dynamic_loading/1";
        driver.get(url);

        WebElement startButton = driver.findElement(By.xpath("//div[@id=\"start\"]//button"));
        startButton.click();

        WebElement finalText = driver.findElement(By.xpath("//div[@id=\"finish\"]//h4"));

        WebDriverWait wait = new WebDriverWait(driver, 2);
        try {
            wait.until(ExpectedConditions.visibilityOf(finalText));
        } catch (TimeoutException exception) {
            System.out.println("Exception catched: " + exception.getMessage());
            sleep(3000);
        }

        String expectedFinalText = "Hello World!";
        String actualFinalText = finalText.getText();
        Assert.assertTrue(actualFinalText.contains(expectedFinalText));

    }

    @Test(priority = 3)
    public void noSuchElementTest() {
        String url = "http://the-internet.herokuapp.com/dynamic_loading/2";
        driver.get(url);

        WebElement startButton = driver.findElement(By.xpath("//div[@id=\"start\"]//button"));
        startButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Assert.assertTrue(wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@id=\"finish\"]//h4"), "Hello World!")),
                "Couldn't verify expected text 'Hello World!'");

        //OR
        /*WebElement finalText = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id=\"finish\"]//h4")));
        String expectedFinalText = "Hello World!";
        String actualFinalText = finalText.getText();
        Assert.assertTrue(actualFinalText.contains(expectedFinalText));*/

    }

    @Test
    public void staleElementTest() {
        String url = "http://the-internet.herokuapp.com/dynamic_controls";
        driver.get(url);

        WebElement checkbox = driver.findElement(By.id("checkbox"));
        WebElement removeButton = driver.findElement(By.xpath("//button[contains(text(), 'Remove')]"));

        WebDriverWait wait = new WebDriverWait(driver, 10);
        removeButton.click();

        //1
        /*wait.until(ExpectedConditions.invisibilityOf(checkbox));
        Assert.assertFalse(checkbox.isDisplayed());*/

        //2
        /*Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOf(checkbox)),
        "Checkbox is still visible, but shouldn't be");*/

        //3
        Assert.assertTrue(wait.until(ExpectedConditions.stalenessOf(checkbox)),
                "Checkbox is still visible, but shouldn't be");

        WebElement addButton = driver.findElement(By.xpath("//button[contains(text(), 'Add')]"));
        addButton.click();

        checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
        Assert.assertTrue(checkbox.isDisplayed(),
                "Checkbox is not visible, but it should be");

    }


    @Test
    public void disabledElementTest() {
        String url = "http://the-internet.herokuapp.com/dynamic_controls";
        driver.get(url);

        WebElement enableButton = driver.findElement(By.xpath("//button[contains(text(), 'Enable')]"));
        WebElement textField = driver.findElement(By.xpath("//input[contains(@type, 'text')]"));

        enableButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(textField));

        textField.sendKeys("some text");
        String expectedText = "some text";
        String actualText = textField.getAttribute("value");
        Assert.assertTrue(actualText.contains(expectedText), "Text is not the same");

    }

    @AfterMethod(alwaysRun = true)
    private void tearDown() {
        // close browser
        driver.quit();
    }

    // sleep for 3 seconds
    private void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
