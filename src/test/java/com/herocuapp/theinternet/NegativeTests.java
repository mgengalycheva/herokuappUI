package com.herocuapp.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NegativeTests {


    /*@Parameters({"username", "password", "expectedMessage"})
    @Test(priority = 1, groups = { "negativeTests", "smokeTests"})
    public void negativeLoginTest(String username, String password, String expectedMessage) {

        System.out.println("Starting negativeLoginTest with " + username + " and " + password);

        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

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

        //

        driver.quit();

    }*/

    /*@Test(priority = 2, enabled = true, groups = { "negativeTests"})
    public void incorrectPasswordTest() {

        System.out.println("Starting incorrectPasswordTest");

        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver");
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        String url = "http://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("Page is opened");

        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("tomsmith");

        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("incorrectPassword");

        WebElement loginButton = driver.findElement(By.xpath("//button[contains(@type, 'submit')]"));
        loginButton.click();

        //verification

        WebElement notSuccessMessage = driver.findElement(By.cssSelector("#flash"));
        String expectedMessage = "Your password is invalid!";
        String actualMessage = notSuccessMessage.getText();

        Assert.assertTrue(actualMessage.contains(expectedMessage), "Actual message is not the same as expected");

        //

        driver.quit();

    }*/
}
