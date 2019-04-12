package tdh.sjsb.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import tdh.util.CacheUtils;

@Service("commService")
public class CommService {
	@Autowired
	public JdbcTemplate framejdbcTemplate;
	
	@Autowired
	public JdbcTemplate sjlyjdbcTemplate;

	/**
	 * 获取法院列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getAllFyList() {
		return framejdbcTemplate.queryForList("select FYDM,DM,FJM,FYMC FROM TS_FYMC");
	}
	
	public List<Map<String, Object>> getAllAjlxList(){
		return framejdbcTemplate.queryForList("select DM,AJLXMC,AJLXDM,SM from DMZH_AJLX");
	}
	
	/**
	 * 创建今日的文件接收表
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public void createTodayTable(String tablename){
		int cnt = sjlyjdbcTemplate.queryForInt("select isnull(count(1),0) from sysobjects where name='"+tablename+"' type = 'U'");
		if(cnt==0){
			sjlyjdbcTemplate.execute("CREATE TABLE "+tablename+"(LSH VARCHAR(32),LB VARCHAR(10),WJMC VARCHAR(200),SBSJ DATETIME,FYDM VARCHAR(6))");
		}
	}

	@SuppressWarnings("deprecation")
	public boolean isExist(String sbrq, String ajlx, String ajbs, String table) {
		int cnt = sjlyjdbcTemplate.queryForInt("select isnull(count(1),0) from "+table+" where SBRQ='"+sbrq+"' and AJLX="+ajlx+" and AJBS='"+ajbs+"'");
		System.out.println("select isnull(count(1),0) from "+table+" where SBRQ='"+sbrq+"' and AJLX="+ajlx+" and AJBS='"+ajbs+"'");
		return cnt>0;
	}
}
