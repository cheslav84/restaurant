<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="d" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.locale.country}" />
<fmt:setBundle basename="language" />
<!DOCTYPE html>
<html lang="${sessionScope.language}">
<head>
    <meta content="text/html; charset=UTF-8">
    <title>
        <fmt:message key="basket.myOrders" />
    </title>
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <link href="view/css/common.css" rel="stylesheet" type="text/css">
    <link href="view/css/sidebar.css" rel="stylesheet" type="text/css">
    <link href="view/css/basket.css" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="../sidebar.jsp" />
    <div id="top" class="page-header">
        <div class="page-header-overlay orders-header">
            <div class="container w-container orders-container">
                <div class="section-intro-block">
                    <h2 data-ix="fade-in-on-load" class="section-intro-title">
                        <fmt:message key="basket.myOrders" />
                    </h2>
                    <h2 data-ix="fade-in-on-load-2" class="section-intro-title subtitle">V&amp;H VICTORY</h2>
                    <h2 data-ix="fade-in-on-load-2" class="section-intro-title subtitle smaler">
                        <fmt:message key="index.cafe" />&nbsp;&amp;
                        <fmt:message key="index.restaurant" />
                    </h2>
                </div>
            </div>
        </div>
    </div>
    <div class="section error-message">
        ${sessionScope.errorMessage}
    </div>
    <c:if test="${sessionScope.ordersAndPrices eq null}">
        <jsp:include page="about-us.jsp" />
    </c:if>
    <c:forEach var="orderAndPrice" items="${sessionScope.ordersAndPrices}">
        <div id="order-item" class="section menu-page-section wf-section">
                <div class="container w-container order-container">
                    <div class="menu-page-tabs-content w-tab-content">
                        <div class="menu-page-tab-pane w-tab-pane w--tab-active order-content">
                            <h3 class="order-date">
                                <d:date value="${orderAndPrice.key.creationDate}" />
                                <hr>
                                <fmt:formatDate type="both" dateStyle="long" timeStyle="short"
                                    value="${orderAndPrice.key.creationDate}" />
                            </h3>
                            <div class="section-divider-line order-divider"></div>
                            <c:forEach var="basket" items="${orderAndPrice.key.baskets}">
                                <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6 order-list">
                                    <div class="one-dish-info"></div>
                                    <div class="menu-image-container">
                                        <div style="background-image: url('view/pictures/dish_pictures/${basket.dish.image}');"
                                            class="menu-item-image-box">
                                        </div>
                                    </div>
                                    <div class="menu-item-label menu-item-amount">
                                        <c:out value="${basket.amount}" />
                                        <span class="weight-marker"> <fmt:message key="basket.piecesMarker" /></span>
                                    </div>
                                    <div class="menu-item-label menu-item-weight">
                                        <c:out value="${basket.dish.weight}" />
                                        <span class="weight-marker"> <fmt:message key="basket.weightMarker" /></span>
                                    </div>
                                    <div class="menu-item-label menu-item-price-reload">
                                        <c:out value="${basket.fixedPrice}" />
                                        <span class="price-marker">₴</span>
                                    </div>
                                    <div class="menu-item-text order-item">
                                        <div class="menu-item-title order-title">
                                            <c:out value="${basket.dish.name}" />
                                        </div>
                                        <div class="menu-item-title description order-description">
                                            <c:out value="${basket.dish.description}" />
                                        </div>
                                    </div>
                                    <c:if test="${orderAndPrice.key.bookingStatus == 'BOOKING'}">
                                        <form method="post" action="remove_from_order">
                                            <input type="hidden" name="orderId" value="${orderAndPrice.key.id}">
                                            <button value="${basket.dish.id}" name="dishId"
                                                class="button order-edit-button">
                                                <fmt:message key="basket.deleteBtn" />
                                            </button>
                                        </form>
                                    </c:if>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <c:if test="${orderAndPrice.key.bookingStatus ne 'BOOKING'}">
                        <div class="section-intro-title subtitle order-status">
                            <fmt:message key="status.message.${orderAndPrice.key.bookingStatus}" />
                        </div>
                    </c:if>
                    <c:if test="${orderAndPrice.key.bookingStatus == 'BOOKING'}">
                        <div class="total-price">
                            <fmt:message key="basket.totalPrice" />
                            <c:out value="${orderAndPrice.value}" />
                            <span class="price-marker"> ₴</span>
                        </div>
                        <input type="text" name="address" placeholder="Enter your delivery address" maxlength="1024"
                            class="field w-input" value="${orderAndPrice.key.address}">
                        <input type="text" name="phone" placeholder="Enter your phone" maxlength="13"
                            class="field w-input" value="${orderAndPrice.key.phoneNumber}">
                        <form method="post" action="set_next_status?currentStatus=${orderAndPrice.key.bookingStatus}"
                            name="NEW">
                            <a href="menu" class="button order-confirm-button">
                                <fmt:message key="basket.continueOrdering" />
                            </a>
                            <button value="${orderAndPrice.key.id}" name="orderId"
                                class="button order-confirm-button">
                                <fmt:message key="basket.confirmOrders" />
                            </button>
                        </form>
                    </c:if>
                    <c:if test="${orderAndPrice.key.bookingStatus == 'WAITING_PAYMENT'}">
                        <div class="total-price waiting-payment">
                            <fmt:message key="basket.totalPrice" />
                            <c:out value="${orderAndPrice.value}" />
                            <span class="price-marker"> ₴</span>
                        </div>
                        <div class="order-status">
                            <form method="post" action="set_next_status?currentStatus=${orderAndPrice.key.bookingStatus}">
                                <button value="${orderAndPrice.key.id}" name="orderId"
                                            class="button order-confirm-button">
                                    <fmt:message key="basket.payForOrder" />
                                </button>
                            </form>
                        </div>
                    </c:if>
                </div>
                <c:if test="${orderAndPrice.key eq null}">
                    <div class="one-dish-info">
                        <fmt:message key="basket.emptyBasket" />
                    </div>
                    <a href="menu" class="button order-edit-button">
                        <fmt:message key="basket.goToMenu" />
                    </a>
                </c:if>
                <div id="delimiter"></div>
        </div>
    </c:forEach>
    <jsp:include page="../footer.jsp" />
    <script src="view/js/jquery3.6.1.js" type="text/javascript" crossorigin="anonymous"></script>
    <script src="view/js/webflow.e.js" type="text/javascript"></script>
    <script src="view/js/basket.js" type="text/javascript"></script>
</body>
</html>