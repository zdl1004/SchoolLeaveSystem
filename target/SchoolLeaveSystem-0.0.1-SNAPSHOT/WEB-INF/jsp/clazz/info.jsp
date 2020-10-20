<%@page import="pers.zdl1004.SchoolLeaveSystem.type.UserType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>班级详情</title>
<script>

<%--添加用户ajax请求--%>
function addUserSubmit(){
	if(!/^[0-9]{6,10}$/.test($("#userNameText").val())){//验证输入的用户名
		alert("用户名格式错误");
		return;
	}
	$.ajax({
		url : '${pageContext.request.contextPath}/api/clazz/addUser',
		dataType : 'json',
		data : {
			id: ${clazzId },
			username: $("#userNameText").val()

		},
		type : 'POST',
		success : function(data) {
			if(data.code === 100){
				location.reload();//刷新页面
			} else {
				alert(data.message);
			}
		},
		error : function(e) {
			alert(e);
		}
	});
}

//转让用户ajax请求
function changeUserSubmit(){
	if(!/^[0-9]{6,10}$/.test($("#userNameText1").val())){//验证输入的用户名
		alert("用户名格式错误");
		return;
	}
	$.ajax({
		url : '${pageContext.request.contextPath}/api/clazz/changeUserType',
		dataType : 'json',
		data : {
			id: ${clazzId },
			username: $("#userNameText1").val()
		},
		type : 'POST',
		success : function(data) {
			if(data.code === 100){
				location.reload();//刷新页面
			} else {
				alert(data.message);
			}
		},
		error : function(e) {
			alert(e);
		}
	});
}

function giveUserSubmit(){
	if(!/^[0-9]{6,10}$/.test($("#userNameText2").val())){//验证输入的用户名
		alert("用户名格式错误");
		return;
	}
	$.ajax({
		url : '${pageContext.request.contextPath}/api/clazz/giveUserType',
		dataType : 'json',
		data : {
			id: ${clazzId },
			username: $("#userNameText2").val()
		},
		type : 'POST',
		success : function(data) {
			if(data.code === 100){
				location.reload();//刷新页面
			} else {
				alert(data.message);
			}
		},
		error : function(e) {
			alert(e);
		}
	});
}
//移除用户
function removeUserClick(id, name){
	$("#removeUserBody").text("确定要移除用户 " + name + " 吗？");
	window.remove_user_id = id;
}

function removeUserSubmit(){
	$.ajax({
		url : '${pageContext.request.contextPath}/api/clazz/removeUser',
		dataType : 'json',
		data : {
			id: ${clazzId },
			userId: window.remove_user_id
		},
		type : 'POST',
		success : function(data) {
			if(data.code === 100){
				location.reload();
			} else {
				alert(data.message);
			}
		},
		error : function(e) {
			alert(e);
		}
	});
}

</script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/include/header.jsp"%>
	<div class="container">
<%--		添加用户--%>
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
		 aria-labelledby="addModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="addModalLabel">添加用户到班级</h4>
				</div>
				<div class="modal-body">请输入预添加用户的用户名</div>
				<div class="form-group">
					<input type="text" id="userNameText" class="form-control" />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary"
							onclick="addUserSubmit()">确认添加</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
<%--		权限转让--%>
	<div class="modal fade" id="changeUserModal" tabindex="-1" role="dialog"
		 aria-labelledby="addModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="addModalLabel">管理员权限转让</h4>
				</div>
				<div class="modal-body">请输入预转让的用户名</div>
				<div class="form-group">
					<input type="text" id="userNameText1" class="form-control" />
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary"
							onclick="changeUserSubmit()">确认转让</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<div class="modal fade" id="giveUserModal" tabindex="-1" role="dialog"
		 aria-labelledby="addModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="addModalLabel">班级管理员任命</h4>
				</div>
				<div class="modal-body">请输入预任命的用户名</div>
				<div class="form-group">
					<input type="text" id="userNameText2" class="form-control" />
				</div>

				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary"
							onclick="giveUserSubmit()">确认任命</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
		<div class="modal fade" id="removeUserModal" tabindex="-1" role="dialog"
			aria-labelledby="addModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="addModalLabel">移除用户</h4>
					</div>
					<div class="modal-body" id="removeUserBody"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary"
							onclick="removeUserSubmit()">确认移除</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>



		<div class="form-group">
			<button class="btn btn-primary" data-toggle="modal" data-target="#addModal">添加用户到班级</button>
<%--			<a class="btn btn-primary" href="/api/user/batchRegister">批量注册</a>--%>
			<a class="btn btn-primary" href="${pageContext.request.contextPath}/user/batchRegister">批量注册</a>

<%--			<c:if test="${user.type.code == UserType.CLAZZ_ADMIN.code }">--%>
<%--				<button class="btn btn-primary" data-toggle="modal" data-target="#changeUserModal">班级权限转让</button>--%>
<%--			</c:if>--%>

			<c:if test="${user.type.code <= UserType.CLAZZ_ADMIN.code }">
				<button class="btn btn-primary" data-toggle="modal" data-target="#giveUserModal">班级管理员任命</button>
			</c:if>


		</div>
		<div class="table-responsive">
			<table class="table">
				<tr>
					<th>学号</th>
					<th>姓名</th>
					<th>手机</th>
<%--					<th>请假次数</th>--%>
					<th>操作</th>
				</tr>
				<c:forEach items="${users }" var="listUser">
					<tr>
						<td>${listUser.username }</td>
						<td>${listUser.name }</td>
						<td>${listUser.telephone }</td>
<%--						<td></td>--%>
						<td>
							<div class="form-group">
								<button class="btn btn-default" data-toggle="modal"
									data-target="#removeUserModal" onclick="removeUserClick(${listUser.id }, '${listUser.name }')">移出班级</button>
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>

</body>
</html>