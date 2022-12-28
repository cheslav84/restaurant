<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%-- <%@ taglib prefix="my" tagdir="/WEB-INF/tags"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>



<div data-animation="default" class="navbar-left w-nav" data-easing2="ease" data-easing="ease" data-collapse="small"
    role="banner" data-no-scroll="1" data-duration="400" data-doc-height="1">
    <div class="menu-overlay">

        <div class="languages">
            <fmt:setLocale value="${sessionScope.language}"/>
            <fmt:setBundle basename="menu"/>
            <a href="?locale=EN" class="nav-social-button w-inline-block language-link">ENG</a>
            <a href="?locale=UA" class="nav-social-button w-inline-block language-link">UA</a>
            <%-- <a href="set_language?language=EN" class="nav-social-button w-inline-block language-link">ENG</a>
            <a href="set_language?language=UA" class="nav-social-button w-inline-block language-link">UA</a> --%>
        </div>

        <div class="w-container">
            <a href="index.html" aria-current="page" class="logo-container w-clearfix w-nav-brand w--current">
                <img src="view/pictures/icons/VH_logo_white.png" alt="" class="logo-icon">
                <h1 class="logo-text">V&amp;H VICTORY</h1>
                <h2 class="logo-text subtitle">
                    <fmt:message key="sidebar.cafe"/>&nbsp;&amp; <fmt:message key="sidebar.restaurant"/>
                </h2>
            </a>
            <div class="menu-divider w-hidden-small w-hidden-tiny"></div>
            <div class="menu-button w-nav-button">
                <div class="w-icon-nav-menu"></div>
            </div>

            <nav role="navigation" class="nav-menu w-nav-menu">
                <a href="index" id="index-nav" aria-current="page" class="nav-link w-nav-link w--current"
                    style="max-width: 940px;">
                    <fmt:message key="sidebar.home"/>
                </a>

                <a href="page?path=menu" aria-current="page" class="nav-link w-nav-link" style="max-width: 940px;">
                    <fmt:message key="sidebar.menu"/>
                </a>

                <c:if test="${sessionScope.loggedUser eq null}">
                    <a href="login_page" aria-current="page"  class="nav-link w-nav-link" style="max-width: 940px;">
                    <%-- <a href="page?path=registration"  class="nav-link w-nav-link" style="max-width: 940px;"> --%>
                        <fmt:message key="sidebar.login"/>
                    </a>
                </c:if>

                <c:if test="${sessionScope.loggedUser ne null}">
                    <a href="basket" class="nav-link w-nav-link" style="max-width: 940px;">
                        <fmt:message key="sidebar.basket"/>
                    </a>
                </c:if>


                 <c:if test="${sessionScope.loggedUser ne null}">
                     <c:if test="${sessionScope.loggedUser.role == 'MANAGER'}">
                          <a href="manage_orders" aria-current="page" class="nav-link w-nav-link">
                            <fmt:message key="sidebar.managerPage"/>
                          </a>
                     </c:if>
                     <a href="logout" aria-current="page" class="nav-link w-nav-link">
                        <fmt:message key="sidebar.logout"/>
                     </a>
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