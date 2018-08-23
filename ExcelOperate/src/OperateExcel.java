import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class OperateExcel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//文件读取地址
		String formPth="InputPath";
		//导出地址
		String toPath="OutputPath";
//		newExcel(toPath);
		copyExcel(formPth,toPath);

	}
	//新建、写入、导出Excel
	public static void newExcel(String path){
		//XSSF前缀的类用于xslx文件，HSSF前缀的类用于xsl文件
		//创建XSSFWorkbook对象，用来打开或创建xslx文件对象
		XSSFWorkbook wb=new XSSFWorkbook();
		//创建HSSFSheet对象，在Excel中新建一个sheet
		XSSFSheet sheet=wb.createSheet("一个新建的Sheet");
		//创建HSSFRow对象新建行
		for(int j=0;j<=9;j++){
			XSSFRow row=sheet.createRow(j);
			//创建HSSFCell对象，通过Cell对象进行读写
			for(int i=0;i<=9;i++){
				XSSFCell cell=row.createCell(i);
				cell.setCellValue("第"+(i+1)+"列"+(j+1)+"行");
			}
		}
		
		//导出Excel
		try {
			FileOutputStream output=new FileOutputStream(path);
			wb.write(output);
			System.out.println("SUCCESS");
			wb.close();
			output.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//读取、复制Excel
	public static void copyExcel(String fromPath,String toPath){
		try {
			FileInputStream input=new FileInputStream(toPath);
			XSSFWorkbook sfb=new XSSFWorkbook(fromPath);
			XSSFWorkbook outSfb=new XSSFWorkbook(input);
			input.close();
			XSSFSheet xsfs=sfb.getSheet("SheetName");
			XSSFSheet outXsfs=outSfb.getSheet("SheetName");
			
				XSSFRow row=xsfs.getRow(0);
				XSSFRow outRow=outXsfs.getRow(0);
				
				for(int i=1;i<xsfs.getPhysicalNumberOfRows();i++){
					XSSFRow row1=xsfs.getRow(i);
					XSSFRow outRow1=outXsfs.createRow(i);
					for(int j=0;j<row.getPhysicalNumberOfCells();j++){
						if(outRow.getCell(j)!=null&&row.getCell(j).toString().equals(outRow.getCell(j).toString())){
								XSSFCell cell=row1.getCell(j);
								XSSFCell outCell=outRow1.createCell(j);
								outCell.setCellValue(cell.toString());
								System.out.println(i+"行"+j+"列"+"::"+outCell.toString());
							}
					}
				}
				
				FileOutputStream output=new FileOutputStream(toPath);
				outSfb.write(output);
				output.flush();
				sfb.close();
				output.close();
				System.out.println("Success");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
