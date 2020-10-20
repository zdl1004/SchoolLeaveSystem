<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>注册</title>
<script src="${pageContext.request.contextPath}/js/jsencrypt.min.js"></script>
<script>
function onSubmit(){
	var public_key = document.getElementById("publicKey").value;
	var password = document.getElementById("password").value;
	var encrypt = new JSEncrypt();
    encrypt.setPublicKey(public_key);
    var encrypted_password = encrypt.encrypt(password);
    document.getElementById("password").value = encrypted_password;
}
</script>
	<style type="text/css">
		@media (min-width:768px){
			.bg{background: url('${pageContext.request.contextPath}/images/bg3.jpg') no-repeat center center fixed;
				background-size: cover;
				/*opacity: 0.5;*/}
			#bg-font{
				color: whitesmoke;
			}
		}
		@media (max-width:768px){
			.bg{background: url('${pageContext.request.contextPath}/images/bg4.jpg') no-repeat center center fixed;
				background-size: cover;
				/*opacity: 0.5;*/}
		}
		/*.form-control:hover, .form-control:focus {*/
		/*	border: 1px solid #474d5b;*/
		/*	color: #464e60;*/
		/*	box-shadow: 0px 5px 10px rgba(45,50,64,.2);*/
		/*}*/
		/*.enter_form:hover,.enter_form:focus{*/
		/*	border: 1px solid #474d5b;*/
		/*	color: #464e60;*/
		/*	!*box-shadow: 0px 5px 10px rgba(45,50,64,.2);*!*/
		/*}*/

	</style>
<%--	<%@ include file="/WEB-INF/jsp/include/head.jsp"%>--%>
</head>
<body class="bg">
	<%@ include file="/WEB-INF/jsp/include/header.jsp"%>
	<div class="container">
		<div style="margin-top: 10%; font-size: 20px">
			<form name="form"
				action="${pageContext.request.contextPath}/user/register"
				onsubmit="onSubmit()" method="POST">
				<div
					class="form-group has-feedback form-inline  form-horizontal row">
					<span
						class="glyphicon glyphicon-user col-xs-3 col-md-1 col-md-offset-3" style="color: #2aabd2"></span>
					<input type="text" name="username" placeholder="请输入学号"
						class="col-xs-8 col-md-5" maxlength="10" required>
					<span id="error_act" class="col-xs-6 col-md-4"></span>
				</div>
				<div
					class="form-group has-feedback form-inline  form-horizontal row">
					<span
						class="glyphicon glyphicon-lock col-xs-3 col-md-1 col-md-offset-3"style="color: #2aabd2"></span>
					<input  type="password" name="password" id="password"
						placeholder="请输入密码" class="col-xs-8 col-md-5 " maxlength="18" required>
					<span id="error_psw" class="col-xs-6 col-md-4"></span>
				</div>
				<div
					class="form-group has-feedback form-inline  form-horizontal row">
					<span
						class="glyphicon glyphicon-tag col-xs-3 col-md-1 col-md-offset-3"style="color: #2aabd2"></span>
					<input  type="text" name="name" id="name" placeholder="请输入姓名"
						class="col-xs-8 col-md-5 " maxlength="10" required>
					<span id="error_name" class="col-xs-6 col-md-4"></span>
				</div>
				<div
					class="form-group has-feedback form-inline  form-horizontal row">
					<span
						class="glyphicon glyphicon-earphone col-xs-3 col-md-1 col-md-offset-3"style="color: #2aabd2"></span>
					<input type="text" name="telephone" id="telephone"
						placeholder="请输入电话" class="col-xs-8 col-md-5 " maxlength="11" required>
					<span id="error_tel" class="col-xs-6 col-md-4"></span>
				</div>
				<div
					class="form-group has-feedback form-inline  form-horizontal row">
					<span class=" col-xs-3 col-md-1 col-md-offset-3"></span>
					<input id="publicKey" type='hidden' name="publicKey"
						value="${rsaPublicKey }" />
					<input type='hidden' name="timestamp" value="${rsaCreateTimestamp }" />
					<span id="" class="col-xs-6 col-md-4"></span>
				</div>
				<div
					class="form-group has-feedback form-inline  form-horizontal row"
					style="margin: 0 auto; text-align: center">
					<button type="submit" class="btn btn-default">注册</button>
					<button type="reset" class="btn btn-default col-md-offset-1">重置</button>
				</div>
				<div
					style="text-align: center; margin: 0 auto; font-size: 15px; padding-top: 5%;"
					class="row">
					<a href="javascript:void(0);" class="col-md-offset-2 col-md-4" id="bg-font"
						onclick="javascript:window.location.href='login'" >已有账号，点击登录...</a>
				</div>
			</form>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/include/footer.jsp"%>
</body>
</html>