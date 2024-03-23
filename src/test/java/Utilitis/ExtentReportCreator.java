package Utilitis;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import TestBase.baseClass;

public class ExtentReportCreator implements ITestListener
{
	public ExtentSparkReporter sparkReporter;//UI of the report
	public ExtentReports extent; //populate common info on the report
	public ExtentTest test;  // creating test case entries in the report and update status of the test methods
 
	String repName;
 
	public void onStart(ITestContext testContext) 
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);//specify location of the report
 
		sparkReporter.config().setDocumentTitle("BeCognizant Header view Report");// TiTle of report 
		sparkReporter.config().setReportName("BeCognizant Functional Testing"); //name of the report
		sparkReporter.config().setTheme(Theme.STANDARD);
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Timesheet");
		extent.setSystemInfo("User Id", System.getProperty("user.name"));
		extent.setSystemInfo("User Name", "Ravikumar" );
		extent.setSystemInfo("Environemnt", "QA");
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty())
		{
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}
 
	public void onTestSuccess(ITestResult result) 
	{
		test = extent.createTest(result.getTestClass().getName()); // create a new enty in the report
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.PASS,result.getName()+" got successfully executed");// update status p/f/s
	}
 
	public void onTestFailure(ITestResult result) 
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL,result.getName()+" got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
		try
		{
			String imgPath = new baseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} 
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}
 
	public void onTestSkipped(ITestResult result) 
	{
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+" got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}
 
	public void onFinish(ITestContext testContext) 
	{
		extent.flush();
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport = new File(pathOfExtentReport);
		try 
		{
			Desktop.getDesktop().browse(extentReport.toURI());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
 
	}

}
