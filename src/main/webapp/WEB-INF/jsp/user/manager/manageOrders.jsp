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
                <fmt:setLocale value="${sessionScope.language}" />
                <fmt:setBundle basename="menu" />
                <jsp:include page="../../sidebar.jsp" />
            
                <div id="top" class="page-header">
                    <div class="page-header-overlay orders-header">
                        <div class="container w-container orders-container">
                            <div class="section-intro-block">
                                <h2 data-ix="fade-in-on-load" class="section-intro-title">My orders</h2>
                                <h2 data-ix="fade-in-on-load-2" class="section-intro-title subtitle">V&amp;H VICTORY
                                </h2>
                                <h2 data-ix="fade-in-on-load-2" class="section-intro-title subtitle smaler">Cafe and
                                    Restaurant</h2>
                            </div>
                        </div>
                    </div>
                </div>
            
            
            
                <div class="error-message">
                    <c:if test="${requestScope.errorMessage}"></c:if>
                </div>
            
                <c:forEach var="order" items="${requestScope.orders}">
            
                    <div class="section menu-page-section wf-section">
                        <%-- <c:if test="${order ne null}"> --%>
                            <div class="container w-container order-container">
            
                                <div class="menu-page-tabs-content w-tab-content">
            
            
                                    <div class="menu-page-tab-pane w-tab-pane w--tab-active order-content">
            
            
                                        <div class="order-details">
                                            <div class="order-title">
                                                <div class="order-date">
                                                    <fmt:formatDate type="both" dateStyle="long" timeStyle="short"
                                                        value="${order.creationDate}" />
                                                </div>
                                                <div class="order-status">
                                                    <%-- Status: --%>
                                                        <fmt:message key="status.message.${order.bookingStatus}" />
                                                        <%-- <c:out value="${order.bookingStatus}" /> --%>
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
                                                    <span class="weight-marker"> pcs</span>
                                                </div>
                                                <div class="menu-item-label menu-item-weight">
                                                    <c:out value="${basket.dish.weight}" />
                                                    <span class="weight-marker"> g</span>
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
            
                                    <%-- <c:if test="${order.bookingStatus != 'WAITING_PAYMENT'}"> --%>
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
            
            
             <%-- <div > --%>
                <div class="page-wrapper">
                    
            
            
                <div class="pages previous-page">
                    <c:if test="${currentPage != 1}">
                        <td><a href="manage_orders?page=${currentPage - 1}">Previous</a></td>
                        </c:if>
            
                </div>
                
                        <%-- <table border="1" cellpadding="5" cellspacing="5">
                            <tr> --%>
                                <c:forEach begin="1" end="${noOfPages}" var="i">
                <div class="pages page-numbers">
                                    <c:choose>
                                        <c:when test="${currentPage eq i}">
                                            <div>${i}</div>
                                        </c:when>
                                        <c:otherwise>
                                            <div><a href="manage_orders?page=${i}">${i}</a></div>
                                        </c:otherwise>
                                    </c:choose>
                </div>
                                </c:forEach>
                            <%-- </tr>
                        </table> --%>
              
                <div class="pages next-page">
                        <c:if test="${currentPage lt noOfPages}">
                            <td><a href="manage_orders?page=${currentPage + 1}">Next</a></td>
                        </c:if>
            
                </div>
            
    </div>
               <%-- </div> --%>
                        <jsp:include page="../../footer.jsp" />
            
                        <script src="view/js/jquery3.6.1.js" type="text/javascript" crossorigin="anonymous"></script>
                        <script src="view/js/webflow.e.js" type="text/javascript"></script>
                        <script src="view/js/basket.js" type="text/javascript"></script>
    </body>
</html>