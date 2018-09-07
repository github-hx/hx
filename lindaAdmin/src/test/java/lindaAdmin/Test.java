package lindaAdmin;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	@org.junit.Test
	public void fun1(){
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String str=format.format(date);
		System.out.println(str);
	}
}
