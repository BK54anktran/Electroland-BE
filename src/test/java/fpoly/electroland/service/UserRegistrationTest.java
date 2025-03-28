package fpoly.electroland.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

class UserRegistrationTest {

    private WebDriver driver;

    @BeforeEach
    void setup() {
        // Automatically set up ChromeDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximize the browser window
    }

    @Test
    void testUserRegistration_ValidUser() {
        // Open the login page (Initially login form is visible)
        driver.get("http://localhost:3000/login");

        // Locate the "selectButton" to toggle between login and register mode
        WebElement selectButton = driver.findElement(By.id("selectButton"));
        selectButton.click(); // Click to switch to registration mode

        // Wait until the page switches to registration mode, checking for a title or element change
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text()='ĐĂNG KÝ']"))); // Check for the "ĐĂNG KÝ" title

        // Now that we are in registration mode, locate the input elements and fill in registration information
        WebElement nameInput = driver.findElement(By.id("fullName"));
        WebElement emailInput = driver.findElement(By.id("email"));
        WebElement dateInput = driver.findElement(By.id("dateOfBirth"));

        WebElement genderMale = driver.findElement(By.xpath("//div[@id='gender']//input[@value='true']"));
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click();", genderMale); // Nhấp vào radio button Nam

        // Số điện thoại (Input)
        WebElement phoneInput = driver.findElement(By.id("phoneNumber"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement confirmPasswordInput = driver.findElement(By.id("confirmPassword"));
        WebElement submitButton = driver.findElement(By.id("registerButton"));

        // Fill in the registration form
        nameInput.sendKeys("Nguyễn Đình Long");
        emailInput.sendKeys("newuser@example.com"); // Valid email
        dateInput.sendKeys("1990-01-01"); // Nhập ngày sinh theo định dạng YYYY-MM-DD
       
        phoneInput.sendKeys("0123456789"); // Nhập số điện thoại hợp lệ
        passwordInput.sendKeys("NewUser12345!!"); // Valid password
        confirmPasswordInput.sendKeys("NewUser12345!!"); // Confirm password

        // Click the register button
        submitButton.click();

        // Handle alert if it appears
        try {
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert text: " + alert.getText()); // Print alert text to console
            alert.accept(); // Accept the alert (click "OK")
        } catch (NoAlertPresentException e) {
            // No alert present, continue with the test
            System.out.println("No alert present.");
        }

        // Add wait time to ensure the page redirects after registration
        wait.until(ExpectedConditions.urlContains("http://localhost:3000")); // Check URL after successful registration

        // Verify that after registration, the user is redirected to the homepage
        assertTrue(driver.getCurrentUrl().contains("http://localhost:3000"), "URL is incorrect after registration");

        System.out.println("testUserRegistration_ValidUser passed!");
    }

    @AfterEach
    void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
        }
    }
}
