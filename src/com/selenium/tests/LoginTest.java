package com.selenium.tests;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.Assert;
import org.testng.annotations.*;

// Mục tiêu:
/*Viết hệ thông kiểm thử tự động cho chức năng đăng nhập hệ thông bán hàng balo online nếu người dùng đăng nhập sai hiển thị
 * Tài khoản đăng nhập hoặc mật khẩu sai !!! 
 */

public class LoginTest {

	static WebDriver driver;

	@BeforeTest
	public void setUp() {

		// khai báo driver
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\HAU\\Downloads\\SeleniumDemo\\SeleniumDemo\\src\\chromedriver.exe");
		driver = new ChromeDriver();

		// đặt một thời gian đợi ngầm hiểu là việc tìm kiếm bất kì phần tử nào trong
		// trang web có thể tốn thời gian tối đa trước khi ném một ngoại lệ
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		// mở trang web
		driver.get("http://localhost:8080/DTCM/");
		driver.manage().window().maximize();
	}

	@BeforeMethod
	public void beforeMethod() {
		// Cài đặt reset để tránh làm nhiễu tescase
		driver.navigate().refresh();
	}

	@AfterMethod
	public void afterMethod() {
		// Cài đặt sử lý lỗi
	}

	// đăng nhập thành công
	@Test
	public void loginSuceesTest() {
		WebElement login = driver.findElement(By.linkText("Đăng nhập"));
		login.click();

		// 2.Nhập dữ liệu
		WebElement txtUsername = driver.findElement(By.cssSelector("[name='username']"));
		txtUsername.sendKeys("hau");
		WebElement txtPassword = driver.findElement(By.cssSelector("[name='password']"));
		txtPassword.sendKeys("123456");

		// 3.click login
		WebElement btnLogin = driver.findElement(By.xpath("//button[@class='aa-browse-btn']"));
		btnLogin.click();

		// 4. chờ thông báo
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 5.kiểm tra
		String actualString = driver.findElement(By.xpath("//*[@class=\"aa-head-top-nav-right\"]/li[1]")).getText();
		String expectString = "Chào Nguyễn Công Hậu";
		Assert.assertEquals(actualString, expectString);

		// 6.Đăng xuất
		WebElement logout = driver.findElement(By.linkText("Đăng xuất"));
		logout.click();
	}

	// đăng nhập thất bại
	@Test
	public void loginErrorTest() {
		// 1.vào trang đăng nhập
		WebElement login = driver.findElement(By.linkText("Đăng nhập"));
		login.click();

		// 2.Nhập dữ liệu
		WebElement txtUsername = driver.findElement(By.cssSelector("[name='username']"));
		txtUsername.sendKeys("hau");
		WebElement txtPassword = driver.findElement(By.cssSelector("[name='password']"));
		txtPassword.sendKeys("123455");

		// 3.click login
		WebElement btnLogin = driver.findElement(By.xpath("//button[@class='aa-browse-btn']"));
		btnLogin.click();

		// 4. chờ thông báo
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 5.kiểm tra message
		String actualString = driver.findElement(By.xpath("//*[@name='loginform']/p[2]")).getText();
		String expectString = "Tài khoản đăng nhập hoặc mật khẩu sai !!!";
		Assert.assertEquals(actualString, expectString);
	}

	@Test
	public void checkoutTest() {

		// 1. Tìm sản phẩm.
		WebElement searchProduct = driver.findElement(By.cssSelector("[name='s']"));
		searchProduct.sendKeys("Balo Mikkor The Edwin Premier M Graphite");
		WebElement btnSeach = driver.findElement(By.xpath("//button[@class='serach-box']"));
		btnSeach.click();

		// 2. Thêm vào giỏ hàng
		WebElement detailProduct = driver.findElement(By.xpath("//a[@class='aa-product-img']"));
		detailProduct.click();
		WebElement addProduct = driver.findElement(By.xpath("//a[@class='aa-add-to-cart-btn']"));
		addProduct.click();
		
		// 3.Đăng nhập để thanh toán
		//3.1 vào trang đăng nhập
		WebElement login = driver.findElement(By.linkText("Đăng nhập"));
		login.click();

		// 3.2.Nhập dữ liệu
		WebElement txtUsername = driver.findElement(By.cssSelector("[name='username']"));
		txtUsername.sendKeys("hau");
		WebElement txtPassword = driver.findElement(By.cssSelector("[name='password']"));
		txtPassword.sendKeys("123456");

		// 3.3.click login
		WebElement btnLogin = driver.findElement(By.xpath("//button[@class='aa-browse-btn']"));
		btnLogin.click();

		//3. vào trang thanh toán
		WebElement checkout = driver.findElement(By.xpath("//div[@class='aa-cartbox-summary']/a[2]"));
		checkout.click();
		
		//4.Nhập địa chỉ nhận
		WebElement addressElement= driver.findElement(By.xpath("//textarea[@name='transaction_address']"));
		addressElement.sendKeys("Le văn việt quận 9");
		
		// 5.Tiến hành đặt hàng
		WebElement pay = driver.findElement(By.xpath("//input[@value='Đặt hàng']"));
		pay.click();

		// 6. chờ thông báo
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 7.kiểm tra message
		String actualString = driver.findElement(By.xpath("//div[@class='checkout-right']/p")).getText();
		String expectString = "Bạn đã đặt hàng thành công!";
		Assert.assertEquals(actualString, expectString);
		
		// 8.Đăng xuất
		WebElement logout = driver.findElement(By.linkText("Đăng xuất"));
		logout.click();
	}

	@AfterTest
	public void tearDown() {
		//
	driver.quit();
	}
}
