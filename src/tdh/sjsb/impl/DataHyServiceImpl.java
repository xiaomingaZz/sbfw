package tdh.sjsb.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;

import tdh.frame.web.context.WebAppContext;
import tdh.framework.util.StringUtils;
import tdh.sjsb.DataHyService;
import tdh.sjsb.bean.AjRequest;
import tdh.sjsb.bean.AjRequest15;
import tdh.sjsb.bean.DrjaInfo;
import tdh.sjsb.bean.DrxsInfo;
import tdh.sjsb.bean.JaInfo;
import tdh.sjsb.bean.JaajInfo;
import tdh.sjsb.bean.JaxqInfo;
import tdh.sjsb.bean.LaInfo;
import tdh.sjsb.bean.LaajInfo;
import tdh.sjsb.bean.LaxqInfo;
import tdh.sjsb.bean.Response;
import tdh.sjsb.bean.XqInfo;
import tdh.sjsb.bean.XqInfo15;
import tdh.sjsb.msg.ParamMessage;
import tdh.sjsb.service.CommService;
import tdh.util.CacheUtils;
import tdh.util.CacheUtils.TsFymc;
import tdh.util.DBUtils;
import tdh.util.UtilComm;
import tdh.frame.common.UUID;

public class DataHyServiceImpl implements DataHyService {

	public static Logger logger = Logger.getLogger(DataSbServiceImpl.class);

