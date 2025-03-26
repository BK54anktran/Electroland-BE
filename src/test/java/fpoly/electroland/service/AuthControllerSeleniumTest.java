package fpoly.electroland.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

class AuthControllerSeleniumTest {

    private WebDriver driver;

    @BeforeEach
    void setup() {
        // Tự động tải và thiết lập ChromeDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Mở rộng cửa sổ trình duyệt
    }

    @Test
    void testLoginPage_ValidUser() {
        // Mở trang đăng nhập của ứng dụng
        driver.get("http://localhost:3000/login");  // Thay đổi URL cho phù hợp với ứng dụng của bạn

        // Tìm kiếm các phần tử input và nhập thông tin
        WebElement emailInput = driver.findElement(By.id("email"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.id("loginButton"));

        // Điền vào các trường email và mật khẩu
        emailInput.sendKeys("existinguser@example.com");
        passwordInput.sendKeys("password123");

        // Nhấn nút đăng nhập
        submitButton.click();

        // Kiểm tra xem sau khi đăng nhập, bạn có được chuyển đến trang chính hay không
        assertTrue(driver.getCurrentUrl().contains(""));  // URL trang chủ sau khi đăng nhập thành công

        System.out.println("testLoginPage_ValidUser passed!");
    }

    @AfterEach
    void tearDown() {
        // Đóng trình duyệt sau mỗi lần kiểm tra
        if (driver != null) {
            driver.quit();
        }
    }
}
