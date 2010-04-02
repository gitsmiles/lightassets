package org.youisoft.config;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * 
 * @author janly
 *
 */
public class XmlConfig {
	private final static String SERVICE="/config/service";
	private final static String METHODADAPTER="/config/service/adapter";
	private final static String SERVICEMETHOD="/config/service/method";
	private final static String SERVICEPATTERN="/config/service/method/descriptor/pattern";
	private final static String SERVICEARG="/config/service/method/descriptor/args/arg";
	private final static String SERVICERET="/config/service/method/descriptor/args/ret";
	private final static String SERVICEEXP="/config/service/method/descriptor/args/exp";
	private final static String COMPONENT="/config/service/component";
	private final static String COMPONENTMETHOD="/config/service/component/method";
	private final static String COMPONENTPATTERN="/config/service/component/method/descriptor/pattern";
	private final static String COMPONENTARG="/config/service/component/method/descriptor/args/arg";
	private final static String COMPONENTRET="/config/service/component/method/descriptor/args/ret";
	private final static String COMPONENTEXP="/config/service/component/method/descriptor/args/exp";
	
	
	public static Configs parserXml(org.xml.sax.InputSource inputSource){
		final Configs configs=new Configs();
		
		SAXParserFactory saxFactory=SAXParserFactory.newInstance();
		saxFactory.setNamespaceAware(true);
		try {
			javax.xml.parsers.SAXParser saxParser=saxFactory.newSAXParser();
			
			saxParser.parse(inputSource, new DefaultHandler(){
				private PathStack ps=new PathStack();
				private Config config;
				/* (non-Javadoc)
				 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
				 */
				public void characters(char[] ch, int start, int length)
						throws SAXException {
					
					if(SERVICEPATTERN.equals(ps.getPath())){
						config.getServiceMethod().setPattern(new String(ch,start,length));
					}
					
					if(COMPONENTPATTERN.equals(ps.getPath())){
						config.getComponentMethod().setPattern(new String(ch,start,length));
					}
					
					if(SERVICEARG.equals(ps.getPath())){
						config.getServiceMethod().addArg(new String(ch,start,length));
					}
					
					if(SERVICERET.equals(ps.getPath())){
						config.getServiceMethod().addRet(new String(ch,start,length));
					}
					
					if(SERVICEEXP.equals(ps.getPath())){
						config.getServiceMethod().addExp(new String(ch,start,length));
					}
					
					if(COMPONENTARG.equals(ps.getPath())){
						config.getComponentMethod().addArg(new String(ch,start,length));
					}
					
					if(COMPONENTRET.equals(ps.getPath())){
						config.getComponentMethod().addRet(new String(ch,start,length));
					}
					
					if(COMPONENTEXP.equals(ps.getPath())){
						config.getComponentMethod().addExp(new String(ch,start,length));
					}
					
					
					
					
					
					
				}

				/* (non-Javadoc)
				 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
				 */
				public void endElement(String uri, String localName, String name)
						throws SAXException {
					if(SERVICE.equals(ps.getPath())){
						configs.setConfig(config);
					}
					ps.pop();
					
				}

				/* (non-Javadoc)
				 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
				 */
				public void startElement(String uri, String localName,
						String name, Attributes attributes) throws SAXException {
					ps.push(localName);
					
					if(SERVICE.equals(ps.getPath())){
						config=new Config();
						config.setServiceClassName(attributes.getValue("class"));
						config.setServiceName(attributes.getValue("name"));
						if(attributes.getValue("style")!=null) config.setStyle(attributes.getValue("style"));
						if(attributes.getValue("match")!=null) config.setMatch(attributes.getValue("match"));
						if(attributes.getValue("static")!=null) config.setStaticInvoke(new Boolean(attributes.getValue("static")).booleanValue());
					}
					
					if(METHODADAPTER.equals(ps.getPath())){
						config.setAdapterClassName(attributes.getValue("class"));
						config.setAdapterName(attributes.getValue("name"));
						
					}
					
					if(SERVICEMETHOD.equals(ps.getPath())){
						config.getServiceMethod().setMethodName(attributes.getValue("name"));
						config.getServiceMethod().setMatch(attributes.getValue("match"));
						
					}
					
					if(COMPONENT.equals(ps.getPath())){
						config.setComponentClassName(attributes.getValue("class"));
						config.setComponentName(attributes.getValue("name"));
					}
					
					if(COMPONENTMETHOD.equals(ps.getPath())){
						config.getComponentMethod().setMethodName(attributes.getValue("name"));
						config.getComponentMethod().setMatch(attributes.getValue("match"));
					}
					
					
				}
				
					/**
					 * 
					 * @author janly
					 *
					 */
					class PathStack extends java.util.Stack{
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;
						private java.util.Stack stack=new java.util.Stack();
						
						public String getPath(){
							java.lang.StringBuffer sb=new java.lang.StringBuffer();
							for(int i=0;i<stack.size();i++){
								sb.append("/");
								sb.append(stack.get(i).toString());
							}
							
							return sb.toString();
							
						}

						/* (non-Javadoc)
						 * @see java.util.Stack#empty()
						 */
						public boolean empty() {
							// TODO Auto-generated method stub
							return stack.empty();
						}

						/* (non-Javadoc)
						 * @see java.util.Stack#peek()
						 */
						public synchronized Object peek() {
							// TODO Auto-generated method stub
							return stack.peek();
						}

						/* (non-Javadoc)
						 * @see java.util.Stack#pop()
						 */
						public synchronized Object pop() {
							// TODO Auto-generated method stub
							return stack.pop();
						}

						/* (non-Javadoc)
						 * @see java.util.Stack#push(java.lang.Object)
						 */
						public Object push(Object arg0) {
							// TODO Auto-generated method stub
							return stack.push(arg0);
						}

						/* (non-Javadoc)
						 * @see java.util.Stack#search(java.lang.Object)
						 */
						public synchronized int search(Object o) {
							// TODO Auto-generated method stub
							return stack.search(o);
						}
						
						
					}
			
				}
			
			
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return configs;
	}
	


}
