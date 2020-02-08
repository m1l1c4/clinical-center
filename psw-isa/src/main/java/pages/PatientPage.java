package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PatientPage {

	private WebDriver driver;

	@FindBy(id = "allClinicsE2E")
	private WebElement allClinicsE2E;

	@FindBy(id = "typeOfCheckupE2E")
	private WebElement typeOfCheckupE2E;

	@FindBy(id = "dateOfCheckupE2E")
	private WebElement dateOfCheckupE2E;

	@FindBy(id = "searchE2E")
	private WebElement searchE2E;

	@FindBy(id = "tableE2E")
	private WebElement tableE2E;

	@FindBy(id = "filterOcjena")
	private WebElement filterOcjena;

	@FindBy(id = "filterClick")
	private WebElement filterClick;

	@FindBy(id = "predefinisaniPregledi")
	private WebElement quickAppointments;

	@FindBy(xpath = "//*[@id=\"tableE2E\"]//tr")
	private List<WebElement> rows;
	
	@FindBy(xpath = "//*[@id=\"pregledi\"]/div")
	private List<WebElement> cards;

	@FindBy(id = "pregledi")
	private WebElement checkups;

	@FindBy(id = "alert")
	private WebElement alert;
	
	@FindBy(id = "labelHide")
	private WebElement label;
	
	public void ensureIsDisplayedAllClinicsE2E() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(allClinicsE2E));
	}
	
	public void ensureIsDisplayedAlert() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(alert));
	}

	public void ensureIsDisplayedTypeOfCheckupE2E() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(typeOfCheckupE2E));
	}

	public void ensureIsDisplayedDateOfCheckupE2E() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(dateOfCheckupE2E));
	}

	public void ensureIsDisplayedDateOfsSarchE2E() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(searchE2E));
	}
	
	public void ensureIsDisplayedQuickAppointments() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(quickAppointments));
	}
	
	public void ensureIsDisplayedFilter() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(filterOcjena));
	}


	public void ensureIsDisplayedTableE2E() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(tableE2E));
	}
	
	public void ensureIsDisplayedLabel() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(label));
	}

	public void ensureRows() {
		(new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver input) {
				// TODO Auto-generated method stub
				return rows.size() > 1;
			}
		});
	}
	
	public void ensureCards() {
		(new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver input) {
				// TODO Auto-generated method stub
				return cards.size() > 0;
			}
		});
	}
	
	public void ensureLessCards(List<WebElement> oldCards) {
		(new WebDriverWait(driver, 20)).until(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver input) {
				// TODO Auto-generated method stub
				return oldCards.size() > cards.size();
			}
		});
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getAllClinicsE2E() {
		return allClinicsE2E;
	}

	public void setAllClinicsE2E(WebElement allClinicsE2E) {
		this.allClinicsE2E = allClinicsE2E;
	}

	public WebElement getTypeOfCheckupE2E() {
		return typeOfCheckupE2E;
	}

	public void setTypeOfCheckupE2E(WebElement typeOfCheckupE2E) {
		this.typeOfCheckupE2E = typeOfCheckupE2E;
	}

	public WebElement getDateOfCheckupE2E() {
		return dateOfCheckupE2E;
	}

	public void setDateOfCheckupE2E(WebElement dateOfCheckupE2E) {
		this.dateOfCheckupE2E = dateOfCheckupE2E;
	}

	public WebElement getSearchE2E() {
		return searchE2E;
	}

	public void setSearchE2E(WebElement searchE2E) {
		this.searchE2E = searchE2E;
	}

	public WebElement getTableE2E() {
		return tableE2E;
	}

	public void setTableE2E(WebElement tableE2E) {
		this.tableE2E = tableE2E;
	}

	public WebElement getFilterOcjena() {
		return filterOcjena;
	}

	public void setFilterOcjena(WebElement filterOcjena) {
		this.filterOcjena = filterOcjena;
	}

	public WebElement getFilterClick() {
		return filterClick;
	}

	public void setFilterClick(WebElement filterClick) {
		this.filterClick = filterClick;
	}

	public WebElement getQuickAppointments() {
		return quickAppointments;
	}

	public void setQuickAppointments(WebElement quickAppointments) {
		this.quickAppointments = quickAppointments;
	}

	public WebElement getCheckups() {
		return checkups;
	}

	public void setCheckups(WebElement checkups) {
		this.checkups = checkups;
	}

	public PatientPage(WebDriver driver) {
		super();
		this.driver = driver;
	}

	public PatientPage() {
		super();
	}

	public List<WebElement> getRows() {
		return rows;
	}

	public void setRows(List<WebElement> rows) {
		this.rows = rows;
	}
	
	public WebElement findFirstButtonClinic() {
        WebElement row = rows.get(1);
        return row.findElement(By.tagName("button"));
	}
	
	public WebElement findFirstButtonBook() {
        WebElement card = cards.get(0);
        return card.findElement(By.tagName("button"));
	}

	public List<WebElement> getCards() {
		return cards;
	}

	public void setCards(List<WebElement> cards) {
		this.cards = cards;
	}

	public void setTypeOfCheckup(String type) {
		typeOfCheckupE2E.click();
		typeOfCheckupE2E.sendKeys("KARDIOLOSKI");
		typeOfCheckupE2E.click();
	}
	
	public void setOcjena(String a) {
		filterOcjena.clear();
		filterOcjena.sendKeys(a);
		filterClick.click();
	}
	
	public void setDateOfCheckup(String a, String b, String c) {
		dateOfCheckupE2E.click();
		dateOfCheckupE2E.sendKeys(a);
		dateOfCheckupE2E.click();
		dateOfCheckupE2E.sendKeys(b);
		dateOfCheckupE2E.sendKeys(Keys.TAB);
		dateOfCheckupE2E.sendKeys(c);
	}
}