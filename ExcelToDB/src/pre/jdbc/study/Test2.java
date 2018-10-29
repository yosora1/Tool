package pre.jdbc.study;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Test2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//读取Excel文件中的内容
		String fromPath="C:\\Users\\zhongkaihe\\Desktop\\1234.xlsx";
		XSSFWorkbook sfb=new XSSFWorkbook(fromPath);
		XSSFSheet xsfs=sfb.getSheet("111");
		String RealName="";
		String Village="";
		String Town="";
		String River="";
		String Data="";
		int UserId=0,TownID = 0,RiverID=0,VillageID=0;
		for(int i=0;i<xsfs.getPhysicalNumberOfRows();i++){
			for(int j=0;j<4;j++){
				XSSFRow row=xsfs.getRow(i);
				XSSFCell cell=row.getCell(j);
				Data=cell.toString();
				if(j==0){
					if(Data.length()>1){
						Town=Data;
					}
				}
				if(j==1){
					if(Data.length()>1){
						Village=Data;
					}
				}
				if(j==2){
					RealName=Data;
				}
				if(j==3){
					if(Data.length()>1){
						River=Data;
					}
				}
			}
			System.out.print(Town+"  "+Village+"  "+RealName+"  "+River);
			//查询对应的ID,有各种简洁的方式，我只是懒得整合了，反正估计导入这破数据也就这一次
			String villageSql="select ID from VillageInfo where VillageName='"+Village+"'";
			String sql="select ID from TownInfo where TownName='"+Town+"'";
			String userSql="select ID from UserInfo where RealName='"+RealName+"'";
			String riverSql="select ID from RiverInfo where RiverName='"+River+"'";
			TestJDBC test=new TestJDBC();
			Connection con=test.getConnection();
			Statement state,state1,state2,state3;
			try {
				state3=con.createStatement();
				state = con.createStatement();
				state1=con.createStatement();
				state2=con.createStatement();
				
				ResultSet d=state3.executeQuery(villageSql);
				ResultSet a=state.executeQuery(sql);
				ResultSet b=state1.executeQuery(userSql);
				ResultSet c=state2.executeQuery(riverSql);
				
				while(a.next()){
					TownID=a.getInt(1);
					
				}
				while(b.next()){
					UserId=b.getInt(1);
				}
				while(c.next()){
					RiverID=c.getInt(1);
				}
				while(d.next()){
					VillageID=d.getInt(1);
					System.out.println("Village:"+VillageID);
				}
				state.close();
				state1.close();
				state2.close();
				state3.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//插入数据
			System.out.println("TownID:"+TownID+"  VillageID:"+VillageID+"  UserId:"+UserId+"  RiverId:"+RiverID);
			String updSql="update RiverLong set TownID='"+TownID+"',VillageID='"+VillageID+"' where UserID='"+UserId+"' and RiverID='"+RiverID+"'";
			
			try {
				state3=con.createStatement();
				state3.executeUpdate(updSql);
				System.out.println("OK");
				state3.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
