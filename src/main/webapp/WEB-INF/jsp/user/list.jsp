<%@page import="pers.zdl1004.SchoolLeaveSystem.type.UserType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
	<title>用户管理</title>
	<script>
		function changeTypeClick(id, name){
			$("#changeTypeBody").text("请选择 " + name + " 的权限");
			window.changeTypeId = id;
		}

		function changeTypeSubmit(){
			if(window.changeTypeId === ${user.id }){
				alert("不能修改自己的权限");
				return;
			}
			$.ajax({
				url : '${pageContext.request.contextPath}/api/user/changeType',
				dataType : 'json',
				data : {
					id: window.changeTypeId,
					type: $("#changeTypeSelect").val()
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
	<button id="js-export" type="button" class="btn btn-primary" onclick="window.open('export')">表格导出</button>
	<div class="modal fade" id="changeTypeModal" tabindex="-1" role="dialog"
		 aria-labelledby="changeTypeModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="changeTypeModalLabel">修改用户权限</h4>
				</div>
				<div class="modal-body" id="changeTypeBody"></div>

				<div class="form-group">
					<select id="changeTypeSelect" class="form-control">
						<option value="${UserType.SUPER_ADMIN }">超级管理员</option>
						<option value="${UserType.COLLAGE_ADMIN }">院级管理员</option>
						<option value="${UserType.CLAZZ_ADMIN }">班级管理员</option>
						<option value="${UserType.NORMAL_USER }">普通用户</option>
					</select>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="changeTypeSubmit()">确认修改</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<div class="table-responsive">
		<table class="table table-bordered">
			<tr>
				<th>学号</th>
				<th>用户类型</th>
				<th>姓名</th>
				<th>手机</th>
				<th>所属班级</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${users}" var="listUser">
				<tr>
					<td>${listUser.username }</td>
					<td>${listUser.type.name }</td>
					<td>${listUser.name }</td>
					<td>${listUser.telephone }</td>
					<td>${listUser.clazz == null ? "/" : listUser.clazz.fullName }</td>
					<td>
						<div class="form-group">
							<button class="btn btn-default" data-toggle="modal" data-target="#changeTypeModal" onclick="changeTypeClick(${listUser.id }, '${listUser.name }')">修改权限</button>
						</div>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div>
		<ul class="pagination">
			<c:if test="${page > 1}">
				<li class="page-item"><a class="page-link" href="list?page=${page - 1}">上一页</a></li>
			</c:if>
			<c:forEach items="${pageList }" var="current">
				<c:if test="${page != current}">
					<li class="page-item"><a class="page-link" href="list?page=${current}">${current}</a></li>
				</c:if>
				<c:if test="${page == current}">
					<li class="page-item active"><a class="page-link" href="list?page=${current}">${current}</a></li>
				</c:if>
			</c:forEach>
			<c:if test="${page < pageCount}">
				<li class="page-item"><a class="page-link" href="list?page=${page + 1}">下一页</a></li>
			</c:if>
		</ul>
	</div>
</div>
</body>
</html>