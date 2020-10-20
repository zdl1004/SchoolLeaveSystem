<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>学院管理</title>
<script>
$(document).ready(function(){
	$("#addCollageSubmit").on("click", addCollageAjax);
	$("#changeCollageSubmit").on("click", changeCollageAjax);
	$("#deleteCollageSubmit").on("click", deleteCollageAjax);
	$("#addCollageManagerSubmit").on("click", addCollageManagerAjax);
});

function addCollageAjax(){
	$.ajax({
		url : '${pageContext.request.contextPath}/api/collage/add',
		dataType : 'json',
		data : {
			name: $("#addCollageText").val()
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

function addCollageManagerAjax(){
	$.ajax({
		url : '${pageContext.request.contextPath}/api/collage/addManager',
		dataType : 'json',
		data : {
			username: $("#addCollageManagerText").val(),
			name:$("#addCollageManagerText1").val()
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

function deleteModelClick(id, year){
	$("#deleteMessage").text("确认要删除" + year + "吗？所有属于该学院的专业、班级、请假记录等将会被永久删除，请谨慎操作！")
	window.deleteId = id;
}

function changeModelClick(id, name){
	$("#changeCollageText").val(name);
	window.changeId = id;
}

function deleteCollageAjax(){
	$.ajax({
		url : '${pageContext.request.contextPath}/api/collage/delete',
		dataType : 'json',
		data : {
			id: window.deleteId
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

function changeCollageAjax(){
	$.ajax({
		url : '${pageContext.request.contextPath}/api/collage/change',
		dataType : 'json',
		data : {
			id: window.changeId,
			name: $("#changeCollageText").val()
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
		<div class="modal fade" id="addModal" tabindex="-1" role="dialog"
			aria-labelledby="addModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="addModalLabel">添加学院</h4>
					</div>
					<div class="modal-body">请输入学院名称</div>
					<div class="form-group">
						<input type="text" id="addCollageText" class="form-control" />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="addCollageSubmit">确认添加</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>

		<div class="modal fade" id="changeModal" tabindex="-1" role="dialog"
			aria-labelledby="changeModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="changeModalLabel">修改学院名称</h4>
					</div>
					<div class="modal-body">请输入学院名称</div>
					<div class="form-group">
						<input type="text" id="changeCollageText" class="form-control" />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="changeCollageSubmit">确认修改</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>

		<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
			aria-labelledby="deleteModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="deleteModalLabel">删除学院</h4>
					</div>
					<div id="deleteMessage" class="modal-body"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="deleteCollageSubmit">确认删除</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>

		<div class="modal fade" id="addManagerModal" tabindex="-1" role="dialog"
			 aria-labelledby="addModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="addCollageModalLabel">添加学院管理员</h4>
					</div>
					<div class="modal-body">请输入用户名称和学院名称</div>
					<div class="form-group">
						<input type="text" id="addCollageManagerText" class="form-control" />
					</div>
					<div class="modal-body">请输入学院名称</div>
					<div class="form-group">
						<input type="text" id="addCollageManagerText1" class="form-control" />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="addCollageManagerSubmit">确认添加</button>
					</div>
				</div>
<%--				<!-- /.modal-content -->--%>
			</div>
			<!-- /.modal -->
		</div>
		<div class="form-group">
			<button class="btn btn-primary" data-toggle="modal" data-target="#addModal">添加学院</button>
		</div>

		<div class="table-responsive">
			<table class="table">
				<tr>
					<th>学院名称</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${collages }" var="collage">
					<tr>
						<td>${collage.name }</td>
						<td>
							<div class="form-group">
								<button class="btn btn-default" data-toggle="modal" data-target="#changeModal" onclick="changeModelClick(${collage.id }, '${collage.name }')">修改</button>
								<button class="btn btn-default" data-toggle="modal" data-target="#deleteModal" onclick="deleteModelClick(${collage.id }, '${collage.name }')">删除</button>
								<button class="btn btn-primary" data-toggle="modal" data-target="#addManagerModal">添加或更改学院管理员</button>
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>

</body>
</html>