package GenericUtilities;

import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ListernersUtility implements ISuiteListener, ITestListener {
	public static ExtentTest test;
	public ExtentReports report;

	@Override
	public void onTestStart(ITestResult result) {
		String testname = result.getMethod().getMethodName();
		String time = new Date().toString().replace(":", "_").replace(" ", "_");

		test = report.createTest(testname + time);
		UtilityObjectClass.setStest(test);
		Reporter.log(testname + " : Test Execution Started", true);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String testname = result.getMethod().getMethodName();
		Reporter.log(testname + " : Test Execution Success", true);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String testname = result.getMethod().getMethodName();
		Reporter.log(testname + " : Test Execution Failed-Screenshot", true);
		String time = new Date().toString().replace(":", "_").replace(" ", "_");

		TakesScreenshot ts = (TakesScreenshot) UtilityObjectClass.getSdriver();
		String src = ts.getScreenshotAs(OutputType.BASE64);
		test.addScreenCaptureFromBase64String(src, "" + testname + time + "");

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testname = result.getMethod().getMethodName();
		Reporter.log(testname + " : Test Execution Skipped", true);
	}

	@Override
	public void onStart(ISuite suite) {
		Reporter.log("Suite Execution started -Adv report configuration", true);
		String time = new Date().toString().replace(":", "_").replace(" ", "_");
		// Configuration of Advanced report
		ExtentSparkReporter spark = new ExtentSparkReporter("./AdvancedReports/VtigerCRMReport" + time + ".html");
		spark.config().setDocumentTitle("VtigerContactAndOrgReports");
		spark.config().setReportName("Contact_Org");
		spark.config().setTheme(Theme.DARK);

		// Configuration test result
		report = new ExtentReports();
		report.attachReporter(spark);
		report.setSystemInfo("OS-Version", "window-11");
		report.setSystemInfo("Chrome Version", "Chrome-143");
		report.setSystemInfo("Edge Version", "Edge-143");
		report.setSystemInfo("Firefox Version", "Firefox-146");

	}

	@Override
	public void onFinish(ISuite suite) {
		Reporter.log("Suite Execution Finished -Adv report backup", true);
		UtilityObjectClass.getStest().log(Status.INFO, "Suite Execution Finished -Adv report backup");
		report.flush();
	}

}
