package tdh.sjsb.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

import tdh.frame.web.context.WebAppContext;
import tdh.sjsb.DataJgService;
import tdh.sjsb.msg.ParamMessage;
import tdh.util.UtilComm;

public class DataJgServiceImpl implements DataJgService{

	Logger logger = Logger.getLogger(this.getClass());

    @Qualifier("sjlyjdbcTemplate")
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 用于和厂商反馈数据中心接收到的数据存在不满足规则的案件清单，便于厂商能够及时的获取数据问题和修正数据问题。
	 * @param xml
	 * @return
	 */
	@Override
	public String zlgzYcList(String xml) {
		
		return null;
	}

	@Override
	public String sjbdDayList(String xml) {
		StringBuffer resulet = new StringBuffer();
		if(!StringUtils.isEmpty(xml)){
			try {
				logger.info("正在解析xml对象...");
				Document doc = DocumentHelper.parseText(xml);
				logger.info("成功解析");
				if(doc != null){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Element root = doc.getRootElement();
					if (root != null) {
						Connection conn = null;
						Statement st = null;
						try {
							conn = WebAppContext.getConn("sjlyDataSource");
							st = conn.createStatement();
							conn.setAutoCommit(false);
							
							resulet.append(ParamMessage.XML_HEAD);
							resulet.append("<result>");
							List<Element> list = root.elements();
							String fydm = "";
							String date = "";
							for (Element element : list) {
								String name = element.getName();
								if (name.equals("fydm") && element.getText().equals("")) {
									resulet.append("<flag>fail</flag>");
									resulet.append("<message>"+ParamMessage.MESSAGE_NOTNULL_FYID+"</message>");
									resulet.append("</result>");
									logger.error(ParamMessage.MESSAGE_NOTNULL_FYID);
									return resulet.toString();
								}
								if(name.equals("fydm")){//获取法院代码
									fydm = element.getText();
									String getFydmSql = "select FYDM from TS_FYMC where DM = "+fydm;
									ResultSet sqlRs = st.executeQuery(getFydmSql);
									while(sqlRs.next()){
										fydm = sqlRs.getString("FYDM");
									}
								}
								if(name.equals("rq")){//获取日期
									date = element.getText();
									date = date.replace("-", "");
								}
							}
							
							resulet.append("<flag>success</flag>");
							resulet.append("<message></message>");
							if(StringUtils.isEmpty(date)){//date为空获取前一天时间
								date = sdf.format((UtilComm.getDateBefore(new Date(), 1)));
							}
							logger.info("缺少清单开始--->");
							resulet.append("<qsqd>");
							//缺少清单
							String qsqd_sa = "select AJBS,AJLX FROM DC_CASE_SA_DAY_CY where SBRQ = '"+date+"'"
									+ "	and DM='"+fydm+"'";
							String qsqd_ja = "select AJBS,AJLX FROM DC_CASE_JA_DAY_CY where SBRQ = '"+date+"'"
									+ "	and DM='"+fydm+"'";
							
							ResultSet qsJa = st.executeQuery(qsqd_ja);
							appendSjdbXml(resulet,qsJa);//结案
							ResultSet qsSa = st.executeQuery(qsqd_sa);
							appendSjdbXml(resulet,qsSa);//收案
							resulet.append("</qsqd>");
							logger.info("缺少清单结束<---");

							
							logger.info("多出清单开始--->");
							resulet.append("<dcqd>");
							String dcqd_sa = "select AJBS,AJLX FROM DC_ZX_SA_DAY_CY where SBRQ = '"+date+"'"
									+ "	and DM='"+fydm+"'";
							String dcqd_ja = "select AJBS,AJLX FROM DC_ZX_JA_DAY_CY where SBRQ = '"+date+"'"
									+ "	and DM='"+fydm+"'";
							
							ResultSet dcJa = st.executeQuery(qsqd_ja);
							appendSjdbXml(resulet,dcJa);//收案
							ResultSet dcSa = st.executeQuery(qsqd_sa);
							appendSjdbXml(resulet,dcSa);//结案
							
							resulet.append("</dcqd>");
							logger.info("多出清单结束<---");
							
							logger.info("不一致清单开始--->");
							resulet.append("<byzqd>");
							String byzqd_sa = "select AJBS,AJLX FROM DC_ZX_SA_DAY_CY where SBRQ = '"+
										sdf.format(new Date())+"'"
										+ "	and DM='"+fydm+"'";
							ResultSet byzSa = st.executeQuery(qsqd_ja);
							appendSjdbXml(resulet,byzSa);//收案
							String byzqd_ja = "select AJBS,AJLX FROM DC_ZX_JA_DAY_CY where SBRQ = '"+
										sdf.format(new Date())+"'"
										+ "	and DM='"+fydm+"'";
							ResultSet byzJa = st.executeQuery(qsqd_sa);
							appendSjdbXml(resulet,byzJa);//结案
							
							
							resulet.append("</byzqd>");
							logger.info("不一致单结束<---");
							
							resulet.append("</result>");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							try {
								st.close();
								conn.close();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			resulet.append(ParamMessage.XML_HEAD);
			resulet.append("<flag>fail</flag>");
			resulet.append("<message>"+ParamMessage.MESSAGE_NOTNULL_FYID+"</message>");
			resulet.append("</result>");
		}
		return resulet.toString();
	}

	@Override
	public String ptCbList(String xml) {
		Document document = DocumentHelper.createDocument();
		Element rootNode = document.addElement("result");
		try {
		Document doc = DocumentHelper.parseText(xml);
		Element root = doc.getRootElement();
		Element tag = root.element("fydm");
		String fydm = tag.getTextTrim();
		if(org.apache.commons.lang.StringUtils.isEmpty(fydm))
		{
			rootNode.addElement("flag").addText("fail");
			rootNode.addElement("message").addText("没有法院代码");
			return document.asXML();
		}
		
		logger.info("参数解析>>>fydm="+fydm);
		
		String sql = "SELECT * FROM LOG_SJZLCB WHERE FYDM='"+fydm+"'";
		final List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		if(list != null && list.size()>0)
		{
			String insertTask = "INSERT INTO TASK(ROWID,AJBS,AJLX,JBFY)VALUES(?,?,?,?)";
			final String uuid = fydm+UtilComm.getDateString();
			jdbcTemplate.batchUpdate(insertTask, new BatchPreparedStatementSetter() {
				
				@Override
				public void setValues(PreparedStatement pre, int i) throws SQLException {
					pre.setString(1, uuid);
					pre.setString(2, (String)list.get(i).get("AHDM"));
					pre.setString(3, (String)list.get(i).get("XTAJLX"));
					pre.setString(4, (String)list.get(i).get("FYDM"));
				}
				@Override
				public int getBatchSize() {
					return list.size();
				}
			});
			
			rootNode.addElement("flag").addText("success");
			rootNode.addElement("message").addText("");
			Element element = rootNode.addElement("rwqd");
			
			for(Map<String,Object> map:list)
			{
				Element ele = element.addElement("rw");
				ele.addElement("rwid").addText(uuid);
				ele.addElement("ajbs").addText((String)map.get("AHDM"));
				ele.addElement("ajlx").addText((String)map.get("XTAJLX"));
				ele.addElement("jbfy").addText((String)map.get("FYDM"));
			}
			
			return document.asXML();
		}
		
		} catch (Exception e) {
			rootNode.addElement("flag").addText("fail");
			rootNode.addElement("message").addText(e.getMessage());
		}
		
		return document.asXML();
	}

	@Override
	public String ptCbQr(String xml) {

		Document document = DocumentHelper.createDocument();
		Element rootNode = document.addElement("result");
		try {
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			String taskId = root.element("rwid").getTextTrim();
			if(org.apache.commons.lang.StringUtils.isNotEmpty(taskId)){
				String sql = "SELECT * FROM TASK WHERE ROWID = '"+taskId+"'";
				List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
				if(list != null && list.size()>0)
				{
					StringBuffer buffer = new StringBuffer();
					buffer.append("(");
					for(int index=0;index<list.size();index++)
					{
						Map<String,Object> map = list.get(index);
						if(index==list.size()-1)
						{
							String ajbs = (String)map.get("ajbs");
							buffer.append("'").append(ajbs).append("'");
						}else{
							String ajbs = (String)map.get("ajbs");
							buffer.append("'").append(ajbs).append("'").append(",");
						}
						buffer.append(")");
						
					}
					String sql1 = "SELECT AHDM,CBSJ,LASTUPDATE FROM XML_LJJYLOG WHERE AHDM IN"+buffer.toString();
					
					StringBuffer buffer2 = new StringBuffer();
					List<Map<String,Object>> maps = jdbcTemplate.queryForList(sql1);
					for(Map<String,Object> map:maps)
					{
						    Date date1 = (Date)map.get("CBSJ");
						    Date date2 = (Date)map.get("LASTUPDATE");
						    int i = date1.compareTo(date2);
						    if(i==1||1==0)
						    {
						    	buffer2.append(map.get("AHDM"));
						    }
						    
					}
					if("".equals(buffer2.toString())){
						rootNode.addElement("flag").addText("success");
						rootNode.addElement("message");
					}else{
						rootNode.addElement("flag").addText("fail");
						rootNode.addElement("message").addText(buffer2.toString()+"未重报");
					}
				}
			}
		} catch (Exception e) {
			rootNode.addElement("flag").addText("fail");
			rootNode.addElement("message").addText(e.getMessage());
			return document.asXML();
		}
		rootNode.addElement("flag").addText("fail");
		rootNode.addElement("message").addText("参数是空");
		return document.asXML();
	}

	private StringBuffer appendSjdbXml(StringBuffer resulet,ResultSet rs) throws SQLException{
		
		while(rs.next()){
			resulet.append("<aj>");
			resulet.append("<ajbs>");
			resulet.append(rs.getString("AJBS"));
			resulet.append("</ajbs>");
			resulet.append("<ajlx>");
			resulet.append(rs.getString("AJLX"));
			resulet.append("</ajlx>");
			resulet.append("</aj>");
		}
		return resulet;
	}
}
