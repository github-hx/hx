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
		//��ȡ�û���¼ʱ�ͻ���IP
		String ip=getClientIP(request);
		//����IP��ȡ�ͻ��������ַ
		String addr=getAddressByIP(ip);
		user.setUserIp(ip);
		user.setUserAddress(addr);
		//��¼��¼ʱ��
		Date date=new Date();
		user.setUserDate(date);
		UserLoginInfo userLoginInfo=userService.login(user);
		return userLoginInfo;
	}
	
	/*
	 * ��ȡ�û���¼ʱ�ͻ���IP
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
			//���ز���ʱ�ܻ�ȡ�����ص�IP������0:0:0:0:0:0:0:1��127.0.0.1
			if("127.0.0.1".equalsIgnoreCase(ip) || "0:0:0:0:0:0:0:1".equalsIgnoreCase(ip)){
				//����������ȡ�������õ�IP
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
	 * ���ݿͻ���IP��ȡ�ͻ��˵�ַ
	 */
	public String getAddressByIP(String ip) throws IOException{
		//��ȡGeoLite2-City.mmdb(IP��ַ��)�ļ�·��
		URL fileURL=this.getClass().getClassLoader().getResource("iPAddressLibrary/GeoLite2-City.mmdb");
		File iPDataBase=new File(fileURL.getPath());
		//��ȡGeoLite2-City.mmdb�ļ��е�����
		DatabaseReader reader=new DatabaseReader.Builder(iPDataBase).build();
		InetAddress inetAddress=InetAddress.getByName("182.128.0.0");
		//��ȡ��ѯ���
		CityResponse response=null;;
		try {
			response = reader.city(inetAddress);
		} catch (GeoIp2Exception e) {
			//IP��IP��ַ���в����ڶ�Ӧ�ĵ�ַ
			return null;
		}
		//��ȡγ��,����
		Location location=response.getLocation();
		Double lat=location.getLatitude();
		Double log=location.getLongitude();
		String address=getDistrict(lat.toString(), log.toString());
		return address;
	}
	
	/*
	 * ���ݿͻ��˵�γ�Ⱦ��Ȼ�ȡ��ַ
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
		 //���ص�������json��ʽ������
		 JSONObject jsonObject = JSONObject.fromObject(result);
		 JSONArray jsonArray=JSONArray.fromObject(jsonObject.getString("addrList"));
		 JSONObject jb= JSONObject.fromObject(jsonArray.get(0));
		 String address=jb.getString("admName")+","+jb.getString("name");
		return address;
	}
}
