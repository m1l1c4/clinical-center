package tim31.pswisa.selenium;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;

import pages.AdminPage;
import pages.LogginPage;

public class UpdateCheckupE2E {
	private WebDriver browser;

	private LogginPage logginPage;

	private AdminPage adminPage;
	
    private JavascriptExecutor js = (JavascriptExecutor) browser;


	@Before
	public void setUp() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "src/test/java/resources/chromedriver.exe");
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:3000/register-page");
		logginPage = PageFactory.initElements(browser, LogginPage.class);
		adminPage = PageFactory.initElements(browser, AdminPage.class);

		logginPage.getShowModalLogin().click();
		logginPage.ensureIsDisplayedEmail();
		logginPage.getLoginEmail().sendKeys("admin@gmail.com");
		logginPage.getLoginPassword().sendKeys("sifra1");
		logginPage.getConfirmButton().click();
		logginPage.ensureIsNotVisibleModal();

		Thread.sleep(500);
		assertEquals("http://localhost:3000/administrator-page", browser.getCurrentUrl());
	}
	
	@Test
	public void testUpdateCheckup() throws InterruptedException {

		Thread.sleep(500);
		adminPage.getRequestsE2E().click();
		assertEquals("http://localhost:3000/registration-request", browser.getCurrentUrl());
		
		Thread.sleep(500);
        List<WebElement> unorderedList= adminPage.getBookCheckupE2E().findElements(By.tagName("li"));
        WebElement li = unorderedList.get(0);
        WebElement checkupForBooking = li.findElement(By.tagName("button"));
        checkupForBooking.click();
        Thread.sleep(200);
		assertEquals("http://localhost:3000/checkup/1", browser.getCurrentUrl());
		
        Thread.sleep(200);
        adminPage.getFindRoomE2E().click();
        Thread.sleep(500);
        js = (JavascriptExecutor) browser;
        js.executeScript("scroll(0, 700);");

        Thread.sleep(2000);
        List<WebElement> freeRooms = adminPage.getChooseRoomE2E().findElements(By.tagName("tr"));
        Thread.sleep(1000);
        WebElement choosenRoom = freeRooms.get(1).findElement(By.tagName("button"));
        choosenRoom.click();
        assertEquals(4, freeRooms.size());
        
        Thread.sleep(200);
        WebElement book = adminPage.getBookRoomE2E();
        book.click();
        Thread.sleep(3000);
		assertEquals("http://localhost:3000/registration-request", browser.getCurrentUrl());
	}

	@AfterMethod
	public void tearDown() {
		browser.close();
	}
}
