package Utilitis;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebElement;

public class ExcelUtilis {
	public static void write(List<WebElement> timesheetDates) throws IOException
	{
	FileOutputStream fs=new FileOutputStream(".\\TestData\\data.xlsx");
	//Workbook creation in file
	XSSFWorkbook workbook=new XSSFWorkbook();
	//Sheet creation in file
	XSSFSheet sheet=workbook.createSheet();
	//Creation of row in sheet
	//XSSFRow row=sheet.createRow(0);
	//Creation of cell
	int k=0;
	//Sending data to excel file
	for(int i=0;i<timesheetDates.size();i++) {
		XSSFRow row1=sheet.createRow(i);
			row1.createCell(0).setCellValue(timesheetDates.get(k).getText());
			k++;
	}
	
	workbook.write(fs);
	//Closing the workbook
	workbook.close();
	fs.close();

}
}
