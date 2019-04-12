package tdh.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.axis.encoding.Base64;

import tdh.framework.util.StringUtils;
import tdh.security.encryption.StringUtil;

public class UtilComm {
	
	/**
	 * base64编码
	 * @param s
	 * @return
	 */
	public static String encode(String s) {
		s = StringUtils.trim(s);
		try {
			return StringUtil.removeSepcialChar(Base64.encode(s.getBytes("utf-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * base64解码
	 * @param s
	 * @return
	 */
	public static String decode(String s) {
		s = StringUtil.removeSepcialChar(StringUtils.trim(s));
		try {
			byte[] temp = Base64.decode(s);
			return new String(temp, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 将实体转化为 xml数据
	 * @param bean
	 * @return
	 */
	public static String beanToXml(Object bean) {
		
		return new XStreamOper().writeBean2XML(bean);
	}

	/**
	 * 将实体转化为 xml数据
	 * @param bean
	 * @return
	 */
	public static String beanToXml(Object bean,Map<String,Class<?>> alias) {
		return new XStreamOper().writeBean2XML(bean,alias);
	}
	
	/**
	 * 将数据转化为实体
	 * @param xml
	 * @param root
	 * @param clazz
	 * @return
	 */
	public static Object xmlToBean(String xml, String root, @SuppressWarnings("rawtypes") Class clazz) {
		return new XStreamOper().writeXML2Bean(xml, root, clazz);
	}
	
	/**
	 * 将数据转化为实体
	 * @param xml
	 * @param alias xml 数据对应的别名
	 * @return
	 */
	public static Object xmlToBean(String xml,Map<String,Class<?>> alias) {
		return new XStreamOper().writeXML2Bean(xml,alias);
	}
	
	/**
     * 得到前几天的日期
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore (Date d, int day) {
        if (d == null) {
            d = new Date();
        }
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }
    public static String getDateString()
    {
    	Date d =new Date();
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    	String time = format.format(d);
    	return time;
    }
    
}
