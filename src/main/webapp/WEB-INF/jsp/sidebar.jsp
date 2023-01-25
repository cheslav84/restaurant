<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale.country}" />
<fmt:setBundle basename="language" />
<div data-animation="default" class="navbar-left w-nav" data-easing2="ease" data-easing="ease" data-collapse="small"
    role="banner" data-no-scroll="1" data-duration="400" data-doc-height="1">
    <div class="menu-overlay">
        <div class="languages">
            <a href="?locale=EN" class="nav-social-button w-inline-block language-link">EN</a>
            <a href="?locale=UA" class="nav-social-button w-inline-block language-link">UA</a>
        </div>
        <div class="w-container">
            <a href="index.html" aria-current="page" class="logo-container w-clearfix w-nav-brand w--current">
                <img src="view/pictures/icons/VH_logo_white.png" alt="" class="logo-icon">
                <h1 class="logo-text">V&amp;H VICTORY</h1>
                <h2 class="logo-text subtitle">
                    <fmt:message key="sidebar.cafe" />&nbsp;&amp;
                    <fmt:message key="sidebar.restaurant" />
                </h2>
            </a>
            <div class="menu-divider w-hidden-small w-hidden-tiny"></div>
            <div class="menu-button w-nav-button">
                <div class="w-icon-nav-menu"></div>
            </div>
            <nav role="navigation" class="nav-menu w-nav-menu">
                <c:if test="${sessionScope.loggedUser eq null}">
                    <jsp:include page="navigation_visitor.jsp" />
                </c:if>
                <c:if test="${sessionScope.loggedUser.role == 'CLIENT'}">
                    <jsp:include page="user/navigation_user.jsp" />
                </c:if>
                <c:if test="${sessionScope.loggedUser.role == 'MANAGER'}">
                    <jsp:include page="manager/navigation_manager.jsp" />
                </c:if>
            </nav>
            <div class="menu-divider w-hidden-small w-hidden-tiny"></div>
            <div class="nav-contact-block w-hidden-small w-hidden-tiny">
                <a href="http://www.facebook.com/" target="_blank" class="nav-social-button w-inline-block">
                    <img src="pictures/icons/Icon-facebook.png" alt="" class="nav-social-icon"></a>
                <a href="http://www.twitter.com/" target="_blank" class="nav-social-button w-inline-block">
                    <img src="pictures/icons/Icon-twitter.png" alt="" class="nav-social-icon">
                </a>
            </div>
        </div>
    </div>
    <div class="w-nav-overlay" data-wf-ignore=""></div>
</div>