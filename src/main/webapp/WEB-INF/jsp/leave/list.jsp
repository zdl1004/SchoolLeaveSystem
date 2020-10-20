<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<title>请假列表</title>

	<script>
		$('#js-export').click(function(){
			window.location.href="/report/exportBooksTable.do";
		});
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/include/header.jsp"%>
	<div class="container">
		<button id="js-export" type="button" class="btn btn-primary" onclick="window.open('export')">表格导出</button>
		<form>
			筛选班级：
			<select name="clazzId">
				<option value="">不筛选</option>
				<c:forEach items="${enableClazzes }" var="clazz">
					<option value="${clazz.id}">${clazz.fullName}</option>
				</c:forEach>
			</select>
			筛选用户：
			<input name="userId" type="text" />
			<br />
			开始日期：
			<input name="startDate" type="text" />
			结束日期：
			<input name="endDate" type="text" />
			请假状态：
			<select name="type">
				<option value="">不筛选</option>
				<option value="WAIT">待审核</option>
				<option value="CANCEL">已取消</option>
				<option value="PASS">审核通过</option>
				<option value="NOT_PASS">审核未通过</option>

			</select>
			<input type="submit" />
		</form>
		<div class="table-responsive">
			<table class="table table-bordered"  >
				<tr>
					<th>序号</th>
					<th>请假申请者</th>
					<th>所属班级</th>
					<th>请假时间</th>
					<th>请假申请时间</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${leaveListViews }" var="leave">
					<c:if test="${leave.type == 'WAIT' }">
						<tr class="active">
					</c:if>
					<c:if test="${leave.type == 'CANCEL' }">
						<tr class="warning">
					</c:if>
					<c:if test="${leave.type == 'PASS' }">
						<tr class="success">
					</c:if>
					<c:if test="${leave.type == 'NOT_PASS' }">
						<tr class="danger">
					</c:if>

					<td><a href="info?id=${leave.id }">${leave.id }</a></td>
					<td>${leave.user.name }</td>
					<td>${leave.clazzFullName }</td>
					<td>${leave.leaveTime }</td>
<%--					请假时间范围--%>
					<td>${leave.createTime }</td>
<%--					请假创建时间--%>

					<td>
						<c:if test="${leave.type == 'WAIT' }">
						待审核
					</c:if>
						<c:if test="${leave.type == 'CANCEL' }">
						已取消
					</c:if>
						<c:if test="${leave.type == 'PASS' }">
							审核通过
						</c:if>
						<c:if test="${leave.type == 'NOT_PASS' }">
							审核未通过
						</c:if>
					</td>

					<td>
						<c:if
							test="${leave.type == 'WAIT' && user.type != 'NORMAL_USER' }">
<%--							如果等待审核且用户类型不为普通用户跳转到用户审核界面--%>
							<a href="${pageContext.request.contextPath}/leave/info?id=${leave.id }">审核</a>
						</c:if>

						<c:if
							test="${leave.type == 'WAIT' && user.type == 'NORMAL_USER' }">
							<a href="${pageContext.request.contextPath}/leave/cancel?id=${leave.id }">取消申请</a>
						</c:if>

						<c:if
								test="${leave.type == 'NOT_PASS'||leave.type == 'CANCEL'&& user.type == 'NORMAL_USER' }">
							<a href="${pageContext.request.contextPath}/leave/delete?id=${leave.id }">自助销假</a>
						</c:if>

					</td>
					</tr>
				</c:forEach>
			</table>

<%--			搜索模块--%>

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