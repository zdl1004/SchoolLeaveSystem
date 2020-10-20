<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>年级管理</title>
<script>
$(document).ready(function(){
	$("#addGradeSubmit").on("click", addGradeCheck);
	$("#deleteGradeSubmit").on("click", deleteGradeAjax);
});

function addGradeCheck(){
	if(!/^[0-9]{4}$/.test($("#addGradeText").val())){
		alert("输入不符合格式，请重新输入");
		return;
	}
	addGradeAjax();
}

function addGradeAjax(){
	$.ajax({
		url : '${pageContext.request.contextPath}/api/grade/add',
		dataType : 'json',
		data : {
			year: $("#addGradeText").val()
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
	$("#deleteMessage").text("确认要删除" + year + "年级吗？所有属于该年级的班级、请假记录等将会被永久删除，请谨慎操作！")
	window.deleteId = id;
}

function deleteGradeAjax(){
	$.ajax({
		url : '${pageContext.request.contextPath}/api/grade/delete',
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
						<h4 class="modal-title" id="addModalLabel">添加年级</h4>
					</div>
					<div class="modal-body">请输入年份</div>
					<div class="form-group">
						<input type="text" id="addGradeText" class="form-control" />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="addGradeSubmit">确认添加</button>
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
						<h4 class="modal-title" id="deleteModalLabel">删除年级</h4>
					</div>
					<div id="deleteMessage" class="modal-body"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="deleteGradeSubmit">确认删除</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
		<div class="form-group">
			<button class="btn btn-primary" data-toggle="modal" data-target="#addModal">添加年级</button>
		</div>
		<div class="table-responsive">
			<table class="table">
				<tr>
					<th>年级</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${grades }" var="grade">
					<tr>
						<td>${grade.year }</td>
						<td>
							<div class="form-group">
								<button class="btn btn-default" data-toggle="modal" data-target="#deleteModal" onclick="deleteModelClick(${grade.id }, ${grade.year })">删除</button>
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>

</body>
</html>