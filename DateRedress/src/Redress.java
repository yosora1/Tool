import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Redress {
	public static void main(String args[]){
		try {
			BufferedReader in=new BufferedReader(new FileReader("C:\\Users\\zhongkaihe\\Desktop\\aa.txt"));
			BufferedWriter out=new BufferedWriter(new FileWriter("C:\\Users\\zhongkaihe\\Desktop\\1234.txt"));
			String s="";
			String pat="^[0-9]{4}-[01][0-9]-[0-3][0-9]$";
			Pattern pattern=Pattern.compile(pat);
			while((s=in.readLine())!=null){
				if(!(pattern.matcher(s)).matches()){
					if(!(s.replaceAll(" ", "").isEmpty())){
						if(s.charAt(4)=='-'){
							if(s.length()<=7){
								s=s+"-01";
							}
							if(s.charAt(6)=='-'){
								StringBuffer sb=new StringBuffer(s);
								sb.insert(5, "0");
								s=sb.toString();
							}
							if(s.length()==9){
								StringBuffer sb=new StringBuffer(s);
								sb.insert(8, "0");
								s=sb.toString();
							}
						}else{
							s=s+"//////////////////";
						}
					}
					}
				out.write(s);
				out.write("\r\n");
			}
			System.out.println("SUCCESS");
			in.close();
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
	}

}
