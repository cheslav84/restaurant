<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale.country}" />
<fmt:setBundle basename="language" />
<a href="index" id="index-nav" aria-current="page" class="nav-link w-nav-link w--current"
                    style="max-width: 940px;">
    <fmt:message key="sidebar.home"/>
</a>
<a href="menu" aria-current="page" class="nav-link w-nav-link" style="max-width: 940px;">
    <fmt:message key="sidebar.menu"/>
</a>
<a href="manage_orders?sorted=date" aria-current="page" class="nav-link w-nav-link">
    <fmt:message key="sidebar.manageOrders"/>
</a>
<a href="add_dish_page" aria-current="page" class="nav-link w-nav-link">
    <fmt:message key="sidebar.addDishPage"/>
</a>
<a href="logout" aria-current="page" class="nav-link w-nav-link">
    <fmt:message key="sidebar.logout"/>
</a>
     <div class="w-nav-overlay" data-wf-ignore=""></div>
</div>