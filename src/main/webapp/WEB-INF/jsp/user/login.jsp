<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<script src="${pageContext.request.contextPath}/js/jsencrypt.min.js"></script>
<title>登录</title>
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
		/*body{*/
		/*	background-image: url("/images/bg.jpg") no-repeat center center fixed;*/
		/*	!*background-repeat: no-repeat;*!*/
		/*	!*background-size:100% 100%;*!*/
		/*	!*padding-bottom: 0;*!*/
		/*	!**!*/
		/*	!*width:100%;*!*/
		/*	!*height:100%;*!*/
		/*	!*background-size:100% 100%;*!*/
		/*	!*position:absolute;*!*/
		/*	!*filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='bg',sizingMethod='scale');*!*/

		/*}*/




		@media (min-width:768px){
              .bg{background: url('${pageContext.request.contextPath}/images/bg3.jpg') no-repeat center center fixed;
	    background-size: cover;
	/*opacity: 0.5;*/}
			.bg-font{
				color: whitesmoke;
			}
		}
		@media (max-width:768px){
			.bg{background: url('${pageContext.request.contextPath}/images/bg4.jpg') no-repeat center center fixed;
				background-size: cover;
				/*opacity: 0.5;*/}
		}
	</style>
<%--	<%@ include file="/WEB-INF/jsp/include/head.jsp"%>--%>
</head>

<body class="bg">
	<%@ include file="/WEB-INF/jsp/include/header.jsp"%>
	<div class="container ">


<%--		visible-md-block 表示在早中型设备台式电脑（≥992px）可显示

visible-lg-block 表示在大型设备台式电脑（≥1200px）可显示

visible-xs-block 表示在超小设备手机（<768px）可显示--%>
<%--		--%>
		<div style="margin-top: 10%; font-size: 20px;">
			<form name="form" action="${pageContext.request.contextPath}/user/login" onsubmit="onSubmit()" method="POST">
				<div
					class="form-group has-feedback form-inline  form-horizontal row">
					<span class="glyphicon glyphicon-user col-xs-3 col-md-1 col-md-offset-3"style="color: #2aabd2"></span>
					<input type="text" name="username" placeholder="请输入学号" class="col-xs-8 col-md-5"
						maxlength="10" required>
					<span id="error_act" class="col-xs-6 col-md-4"></span>
				</div>

				<div
					class="form-group has-feedback form-inline  form-horizontal row">
					<span
						class="glyphicon glyphicon-lock col-xs-3 col-md-1 col-md-offset-3"style="color: #2aabd2"></span>
					<input type="password" name="password" id="password" placeholder="请输入密码" class="col-xs-8 col-md-5 "
						maxlength="18" required>
					<span id="error_psw" class="col-xs-6 col-md-4"></span>
				</div>

				<div
					class="form-group has-feedback form-inline  form-horizontal row">
					<span class="col-xs-3 col-md-1 col-md-offset-3"></span>
					<input id="publicKey" type='hidden' name="publicKey" value="${rsaPublicKey }" />
					<input type='hidden' name="timestamp" value="${rsaCreateTimestamp }" />
					<span id="" class="col-xs-6 col-md-4"></span>
				</div>

				<div class="form-group has-feedback form-inline  form-horizontal row" style="margin: 0 auto; text-align: center">
					<button type="submit" class="btn btn-default">登录</button>
					<button type="reset" class="btn btn-default col-md-offset-1">重置</button>
				</div>
				<div
					style="text-align: center; margin: 0 auto; font-size: 15px; padding-top: 1%;"
					class="row">
					<a href="javascript:void(0);"href="javascript:void(0);" class="col-md-offset-2 col-md-4"class="bg-font"
							onclick="javascript:window.location.href='register'">没有账号？注册一个</a>
					<a href="javascript:void(0);" class="col-md-offset-0 col-md-4"
						onclick="" class="bg-font">忘记密码？</a>
				</div>
			</form>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/include/footer.jsp"%>
</body>
</html>