	@Autowired
	public CommService commService;

	
	/**
	 * 实时数据报送接口 1、各辖区人民法院实时上报的今日新收、已结案件的统计数据需带上统计数据对应的案件列表信息；
	 * 2、报送数据结构因符合“实时数据报送接口”要求。 3、报送时间要求：每5分钟推送一次。
	 */
	@SuppressWarnings("deprecation")
	@Override
	public String importSpdt(String spdtXml) {
		logger.info("importSpdt接收："+spdtXml);
		Response res = new Response();
		if (checkAjRequest(res, spdtXml)) {
			AjRequest req = (AjRequest) UtilComm.xmlToBean(spdtXml, "root", AjRequest.class);
			List<XqInfo> list = req.getData();
			if (list != null) {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				String curdate = format.format(new Date());
				Connection conn = null;
				Statement st = null;
				try {
					conn = WebAppContext.getConn("sjlyDataSource");
					conn.setAutoCommit(false);
					st = conn.createStatement();

					for (XqInfo info : list) {
						DrxsInfo drxs = info.getDrxs();
						int fydm = Integer.parseInt(info.getId());
						TsFymc tsfymc = CacheUtils.FY_MAP.get(fydm);
						String dm = tsfymc.getFydm();
						if (drxs != null) {
							List<LaxqInfo> lalist = drxs.getLaxq();
							if (lalist != null) {
								for (LaxqInfo lainfo : lalist) {
									String ajlx = StringUtils.trim(lainfo.getAjlx());
									st.executeUpdate(
											"delete from DC_CASE_SA_DAY_TEMP where FYDM=" + fydm + " and AJLX=" + ajlx);

									logger.info("delete from DC_CASE_SA_DAY_TEMP where where  FYDM=" + fydm
											+ " and AJLX=" + ajlx);

									List<LaajInfo> laajlist = lainfo.getLalist();
									if (laajlist != null && laajlist.size() > 0) {
										int cnt = 0, i = 1;
										for (LaajInfo laajinfo : laajlist) {

											String ajbs = StringUtils.trim(laajinfo.getAjbs());
											String ah = StringUtils.trim(laajinfo.getAh());
											String sarq = StringUtils.trim(laajinfo.getSarq());
											String larq = StringUtils.trim(laajinfo.getLarq());
											String laay = StringUtils.trim(laajinfo.getLaay());
											String qsbdje = StringUtils.trim(laajinfo.getQsbdje());
											String tqxzpc = StringUtils.trim(laajinfo.getTqxzpc());
											String ajzlx = StringUtils.trim(laajinfo.getAjzlx());
											BigDecimal decimal = new BigDecimal(qsbdje);
											String insertsql = "INSERT INTO DC_CASE_SA_DAY_TEMP(SBRQ,FYDM,AJLX,AJBS,AH,SARQ,LARQ,LAAY,QSBDJE,AJZLX,TQXZPC,DM) "
													+ "VALUES('" + curdate + "'," + fydm + "," + ajlx + ",'" + ajbs
													+ "','" + ah + "','" + sarq + "','" + larq + "','" + laay + "',"
													+ decimal + ",'" + ajzlx + "','" + tqxzpc + "','" + dm + "')";
											logger.info(insertsql);
											st.addBatch(insertsql);
											cnt++;
											if (cnt == 100) {
												st.executeBatch();
												// conn.commit();
												cnt = 0;
												logger.info("新收 第" + (i++) + "个100条");
											}
										}
										if (cnt > 0) {
											st.executeBatch();
										}
									}
									conn.commit();
								}
							}
						}
						DrjaInfo drja = info.getDrja();
						if (drja != null) {
							List<JaxqInfo> jalist = drja.getJaxq();
							if (jalist != null && jalist.size() > 0) {
								for (JaxqInfo jainfo : jalist) {
									String ajlx = StringUtils.trim(jainfo.getAjlx());
									List<JaajInfo> jaajlist = jainfo.getJalist();
									st.executeUpdate("delete from DC_CASE_JA_DAY_TEMP where  FYDM=" + fydm
											+ " and AJLX=" + ajlx);
									logger.info("delete from DC_CASE_JA_DAY_TEMP where where  FYDM=" + fydm
											+ " and AJLX=" + ajlx);
									if (jaajlist != null) {
										int cnt = 0, i = 0;
										for (JaajInfo jaajinfo : jaajlist) {
											String ajbs = StringUtils.trim(jaajinfo.getAjbs());
											String ah = StringUtils.trim(jaajinfo.getAh());
											String jarq = StringUtils.trim(jaajinfo.getJarq());
											String jaay = StringUtils.trim(jaajinfo.getJaay());
											String jabdje = StringUtils.trim(jaajinfo.getJabdje());
											if (jabdje.length() == 0) {
												jabdje = "0";
											}
											String tqxzpc = StringUtils.trim(jaajinfo.getTqxzpc());
											String ajzlx = StringUtils.trim(jaajinfo.getAjzlx());
											String larq = StringUtils.trim(jaajinfo.getLarq());
											String jafs = StringUtils.trim(jaajinfo.getJafs());//结案方式
											BigDecimal decimal = new BigDecimal(jabdje);
											String insertsql = "INSERT INTO DC_CASE_JA_DAY_TEMP(SBRQ,FYDM,AJLX,AJBS,AH,JARQ,JAAY,JABDJE,LARQ,AJZLX,TQXZPC,DM,JAFS) "
													+ "VALUES('" + curdate + "'," + fydm + "," + ajlx + ",'" + ajbs
													+ "','" + ah + "','" + jarq + "','" + jaay + "'," + decimal + ",'"
													+ larq + "','" + ajzlx + "','" + tqxzpc + "','" + dm + "','"+jafs+"')";
											logger.info(insertsql);
											st.addBatch(insertsql);
											cnt++;
											if (cnt == 100) {
												st.executeBatch();
												cnt = 0;
												logger.info("新结 第" + (i++) + "个100条");
											}
										}
										if (cnt > 0) {
											st.executeBatch();
										}
									}
									conn.commit();
								}
							}
						}
					}
					res.setFlag("true");
					res.setMessage("");
					logger.info("接收成功！");
				} catch (Exception e) {
					res.setFlag("false");
					res.setMessage("接收失败");
					logger.error("接收失败！",e);
					try {
						conn.rollback();
					} catch (SQLException e1) {
						logger.error(e1.getMessage(),e1);
					}
				} finally {
					DBUtils.close(conn, st, null);
				}
			}
		}
		logger.info("返回xml->" + ParamMessage.XML_HEAD + "\n\r" + UtilComm.beanToXml(res));
		return ParamMessage.XML_HEAD + "\n\r" + UtilComm.beanToXml(res);
	}

