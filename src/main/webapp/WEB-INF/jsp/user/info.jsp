<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>个人资料</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/include/header.jsp"%>
	<div class="container">
		<div class="panel panel-default col-md-10 col-xs-12"
			style="font-weight: bold;">
			<div class="panel-heading container-fluid ">
				<h3 class="panel-title">
					<span class="glyphicon glyphicon-tags"></span>
					&nbsp;&nbsp;用户个人资料
				</h3>
			</div>
			<br>
			<div class="panel-body">
				<p>用户名：${userInfoView.username }</p>
			</div>
			<div id="changeType" class="panel-body">
				<p>用户类型：${userInfoView.type.name }</p>
			</div>
			<div class="panel-body">
				<p>姓名：${userInfoView.name }</p>
			</div>
			<div class="panel-body">
				<p>电话：${userInfoView.telephone }</p>
			</div>

			<!-- 班级可能为null -->
			<div class="panel-body">
				<p>
					班级：
					<c:if test="${userInfoView.clazzFullName == null }">无</c:if>
					<c:if test="${userInfoView.clazzFullName != null }">${userInfoView.clazzFullName }</c:if>
				</p>
			</div>
			<hr>
			<div class="panel-body ">
				<a class="btn btn-default" role="button" href="modify">修改信息</a>
			</div>
		</div>
	</div>

</body>
</html>