package com.herocuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTests {

    private WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    private void setUp(@Optional("chrome") String browser) {

// create driver
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

// maximize browser window
        driver.manage().window().maximize();
    }

    @Test(priority = 1, groups = { "positiveTests", "smokeTests"})
    public void positiveLoginTest() {
        System.out.println("Starting positiveLoginTest");

// open test page
        String url = "http://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("Page is opened");

// enter username
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("tomsmith");

// enter password
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("SuperSecretPassword!");

// click login button
        WebElement loginButton = driver.findElement(By.xpath("//button[contains(@type, 'submit')]"));
        loginButton.click();

// verification:
// new url
        String expectedUrl = "http://the-internet.herokuapp.com/secure";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Actual page url is not the same as expected");


// logout button is visible
        WebElement logOutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
        Assert.assertTrue(logOutButton.isDisplayed(), "Logout button is not visible");

// successful login message
        WebElement successMessage = driver.findElement(By.cssSelector("#flash"));
        String expectedMessage = "You logged into a secure area!";
        String actualMessage = successMessage.getText();
        //Assert.assertEquals(actualMessage, expectedMessage, "Actual message is not the same as expected");
        Assert.assertTrue(actualMessage.contains(expectedMessage), "Actual message is not the same as expected");

    }

    @Parameters({"username", "password", "expectedMessage"})
    @Test(priority = 2, groups = { "negativeTests", "smokeTests"})
    public void negativeLoginTest(String username, String password, String expectedMessage) {

        System.out.println("Starting negativeLoginTest with " + username + " and " + password);

        String url = "http://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("Page is opened");

        WebElement usernameElement = driver.findElement(By.id("username"));
        usernameElement.sendKeys(username);

        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.sendKeys(password);

        WebElement loginButton = driver.findElement(By.xpath("//button[contains(@type, 'submit')]"));
        loginButton.click();

        //verification

        WebElement notSuccessMessage = driver.findElement(By.cssSelector("#flash"));
        String actualMessage = notSuccessMessage.getText();

        Assert.assertTrue(actualMessage.contains(expectedMessage), "Actual message is not the same as expected");

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
