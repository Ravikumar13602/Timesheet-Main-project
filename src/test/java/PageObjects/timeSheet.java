package PageObjects;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import Utilitis.ExcelUtilis;

public class timeSheet extends basePage{

	public timeSheet(WebDriver driver) 
	{
		super(driver);
		
	}
	
	String websiteHeader = "Timesheets", firstWeekDate, dateConcat, startDatestr, endDatestr;
	String[] resultDate; 
	
	@FindBy(xpath="//*[@id='PT_PAGETITLElbl']")   //PageTitle Xpath
	WebElement pageTitle;
	
	@FindBy(xpath="//*[@id='win0groupletPTNUI_LAND_REC_GROUPLET$0']")//xpath for timesheet
	WebElement timesheet2;
	
	@FindBy(xpath="//a[contains(@id,'CTS_TS_LAND_PER_DESCR30$') and contains(@class,'ps-link')]")//Xpath for timesheetDates
	List<WebElement> timesheetDates;
	
	@FindBy(xpath="//span[contains(@id,'CTS_TS_LAND_PER_CTS_TS_STATUS_LAND$')]")//xpath for timesheet Status
	List<WebElement> timesheetDatesStatus;
	
	@FindBy(xpath="//*[@id='CTS_TS_LAND_WRK_CTS_TS_SEARCH']")//xpath for search button
	WebElement timesheetSearchBy;
	
	@FindBy(xpath="//*[@id='CTS_TS_LAND_WRK_DATE$prompt']")//xpath for calendar button
	WebElement timesheetCalendar;
	
	@FindBy(xpath="//a[@id='curdate']")//xpath for current date in calendar
	WebElement timesheetCurrentDate;
	
	@FindBy(xpath="//a[@id='CTS_TS_LAND_WRK_SEARCH']")//xpath for search button to click
	WebElement timesheetSearch;
	
	@FindBy(xpath="//*[@id='CTS_TS_LAND_PER_DESCR30$0']")//xpath for current week dates
	WebElement currentWeek;
	
	@FindBy(xpath="//select[@id='CTS_TS_LAND_WRK_CTS_TS_LAND_STATUS']")//xpath for status button
	WebElement timesheetStatus;
	
	@FindBy(xpath="//*[@id='msgcontainer']")
	WebElement errorMsg;
	
	@FindBy(xpath="//*[@id='#ICOK']")//xpath for ok button
	WebElement okButton;
	
