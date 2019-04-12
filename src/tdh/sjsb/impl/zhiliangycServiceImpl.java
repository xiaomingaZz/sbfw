package tdh.sjsb.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.org.apache.bcel.internal.generic.NEW;

import tdh.frame.web.context.WebAppContext;
import tdh.framework.util.StringUtils;
import tdh.sjsb.DataSbService;
import tdh.sjsb.zhiliangycService;
import tdh.sjsb.bean.AjRequest;
import tdh.sjsb.bean.DrjaInfo;
import tdh.sjsb.bean.DrxsInfo;
import tdh.sjsb.bean.JaajInfo;
import tdh.sjsb.bean.JaxqInfo;
import tdh.sjsb.bean.LaajInfo;
import tdh.sjsb.bean.LaxqInfo;
import tdh.sjsb.bean.Response;
import tdh.sjsb.bean.XqInfo;
import tdh.sjsb.bean.zhiliangycRequest;
import tdh.sjsb.msg.ParamMessage;
import tdh.sjsb.service.CommService;
import tdh.util.CacheUtils;
import tdh.util.CacheUtils.TsFymc;
import tdh.util.DBUtils;
import tdh.util.UtilComm;
import tdh.frame.common.UUID;

public class zhiliangycServiceImpl implements zhiliangycService {

	public static Logger logger = Logger.getLogger(zhiliangycServiceImpl.class);



	/**
	 * 文件报送数据接口 描述：供各辖区人民法院报送每日文件清单数据 1、每日报送端（辖区法院）报送的文件（包括案件XML，删除XML、机构XML等）；
	 * 2、报送数据结构因符合“文件报送统计规范”要求。 3、报送时间要求：截止到每日从0点到24时累计报送的文件清单信息。
	 */
	
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public String zlgzYcList(String xml) {
		Response res = new Response();
		logger.info("正在查询数据质量...");
		int cnt=0;
		
		StringBuffer jgqdanStr=new StringBuffer();
		try {
			zhiliangycRequest zlreq = (zhiliangycRequest) UtilComm.xmlToBean(xml, "root", zhiliangycRequest.class);
			
			logger.info("成功解析");
			if (zlreq != null) {
				
				
					Connection conn = null;
					Statement st = null;
					
						
						
						String fydm=null;
						String lx=null;
						boolean flag=true;
						
							if (zlreq.getFydm()!=null ) {
								fydm=zlreq.getFydm();	
							}
							if (zlreq.getLx()!=null ) {
								lx=zlreq.getLx();	
							}
					
						
						try {
							conn = WebAppContext.getConn("sjlyDataSource");
							conn.setAutoCommit(false);
							st = conn.createStatement();
							
					if("".equals(StringUtils.trim(fydm))){	
						res.setFlag("false");
								res.setMessage("fydm为空");
								logger.error("fydm为空");
								flag=false;
							}else if("".equals(StringUtils.trim(lx))){	
						res.setFlag("false");
								res.setMessage("lx为空");
								logger.error("lx为空");
								flag=false;
							}else{
			String sql01=null;
				if("T".equals(lx)){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
					String teday=sdf.format(new Date());
					sql01="select AHDM,XTAJLX,FYDM from XML_LJJYLOG a where FYDM like '"+fydm+"%' and "+
					"CONVERT(varchar(10),a.LASTUPDATE,112) = '"+teday+"' and "+
					"exists(select 'x' from XML_LJJYLOG_JG b where a.AHDM=b.AHDM AND a.FYDM=b.FYDM)	";
				}else if("A".equals(lx)){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
					String teyear=sdf.format(new Date());
					sql01="select AHDM,XTAJLX,FYDM from XML_LJJYLOG a where FYDM like '"+fydm+"%' and "+
					"CONVERT(varchar(10),a.LASTUPDATE,112) like '"+teyear+"%' and "+
					"exists(select 'x' from XML_LJJYLOG_JG b where a.AHDM=b.AHDM AND a.FYDM=b.FYDM)	";
				}else{
					flag=false;
				}
					if(flag){
				ResultSet rs01=st.executeQuery(sql01);
					//int cnt=0;
					ResultSet rs02=null;
					String sql02=null;
					//StringBuffer jgqdanStr=new StringBuffer();
					Statement st02=conn.createStatement();
					while(rs01.next()){
						String ahdma=rs01.getString(1);
						String ajlxb=rs01.getString(2);
						String fydmc=rs01.getString(3);
						cnt++;
						sql02="select GZ FROM XML_LJJYLOG_JG WHERE AHDM='"+ahdma+"' AND FYDM='"+fydmc+"'";
						rs02=st02.executeQuery(sql02);	
						String cwgz="";
						String cwwsgz="";
						while(rs02.next()){
							String gzbm=rs02.getString(1);
							if(gzbm.startsWith("WS")){
								cwwsgz+=gzbm.replace("WS", "").replace("0", "")+",";
							}else{
								cwgz+=gzbm.replace("0", "")+",";		
							}
							
						}
						rs02=null;	
		jgqdanStr.append("<aj><ajbs>"+ahdma+"<ajbs><ajlx>"+ajlxb+"</ajlx><ajgzbh>"+
				(cwgz.length()>0?cwgz.substring(0, cwgz.length()-1):"")+"</ajgzbh><wsgzbh>"+
				(cwwsgz.length()>0?cwwsgz.substring(0, cwwsgz.length()-1):"")+
								"</wsgzbh></aj>");
						
				}
								
								
							}
				
							}
					} catch (Exception e) {
						logger.info("接口异常", e);
						e.printStackTrace();
						res.setFlag("false");
						res.setMessage("");
					} finally {
						DBUtils.close(conn, st, null);
					}
				
				logger.info("成功解析xml");
				res.setFlag("true");
				res.setMessage("");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("返回xml->" + ParamMessage.XML_HEAD + "\n\r" + UtilComm.beanToXml(res));
		StringBuffer retstr=new StringBuffer();
		retstr.append(ParamMessage.XML_HEAD + "\n\r");
		retstr.append("<result>");
		retstr.append("\n\r");
		retstr.append("<flag>");
		retstr.append("\n\r");
		retstr.append(res.getFlag());
		retstr.append("\n\r");
		retstr.append("</flag>");
		retstr.append("\n\r");
		retstr.append("<message>");
		retstr.append("\n\r");
		retstr.append(res.getMessage());
		retstr.append("\n\r");
		retstr.append("</message>");
		retstr.append("\n\r");
		retstr.append("<gzqd>");
		retstr.append("\n\r");
		if(cnt>0){
			retstr.append(jgqdanStr.toString());	
		}
		retstr.append("</gzqd>");
		retstr.append("\n\r");
		
		retstr.append("</result>");
		//return ParamMessage.XML_HEAD + "\n\r" + UtilComm.beanToXml(res);
		System.out.println(">>>>>>>>>>"+retstr.toString());
		return retstr.toString();
	}		
	
}
