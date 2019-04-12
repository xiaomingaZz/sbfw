package tdh.frame;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import tdh.util.CacheUtils;

public class StartupListener implements ServletContextListener {
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// 加载TS_FYMC
		CacheUtils.initTsFymc();
		// 加载案件类型代码
		CacheUtils.initDmzhAjlx();
	}

	public String getResourceUrl(ServletContextEvent sce,String fwdz) {
		String result = "";
		String configFile = sce.getServletContext().getRealPath("/") + "/WEB-INF/config/sys.properties";
		Properties properties = new Properties();
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(configFile);
			properties.load(fs);
			// 获取服务地址
			result = properties.getProperty(fwdz);
		} catch (java.io.IOException e) {
			e.printStackTrace();
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}
