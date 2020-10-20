<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <%@ include file="/WEB-INF/jsp/include/head.jsp" %>
    <title>请假申请</title>


    <script charset="UTF-8">
        var image_count = 0;
        var max_image_count = 5;

        function addImage() {
            if (image_count >= max_image_count) {
                alert("最多只允许上传" + max_image_count + "张图片！");
                return;
            }
            image_count++;
            var line = document.createElement("div");
            var p = document.createElement("span");
            p.innerText = "图片" + image_count + "：";
            var input = document.createElement("input");
            input.type = "file";
            input.name = "image";
            line.append(p);
            line.append(input);
            document.getElementById("images").append(line);
        }

        function clearImages() {
            document.getElementById("images").innerHTML = "";
            image_count = 0;
        }

       function  alertRegulation(){
            alert(
                "请假承诺：\n" +
                    "本人已将此次请假情况告知家长，外出期间本人严格遵守国家法律、社会公德和校纪校规，注意自身的人身和财物安全，防止各种事故的发生，如果发生安全事故（包括人身伤害事故），由学生本人承担安全责任，同时还保证在外出活动期间如发生突发事件或重大情况及时报告，保证不参加任何违反学校规定以及违法的活动。\n"+
                "温馨提示：\n" +
                "　　1、保持电话通畅，增强安全防范意识，防火灾、防交通事故、防食物中毒、防暴力恐怖事件、防心理隐患、防传销行骗、防宗教渗透、防酗酒等。" +
                "\n" +
                "　　2、如遇突发事件，请务必与家长、班主任、同学、当地公安部门联系!" +
                "\n" +
                "　　3、如是学校组织外出，请服从带队老师安排。" +
                "\n" +
                "　　4、如果不能按时返校，请务必提前告知班主任。");
        }
    </script>

</head>
<body>
<%@ include file="/WEB-INF/jsp/include/header.jsp" %>
<div class="container" style="font-weight: 2	">
    <h2>请假申请</h2>
    <form name="form" action="${pageContext.request.contextPath}/leave/create"
          method="POST" enctype="multipart/form-data">
        <!--请假日期模块  -->
        <div class="container">
            <!-- 开始日期 -->
            <div class="row">
                <div class="col-xs-12 col-md-10 col-md-offset-2">
                    请假开始日期： <select name="startYear">
                    <c:forEach items="${years }" var="year">
                        <option value=${year }>${year }</option>
                    </c:forEach>
                </select> 年 <select name="startMonth">
                    <c:forEach var="i" begin="${currentMonth }" end="12">
                        <option value=${i }>${i }</option>
                    </c:forEach>
                </select> 月 <select name="startDay">
                    <c:forEach var="i" begin="${currentDay }" end="${lastDay }">
                        <option value=${i }>${i }</option>
                    </c:forEach>
                </select> 日 第 <select name="startLesson">
                    <c:forEach var="i" begin="1" end="10">
                        <option value=${i }>${i }</option>
                    </c:forEach>
                </select> 节课
                </div>
            </div>
            <!-- 结束日期  -->
            <div class="row">
                <div class="col-xs-12 col-md-10 col-md-offset-2">
                    请假结束日期： <select name="endYear">
                    <c:forEach items="${years }" var="year">
                        <option value=${year }>${year }</option>
                    </c:forEach>
                </select> 年 <select name="endMonth">
                    <c:forEach var="i" begin="${currentMonth }" end="12">
                        <option value=${i }>${i }</option>
                    </c:forEach>
                </select> 月 <select name="endDay">
                    <c:forEach var="i" begin="${currentDay }" end="${lastDay }">
                        <option value=${i }>${i }</option>
                    </c:forEach>
                </select> 日 第 <select name="endLesson">
                    <c:forEach var="i" begin="1" end="10">
                        <option value=${i }>${i }</option>
                    </c:forEach>
                </select> 节课
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row" style="padding-top: 20px;">
                <div class="col-xs-12 col-md-10 col-md-offset-2">
                    请假原因：<br>
                    <textarea class="form-control" rows="3" name="reason"></textarea>
                </div>
            </div>
            <div class="row">
                <div id="images" class=" col-xs-12 col-md-10 col-md-offset-2"
                     style="padding-top:20px; padding-bottom: 20px;"></div>
                <div class="row" style="padding-top: 20px;">
                    <div class="col-xs-12 col-md-10 col-md-offset-2">
                        <button type="button" onclick="addImage()" class="btn btn-default active"><span
                                class="glyphicon glyphicon-picture"> &nbsp;添加图片</span></button>
                        <a href="javascript:clearImages()">清空</a> <br>
                        <button type="submit" class="btn btn-default active" style="margin-top: 10px;"
                               onclick="alertRegulation()" ><span class="glyphicon glyphicon-open">&nbsp;&nbsp;&nbsp;&nbsp;提交</span>
                        </button>
                    </div>


                </div>
            </div>

        </div>
    </form>
</div>

</body>
</html>