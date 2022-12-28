<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

<head>
    <title>Menu</title>

    <link href="view/css/common.css" rel="stylesheet" type="text/css">
    <link href="view/css/sidebar.css" rel="stylesheet" type="text/css">
    <link href="view/css/manage-orders.css" rel="stylesheet" type="text/css">

</head>

<body>
    <jsp:include page="../../sidebar.jsp"/>

    <div id="top" class="page-header">
        <div class="page-header-overlay orders-header">
            <div class="container w-container orders-container">
                <div class="section-intro-block">
                    <h2 data-ix="fade-in-on-load" class="section-intro-title">My orders</h2>
                    <h2 data-ix="fade-in-on-load-2" class="section-intro-title subtitle">V&amp;H VICTORY</h2>
                    <h2 data-ix="fade-in-on-load-2" class="section-intro-title subtitle smaler">Cafe and Restaurant</h2>
                </div>
            </div>
        </div>
    </div>

    <div class="section menu-page-section wf-section">

        <div class="error-message"><c:if test="${sessionScope.errorMessage}"></c:if></div>

        <c:forEach var="order" items="${sessionScope.orders}">
        <%-- <c:if test="${order ne null}"> --%>
        <div class="container w-container order-container">

            <div class="menu-page-tabs-content w-tab-content">


                <div class="menu-page-tab-pane w-tab-pane w--tab-active order-content">


                    <div class="order-details">
                        <div class="order-title">
                            <div class="order-date">
                                <fmt:formatDate type = "both" dateStyle="long" timeStyle="short" value="${order.creationDate}" />
                            </div>
                            <div class="order-status">Status: 
                                <c:out value="${order.bookingStatus}" />
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
                                <c:out value="${basket.amount}"/>
                                <span class="weight-marker"> pcs</span>
                            </div>
                            <div class="menu-item-label menu-item-weight">
                                <c:out value="${basket.dish.weight}"/>
                                <span class="weight-marker"> g</span>
                            </div>
                            <div class="menu-item-label menu-item-price-reload">
                                <c:out value="${basket.fixedPrice}"/>
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

           


            <c:if test="${order.bookingStatus ne 'COMPLETED'}"> 
                <form method="post" action="confirmOrder" name="confirmOrder">
                    <%-- <a href="index" class="button order-confirm-button">Contirue ordering</a> --%>
                    <button value="${order.id}" name="orderId" class="button order-confirm-button">Set status to '<c:out value="${order.bookingStatus}'" /></button>
                </form>
            </c:if>
            

        </div>

        </c:forEach>


    </div>



    <jsp:include page="../../footer.jsp"/>

    <script src="view/js/jquery3.6.1.js" type="text/javascript" crossorigin="anonymous"></script>
    <script src="view/js/webflow.e.js" type="text/javascript"></script>
</body>

</html>