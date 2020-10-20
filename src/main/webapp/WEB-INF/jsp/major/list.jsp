<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>专业管理</title>
<script>
function addMajorClick(collage_id, collage_name){
	$("#addMajorBody").text("为 " + collage_name + " 添加专业，请输入专业名称");
	window.add_collage_id = collage_id;
	$("#addModal").modal("show");
}

function addMajorSubmit(){
	$.ajax({
		url : '${pageContext.request.contextPath}/api/major/add',
		dataType : 'json',
		data : {
			collageId: window.add_collage_id,
			name: $("#addMajorText").val()
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

function changeMajorClick(major_id, major_name, collage_id){
	$("#changeMajorBody").text("将 " + major_name + " 修改为");
	window.change_major_id = major_id;
	$("#changeMajorSelect").find("option[value=" + collage_id + "]").attr("selected", true);
	$("#changeMajorText").val(major_name);
	$("#changeModal").modal("show");
}

function changeMajorSubmit(){
	$.ajax({
		url : '${pageContext.request.contextPath}/api/major/change',
		dataType : 'json',
		data : {
			id: window.change_major_id,
			name: $("#changeMajorText").val(),
			collageId: $("#changeMajorSelect").val()
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

function deleteMajorClick(major_id, major_name){
	$("#deleteMajorBody").text("确定要删除 " + major_name + " 专业吗？所有属于该专业的班级、请假记录将会一并删除，请谨慎操作！");
	window.delete_major_id = major_id;
	$("#deleteModal").modal("show");
}

function deleteMajorSubmit(){
	$.ajax({
		url : '${pageContext.request.contextPath}/api/major/delete',
		dataType : 'json',
		data : {
			id: window.delete_major_id
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
		<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="addModalLabel">添加专业</h4>
					</div>
					<div class="modal-body" id="addMajorBody"></div>

					<div class="form-group">
						<input type="text" id="addMajorText" class="form-control" />
					</div>

					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" onclick="addMajorSubmit()">确认添加</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
		<div class="modal fade" id="changeModal" tabindex="-1" role="dialog" aria-labelledby="changeModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="changeModalLabel">修改专业信息</h4>
					</div>
					<div class="modal-body" id="changeMajorBody"></div>
					<div class="form-group">
						<select id="changeMajorSelect" class="form-control">
							<c:forEach items="${collages }" var="collage">
								<option value="${collage.id }">${collage.name }</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<input type="text" id="changeMajorText" class="form-control" />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" onclick="changeMajorSubmit()">确认修改</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
		<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="deleteModalLabel">删除专业</h4>
					</div>
					<div class="modal-body" id="deleteMajorBody"></div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" onclick="deleteMajorSubmit()">确认删除</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>

		<div class="table-responsive">
			<table class="table">
				<tr>
					<th>名称</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${collages }" var="collage">
					<tr>
						<td>${map[collage].isEmpty() ? "-&nbsp;&nbsp;"  : "▼" }${collage.name }</td>
						<td>
							<div class="form-group">
								<button class="btn btn-primary" onclick="addMajorClick(${collage.id }, '${collage.name }')">添加专业</button>
							</div>
						</td>
					</tr>
					<c:forEach items="${map[collage] }" var="major">
						<tr>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${major.name }</td>
							<td>
								<div class="form-group">
									<button class="btn btn-default" onclick="changeMajorClick(${major.id }, '${major.name }', ${collage.id })">修改</button>
									<button class="btn btn-default" onclick="deleteMajorClick(${major.id }, '${major.name }')">删除</button>
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