///**
// * 
// */
//package org.youisoft.oracle;
//import java.util.Hashtable;
//import org.youisoft.config.Config;
//import org.youisoft.config.Configs;
//import org.youisoft.config.XmlConfig;
//
//import weblogic.utils.classloaders.ClassPreProcessor;
//
///**
// * @author janly
// *
// */
//public class ServiceClassPreProcessor implements ClassPreProcessor {
//	private class KeyListMap{
//		private java.util.Map innerMap=new java.util.HashMap();
//
//		/* (non-Javadoc)
//		 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
//		 */
//		public Object put(Object arg0, Object arg1) {
//			if(this.innerMap.get(arg0)==null){
//				java.util.List list=new java.util.ArrayList();
//				list.add(arg1);
//				this.innerMap.put(arg0,list);
//			}else{
//				((java.util.List)this.innerMap.get(arg0)).add(arg1);
//			}
//			
//			return arg1;
//		}
//
//		/* (non-Javadoc)
//		 * @see java.util.HashMap#get(java.lang.Object)
//		 */
//		public Object get(Object key) {
//			return this.innerMap.get(key);
//		}
//		
//		
//		
//		
//		
//	}
//	public final static String XMLCONFIG="youisoft.service.config";
//	
//	
//	//-Dweblogic.classloader.preprocessor=org.youisoft.oracle.ServiceClassPreProcessor
//	private KeyListMap serviceMap=new KeyListMap();
//
//	/* (non-Javadoc)
//	 * @see weblogic.utils.classloaders.ClassPreProcessor#initialize(java.util.Hashtable)
//	 */
//	public void initialize(Hashtable arg0) {
//
//		
//		String configFileName=null;
//		if(arg0!=null&&arg0.get(XMLCONFIG)!=null){
//			configFileName=arg0.get(XMLCONFIG).toString();
//		}
//		if(System.getProperty(XMLCONFIG)!=null){
//			configFileName=System.getProperty(XMLCONFIG);
//		}
//		try{
//			java.io.FileInputStream fis=new java.io.FileInputStream(configFileName);
//			org.xml.sax.InputSource inputSource=new org.xml.sax.InputSource(fis);
//			Configs obj=(Configs)XmlConfig.parserXml(inputSource);
//			for(int i=0;obj.getConfigs()!=null&&i<obj.getConfigs().length;i++){
//				this.serviceMap.put(obj.getConfigs()[i].getServiceClassName().replace('/', '.'), obj.getConfigs()[i]);
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
//
//	/* (non-Javadoc)
//	 * @see weblogic.utils.classloaders.ClassPreProcessor#preProcess(java.lang.String, byte[])
//	 */
//	public byte[] preProcess(String arg0, byte[] arg1) {
//		if(this.serviceMap.get(arg0)!=null){
//			try{
//				java.util.List list=(java.util.List)this.serviceMap.get(arg0);
//				for(java.util.Iterator it=list.iterator();it.hasNext();){
//					Config config=(Config)it.next();
//					arg1=config.config(arg1);
//				}
//				
//			}catch(java.lang.Throwable t){
//				
//			}
//		
//		}
//
//		return arg1;
//	}
//	
//
//}
