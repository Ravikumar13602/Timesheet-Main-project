package PageObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class oneCognizant extends basePage {

	public oneCognizant(WebDriver driver)
	{
		super(driver);
		
	}
	
	@FindBy(css="li.searchTopBar")//xpath for searchIcon in edge browser
	WebElement searchIconEdge;
	
	@FindBy(xpath="//*[@id='oneC_searchAutoComplete']")//xpath for search bar in chrome
	WebElement searchBarChrome;
	
	@FindBy(xpath="//*[@id='oneCSearchTop']")//xpath for search bar in edge browser
	WebElement searchBarEdge;
	
	@FindBy(xpath="//*[@id='newSearchQALST']/div[1]/div/div[2]")//xpath for submit timesheet button
	WebElement timeSheetChrome;
	
	//@FindBy(xpath="//*[@id='newSearchQALST']/div[1]")
	//WebElement timeSheetIcon1;
	
	//@FindBy(xpath="//*[@id='newSearchQALST']/div[1]/div")
	//WebElement timeSheetIcon2;

	//To enter timesheet word in search bar
	public void inputSearchBar(String br)
	{
		
		//System.out.println(br);
		
		if(br.equalsIgnoreCase("chrome")) 
		{
			searchBarChrome.sendKeys("timesheet");
			timeSheetChrome.click();
		}
		else if(br.equalsIgnoreCase("Edge"))
		{
			searchIconEdge.click();
			searchBarEdge.sendKeys("timesheet");
			
			try
			{
				//timeSheetIcon1.click();
				timeSheetChrome.click();
			}
			catch(Exception e)
			{
				System.out.println(e);
				//timeSheetIcon2.click();
			}
			
		}
	}
	
	//Driver navigate to timesheet page
	public void windowHandelsTimesheet(WebDriver driver) throws InterruptedException
	{
		Set <String> Window = driver.getWindowHandles();
	    List <String> Window1 = new ArrayList<String>(Window);
		driver.switchTo().window(Window1.get(2));
		
	}
}
