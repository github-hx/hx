package starter.user.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import starter.user.bean.User;
import starter.user.bean.UserLoginInfo;
import starter.user.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public UserLoginInfo login(User user,HttpServletRequest request) throws Exception{
		//获取用户登录时客户端IP
		String ip=getClientIP(request);
		//根据IP获取客户端物理地址
		String addr=getAddressByIP(ip);
		user.setUserIp(ip);
		user.setUserAddress(addr);
		//记录登录时间
		Date date=new Date();
		user.setUserDate(date);
		UserLoginInfo userLoginInfo=userService.login(user);
		return userLoginInfo;
	}
	
	/*
	 * 获取用户登录时客户端IP
	 */
	public String getClientIP(HttpServletRequest request) throws UnknownHostException{
		String ip=null;
		String ipAddress=request.getHeader("X-Forwared-For");
		if(ipAddress==null || ipAddress.length()==0 || "unknown".equalsIgnoreCase(ipAddress)){
			ipAddress=request.getHeader("Proxy-Client-IP");
		}
		if(ipAddress==null || ipAddress.length()==0 || "unknown".equalsIgnoreCase(ipAddress)){
			ipAddress=request.getHeader("WL-Proxy-Client-IP");
		}
		if(ipAddress==null || ipAddress.length()==0 || "unknown".equalsIgnoreCase(ipAddress)){
			ipAddress=request.getHeader("HTTP_CLIENT_IP");
		}
		if(ipAddress==null || ipAddress.length()==0 || "unknown".equalsIgnoreCase(ipAddress)){
			ipAddress=request.getHeader("X-Real-IP");
		}
		if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
			ip=request.getRemoteAddr();
			//本地测试时能获取到本地的IP而不是0:0:0:0:0:0:0:1或127.0.0.1
			if("127.0.0.1".equalsIgnoreCase(ip) || "0:0:0:0:0:0:0:1".equalsIgnoreCase(ip)){
				//根据网卡获取本机配置的IP
				InetAddress inetAddress=null;
				inetAddress=InetAddress.getLocalHost();
				ip=inetAddress.getHostAddress();
			}
		}
		if(ipAddress != null && ipAddress.length() != 0){
			if(ipAddress.indexOf(",") == -1){
				ip=ipAddress;
			}else{
				ip=ipAddress.split(",")[0];
			}
		}
		
		return ip;
	}
	
	/*
	 * 根据客户端IP获取客户端地址
	 */
	public String getAddressByIP(String ip) throws IOException{
		//获取GeoLite2-City.mmdb(IP地址库)文件路劲
		URL fileURL=this.getClass().getClassLoader().getResource("iPAddressLibrary/GeoLite2-City.mmdb");
		File iPDataBase=new File(fileURL.getPath());
		//读取GeoLite2-City.mmdb文件中的内容
		DatabaseReader reader=new DatabaseReader.Builder(iPDataBase).build();
		InetAddress inetAddress=InetAddress.getByName("182.128.0.0");
		//获取查询结果
		CityResponse response=null;;
		try {
			response = reader.city(inetAddress);
		} catch (GeoIp2Exception e) {
			//IP在IP地址库中不存在对应的地址
			return null;
		}
		//获取纬度,经度
		Location location=response.getLocation();
		Double lat=location.getLatitude();
		Double log=location.getLongitude();
		String address=getDistrict(lat.toString(), log.toString());
		return address;
	}
	
	/*
	 * 根据客户端的纬度经度获取地址
	 */
	public String getDistrict(String lat,String log) throws IOException{
		 String urlString = "http://gc.ditu.aliyun.com/regeocoding?l="+lat+","+log+"&type=111";
		 URL url=new URL(urlString);
		 HttpURLConnection conn= (HttpURLConnection) url.openConnection();
		 conn.setDoOutput(true);
		 conn.setRequestMethod("POST");
		 BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		 String result="";
		 String line;
		 while((line=br.readLine()) != null){
			 result +=line+"\n";
		 }
		 br.close();
		 //返回的数据是json格式的数据
		 JSONObject jsonObject = JSONObject.fromObject(result);
		 JSONArray jsonArray=JSONArray.fromObject(jsonObject.getString("addrList"));
		 JSONObject jb= JSONObject.fromObject(jsonArray.get(0));
		 String address=jb.getString("admName")+","+jb.getString("name");
		return address;
	}
}
