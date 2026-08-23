package SeleniumLogics;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class GetDataFromExcelSheets {

	public static void main(String[] args) throws EncryptedDocumentException, IOException, InterruptedException {

		// Open the Excel sheet
		FileInputStream excel = new FileInputStream("C:/Users/Admin/Desktop/abc.xlsx");
		Sheet a = WorkbookFactory.create(excel).getSheet("Sheet1");
		String b = a.getRow(0).getCell(0).getStringCellValue();
		String c = a.getRow(1).getCell(0).getStringCellValue();
		String d = a.getRow(2).getCell(0).getStringCellValue();

		Thread.sleep(2000);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);

	}
}
