package TestCases;

import java.io.IOException;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import PageObjects.beCognizant;
import PageObjects.oneCognizant;
import PageObjects.timeSheet;
import TestBase.baseClass;

public class TC001_beCognizant extends baseClass
{
	baseClass bs=new baseClass();
	@Test(priority=1)

	public void userVerification() throws InterruptedException, IOException 
	{
		logger.info("***** starting TC001_beCognizant *****");
		
		beCognizant bec = new beCognizant(driver);//creating object for becognizant page
		bec.clickAccDetails();//calling clickACCDetails method
		logger.info("clicked on profile");
		Thread.sleep(1000);
		logger.info("verifying name of user");
		bec.getAccManagerName();//calling getAccessmanagername method
		bs.captureScreen("img_userprofile");//capturing the screen shot of that page
		logger.info("verifying email of user");
		bec.getAccManagerEmail();//calling getAccmanageremail method
		Thread.sleep(10000);
		System.out.println("Account verified");
		logger.info("Scrolling the page upto OneCognizant link ");
		bec.verifyOneCogni();//calling verifying one cogni method
		logger.info("opening the OneCognizant");
		Thread.sleep(10000);
		bec.clickOnOneCogni();//calling ClickOne Cogni method
		logger.info("Capturing ScreenShots");
		bec.takeScreenShot();//capturing screen shot of page
		bec.windowHandlesOneCog(driver);
		logger.info("handled the OneCognizant window");
		bs.captureScreen("img_onecognizant");//capturing screen shot of one cognizant page
		
		
		}
		
	
	@Test(priority=2)
	@Parameters("browser")
	
	public void oneCognizantFunctions(String br) throws InterruptedException
	{
		logger.info("Navigated to OneCognizant");
		oneCognizant one = new oneCognizant(driver); //creating object for oneCognizant page
		Thread.sleep(1000);
		
		logger.info("clicked on search Icon");
		one.inputSearchBar(br);
		
		logger.info("input timesheet in search bar");
		bs.captureScreen("img_timesheetIcon");
		
		Thread.sleep(10000);
		one.windowHandelsTimesheet(driver);
		logger.info("handled the timesheet window");
	}
	
	@Test(priority=3)
	public void timeSheetNavigation() throws InterruptedException, IOException
	{
		logger.info("Navigated to timesheet");
		timeSheet ts = new timeSheet(driver);//creating object for timesheet page
		
		ts.headerValidation();
		bs.captureScreen("img_timesheet");
		logger.info("validating the header");
		
		Thread.sleep(1000);
		ts.threeWeeksTimesheet();
		logger.info("three weeks of timesheet");
		bs.captureScreen("img_firstThreeWeeks");//capturing screenshot for timesheet page
		
		Thread.sleep(1000);
		ts.currentWeek();
		logger.info("Displayed current week");
		bs.captureScreen("img_currentWeek");//capturing screenshot for current weeks
		ts.dateValidationTimesheet();
		
		Thread.sleep(5000);
		ts.tsStatusApproved();
		logger.info("displayed  tsStatusApproved");
		bs.captureScreen("img_StatusApproved");//capturing screenshot for approved weeks
		
		Thread.sleep(3000);
		ts.tsStatusOverdue();
		logger.info("displayed tsStatusOverdue");
		bs.captureScreen("img_StatusOverdue");//capturing screenshot for status overdue weeks
		
		Thread.sleep(5000);
		ts.tsStatusPartiallyApproved();
		logger.info("displayed tsStatusPartiallyApproved");
		bs.captureScreen("img_StatusPartiallyApproved");////capturing screenshot for partially submitted for approved weeks
		
		Thread.sleep(5000);
		ts.tsStatusPending();
		logger.info("displayed tsStatusPending ");
		bs.captureScreen("img_StatusPending");//capturing screenshot for status pending weeks
	
		Thread.sleep(5000);
		ts.tsStatusSaved();
		logger.info("displayed tsStatusSaved");
		bs.captureScreen("img_StatusSaved");//capturing screenshot for status saved weeks
		
		Thread.sleep(5000);
		ts.tsStatusSentBackforRevision();
		logger.info("displayed tsStatusSentBackforRevision");
		bs.captureScreen("img_StatusSentBackforRevision");//capturing screenshot for Sentback for revision weeks
		
		Thread.sleep(5000);
		ts.tsStatusSubmittedforApproval();
		logger.info("displayed tsStatusSubmittedforApproval");
		bs.captureScreen("img_StatusSubmittedforApproval");//capturing screenshot for submitted for approval weeks
		
		logger.info("***** closing the browser *****");	
	}
		
		
		
}
