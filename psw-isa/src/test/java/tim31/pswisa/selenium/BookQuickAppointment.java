package tim31.pswisa.selenium;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;

import pages.LogginPage;
import pages.PatientPage;

public class BookQuickAppointment {

	private WebDriver browser;

	private LogginPage logginPage;

	private PatientPage patientPage;

	@Before
	public void setUp() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "src/test/java/resources/chromedriver.exe");
		browser = new ChromeDriver();
		browser.manage().window().maximize();
		browser.navigate().to("http://localhost:3000/register-page");
		logginPage = PageFactory.initElements(browser, LogginPage.class);
		patientPage = PageFactory.initElements(browser, PatientPage.class);

		logginPage.getShowModalLogin().click();
		logginPage.ensureIsDisplayedEmail();
		logginPage.getLoginEmail().sendKeys("pacijent@gmail.com");
		logginPage.getLoginPassword().sendKeys("sifra1");
		logginPage.getConfirmButton().click();
		logginPage.ensureIsNotVisibleModal();

		Thread.sleep(500);
		assertEquals("http://localhost:3000/patient-page/5", browser.getCurrentUrl());
	}

	@Test
	public void testBookQuickAppointment() throws InterruptedException {

		Thread.sleep(200);
		patientPage.getAllClinicsE2E().click();
		
        List<WebElement> table= patientPage.getTableE2E().findElements(By.tagName("tr"));
        WebElement row = table.get(1);
        WebElement clinicProfile = row.findElement(By.tagName("button"));
        clinicProfile.click();
        Thread.sleep(200);
		assertEquals("http://localhost:3000/clinic-homepage/1", browser.getCurrentUrl());
		
        Thread.sleep(200);
        patientPage.getQuickAppointments().click();
        Thread.sleep(1000);
        List<WebElement> cards = patientPage.getCheckups().findElements(By.className("card"));
        assertEquals(2, cards.size());
        
        WebElement first = cards.get(0);
        WebElement buttonBook = first.findElement(By.tagName("button"));
        buttonBook.click();
        List<WebElement> cards2 = patientPage.getCheckups().findElements(By.className("card"));
        assertEquals(2, cards2.size());
	}

	@AfterMethod
	public void tearDown() {
		browser.close();
	}
}
