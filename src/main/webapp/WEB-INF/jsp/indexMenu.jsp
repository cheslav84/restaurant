<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="dh" uri="dishes"%>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="language" />
<a name="menu"></a>
<div id="reserv-dish" class="image-background-section wf-section">
    <div class="image-background-overlay">
        <div class="container w-container">
            <h2 data-ix="fade-in-on-scroll" class="section-intro-title">
                <fmt:message key="index.ourMenu" />
            </h2>
            <h2 data-ix="fade-in-on-scroll" class="section-intro-title subtitle">
                <fmt:message key="index.ourSpecials" />
            </h2>
            <div data-duration-in="300" data-duration-out="100" data-current="Coffee" data-easing="ease"
                class="menu-tabs w-tabs">
                <div class="menu-tabs-menu w-tab-menu">
                    <a href="index?menuCategory=COFFEE" data-w-tab="COFFEE" class="menu-tab-button w-inline-block w-tab-link w--current">
                        <div>
                            <fmt:message key="index.coffee" />
                        </div>
                    </a>
                    <a href="index?menuCategory=LUNCH" data-w-tab="LUNCH" class="menu-tab-button w-inline-block w-tab-link w--current">
                        <div>
                            <fmt:message key="index.lunch" />
                        </div>
                    </a>
                    <a href="index?menuCategory=SPECIALS" data-w-tab="SPECIALS" class="menu-tab-button w-inline-block w-tab-link w--current">
                        <div>
                            <fmt:message key="index.everyDaySpecials" />
                        </div>
                    </a>
                </div>
                <div class="w-tab-content">
                    <div data-w-tab="${sessionScope.menuCategory}" class="menu-tab-pane w-tab-pane w--tab-active">
                        <div class="menu-white-wrapper w-dyn-list">
                            <div role="list" class="w-clearfix w-dyn-items w-row">
                                <c:forEach var="dish" items="${requestScope.dishes}">
                                    <div role="listitem"  class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-image-container">
                                            <div style="background-image: url('view/pictures/dish_pictures/${dish.image}');"
                                                class="menu-item-image-box">
                                            </div>
                                        </div>
                                        <div class="menu-item-text">
                                            <div class="menu-item-title">
                                                <dh:dish name="${dish}" />
                                                <%-- <c:out value="${dish.name}" /> --%>
                                            </div>
                                            <div class="menu-item-title description">
                                                <dh:dish description="${dish}" />
                                                <%-- <c:out value="${dish.description}" /> --%>
                                            </div>
                                            <div class="menu-item-title weight">
                                                <dh:dish weight="${dish}" />
                                                <%-- <c:out value="${dish.weight}" /> --%>
                                                <span class="weight-marker"> <fmt:message key="index.weightMarker" /></span>
                                            </div>
                                        </div>
                                        <div class="menu-item-price">
                                            <dh:dish price="${dish}" />
                                            <%-- <c:out value="${dish.price}" /> --%>
                                            <span class="price-marker">₴</span>
                                        </div>
                                        <form method="get" action="show_order_info" name="dishOrder">
                                            <button value="${dish.id}" name="dishId" class="button order-menu-button">
                                                <fmt:message key="index.orderBtn" />
                                            </button>
                                        </form>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="error-message"> 
                        <c:out value="${requestScope.errorMessage}"/>
                    </div>
                </div>
            </div>
            <a href="menu" class="button">
                <fmt:message key="index.viewCompleteMenu" />
            </a>
        </div>
    </div>
</div>