	//validating the timesheet header
	public void headerValidation()
	{
		
		System.out.println("");
		if (pageTitle.getText().equalsIgnoreCase(websiteHeader))
		{
			System.out.println("Website header for TimeSheet is verified.\n \nOpening Timesheet.....");
		}
		else
		{
			System.out.println("Website header is not verified.\n \nOpening Timesheet......");
			timesheet2.click();
			
		}
		System.out.println("");
	}
	//Displaying first three weeks timesheet dates in console
	public void threeWeeksTimesheet() throws IOException 
	{
		
		System.out.println(" ");
		System.out.println("--------- Dates of Last Three Weeks ---------");
		for (int date =0;date<3;date++)
		{ 	
			firstWeekDate =timesheetDates.get(0).getText();
			dateConcat = timesheetDates.get(date).getText();
			System.out.println(dateConcat);
			
		}
		resultDate = firstWeekDate.split(" To ");
		System.out.println("---------------------------------------------");
		System.out.println(" ");
	}
	//Displaying current week dates in timesheet
	public void currentWeek() throws InterruptedException, IOException
	{
		
		Select dropdown = new Select(timesheetSearchBy);
		dropdown.selectByVisibleText("Date");
		
		Thread.sleep(4000);
		timesheetCalendar.click();
		timesheetCurrentDate.click();
		timesheetSearch.click();
		
		System.out.println(" ");
		System.out.println("------------- The Current Week --------------");
		System.out.println(currentWeek.getText());
		System.out.println("---------------------------------------------");
		System.out.println(" ");
	}
	//validating the current week details
	public void dateValidationTimesheet() throws IOException 
	{
		startDatestr = resultDate[0];
		endDatestr = resultDate[1];
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MMM-yyyy");
		try 
		{
			Date startDate=dateFormat.parse(startDatestr);
			Date endDate=dateFormat.parse(endDatestr);
			
			System.out.println(" ");
			System.out.println("--------- Date Validation for Current Week ---------");
			if(isSaturday(startDate)&& isFriday(endDate)) 
			{
				
				System.out.println("Timesheet is valid : "+startDatestr+"is Saturday and\n                     "+endDatestr+"is Friday");
			}
			else 
			{
				System.out.println("The Week given in the Timesheet is invalid");
				
			}
			System.out.println("---------------------------------------------");
			System.out.println(" ");
		}
		catch(ParseException e) 
		{
			e.printStackTrace();
		}
	}
    //checking whether the timesheet start day is saturday
	static boolean isSaturday(Date date) 
	{
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY;
	}
	 //checking whether the timesheet end day is friday
	static boolean isFriday(Date date) 
	{
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK)==Calendar.FRIDAY;
	}
	//Displaying the approved timesheet week details
	public void tsStatusApproved() throws InterruptedException, IOException
	{
		
		Select dropdown = new Select(timesheetSearchBy);
		dropdown.selectByVisibleText("Status");
		Thread.sleep(5000);
		Select dropdownStatus = new Select(timesheetStatus);
		dropdownStatus.selectByVisibleText("Approved");
		timesheetSearch.click();
		
		Thread.sleep(4000);
		
		System.out.println(" ");
		System.out.println("------------- The Approved Week --------------");
		for(int a=0;a<timesheetDates.size();a++)
		{
			
			System.out.println(timesheetDates.get(a).getText()+" - '"+timesheetDatesStatus.get(a).getText()+"'");
			ExcelUtilis.write(timesheetDates);
		}
		System.out.println("----------------------------------------------");
		System.out.println(" ");
	}
	//Displaying the overdue timesheet week details
	public void tsStatusOverdue() throws InterruptedException, IOException
	{
		
		Select dropdownStatus = new Select(timesheetStatus);
		dropdownStatus.selectByVisibleText("Overdue");
		timesheetSearch.click();
		Thread.sleep(5000);
		if(errorMsg.isDisplayed())
		{
			System.out.println(" ");
			System.out.println("------------- The Overdue Week --------------");
			System.out.println("No results found. ");
			System.out.println("---------------------------------------------");
			System.out.println(" ");
			okButton.click();
		}
		
		else
		{
			System.out.println(" ");
			System.out.println("------------- The Overdue Week --------------");
			for(int a =0;a<timesheetDates.size();a++)
			{
				System.out.println(timesheetDates.get(a).getText()+" - '"+timesheetDatesStatus.get(a).getText()+"'");
			}
			System.out.println("---------------------------------------------");
			System.out.println(" ");
		}
	}
	//Displaying the Partially approved timesheet week details
	public void tsStatusPartiallyApproved() throws InterruptedException, IOException
	{
		
		Select dropdownStatus = new Select(timesheetStatus);
		dropdownStatus.selectByVisibleText("Partially Approved");
		timesheetSearch.click();
		
		Thread.sleep(5000);
		if(errorMsg.isDisplayed())
		{
			System.out.println(" ");
			System.out.println("------------- The Partially Approved Week --------------");
			System.out.println("No results found. ");
			System.out.println("---------------------------------------------------------");
			System.out.println(" ");
			okButton.click();
		}
		
		else
		{
			System.out.println(" ");
			System.out.println("------------- The Partially Approved Week --------------");
			for(int a =0;a<timesheetDates.size();a++)
			{
				System.out.println(timesheetDates.get(a).getText()+" - '"+timesheetDatesStatus.get(a).getText()+"'");
			}
			System.out.println("---------------------------------------------------------");
			System.out.println(" ");
		}
	}
	//Displaying the pending timesheet week details
	public void tsStatusPending() throws InterruptedException, IOException
	{
		
		Select dropdownStatus = new Select(timesheetStatus);
		dropdownStatus.selectByVisibleText("Pending");
		timesheetSearch.click();
		
		Thread.sleep(5000);
		if(errorMsg.isDisplayed())
		{
			System.out.println(" ");
			System.out.println("------------- The Pending Week --------------");
			System.out.println("No results found. ");
			System.out.println("---------------------------------------------");
			System.out.println(" ");
			okButton.click();
		}
		else 
		{
		System.out.println(" ");
		System.out.println("------------- The Pending Week --------------");
		for(int a =0;a<timesheetDates.size();a++)
		{
			System.out.println(timesheetDates.get(a).getText()+" - '"+timesheetDatesStatus.get(a).getText()+"'");
		
		}
		System.out.println("----------------------------------------------");
		System.out.println(" ");
		}
	}
	//Displaying the saved  timesheet week details
	public void tsStatusSaved() throws InterruptedException, IOException
	{
		
		Select dropdownStatus = new Select(timesheetStatus);
		dropdownStatus.selectByVisibleText("Saved");
		timesheetSearch.click();
		
		Thread.sleep(5000);
		if(errorMsg.isDisplayed())
		{
			System.out.println(" ");
			System.out.println("------------- The Saved Week --------------");
			System.out.println("No results found. ");
			
			System.out.println("-------------------------------------------");
			
			System.out.println(" ");
			okButton.click();
		}
		
		else
		{
			System.out.println(" ");
			System.out.println("------------- The Saved Week --------------");
			for(int a =0;a<timesheetDates.size();a++)
			{
				System.out.println(timesheetDates.get(a).getText()+" - '"+timesheetDatesStatus.get(a).getText()+"'");
			}
			System.out.println("--------------------------------------------");
			System.out.println(" ");
		}	
	}
	
	//Displaying the sentbackfor Revision timesheet week details
	public void tsStatusSentBackforRevision() throws InterruptedException, IOException
	{
		
		Select dropdownStatus = new Select(timesheetStatus);
		dropdownStatus.selectByVisibleText("Sent Back for Revision");
		timesheetSearch.click();
		
		Thread.sleep(5000);
		if(errorMsg.isDisplayed())
		{
			System.out.println(" ");
			System.out.println("------------- The Sent Back for Revision Week --------------");
			System.out.println("No results found. ");
			System.out.println("------------------------------------------------------------");
			System.out.println(" ");
			okButton.click();
		}
		
		else
		{
			System.out.println(" ");
			System.out.println("------------- The Sent Back for Revision Week --------------");
			for(int a =0;a<timesheetDates.size();a++)
			{
				System.out.println(timesheetDates.get(a).getText()+" - '"+timesheetDatesStatus.get(a).getText()+"'");
					
			}
			System.out.println("------------------------------------------------------------");
			System.out.println(" ");
		}
		
	}
	
	//Displaying the submited for approval timesheet week details
	public void tsStatusSubmittedforApproval() throws InterruptedException, IOException
	{
		
		Select dropdownStatus = new Select(timesheetStatus);
		dropdownStatus.selectByVisibleText("Submitted for Approval");
		timesheetSearch.click();
		
		Thread.sleep(5000);
		if(errorMsg.isDisplayed())
		{
			System.out.println(" ");
			System.out.println("------------- The Submitted for Approval Week --------------");
			System.out.println("No results found. ");
			System.out.println("---------------------------------------------");
			System.out.println(" ");
			okButton.click();
		}
		else
		{
		System.out.println(" ");
		System.out.println("------------- The Submitted for Approval Week --------------");
		for(int a =0;a<timesheetDates.size();a++)
		{
			System.out.println(timesheetDates.get(a).getText()+" - '"+timesheetDatesStatus.get(a).getText()+"'");
			
		}
		System.out.println("------------------------------------------------------------");
		System.out.println(" ");
		}
	}
	
}
