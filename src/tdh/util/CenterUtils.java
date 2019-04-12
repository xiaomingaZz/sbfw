package tdh.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import tdh.framework.log.Logger;
import tdh.framework.util.StringUtils;

public class CenterUtils {

	public static String URL;
	
	public static String ZGYSFYZ;
	
	public static String ZGYZZJGYZ;
	
	public static boolean CZRZB;//是否为每天创建日志表
	
	public static boolean DSF = false;//是否为第三方调用该接口
	
	public static String XMLDIR = "xml/";
	
	private static ApplicationContext ac;

	public static ApplicationContext getContext() {
		if (ac == null) {
			ac = ContextLoader.getCurrentWebApplicationContext();
		}
		return ac;
	}

	public static String createXmlDir(WebServiceContext context, String ipstr) {
		String path = getAbsPath(context);
		path = path.replaceAll("\\\\", "/");
		return path = path + XMLDIR + StringUtils.formatDate(new Date(), "yyyyMMdd") + "/" + ipstr ;
	}

	public static void save2File(String filepath, String data) {
		save2File(new File(filepath), data);
	}

	public static void save2File(File file, String data) {
		try {
			FileUtils.writeStringToFile(file, data);
		} catch (IOException e) {
			Logger.error("save str 2 file error", e);
		}
	}

	public static String genUUId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String encode(String s) {
		return UtilComm.encode(s);
	}

	public static String decode(String s) {
		return UtilComm.decode(s);
	}

	public static String beanToXml(Object bean) {
		return UtilComm.beanToXml(bean);
	}

	public static String beanToXml(Object bean, Map<String, Class<?>> alias) {
		return new XStreamOper().writeBean2XML(bean, alias);
	}

	public static Object xmlToBean(String xml, String root, @SuppressWarnings("rawtypes") Class clazz) {
		return UtilComm.xmlToBean(xml, root, clazz);
	}

	public static String getInStr(String strs) {
		StringBuilder sb = new StringBuilder();
		if (strs != null) {
			if (strs.startsWith(",")) {
				strs = strs.substring(1);
			}
			String[] arr = strs.split(",");
			for (String str : arr) {
				sb.append(",'" + str + "'");
			}
			if (sb.length() > 0) {
				return sb.substring(1);
			}
		}
		return "";
	}

	public static Object getBean(String beanName) {
		return CenterUtils.getContext().getBean(beanName);
	}

	public static String getAbsPath(WebServiceContext context) {
		String path = "";
		try {
			MessageContext ctx = context.getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);
			path = request.getSession().getServletContext().getRealPath("/");
		} catch (Exception e) {
			Logger.error("获取 path 失败", e);
		}
		return path;
	}

	public static String getWsIp(WebServiceContext context) {
		String ipStr = "";
		try {
			MessageContext ctx = context.getMessageContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(AbstractHTTPDestination.HTTP_REQUEST);

			ipStr = StringUtils.trim(request.getRemoteAddr());
		} catch (Exception e) {
			Logger.error("获取Ws Ip 失败", e);
		}
		return ipStr;
	}
}
