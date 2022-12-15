<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%-- <%@ taglib prefix="my" tagdir="/WEB-INF/tags"%> --%>

<div data-animation="default" class="navbar-left w-nav" data-easing2="ease" data-easing="ease" data-collapse="small"
    role="banner" data-no-scroll="1" data-duration="400" data-doc-height="1">
    <div class="menu-overlay">
        <div class="user-panel">
            <div class="user-info">
                <c:if test="${sessionScope.loggedUser ne null}">
                    <c:if test="${sessionScope.loggedUser.role == 'CLIENT'}"> 
                        <div class="user-name">Welcome, 
                            <c:out value="${sessionScope.loggedUser.name}"/>
                         </div>
                    </c:if>
                    <c:if test="${sessionScope.loggedUser.role == 'MANAGER'}"> 
                        <a href="adminPage" target="_blank" class="user-name">Admin page</a>
                    </c:if>
                    <a href="logout" aria-current="page" class="nav-social-button w-inline-block login-page-link">Log out</a>
                </c:if>
                <c:if test="${sessionScope.loggedUser eq null}">
                    <a href="login" aria-current="page" class="nav-social-button w-inline-block login-page-link">Log in</a>
                </c:if>
            </div>

            <div class="ordering">
                <a href="basket" class="nav-social-button w-inline-block order-link"> My orders
                    <img src="view/pictures/icons/shopping_basket_unactive.png" alt="" class="nav-social-icon basket-image">
                </a>
            </div>
            
        </div>
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
                <a href="index" aria-current="page" class="nav-link w-nav-link w--current"
                    style="max-width: 940px;">Home</a>
                <a href="menu" class="nav-link w-nav-link" style="max-width: 940px;">Menu</a>
                <a href="reservation.html" class="nav-link w-nav-link" style="max-width: 940px;">Reservation</a>
                <a href="gallery.html" class="nav-link w-nav-link" style="max-width: 940px;">Gallery</a>
                <a href="parties.html" class="nav-link w-nav-link" style="max-width: 940px;">Parties &amp;
                    Events</a>
                <a href="about-us.html" class="nav-link w-nav-link" style="max-width: 940px;">About us</a>
                <a href="blog.html" class="nav-link w-nav-link" style="max-width: 940px;">Blog</a>
                <a href="registration" class="nav-link w-nav-link" style="max-width: 940px;">Registration</a>
                <a href="contact.html" class="nav-link w-nav-link" style="max-width: 940px;">Contact us</a>
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