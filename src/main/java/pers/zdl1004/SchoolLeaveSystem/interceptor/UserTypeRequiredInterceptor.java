package pers.zdl1004.SchoolLeaveSystem.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import pers.zdl1004.SchoolLeaveSystem.adapter.HttpSessionAdapter;
import pers.zdl1004.SchoolLeaveSystem.annotation.UserTypeRequired;
import pers.zdl1004.SchoolLeaveSystem.pojo.User;
import pers.zdl1004.SchoolLeaveSystem.type.UserType;

/*
* 用户类型拦截器
* */


public class UserTypeRequiredInterceptor extends HandlerInterceptorAdapter {
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//判断方法是否有UserTypeRequired注解
		UserTypeRequired userTypeRequired = ((HandlerMethod)handler).getMethodAnnotation(UserTypeRequired.class);
		if(userTypeRequired == null) {
			//如果没有注解不检测
			return true;
		}
		//获取需要的最低权限类型
		UserType needUserType = userTypeRequired.value();
		//获取用户对象
		HttpSessionAdapter sessionAdapter = new HttpSessionAdapter(request.getSession());
		User user = sessionAdapter.getUser();
		//如果用户未登录或者权限不足,页面返回403
		if(user == null || user.getType().getCode() > needUserType.getCode()) {
			response.sendError(403);
			return false;
		}
		return true;
	};
}