	public boolean checkAjRequest(Response res, String spdtXml) {
		boolean result = true;
		if (StringUtils.isEmpty(spdtXml)) {
			res.setFlag("false");
			res.setMessage(ParamMessage.MESSAGE_ERROR_PARAM);
			result = false;
			logger.error(ParamMessage.MESSAGE_ERROR_PARAM);
		} else {
			if (StringUtils.isEmpty(spdtXml)) {
				res.setFlag("false");
				res.setMessage(ParamMessage.MESSAGE_ERROR_DECODE);
				result = false;
				logger.error(ParamMessage.MESSAGE_ERROR_DECODE);
			} else {
				try {
					AjRequest req = (AjRequest) UtilComm.xmlToBean(spdtXml, "root", AjRequest.class);
					if (req != null) {
						String gfdm = StringUtils.trim(req.getGyid());
						String fydm = CacheUtils.getFjm(Integer.valueOf(gfdm));
						String tjsj = StringUtils.trim(req.getTjsj());
						if (fydm.equals("")) {
							res.setFlag("false");
							res.setMessage(ParamMessage.MESSAGE_ERROR_FYDM);
							result = false;
							logger.error(ParamMessage.MESSAGE_ERROR_FYDM);
						} else if (tjsj.equals("")) {
							res.setFlag("false");
							res.setMessage(ParamMessage.MESSAGE_NOTNULL_TJSJ);
							result = false;
							logger.error(ParamMessage.MESSAGE_NOTNULL_TJSJ);
						}
						List<XqInfo> list = req.getData();
						if (list == null) {
							res.setFlag("false");
							res.setMessage(ParamMessage.MESSAGE_NOTNULL_DATA);
							result = false;
							logger.error(ParamMessage.MESSAGE_NOTNULL_DATA);
						} else {
							for (XqInfo info : list) {
								String fyid = StringUtils.trim(info.getId());
								String xxqfydm = CacheUtils.getFjm(Integer.valueOf(fyid));
								if (xxqfydm.equals("")) {
									res.setFlag("false");
									res.setMessage(ParamMessage.MESSAGE_ERROR_XXQFYDM);
									result = false;
									logger.error(ParamMessage.MESSAGE_ERROR_XXQFYDM);
								} else {
									DrxsInfo drxs = info.getDrxs();
									if (drxs != null) {
										List<LaxqInfo> lalist = drxs.getLaxq();
										if (lalist != null) {
											for (LaxqInfo lainfo : lalist) {
												String ajlx = StringUtils.trim(lainfo.getAjlx());
												String sl = StringUtils.trim(lainfo.getSl());
												if (ajlx.equals("")) {
													res.setFlag("false");
													res.setMessage(ParamMessage.MESSAGE_NOTNULL_AJLX);
													result = false;
													logger.error(ParamMessage.MESSAGE_NOTNULL_AJLX);
												} else if (sl.equals("")) {
													res.setFlag("false");
													res.setMessage(ParamMessage.MESSAGE_NOTNULL_SL);
													result = false;
													logger.error(ParamMessage.MESSAGE_NOTNULL_SL);
												}
											}
										}
									} else {
										DrjaInfo drja = info.getDrja();
										if (drja != null) {
											List<JaxqInfo> jalist = drja.getJaxq();
											if (jalist != null) {
												for (JaxqInfo jainfo : jalist) {
													String ajlx = StringUtils.trim(jainfo.getAjlx());
													String sl = StringUtils.trim(jainfo.getSl());
													if (ajlx.equals("")) {
														res.setFlag("false");
														res.setMessage(ParamMessage.MESSAGE_NOTNULL_AJLX);
														result = false;
														logger.error(ParamMessage.MESSAGE_NOTNULL_AJLX);
													} else if (sl.equals("")) {
														res.setFlag("false");
														res.setMessage(ParamMessage.MESSAGE_NOTNULL_SL);
														result = false;
														logger.error(ParamMessage.MESSAGE_NOTNULL_SL);
													}
												}
											}
										}
									}
								}
							}
						}
					} else {
						res.setFlag("false");
						res.setMessage(ParamMessage.MESSAGE_ERROR_DECODE);
						result = false;
						logger.error(ParamMessage.MESSAGE_ERROR_DECODE);
					}
				} catch (Exception e) {
					logger.error("xml解析异常");
					e.printStackTrace();
					res.setFlag("false");
					res.setMessage(ParamMessage.MESSAGE_ERROR_DECODE);
					result = false;
				}
			}
		}
		return result;
	}

