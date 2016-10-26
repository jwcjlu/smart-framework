package org.smart4j.framework;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.helper.HelperLoader;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CodecUtil;
import org.smart4j.framework.util.JsonUtil;
import org.smart4j.framework.util.ReflectionUtil;
import org.smart4j.framework.util.StreamUtil;
import org.smart4j.framework.util.StringUtil;
/**
 * 核心请求调度器
 * @author jinwei
 *
 */
@WebServlet(urlPatterns="/*",loadOnStartup=0)
public class DispatcherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6668915447714025956L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		HelperLoader.init();
		ServletContext  context=config.getServletContext();
		ServletRegistration  jspRegistration=context.getServletRegistration("jsp");
		jspRegistration.addMapping(ConfigHelper.getAppJspPath()+"*");
		ServletRegistration  defaultRegistration=context.getServletRegistration("default");
		defaultRegistration.addMapping(ConfigHelper.getAppAssetPath()+"*");
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String requestMethod=req.getMethod();
		String reqeustPath=req.getPathInfo();
		Handler handler=ControllerHelper.getHandler(requestMethod, reqeustPath);
		if(handler!=null){
			Class<?>  beanClassName=handler.getControllerClass();
			Object controllerBean=BeanHelper.getBean(beanClassName);
			Map<String,Object>paramMap=new HashMap<String,Object>();
			Enumeration<String> paramNames=req.getParameterNames();
			while(paramNames.hasMoreElements()){
				String paramName=paramNames.nextElement();
				Object paramValue=req.getParameter(paramName);
				paramMap.put(paramName, paramValue);
			}
			String body=CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
			if(StringUtil.isNotEmpty(body)){
				String []params=StringUtil.splitString(body,"&");
				if(ArrayUtil.isNotEmpty(params)){
					for(String p:params){
						String []array=StringUtil.splitString(p, "=");
						if(ArrayUtil.isNotEmpty(array)&&array.length==2){
							String key=array[0];
							String value=array[1];
							paramMap.put(key, value);
						}
					}
				}
				Param param=new Param(paramMap);
				Method mothod=handler.getActionMethod();
				Object result=ReflectionUtil.invokeMethod(controllerBean, mothod, param);
				if(result instanceof View){
					View view =(View) result;
					String path=view.getPath();
					if(StringUtil.isNotEmpty(path)){
						if(path.startsWith("/")){
							resp.sendRedirect(req.getServletContext()+path);
						}else{
							Map<String,Object> map=	view.getModel();
							for(Map.Entry<String, Object>entry:map.entrySet()){
								req.setAttribute(entry.getKey(), entry.getValue());
							}
							req.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).
							forward(req, resp);
						}
					}	
					
				}else if(result instanceof  Data){
					Data data=(Data)result;
					Object model=data.getModel();
					if(model!=null){
						resp.setContentType("application/json");
						resp.setCharacterEncoding("UTF-8");
						Writer writer=resp.getWriter();
						String json=JsonUtil.toJson(model);
						writer.write(json);
						writer.flush();
						writer.close();
					}
				}
			}
			
		}
		
	}
	

}
