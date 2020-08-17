<%--
  Created by IntelliJ IDEA.
  User: ngocn
  Date: 16/08/2020
  Time: 1:33 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored = "false" %>
<!-- sidebar menu -->
<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
    <div class="menu_section">
        <h3>General</h3>
        <ul class="nav side-menu">
            <!-- Parent menu -->
            <c:forEach items="${menuSession}" var="menu">
            <li><a><i class="fa fa-home"></i> ${menu.name} <span class="fa fa-chevron-down"></span></a>
                <!-- Child menu -->
                <ul class="nav child_menu">
                    <c:forEach items="${menu.menuChild}" var="menuC">
                    <li><a href="index.html">${menuC.name}</a></li>
                    </c:forEach>
                </ul>
                <!-- Child menu -->
            </li>
            </c:forEach>
            <!-- Parent menu -->
        </ul>
    </div>

</div>
<!-- /sidebar menu -->