	public static void truncate(Connection conn, String table) {
		Statement st = null;
		try {
			st = conn.createStatement();
			conn.setAutoCommit(true);
			conn.setAutoCommit(false);
			st.execute("TRUNCATE TABLE " + table);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeStatement(st);
		}
	}

	@Override
	public String importSpdtAndAjList(String xmlSpdt) {
		String ip = getClientInfo();
		logger.info("["+ip+"]importSpdtAndAjList接收数据长度："+xmlSpdt.length());
		Response res = new Response();
		Date d =  new Date();
		Date d1 = UtilComm.getDateBefore(d, 1);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String curdate = format.format(d);
		String lastDate = format.format(d1);
		File  f = new File(System.getProperty("user.dir")+"/sbfw_request_data/"+lastDate);
		if(f.exists()){
			FileUtils.deleteQuietly(f);
		}
		if (checkAjRequest15(res, xmlSpdt)) {
			AjRequest15 req=null;
			try {
				req = toAjRequest15(xmlSpdt);
			}catch (ParseException e) {
				logger.error("",e);
			} catch (DocumentException e2) {
				logger.error("",e2);
			} 
			List<XqInfo15> list = req.getData();
			if (list != null) {
				
				Connection conn = null;
				Statement st = null;
				try {
					conn = WebAppContext.getConn("sjlyDataSource");
					conn.setAutoCommit(false);
					st = conn.createStatement();

					for (XqInfo15 info : list) {
						/*int fydm = Integer.parseInt(info.getId());
						TsFymc tsfymc = CacheUtils.FY_MAP.get(fydm);
						String dm = tsfymc.getFydm();*/

						List<LaInfo> lalist = info.getLalist();
						if (lalist != null) {
							int cnt = 0;
							List<String> fydms = new ArrayList<String>();
							for (LaInfo lainfo : lalist) {
								int fydm = Integer.parseInt(lainfo.getJbfy());
								TsFymc tsfymc = CacheUtils.FY_MAP.get(fydm);
								String dm = tsfymc.getFydm();
								String ajlx = StringUtils.trim(lainfo.getAjlx());
							
								String ajbs = StringUtils.trim(lainfo.getAjbs());
								if(!fydms.contains(dm)){
									st.executeUpdate("delete from DC_CASE_SA_DAY_TEMP where FYDM='" + dm+ "' and SBRQ='" + curdate + "'");
									fydms.add(dm);
								}
								/*st.executeUpdate(
										"delete from DC_CASE_SA_DAY_TEMP where SBRQ='" + curdate + "' and AJLX='" + ajlx+"' AND AJBS='"+ajbs+"'");*/
							
								String ah = StringUtils.trim(lainfo.getAh());
								String sarq = StringUtils.trim(lainfo.getSarq());
								String larq = StringUtils.trim(lainfo.getDjlarq());
								String laay = StringUtils.trim(lainfo.getLaay());
								String insertsql = "INSERT INTO DC_CASE_SA_DAY_TEMP(SBRQ,JBFY,AJLX,AJBS,AH,SARQ,DJLARQ,LAAY,FYDM) "
										+ "VALUES('" + curdate + "','" + fydm + "','" + ajlx + "','" + ajbs + "','" + ah
										+ "','" + sarq + "','" + larq + "','" + laay + "','" + dm + "')";
								st.addBatch(insertsql);
								cnt++;
								if (cnt == 100) {
									st.executeBatch();
									// conn.commit();
									cnt = 0;
								}
							}
							if (cnt > 0) {
								st.executeBatch();
							}
							conn.commit();
						}

						List<JaInfo> jalist = info.getJalist();
						if (jalist != null && jalist.size() > 0) {
							int cnt = 0;
							List<String> fydms = new ArrayList<String>();
							for (JaInfo jainfo : jalist) {
								int fydm = Integer.parseInt(jainfo.getJbfy());
								TsFymc tsfymc = CacheUtils.FY_MAP.get(fydm);
								String dm = tsfymc.getFydm();
								String ajlx = StringUtils.trim(jainfo.getAjlx());
								String ajbs = StringUtils.trim(jainfo.getAjbs());
								
								if(!fydms.contains(dm)){
									st.executeUpdate("delete from DC_CASE_SA_DAY_TEMP where FYDM='" + dm+ "' and SBRQ='" + curdate + "'");
									fydms.add(dm);
								}
								/*st.executeUpdate(
										"delete from DC_CASE_JA_DAY_TEMP where  SBRQ='" + curdate + "' and AJLX='" + ajlx+"' AND AJBS='"+ajbs+"'");*/
								String ah = StringUtils.trim(jainfo.getAh());
								String jarq = StringUtils.trim(jainfo.getJarq());
								String jaay = StringUtils.trim(jainfo.getJaay());
								String larq = StringUtils.trim(jainfo.getDjlarq());
								String jafs = StringUtils.trim(jainfo.getJafs());// 结案方式
								String insertsql = "INSERT INTO DC_CASE_JA_DAY_TEMP(SBRQ,JBFY,AJLX,AJBS,AH,JARQ,JAAY,DJLARQ,FYDM,JAFS) "
										+ "VALUES('" + curdate + "','" + fydm + "','" + ajlx + "','" + ajbs + "','" + ah
										+ "','" + jarq + "','" + jaay + "','" + larq + "','" + dm + "','" + jafs + "')";
								st.addBatch(insertsql);
								cnt++;
								if (cnt == 100) {
									st.executeBatch();
									cnt = 0;
								}
							}
							if (cnt > 0) {
								st.executeBatch();
							}
							conn.commit();
						}
					}
					res.setFlag("true");
					res.setMessage("");
					logger.info("接收成功！");
				} catch (Exception e) {
					res.setFlag("false");
					res.setMessage("接收失败");
					logger.error("接收失败！", e);
					try {
						conn.rollback();
					} catch (SQLException e1) {
						logger.error(e1.getMessage(), e1);
					}
				} finally {
					DBUtils.close(conn, st, null);
				}
			}

		}else{
			File outFile = new File(System.getProperty("user.dir")+"/sbfw_request_data/"+curdate,ip+"_"+tdh.frame.common.UtilComm.dateFormat(d, "HH_mm_ss_SSS")+".xml");
			try {
				FileUtils.writeStringToFile(outFile, tdh.util.StringUtils.trim(xmlSpdt), "utf-8");
			} catch (IOException e) {
				logger.error("["+ip+"]请求数据异常 并且-->写到本地异常");
			}
		}
		logger.info("["+ip+"]返回xml->" + ParamMessage.XML_HEAD + "\n\r" + UtilComm.beanToXml(res));
		return ParamMessage.XML_HEAD + "\n\r" + UtilComm.beanToXml(res);

	}
	
