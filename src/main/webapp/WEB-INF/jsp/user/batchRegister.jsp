<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<script>
	function check() {
		$("#text_result").attr("class", "form-group alert");
		$("#text_result").text("");
		$("#submit").attr("disabled", false);
		var text = $("#text").val().trim();
		if (text === "") {
			$("#text_result").addClass("alert-danger");
			$("#text_result").text("信息为空");
			$("#submit").attr("disabled", true);
			return;
		}
		var arr = text.split("\n");
		for (var i = 0; i < arr.length; i++) {
			var info = arr[i].split("\t");
			if (info.length != 3) {
				$("#text_result").addClass("alert-danger");
				$("#text_result").text("第" + (i + 1) + "行格式有误，内容为：" + arr[i]);
				$("#submit").attr("disabled", true);
				return;
			}
			if (!/^[0-9]{10}$/.test(info[0])) {
				$("#text_result").addClass("alert-danger");
				$("#text_result")
						.text("第" + (i + 1) + "行学号格式有误，内容为：" + info[0]);
				$("#submit").attr("disabled", true);
				return;
			}
			if (!/^[\u4E00-\u9FFF]{2,4}$/.test(info[1])) {
				$("#text_result").addClass("alert-danger");
				$("#text_result")
						.text("第" + (i + 1) + "行姓名格式有误，内容为：" + info[1]);
				$("#submit").attr("disabled", true);
				return;
			}
			if (!/^0?(13|14|15|17|18|19)[0-9]{9}$/.test(info[2])) {
				$("#text_result").addClass("alert-danger");
				$("#text_result")
						.text("第" + (i + 1) + "行手机格式有误，内容为：" + info[2]);
				$("#submit").attr("disabled", true);
				return;
			}
		}
		$("#text_result").addClass("alert-success");
		$("#text_result").text("信息校验通过");
	}
	$(document).ready(function() {
		$("#text").on("propertychange input", check);
		check();
	});


	function ajaxSubmit() {
		$.ajax({
			url : '${pageContext.request.contextPath}/api/user/batchRegister',
			dataType : 'json',
			data : {
				text : $("#text").val().trim(),
				clazzId : $("#clazzId")[0].value
			},
			type : 'POST',
			success : function(data) {
				$("#submit_result").attr("class", "form-group alert");
				$("#submit_result").text("");
				if(data.code === 100){
					$("#submit_result").addClass("alert-success");
				} else {
					$("#submit_result").addClass("alert-danger");
				}
				$("#submit_result").text(data.message.replace(/\n/g, "<br/>"));
			},
			error : function(e) {
				alert(e);
			}
		});
	}
</script>
<title>批量注册</title>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/include/header.jsp"%>
	<div class="container">
		<form role="form" name="form">
			<div class="form-group">
				<span>请下载附件，按照格式填写信息，后将所有人信息框选后复制到文本框中：</span>
				<a
					href="${pageContext.request.contextPath}/static/batchRegister.xlsx">点此下载附件</a>
				<textarea id="text" name="text" class="form-control" rows="20"></textarea>
			</div>
			<div id="text_result" class="form-group alert"></div>
			<div class="form-group">
				<span>请选择注册后这些用户加入的班级（仅可选择自己管理的班级）：</span>
				<select id="clazzId" class="form-control" name="clazzId">
					<c:forEach items="${clazzes }" var="x">
						<option value="${x.id }">${x.fullName }</option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<button id="submit" type="button" class="btn btn-default"
					onclick="ajaxSubmit()">批量注册</button>
			</div>
			<div id="submit_result" class="form-group alert"></div>
		</form>
	</div>

</body>
</html>