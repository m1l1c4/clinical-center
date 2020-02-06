package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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
	
	@FindBy(id = "pregledi")
	private WebElement checkups;

	public void ensureIsDisplayedAllClinicsE2E() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(allClinicsE2E));
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

	public void ensureIsDisplayedTableE2E() {
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(tableE2E));
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

}