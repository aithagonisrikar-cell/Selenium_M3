package contactModule;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.Test;

import BaseClassUtility.BaseClass;
import GenericUtilities.ExcelFileUtility;
import GenericUtilities.JavaUtility;
import GenericUtilities.WebDriverUtility;
import POMUtilities.ContactInfoPompage;
import POMUtilities.ContactPompage;
import POMUtilities.CreateContactPompage;
import POMUtilities.HomePompage;

public class CreateContactTest extends BaseClass {

	@Test(groups = "smoke")
	public void createConTest() throws IOException, InterruptedException {

		// Creating instances
		JavaUtility jutil = new JavaUtility();
		ExcelFileUtility exutil = new ExcelFileUtility();
		WebDriverUtility wutil = new WebDriverUtility();

		// Get the random no
		int randomint = jutil.generateRandomNo();

		// Fetch data from excel utility
		String conname = exutil.fetchDataFromExcelFile("contact", 1, 2) + randomint;

		HomePompage home = new HomePompage(driver);

		// Identify contact tab and click on it
		home.getContact_tab();

		// Identify plus icon and click on it
		ContactPompage con = new ContactPompage(driver);
		con.getConplusicon();

		// Identify lastname TF and enter cont name
		CreateContactPompage createCon = new CreateContactPompage(driver);
		createCon.getLastnameTF(conname);

		// Identify save btn and click on it
		createCon.getSaveBtn();

		// Validate contact name
		ContactInfoPompage coninfo = new ContactInfoPompage(driver);

		String verifyconname = coninfo.getVerifyLastname();

		if (verifyconname.contains(conname)) {
			Reporter.log("Create contact Test pass", true);
		} else {
			Reporter.log("Create contact Test fail", true);
		}

		// Identify contact tab and click on it
		home.getContact_tab();

		// Identify delete link and click on it
		driver.findElement(
				By.xpath("//a[text()='" + conname + "']/ancestor::tr[@bgcolor='white']/descendant::a[text()='del']"))
				.click();

		Thread.sleep(2000);

		// Handle alert popup
		wutil.switchToAlert_ClickOK(driver);

		// Close the excel
		exutil.closeExcelFile();

	}

}
