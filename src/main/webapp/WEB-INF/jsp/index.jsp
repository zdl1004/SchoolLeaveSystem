<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<html lang="en">
<head>
<title>首页</title>

<%@ include file="/WEB-INF/jsp/include/head.jsp"%>
<script>
    $(window).scroll(function () {
        //小屏幕下的导航条折叠
        if ($(window).width() < 768) {
            //点击导航链接之后，把导航选项折叠起来
            $("#order").onclick(function () {
                $("#order").collapse('hide');
            });
            //滚动屏幕时，把导航选项折叠起来
            $(window).scroll(function () {
                $("#order").collapse('hide');
            });
        }
    });
    </script>
	<style type="text/css">
		.button_span {
			border: none;
		}
		@media (min-width:768px){
			.bg1{background: url('${pageContext.request.contextPath}/images/bg.jpg') no-repeat center center fixed;
				background-size: cover;

				/*opacity: 0.5;*/}
		}
		@media (max-width:768px){
			.bg1{background: url('${pageContext.request.contextPath}/images/bg5.jpg') no-repeat center center fixed;
				background-size: cover;
				height: 100%;
				/*opacity: 0.5;*/}
		}



	</style>
</head>

<body class="bg1">
	<%@ include file="/WEB-INF/jsp/include/header.jsp"%>
<%--	<div id="carousel-example-generic" class="carousel slide container" data-ride="carousel">--%>
<%--		<!-- Indicators -->--%>
<%--		&lt;%&ndash;  创建容器 &ndash;%&gt;--%>
<%--		<ol class="carousel-indicators">--%>
<%--			&lt;%&ndash; 图片序号 &ndash;%&gt;--%>
<%--			<li data-target="#carousel-example-generic" data-slide-to="0"--%>
<%--				class="active"></li>--%>
<%--			<li data-target="#carousel-example-generic" data-slide-to="1"></li>--%>
<%--			<li data-target="#carousel-example-generic" data-slide-to="2"></li>--%>
<%--		</ol>--%>


<%--		<!-- Wrapper for slides -->--%>
<%--		<div class="carousel-inner" role="listbox">--%>
<%--			<div class="item active">--%>
<%--				<img src="images/bg.jpg" alt="" class="img-responsive "style="background-size: cover;background: url('${pageContext.request.contextPath}/images/bg1.jpg') no-repeat center center fixed;--%>
<%--						background-size: cover;" >--%>
<%--				<div class="carousel-caption">--%>
<%--					<h3>SchoolLeaveSystem</h3>--%>
<%--				</div>--%>
<%--			</div>--%>
<%--			<div class="item">--%>
<%--				<img src="images/bg.jpg" alt=""class="img-responsive "style="max-width:100%;overflow:hidden;"  >--%>
<%--				<div class="carousel-caption">--%>
<%--					<h3>好好学习，合理请假</h3>--%>
<%--				</div>--%>
<%--			</div>--%>
<%--			<div class="item">--%>
<%--				<img src="images/bg.jpg" alt=""class="img-responsive "style="max-width:100%;overflow:hidden;" >--%>
<%--				<div class="carousel-caption">--%>
<%--					<h3>好好学习，合理请假</h3>--%>
<%--				</div>--%>
<%--			</div>--%>
<%--		</div>--%>

<%--		<!-- Controls -->--%>
<%--		<a class="left carousel-control" href="#carousel-example-generic"--%>
<%--			role="button" data-slide="prev">--%>
<%--			<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>--%>
<%--			<span class="sr-only">Previous</span>--%>
<%--		</a>--%>
<%--		<a class="right carousel-control" href="#carousel-example-generic"--%>
<%--			role="button" data-slide="next">--%>
<%--			<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>--%>
<%--			<span class="sr-only">Next</span>--%>
<%--		</a>--%>

<%--	</div>--%>

	<%@ include file="/WEB-INF/jsp/include/footer.jsp"%>

</body>
</html>