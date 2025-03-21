package org.codingburgas.zsmihaleva20.exotic_destination_management_system;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        // Automatically downloads the correct ChromeDriver
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Run tests without opening a browser
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        // Initialize WebDriverWait
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testPageTitle() {
        driver.get("http://localhost:8080"); // Replace with the actual URL

        // Verify the page title
        String pageTitle = driver.getTitle();
        System.out.println("Page title: " + pageTitle);
        assertTrue(pageTitle.contains("Exotico - Открий света"), "Title mismatch!");
    }

    @Test
    public void testNavigationLinks() {
        driver.get("http://localhost:8080"); // Replace with the actual URL

        // Wait for login and register links to be visible
        WebElement loginLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Влез")));
        WebElement registerLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Регистрирай се")));

        assertTrue(loginLink.isDisplayed(), "'Влез' link is not displayed.");
        assertTrue(registerLink.isDisplayed(), "'Регистрирай се' link is not displayed.");
    }

    @Test
    public void testDestinationsSection() {
        driver.get("http://localhost:8080"); // Replace with the actual URL

        // Wait for the destination cards to be visible
        WebElement baliCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(), 'Бали, Индонезия')]")));
        WebElement maldivesCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(), 'Малдиви')]")));
        WebElement seychellesCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(), 'Сейшелски острови')]")));

        assertTrue(baliCard.isDisplayed(), "Bali card is not displayed.");
        assertTrue(maldivesCard.isDisplayed(), "Maldives card is not displayed.");
        assertTrue(seychellesCard.isDisplayed(), "Seychelles card is not displayed.");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
