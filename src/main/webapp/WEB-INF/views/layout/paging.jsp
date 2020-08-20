<%--
  Created by IntelliJ IDEA.
  User: ngocn
  Date: 19/08/2020
  Time: 3:45 CH
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored = "false" %>
<ul class="pagination">
    <c:forEach begin="1" end="${pageInfo.totalPages}" varStatus="loop">
        <c:choose>
            <c:when test="${pageInfo.indexPage== loop.index}">
                <li class="active"><a href="javascript:void(0);">${loop.index}</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="javascript:void(0);" onclick="gotoPage(${loop.index});">${loop.index}</a></li>
            </c:otherwise>
        </c:choose>

        <li></li>
    </c:forEach>
</ul>