	public boolean checkAjRequest15(Response res, String xmlSpdt) {
		boolean result = true;
		if (StringUtils.isEmpty(xmlSpdt)) {
			res.setFlag("false");
			res.setMessage(ParamMessage.MESSAGE_ERROR_PARAM);
			result = false;
			logger.error(ParamMessage.MESSAGE_ERROR_PARAM);
		} else {
			if (StringUtils.isEmpty(xmlSpdt)) {
				res.setFlag("false");
				res.setMessage(ParamMessage.MESSAGE_ERROR_DECODE);
				result = false;
				logger.error(ParamMessage.MESSAGE_ERROR_DECODE);
			} else {
				try {
					AjRequest15 req = toAjRequest15(xmlSpdt);
					if (req != null) {
						String gfdm = StringUtils.trim(req.getGyid());
						String fydm = CacheUtils.getFjm(Integer.valueOf(gfdm));
						String tjsj = StringUtils.trim(req.getTjsj());
						if (fydm.equals("")) {
							res.setFlag("false");
							res.setMessage(ParamMessage.MESSAGE_ERROR_FYDM);
							result = false;
							logger.error(ParamMessage.MESSAGE_ERROR_FYDM);
						} else if (tjsj.equals("")) {
							res.setFlag("false");
							res.setMessage(ParamMessage.MESSAGE_NOTNULL_TJSJ);
							result = false;
							logger.error(ParamMessage.MESSAGE_NOTNULL_TJSJ);
						}
						List<XqInfo15> list = req.getData();
						if (list == null || list.size()==0) {
							res.setFlag("false");
							res.setMessage(ParamMessage.MESSAGE_NOTNULL_DATA);
							result = false;
							logger.error(ParamMessage.MESSAGE_NOTNULL_DATA);
						} else {
							for (XqInfo15 info : list) {
								String fyid = StringUtils.trim(info.getId());
								String xxqfydm = CacheUtils.getFjm(Integer.valueOf(fyid));
								if (xxqfydm.equals("")) {
									res.setFlag("false");
									res.setMessage(ParamMessage.MESSAGE_ERROR_XXQFYDM);
									result = false;
									logger.error(ParamMessage.MESSAGE_ERROR_XXQFYDM);
								} else {
									List<LaInfo> lalist = info.getLalist();
									if (lalist != null) {
										for (LaInfo lainfo : lalist) {
											String ajlx = StringUtils.trim(lainfo.getAjlx());
											String sl = StringUtils.trim(info.getDrxsajsl());
											if (ajlx.equals("")) {
												res.setFlag("false");
												res.setMessage(ParamMessage.MESSAGE_NOTNULL_AJLX);
												result = false;
												logger.error(ParamMessage.MESSAGE_NOTNULL_AJLX);
											} else if (sl.equals("")) {
												res.setFlag("false");
												res.setMessage(ParamMessage.MESSAGE_NOTNULL_SL);
												result = false;
												logger.error(ParamMessage.MESSAGE_NOTNULL_SL);
											}
										}
									}
									List<JaInfo> jalist = info.getJalist();
									if (jalist != null) {
										for (JaInfo jainfo : jalist) {
											String ajlx = StringUtils.trim(jainfo.getAjlx());
											String sl = StringUtils.trim(info.getDryjajsl());
											if (ajlx.equals("")) {
												res.setFlag("false");
												res.setMessage(ParamMessage.MESSAGE_NOTNULL_AJLX);
												result = false;
												logger.error(ParamMessage.MESSAGE_NOTNULL_AJLX);
											} else if (sl.equals("")) {
												res.setFlag("false");
												res.setMessage(ParamMessage.MESSAGE_NOTNULL_SL);
												result = false;
												logger.error(ParamMessage.MESSAGE_NOTNULL_SL);
											}
										}
									}
								}
							}
						}
					} else {
						res.setFlag("false");
						res.setMessage(ParamMessage.MESSAGE_ERROR_DECODE);
						result = false;
						logger.error(ParamMessage.MESSAGE_ERROR_DECODE);
					}
				} catch (Exception e) {
					logger.error("xml解析异常");
					res.setFlag("false");
					res.setMessage(ParamMessage.MESSAGE_ERROR_DECODE);
					result = false;
				}
			}
		}
		return result;
	}
	
