<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>

<title>修改个人信息</title>
<script src="${pageContext.request.contextPath}/js/jsencrypt.min.js"></script>
<script>
	function onSubmit() {
		var public_key = document.getElementById("publicKey").value;
		var password = document.getElementById("oldPassword").value;
		var encrypt = new JSEncrypt();
		encrypt.setPublicKey(public_key);
		var encrypted_password = encrypt.encrypt(password);
		document.getElementById("oldPassword").value = encrypted_password;

		password = document.getElementById("newPassword").value;
		encrypted_password = encrypt.encrypt(password);
		document.getElementById("newPassword").value = encrypted_password;
	}
</script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/include/header.jsp"%>
	<div class="container">
		<form action=" " method="POST" onSubmit="onSubmit()">
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
					<p>用户名：${user.username }</p>
				</div>
				<div class="panel-body">
					<p>用户类型：${user.type.name }</p>
				</div>
				<div class="panel-body">
					<p>
						旧密码：
						<input type="password" name="oldPassword" id="oldPassword">
					</p>
				</div>
				<div class="panel-body">
					<p>
						新密码：
						<input type="password" name="newPassword" id="newPassword">
					</p>
				</div>
				<div class="panel-body">
					<p>
						重复新密码：
						<input id="newPasswordRepeat" type="password"
							id="newPasswordRepeat">
					</p>
				</div>

				<div class="panel-body">
					<p>
						姓名：
						<input type="text" name="name" value="${user.name }">
					</p>
				</div>
				<div class="panel-body">
					<p>
						电话：
						<input type="text" name="telephone" value="${user.telephone }">
					</p>
				</div>
				<hr>
				<div class="panel-body ">
					<input id="publicKey" type='hidden' name="publicKey"
						value="${rsaPublicKey }" />
					<input type='hidden' name="timestamp" value="${rsaCreateTimestamp }" />
					<button class="btn btn-default" type="submit">确认修改</button>
				</div>
			</div>
		</form>
	</div>

</body>
</html>