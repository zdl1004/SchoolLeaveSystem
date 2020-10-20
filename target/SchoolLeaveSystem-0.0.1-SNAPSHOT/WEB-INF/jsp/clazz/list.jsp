<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>班级管理</title>
<c:if test="${user.type.code <= UserType.COLLAGE_ADMIN.code }">
	<script>
function addClazzClick(major_id, major_name){
	$("#addClazzBody").text("为 " + major_name + " 添加班级，请输入班级序号并选择年级");
	window.add_major_id = major_id;
	$("#addModal").modal("show");
}

function addClazzSubmit(){
	var no = $("#addClazzNoText").val();
	if(!/^[1-9][0-9]*$/.test(no)){
		alert("序号必须为纯数字");
		return;
	}
	$.ajax({
		url : '${pageContext.request.contextPath}/api/clazz/add',
		dataType : 'json',
		data : {
			no: $("#addClazzNoText").val(),
			majorId: window.add_major_id,
			gradeId: $("#addClazzGradeSelect").val()
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

function changeClazzClick(clazz_id, clazz_name, major_id){
	$("#changeClazzBody").text("将 " + clazz_name + " 修改为");
	window.change_clazz_id = clazz_id;
	$("#changeClazzSelect").find("option[value=" + major_id + "]").attr("selected", true);
	$("#changeClazzText").val(clazz_name);
	$("#changeModal").modal("show");
}

function changeClazzSubmit(){
	var no = $("#changeClazzText").val();
	if(!/[1-9][0-9]*/.test(no)){
		alert("序号必须为纯数字");
		return;
	}
	$.ajax({
		url : '${pageContext.request.contextPath}/api/clazz/change',
		dataType : 'json',
		data : {
			id: window.change_clazz_id,
			no: $("#changeClazzText").val(),
			majorId: $("#changeClazzSelect").val(),
			gradeId: $("#changeClazzGradeSelect").val()
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

function deleteClazzClick(clazz_id, clazz_name){
	$("#deleteClazzBody").text("确定要删除 " + clazz_name + " 班级吗？所有属于该专业的请假记录将会一并删除，请谨慎操作！");
	window.delete_clazz_id = clazz_id;
	$("#deleteModal").modal("show");
}

function deleteClazzSubmit(){
	$.ajax({
		url : '${pageContext.request.contextPath}/api/clazz/delete',
		dataType : 'json',
		data : {
			id: window.delete_clazz_id
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
</c:if>
<script>
function info(id){
	open("${pageContext.request.contextPath}/clazz/info?id=" + id);
}
</script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/include/header.jsp"%>
	<div class="container">
		<c:if test="${user.type.code <= UserType.COLLAGE_ADMIN.code }">
			<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title" id="addModalLabel">添加班级</h4>
						</div>
						<div class="modal-body" id="addClazzBody"></div>
						<div class="form-group">
							<select id="addClazzGradeSelect" class="form-control">
								<c:forEach items="${grades }" var="grade">
									<option value="${grade.id }">${grade.year }</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<input type="text" id="addClazzNoText" class="form-control" />
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<button type="button" class="btn btn-primary"
								onclick="addClazzSubmit()">确认添加</button>
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
							<h4 class="modal-title" id="changeClazzLabel">修改班级信息</h4>
						</div>
						<div class="modal-body" id="changeClazzBody"></div>
						<div class="form-group">
							<select id="changeClazzGradeSelect" class="form-control">
								<c:forEach items="${grades }" var="grade">
									<option value="${grade.id }">${grade.year }</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<select id="changeClazzSelect" class="form-control">
								<c:forEach items="${majors }" var="major">
									<option value="${major.id }">${major.name }</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<input type="text" id="changeClazzText" class="form-control" />
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<button type="button" class="btn btn-primary"
								onclick="changeClazzSubmit()">确认修改</button>
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
							<h4 class="modal-title" id="deleteModalLabel">删除班级</h4>
						</div>
						<div class="modal-body" id="deleteClazzBody"></div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">关闭</button>
							<button type="button" class="btn btn-primary"
								onclick="deleteClazzSubmit()">确认删除</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal -->
			</div>
		</c:if>


		<div class="table-responsive">
			<table class="table" >
				<tr>
					<th>名称</th>
					<th>操作</th>
				</tr>

				<c:forEach items="${majors }" var="major">
					<tr >
						<td>${map[major].isEmpty() ? "-&nbsp;&nbsp;"  : "▼" }${major.name }</td>
						<td><c:if
								test="${user.type.code <= UserType.COLLAGE_ADMIN.code }">
								<div class="form-group">
									<button class="btn btn-primary"
										onclick="addClazzClick(${major.id }, '${major.name }')">添加班级</button>
								</div>
							</c:if></td>
					</tr>
					<c:forEach items="${map[major] }" var="clazz">
						<tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${clazz.fullName }</td>

							<td>
								<div class="form-group">
									<button class="btn btn-default"
										onclick="info(${clazz.id })">详细信息</button>

									<c:if test="${user.type.code <= UserType.COLLAGE_ADMIN.code }">
<%--										院级管理员和超级管理员权限--%>
										<button class="btn btn-default"
											onclick="changeClazzClick(${clazz.id }, ${clazz.no }, ${major.id })">修改</button>
										<button class="btn btn-default"
											onclick="deleteClazzClick(${clazz.id }, '${clazz.fullName }')">删除</button>
									</c:if>

								</div>
							</td>
						</tr>
					</c:forEach>
				</c:forEach>
			</table>
		</div>
	</div>


</body>
</html>