	public AjRequest15 toAjRequest15(String xmlSpdt) throws DocumentException, ParseException {
		AjRequest15 res=new AjRequest15();
		Document doc = DocumentHelper.parseText(xmlSpdt);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sad=new SimpleDateFormat("yyyyMMdd");
		if (doc != null) {
			Element root = doc.getRootElement();
			List<Element> elist = root.elements();
			for (Element element : elist) {
				String name=element.getName();
				if(name.equals("gyid")) {
					res.setGyid(element.getText());
				}
				if(name.equals("tjsj")) {
					res.setTjsj(element.getText());
				}
				if(name.equals("datas")) {
					List<Element> es=element.elements();
					List<XqInfo15> list=new ArrayList<XqInfo15>();
					for(Element e:es){
//						System.out.println("====="+e.asXML());
						List<Element> q=e.elements();
						
						XqInfo15 xq=new XqInfo15();
						for(Element eq:q){
							String n=eq.getName();
							if(n.equals("id")){
								xq.setId(eq.getText());
							}
							if(n.equals("drxsajsl")){
								xq.setDrxsajsl(Integer.parseInt(eq.getText()));
							}
							if(n.equals("dryjajsl")){
								xq.setDryjajsl(Integer.parseInt(eq.getText()));
							}
							if(n.equals("lalist")){
								List<Element> as=eq.elements();
								List<LaInfo> lalist=new ArrayList<LaInfo>();
								if(null!=as && as.size()>0){
									for(Element a:as){
										LaInfo lainfo=new LaInfo();
										List<Element> os=a.elements();
										for(Element o:os){
											String la=o.getName();
											if(la.equals("ajbs")){
												lainfo.setAjbs(o.getText());
											}
											if(la.equals("ajlx")){
												lainfo.setAjlx(o.getText());
											}
											if(la.equals("ah")){
												lainfo.setAh(o.getText());
											}
											if(la.equals("jbfy")){
												lainfo.setJbfy(o.getText());
											}
											if(la.equals("sarq")){
												lainfo.setSarq(sad.format(sdf.parse(o.getText())));
											}
											if(la.equals("bydjlarq")){
												lainfo.setBydjlarq(sad.format(sdf.parse(o.getText())));
											}
											if(la.equals("byslcdrq")){
												lainfo.setByslcdrq(sad.format(sdf.parse(o.getText())));
											}
											if(la.equals("djlarq")){
												lainfo.setDjlarq(sad.format(sdf.parse(o.getText())));
											}
											if(la.equals("laay")){
												lainfo.setLaay(o.getText());
											}
											if(la.equals("saly")){
												lainfo.setSaly(o.getText());
											}
											if(la.equals("laayxzxwzl")){
												lainfo.setLaayxzxwzl(o.getText());
											}
											if(la.equals("laayztlx")){
												lainfo.setLaayztlx(o.getText());
											}
										}
										lalist.add(lainfo);
									}
								}
								xq.setLalist(lalist);
							}
							if(n.equals("jalist")){
								List<Element> as=eq.elements();
								List<JaInfo> jalist=new ArrayList<JaInfo>();
								if(null!=as && as.size()>0){
									for(Element a:as){
										JaInfo jainfo=new JaInfo();
										List<Element> os=a.elements();
										for(Element o:os){
											String ja=o.getName();
											if(ja.equals("ajbs")){
												jainfo.setAjbs(o.getText());
											}
											if(ja.equals("ajlx")){
												jainfo.setAjlx(o.getText());
											}
											if(ja.equals("ah")){
												jainfo.setAh(o.getText());
											}
											if(ja.equals("jbfy")){
												jainfo.setJbfy(o.getText());
											}
											if(ja.equals("sarq")){
												jainfo.setSarq(sad.format(sdf.parse(o.getText())));
											}
											if(ja.equals("bydjlarq")){
												jainfo.setBydjlarq(sad.format(sdf.parse(o.getText())));
											}
											if(ja.equals("byslcdrq")){
												jainfo.setByslcdrq(sad.format(sdf.parse(o.getText())));
											}
											if(ja.equals("djlarq")){
												jainfo.setDjlarq(sad.format(sdf.parse(o.getText())));
											}
											if(ja.equals("laay")){
												jainfo.setLaay(o.getText());
											}
											if(ja.equals("saly")){
												jainfo.setSaly(o.getText());
											}
											if(ja.equals("laayxzxwzl")){
												jainfo.setLaayxzxwzl(o.getText());
											}
											if(ja.equals("laayztlx")){
												jainfo.setLaayztlx(o.getText());
											}
											if(ja.equals("jarq")){
												jainfo.setJarq(sad.format(sdf.parse(o.getText())));
											}
											if(ja.equals("jaay")){
												jainfo.setJaay(o.getText());
											}
											if(ja.equals("jafs")){
												jainfo.setJafs(o.getText());
											}
										}
										jalist.add(jainfo);
									}
							    }
								xq.setJalist(jalist);
							}
						}
						list.add(xq);
					}
					res.setData(list); 
				}
			}
		}
		return res;
	}
	@Resource
	private WebServiceContext wsContext;

	private String getClientInfo() {
		String remortAddress = org.apache.commons.lang.StringUtils.EMPTY;
		if (wsContext != null) {
			MessageContext mc = wsContext.getMessageContext();
			HttpServletRequest request = (HttpServletRequest) (mc.get(MessageContext.SERVLET_REQUEST));
			remortAddress = getRemoteHost(request);
		}
		return (remortAddress);
	}
	private String getRemoteHost(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
	}
}
