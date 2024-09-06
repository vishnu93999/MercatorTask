package com.saucelab.testapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

/**
 * Unit test for simple App.
 */

public class AppTest{
    @Test
    public void addToCartTest (){
        double maxPrice = Double.MIN_VALUE;
        WebElement highestPriceItem = null;
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        if(driver.getCurrentUrl().equals("https://www.saucedemo.com/inventory.html")){
            List<WebElement> inventoryList = driver.findElements(By.className("inventory_item"));
            for (WebElement element:inventoryList) {
                String productName = element.findElement(By.cssSelector("div[data-test='inventory-item-name']")).getText();
                String dollarValue = element.findElement(By.className("inventory_item_price")).getText();
                double price = convertDollarToDouble(dollarValue);
                if (price > maxPrice) {
                    maxPrice = price;
                    highestPriceItem = element;
                }

            }
            highestPriceItem.findElement(By.tagName("button")).click();
        }
        else{
            throw new RuntimeException("Failed:Inventory Page was not found.");
        }
       driver.quit();
    }


    public static double convertDollarToDouble(String dollarValue) {
        // Remove the dollar sign
        String numericValue = dollarValue.replace("$", "");

        // Convert the remaining string to double
        return Double.parseDouble(numericValue);
    }
}
