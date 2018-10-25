package pre.jdbc.study;

import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import java.util.Date;
import java.util.Locale;
public class Test1 {
	
	public static void main(String[] arg) throws IOException{
		String fromPath="C:\\Users\\zhongkaihe\\Desktop\\1234.xlsx";
		XSSFWorkbook sfb=new XSSFWorkbook(fromPath);
		XSSFSheet xsfs=sfb.getSheet("111");
		for(int i=0;i<xsfs.getPhysicalNumberOfRows();i++){
			String RealName="";
			String Phone="";
			String UserName="";
			Date da = new Date(); 
			Timestamp time=new Timestamp(da.getTime());
			for(int j=0;j<2;j++){
				DecimalFormat df = new DecimalFormat("0");
				
				XSSFRow row=xsfs.getRow(i);
				XSSFCell cell=row.getCell(j);
				if(j==1){
					switch (cell.getCellTypeEnum()) {
					 case STRING:  
	                     Phone = cell.toString();  
	                     break;
					 case NUMERIC:
						 Phone=df.format(cell.getNumericCellValue());
						 break;
					}
				}else if(j==0){
					RealName=cell.toString().trim();
				}
			//	str=cell.toString().trim();
				
			}
			String sql="select count(*) from UserInfo where RealName='"+RealName+"'";
			try {
				TestJDBC test=new TestJDBC();
				Connection con=test.getConnection();
				Statement state=con.createStatement();
				ResultSet a=state.executeQuery(sql);
				while(a.next()){
					int result=a.getInt(1);
					if(result>0){
						System.out.println("--------"+RealName+"--------");
						
					}else{
						System.out.println(RealName+"不存在"+Phone);
						
						char[] newChar = RealName.toCharArray();
						HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();  
						  defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);  
						  defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);  
						 for (int i1 = 0; i1 < newChar.length; i1++) {  
						        if (newChar[i1] > 128) {  
						         try {  
						        	 UserName += PinyinHelper.toHanyuPinyinStringArray(newChar[i1], defaultFormat)[0];  
						             } catch (BadHanyuPinyinOutputFormatCombination e) {  
						                   e.printStackTrace();  
						           }  
						       }else{  
						             UserName += newChar[i1];  
						           }  
						      }  
				
						String sql1="";
						if(Phone.length()>8){
							sql1="insert into UserInfo(UserName,Password,RealName,TownID,Phone,Email,GroupID,AddUser,AddTime,UpdUser,UpdTime,DelFlag) values"
									+ " ('"+UserName+"','49BA59ABBE56E057','"+RealName+"',0,'"+Phone+"','',3,'7','"+time+"','7','"+time+"',0);";
						}
						else{
							sql1="insert into UserInfo(UserName,Password,RealName,TownID,Phone,Telnumber,Email,GroupID,AddUser,AddTime,UpdUser,UpdTime,DelFlag) values"
									+ " ('"+UserName+"','49BA59ABBE56E057','"+RealName+"',0,'','"+Phone+"','',3,'7','"+time+"','7','"+time+"',0);";
						}
				//		String sql="update UserInfo set Phone='',Telnumber='"+Phone+"' where RealName='"+RealName+"'";
						try {
							TestJDBC test1=new TestJDBC();
							Connection con1=test1.getConnection();
							Statement state1=con1.createStatement();
							state1.executeUpdate(sql1);
							state1.close();
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
				state.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
	}
	

}
