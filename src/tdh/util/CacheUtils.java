package tdh.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tdh.framework.util.StringUtils;
import tdh.sjsb.service.CommService;

/**
 * 缓存
 *
 * @author 代雅男
 *
 *         2016年6月18日下午3:14:44
 */
public class CacheUtils {

	/******** 法院代码翻译cache ********/
	public static Map<Integer, TsFymc> FY_MAP = new HashMap<Integer, TsFymc>();
	
	public static Map<Integer, DmzhAjlx> AJLX_MAP = new HashMap<Integer, DmzhAjlx>();
	
	/**
	 * 初始化TSFYMC
	 */
	public static void initTsFymc() {
		FY_MAP.clear();
		CommService serv = (CommService) CenterUtils.getBean("commService");
		List<Map<String, Object>> list = serv.getAllFyList();
		Integer dm = null;
		for (Map<String, Object> map : list) {
			if (map == null)
				continue;
			dm = (Integer) map.get("DM");
			if (dm == null)
				continue;
			FY_MAP.put(dm, new TsFymc(StringUtils.trim(map.get("FYDM")), StringUtils.trim(map.get("FJM"))));
		}
	}
	
	/**
	 * 根据DM获取FJM
	 * 
	 * @param dm
	 * @return
	 */
	public static String getFjm(Integer dm) {
		TsFymc bean = FY_MAP.get(dm);
		if (bean != null) {
			return bean.getFjm();
		} else {
			return "";
		}
	}

	/**
	 * 根据DM获取FYDM
	 * 
	 * @param dm
	 * @return
	 */
	public static String getFydm(Integer dm) {
		TsFymc bean = FY_MAP.get(dm);
		if (bean != null) {
			return bean.getFydm();
		} else {
			return "";
		}
	}
	
	/**
	 * 初始化案件类型代码
	 */
	public static void initDmzhAjlx(){
		AJLX_MAP.clear();
		CommService serv = (CommService) CenterUtils.getBean("commService");
		List<Map<String, Object>> list = serv.getAllAjlxList();
		Integer dm = null;
		for (Map<String, Object> map : list) {
			if (map == null)
				continue;
			dm = (Integer) map.get("DM");
			if (dm == null)
				continue;
			AJLX_MAP.put(dm, new DmzhAjlx((Integer)map.get("DM"),StringUtils.trim(map.get("AJLXMC")),StringUtils.trim(map.get("AJLXDM")),StringUtils.trim(map.get("SM"))));
		}
	}
	
	public static String getAjlxdmByDm(int dm){
		DmzhAjlx bean = AJLX_MAP.get(dm);
		if (bean != null) {
			return bean.getAjlxdm();
		} else {
			return "";
		}
	}
	
	public static String getAjlxmcByDm(String dm){
		DmzhAjlx bean = AJLX_MAP.get(dm);
		if (bean != null) {
			return bean.getAjlxmc();
		} else {
			return "";
		}
	}

	public static class DmzhAjlx{
		private int dm;
		private String ajlxmc;
		private String ajlxdm;
		private String sm;
		
		public DmzhAjlx(){}
		public DmzhAjlx(int dm, String ajlxmc, String ajlxdm, String sm){
			this.dm = dm;
			this.ajlxdm = ajlxdm;
			this.ajlxmc = ajlxmc;
			this.sm = sm;
		}
		
		public final int getDm() {
			return dm;
		}
		public final void setDm(int dm) {
			this.dm = dm;
		}
		public final String getAjlxmc() {
			return ajlxmc;
		}
		public final void setAjlxmc(String ajlxmc) {
			this.ajlxmc = ajlxmc;
		}
		public final String getAjlxdm() {
			return ajlxdm;
		}
		public final void setAjlxdm(String ajlxdm) {
			this.ajlxdm = ajlxdm;
		}
		public final String getSm() {
			return sm;
		}
		public final void setSm(String sm) {
			this.sm = sm;
		}
		
		
	}
	
	public static class TsFymc {
		private String fydm;
		private String fjm;

		public TsFymc() {
		}

		public TsFymc(String _fydm, String _fjm) {
			fydm = _fydm;
			fjm = _fjm;
		}

		public String getFydm() {
			return fydm;
		}

		public void setFydm(String fydm) {
			this.fydm = fydm;
		}

		public String getFjm() {
			return fjm;
		}

		public void setFjm(String fjm) {
			this.fjm = fjm;
		}
	}

}
