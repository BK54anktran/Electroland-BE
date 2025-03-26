package fpoly.electroland.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AuthControllerTest {

    @Test
    public void testLogin() {
        assertEquals(1, 1);
        // Đặt đường dẫn đến ChromeDriver
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        // Khởi tạo WebDriver
        WebDriver driver = new ChromeDriver();

        // Mở trang đăng nhập
        driver.get("http://localhost:3000/login");  // Thay bằng URL của trang đăng nhập của bạn

        // Tìm các phần tử input cho email và password
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("password"));

        // Nhập email và mật khẩu
        emailField.sendKeys("tani1009@gmail.com");
        passwordField.sendKeys("123");

        // Tìm và click vào nút đăng nhập
        WebElement loginButton = driver.findElement(By.id("loginButton"));  // Thay bằng id của nút đăng nhập của bạn
        loginButton.click();

        // Kiểm tra xem đăng nhập thành công bằng cách kiểm tra sự xuất hiện của phần tử sau khi đăng nhập
        WebElement loggedInElement = driver.findElement(By.id("welcomeMessage"));  // Thay bằng id của phần tử mà bạn muốn kiểm tra sau khi đăng nhập

        // Xác nhận rằng phần tử đã được tìm thấy (có nghĩa là đăng nhập thành công)
        assertTrue(loggedInElement.isDisplayed(), "Đăng nhập không thành công!");

        // Đóng trình duyệt
        driver.quit();
    }
}
