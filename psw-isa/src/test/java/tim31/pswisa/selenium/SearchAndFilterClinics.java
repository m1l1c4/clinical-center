package tim31.pswisa.selenium;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import pages.LogginPage;
import pages.PatientPage;

public class SearchAndFilterClinics {

	private WebDriver browser;

	private LogginPage logginPage;

	private PatientPage patientPage;

	@Before
	public void setup() throws InterruptedException {
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
	public void seleniumSearchAndFilterClinicsOk() throws InterruptedException {

		Thread.sleep(500);
		patientPage.getAllClinicsE2E().click();
		Thread.sleep(500);

		patientPage.getTypeOfCheckupE2E().click();
		patientPage.getTypeOfCheckupE2E().sendKeys("KARDIOLOSKI");
		patientPage.getTypeOfCheckupE2E().click();

		patientPage.getDateOfCheckupE2E().click();
		patientPage.getDateOfCheckupE2E().sendKeys("23");
		patientPage.getDateOfCheckupE2E().click();
		patientPage.getDateOfCheckupE2E().sendKeys("Jan");
		patientPage.getDateOfCheckupE2E().sendKeys(Keys.TAB);
		patientPage.getDateOfCheckupE2E().sendKeys("2020");

		patientPage.getSearchE2E().click();

		List<WebElement> tableAfterSearch = patientPage.getTableE2E().findElements(By.tagName("tr"));
		assertEquals(2, tableAfterSearch.size() - 1);

		patientPage.getFilterOcjena().clear();
		patientPage.getFilterOcjena().sendKeys("20");
		patientPage.getFilterClick().click();
		List<WebElement> tableAfterFilter = patientPage.getTableE2E().findElements(By.tagName("tr"));
		assertEquals(0, tableAfterFilter.size() - 1);

	}

	@Test
	public void seleniumSearchAndFilterClinicsNonEnteredValue() throws InterruptedException {

		Thread.sleep(500);
		patientPage.getAllClinicsE2E().click();
		Thread.sleep(500);

		patientPage.getDateOfCheckupE2E().click();
		patientPage.getDateOfCheckupE2E().sendKeys("23");
		patientPage.getDateOfCheckupE2E().click();
		patientPage.getDateOfCheckupE2E().sendKeys("Jan");
		patientPage.getDateOfCheckupE2E().sendKeys(Keys.TAB);
		patientPage.getDateOfCheckupE2E().sendKeys("2020");

		patientPage.getSearchE2E().click();

		List<WebElement> tableAfterSearch = patientPage.getTableE2E().findElements(By.tagName("tr"));
		assertEquals(2, tableAfterSearch.size() - 1);

		patientPage.getFilterOcjena().clear();
		patientPage.getFilterOcjena().sendKeys("10");
		patientPage.getFilterClick().click();
		List<WebElement> tableAfterFilter = patientPage.getTableE2E().findElements(By.tagName("tr"));
		assertEquals(1, tableAfterFilter.size() - 1);

	}

	@After
	public void tearDown() throws InterruptedException {
		Thread.sleep(1000);
		browser.close();
	}

}