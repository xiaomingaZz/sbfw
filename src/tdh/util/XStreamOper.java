package tdh.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class XStreamOper {

	private XStream xstream = null;

	public XStreamOper() {
		init("utf-8");
	}

	public XStreamOper(String charset) {
		init(charset);
	}

	public void init(String charset) {
		xstream = new XStream(new DomDriver(charset)) {
			// 忽略未知属
			protected MapperWrapper wrapMapper(MapperWrapper next) {
				return new MapperWrapper(next) {
					public boolean shouldSerializeMember(@SuppressWarnings("rawtypes") Class definedIn, String fieldName) {
						return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
					}
				};
			}
		};
	}

	public void destory() {
		xstream = null;
		//System.gc();
	}


	public final void failRed(String string) {
		System.err.println(string);
	}

	public String writeBean2XML(Object bean) {
		return this.writeBean2XML(bean, null);
	}

	public String writeBean2XML(Object bean,Map<String,Class<?>> alias) {
		try {
			xstream.autodetectAnnotations(true);
			if(alias!=null){
				Iterator<Map.Entry<String,Class<?>>> it = alias.entrySet().iterator();
				while(it.hasNext()){
					Map.Entry<String,Class<?>> en = it.next();
					xstream.alias(en.getKey(), en.getValue());
				}
			}
			return xstream.toXML(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.destory();
		}
		return "";
	}
	
	public Object writeXML2Bean(String xmlStr, String rootNode, @SuppressWarnings("rawtypes") Class clazz) {
		Map<String,Class<?>> alias = new HashMap<String, Class<?>>();
		alias.put(rootNode, clazz);
		return this.writeXML2Bean(xmlStr, alias);
	}

	public Object writeXML2Bean(String xmlStr,Map<String,Class<?>> alias){
		xstream.autodetectAnnotations(true);
		Iterator<Map.Entry<String,Class<?>>> it = alias.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String,Class<?>> en = it.next();
			xstream.alias(en.getKey(), en.getValue());
		}
		Object o = xstream.fromXML(xmlStr);
		this.destory();
		return o;
	}
	


}
