package fpoly.electroland.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest {
    public static void main(String[] args) {
        // Đặt đường dẫn tới ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C://Users//daota//Downloads//chromedriver-win64//chromedriver-win64//chromedriver.exe");

        // Khởi tạo đối tượng WebDriver (trình duyệt Chrome)
        WebDriver driver = new ChromeDriver();

        // Mở trang Google
        driver.get("https://www.google.com");

        // In ra tiêu đề của trang
        System.out.println("Page title: " + driver.getTitle());

        // Đóng trình duyệt sau 5 giây
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Đóng trình duyệt
        driver.quit();
    }
}
