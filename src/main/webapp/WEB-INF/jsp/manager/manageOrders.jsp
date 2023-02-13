<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="d" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.locale.country}" />
<fmt:setBundle basename="language" />
<!DOCTYPE html>
<html lang="${sessionScope.locale.language}">
<head>
    <title>
        <fmt:message key="manageOrders.manageOrders" />
    </title>    
    <meta content="text/html; charset=UTF-8">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <link href="view/css/common.css" rel="stylesheet" type="text/css">
    <link href="view/css/sidebar.css" rel="stylesheet" type="text/css">
    <link href="view/css/manage-orders.css" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="../sidebar.jsp" />
    <div id="top" class="page-header">
        <div class="page-header-overlay orders-header">
            <div class="container w-container orders-container">
                <div class="section-intro-block">
                    <h2 data-ix="fade-in-on-load" class="section-intro-title">
                        <fmt:message key="manageOrders.manageOrders" />
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
    <div class="order-sort-button-wrapper">
        <a href="manage_orders?sorted=DATE" data-w-tab="DATE" class="button order-sort-button">
            <div>
                <fmt:message key="manageOrders.sortByDate" />
            </div>
        </a>
        <a href="manage_orders?sorted=STATUS" data-w-tab="STATUS" class="button order-sort-button">
            <div>
                <fmt:message key="manageOrders.sortByStatus" />
            </div>
        </a>
    </div>
    <div class="error-message">
        <c:if test="${requestScope.errorMessage}"></c:if>
    </div>
    <c:forEach var="order" items="${requestScope.orders}">
        <div class="section menu-page-section wf-section">
                <div class="container w-container order-container">
                    <div class="menu-page-tabs-content w-tab-content">
                        <div class="menu-page-tab-pane w-tab-pane w--tab-active order-content">
                            <div class="order-details">
                                <div class="order-title">
                                    <div class="order-date">
                                        <d:date value="${order.creationDate}" />
                                    </div>
                                    <div class="order-status">
                                        <fmt:message key="status.message.${order.bookingStatus}" />
                                    </div>
                                </div>
                                <div class="delivery-title">
                                    <div class="delivery-address">
                                        <c:out value="${order.address}" />
                                    </div>
                                    <div class="delivery-phoneNumber">
                                        <c:out value="${order.phoneNumber}" />
                                    </div>
                                </div>
                            </div>
                            <div class="section-divider-line order-divider"></div>
                            <c:forEach var="basket" items="${order.baskets}">
                                <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6 order-list">
                                    <div class="one-dish-info"></div>
                                    <div class="menu-item-label menu-item-amount">
                                        <c:out value="${basket.amount}" />
                                        <span class="weight-marker"> <fmt:message key="manageOrders.piecesMarker" /></span>
                                    </div>
                                    <div class="menu-item-label menu-item-weight">
                                        <c:out value="${basket.dish.weight}" />
                                        <span class="weight-marker"> <fmt:message key="manageOrders.weightMarker" /></span>
                                    </div>
                                    <div class="menu-item-label menu-item-price-reload">
                                        <c:out value="${basket.fixedPrice}" />
                                        <span class="price-marker">₴</span>
                                    </div>
                                    <div class="menu-item-text order-item">
                                        <div class="menu-item-title order-title">
                                            <c:out value="${basket.dish.name}" />
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <c:if test="${(order.bookingStatus ne 'COMPLETED') && (order.bookingStatus ne 'WAITING_PAYMENT')}">
                        <form method="post" action="set_next_status?currentStatus=${order.bookingStatus}">
                            <button value="${order.id}" name="orderId" class="button order-confirm-button">
                                <fmt:message key="status.next.button.${order.bookingStatus}" />
                            </button>
                        </form>
                    </c:if>
                </div>
                <div id="delimiter"></div>
        </div>
    </c:forEach>
    <div class="page-wrapper">
        <div class="pages orders-per-page">
            <fmt:message key="manageOrders.ordersPerPage" />
            <select name="amount" class="orders-per-page-amout" id="recordsPerPage">
                <%-- <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option selected value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option> --%>
                <option value="1" ${"1" == recordsPerPage ? 'selected="selected"' : ''}>1</option>
                <option value="2" ${"2" == recordsPerPage ? 'selected="selected"' : ''}>2</option>
                <option value="3" ${"3" == recordsPerPage ? 'selected="selected"' : ''}>3</option>
                <option value="4" ${"4" == recordsPerPage ? 'selected="selected"' : ''}>4</option>
                <option value="5" ${"5" == recordsPerPage ? 'selected="selected"' : ''}>5</option>
                <option value="6" ${"6" == recordsPerPage ? 'selected="selected"' : ''}>6</option>
            </select>
            <a class="setPerPage button apply-order-per-page-button" href="manage_orders?page=${currentPage}">
                <fmt:message key="manageOrders.apply" />
            </a>
        </div>
        <div class="pages previous-page">
            <c:if test="${currentPage != 1}">
                <a class="setPerPage" href="manage_orders?page=${currentPage - 1}">
                    <fmt:message key="manageOrders.previous" />
                </a>
            </c:if>
        </div>
        <c:forEach begin="1" end="${noOfPages}" var="i">
            <div class="pages page-numbers">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <div>${i}</div>
                    </c:when>
                    <c:otherwise>
                        <div>
                            <a class="setPerPage" href="manage_orders?page=${i}">${i}</a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>
        <div class="pages next-page">
            <c:if test="${currentPage lt noOfPages}">
                <a class="setPerPage" href="manage_orders?page=${currentPage + 1}">
                    <fmt:message key="manageOrders.next" />
                </a>
            </c:if>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
    <script src="view/js/jquery3.6.1.js" type="text/javascript" crossorigin="anonymous"></script>
    <script src="view/js/webflow.e.js" type="text/javascript"></script>
    <script src="view/js/basket.js" type="text/javascript"></script>
</body>
</html>