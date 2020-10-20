package pers.zdl1004.SchoolLeaveSystem.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import pers.zdl1004.SchoolLeaveSystem.Messages;
import pers.zdl1004.SchoolLeaveSystem.adapter.HttpSessionAdapter;
import pers.zdl1004.SchoolLeaveSystem.annotation.RSATimestampCheck;
import pers.zdl1004.SchoolLeaveSystem.pojo.json.JSONResult;
import pers.zdl1004.SchoolLeaveSystem.type.JSONCodeType;

/**
 * 通过时间戳验证RSA密钥对是否过期的拦截器
 * @author zdl1004
 *
 */
public class RSATimestampCheckInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//获取即将执行的方法
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		//如果含有RSATimestampCheck注解
		if (handlerMethod.getMethodAnnotation(RSATimestampCheck.class) != null) {
			//获取用户的RSA密钥生成时的时间戳
			String clientRSACreateTimestamp = request.getParameter("timestamp"); //$NON-NLS-1$
			if (clientRSACreateTimestamp == null) {
				response.sendError(400);
				return false;
			}
			long clientRSACreateTimestampLong = 0;
			try {
				clientRSACreateTimestampLong = Long.parseLong(clientRSACreateTimestamp);
			} catch (NumberFormatException e) {
				response.sendError(400);
				return false;
			}
			HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(request.getSession());
			if (clientRSACreateTimestampLong != sessionAdapter.getRsaCreateTimestamp()) { //$NON-NLS-1$
				JSONResult result = new JSONResult(JSONCodeType.SESSION_TIMEOUT, Messages.getString("SessionTimeout"), //$NON-NLS-1$
						null);
				request.setAttribute("result", result); //$NON-NLS-1$
				//将JSON结果转发以返回JSON数据
				request.getRequestDispatcher("/result").forward(request, response); //$NON-NLS-1$
				return false;
			}
		}
		return true;
	}
}
