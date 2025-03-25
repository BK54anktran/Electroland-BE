package fpoly.electroland.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

class AdminSeleniumTest {

    private WebDriver driver;

    @BeforeEach
    void setup() {
        // Tự động tải và thiết lập ChromeDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Mở rộng cửa sổ trình duyệt
    }

    @Test
    void testAdminLogin_ValidUser() {
        // Mở trang đăng nhập của ứng dụng Admin
        driver.get("http://localhost:3000/admin"); // Thay đổi URL cho admin login

        // Tìm kiếm các phần tử input và nhập thông tin
        WebElement emailInput = driver.findElement(By.id("email"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.id("loginButton"));

        // Điền vào các trường email và mật khẩu
        emailInput.sendKeys("longtran.01102004@gmail.com"); // Sử dụng tài khoản admin hợp lệ
        passwordInput.sendKeys("Longbaby12309@@");

        // Nhấn nút đăng nhập
        submitButton.click();

        // Thêm thời gian chờ để đảm bảo trang đã chuyển hướng
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/product")); 

        // Kiểm tra xem sau khi đăng nhập, bạn có được chuyển đến trang admin dashboard hay không
        assertTrue(driver.getCurrentUrl().contains("/product"), "URL không chính xác sau khi đăng nhập");

        System.out.println("testAdminLogin_ValidUser passed!");
    }


    @AfterEach
    void tearDown() {
        // Đóng trình duyệt sau mỗi lần kiểm tra
        if (driver != null) {
            driver.quit();
        }
    }
}
