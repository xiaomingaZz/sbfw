package tdh.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesUtils {
	
	
	/**
	 * 读取配置文件
	 * @param filename
	 * @return
	 */
	public  static Properties loadProperties(String filename){
		InputStream in = null;
		Properties p = new Properties();
		try {
			in = new FileInputStream(filename);
			if (in != null) {
				p.load(in);
			}
		}catch(Exception e){
			System.out.println("读取配置文件失败："+filename);
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return p;
	}
	
	public static Properties   loadProperties(InputStream in) {
		Properties p = new Properties();
		 try {
			p.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}
}
