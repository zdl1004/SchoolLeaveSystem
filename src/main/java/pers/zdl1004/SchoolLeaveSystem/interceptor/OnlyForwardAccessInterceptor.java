package pers.zdl1004.SchoolLeaveSystem.interceptor;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import pers.zdl1004.SchoolLeaveSystem.annotation.OnlyForwardAccess;
/*
* 判断访问类型是否转发的拦截器
* */
public class OnlyForwardAccessInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(((HandlerMethod)handler).getMethodAnnotation(OnlyForwardAccess.class) == null) {
			return true;
		}
		//如果访问类型不是转发
		if(request.getDispatcherType() != DispatcherType.FORWARD) {
			response.sendError(404);
			return false;
		}
		return true;
	}
}
