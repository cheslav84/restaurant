<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%-- <%@ taglib prefix="my" tagdir="/WEB-INF/tags"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${user.language}"/>
<fmt:setBundle basename="cra_language"/>

<div data-animation="default" class="navbar-left w-nav" data-easing2="ease" data-easing="ease" data-collapse="small"
    role="banner" data-no-scroll="1" data-duration="400" data-doc-height="1">
    <div class="menu-overlay">
        <div class="w-container">
            <a href="index.html" aria-current="page" class="logo-container w-clearfix w-nav-brand w--current">
                <img src="view/pictures/icons/VH_logo_white.png" alt="" class="logo-icon">
                <h1 class="logo-text">V&H VICTORY</h1>
                <h2 class="logo-text subtitle">Cafe&nbsp;&amp; Restaurant</h2>
            </a>
            <div class="menu-divider w-hidden-small w-hidden-tiny"></div>
            <div class="menu-button w-nav-button">
                <div class="w-icon-nav-menu"></div>
            </div>

            <nav role="navigation" class="nav-menu w-nav-menu">
                <a href="index" id="index-nav" aria-current="page" class="nav-link w-nav-link w--current"
                    style="max-width: 940px;">Home</a>
                <a href="menu" id="menu-nav" class="nav-link w-nav-link" style="max-width: 940px;">Menu</a>

                <c:if test="${sessionScope.loggedUser eq null}">
                    <a href="login" id="login-nav" class="nav-link w-nav-link" style="max-width: 940px;">Log in</a>
                </c:if>

                <c:if test="${sessionScope.loggedUser ne null}">
                    <a href="basket" id="basket-nav" class="nav-link w-nav-link" style="max-width: 940px;">My orders</a>
                </c:if>


                 <c:if test="${sessionScope.loggedUser ne null}">
                     <c:if test="${sessionScope.loggedUser.role == 'MANAGER'}">
                          <a href="managerPage" aria-current="page" class="nav-link w-nav-link">Manager page</a>
                     </c:if>
                     <a href="${pageContext.request.contextPath}/logout" key="logout" aria-current="page" class="nav-link w-nav-link">Log out</a>
                 </c:if>



           <%--     <a href="reservation.html" class="nav-link w-nav-link" style="max-width: 940px;">Reservation</a>
                <a href="gallery.html" class="nav-link w-nav-link" style="max-width: 940px;">Gallery</a>
                <a href="parties.html" class="nav-link w-nav-link" style="max-width: 940px;">Parties &amp;
                    Events</a>
                <a href="about-us.html" class="nav-link w-nav-link" style="max-width: 940px;">About us</a>
                <a href="blog.html" class="nav-link w-nav-link" style="max-width: 940px;">Blog</a>
                <a href="contact.html" class="nav-link w-nav-link" style="max-width: 940px;">Contact us</a>--%>


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