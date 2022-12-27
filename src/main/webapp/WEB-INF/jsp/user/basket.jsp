<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

<head>
    <title>Menu</title>

    <link href="view/css/common.css" rel="stylesheet" type="text/css">
    <link href="view/css/sidebar.css" rel="stylesheet" type="text/css">
    <link href="view/css/basket.css" rel="stylesheet" type="text/css">

</head>

<body>
    <jsp:include page="../sidebar.jsp"/>

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
                    <h3 class="order-date">
                        <fmt:formatDate type = "both" dateStyle="long" timeStyle="short" value="${order.creationDate}" />
                    </h3>
                    <div class="section-divider-line order-divider"></div>
                    <c:forEach var="basket" items="${order.baskets}">

                        <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6 order-list">
                            <div class="one-dish-info"></div>
                            <div class="menu-image-container">
                                <div style="background-image: url('view/pictures/dish_pictures/${basket.dish.image}');"
                                    class="menu-item-image-box">
                                </div>
                            </div>
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
                                <div class="menu-item-title description order-description">
                                    <c:out value="${basket.dish.description}" />
                                </div>
                            </div>
                            <c:if test="${order.bookingStatus == 'BOOKING'}">
                                <form method="post" action="removeFromOrder" name="confirmOrder">
                                    <input type="hidden" name="orderId" value="${order.id}"> 
                                    <button value="${basket.dish.id}" name="dishId" class="button order-edit-button">
                                        Delete
                                    </button>
                                </form>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
            </div>

           
           <c:out value="${order.bookingStatus}" />



            <c:if test="${order.bookingStatus == 'BOOKING'}">
                <input type="text" name="address" placeholder="Enter your delivery address" maxlength="1024" class="field w-input" value="${order.address}">
                <input type="text" name="phone" placeholder="Enter your phone" maxlength="13" class="field w-input" value="${order.phoneNumber}">
     
                <form method="post" action="confirmOrder" name="confirmOrder">
                    <a href="index" class="button order-confirm-button">Contirue ordering</a>
                    <button value="${order.id}" name="orderId" class="button order-confirm-button">Confirm my orders</button>
                </form>
            </c:if>
            

        </div>
        <%-- </c:if> --%>
        <c:if test="${order eq null}">
            <div class="one-dish-info">Your basket is empty</div>
            <button class="button order-edit-button">
                        Go to menu
            </button>
        </c:if>

        </c:forEach>


        <%-- <div class="container w-container order-container">

            <div class="menu-page-tabs-content w-tab-content">


                <div class="menu-page-tab-pane w-tab-pane w--tab-active order-content">
                    <h3 class="order-date">December 23, 2022</h3>

                    <div class="section-divider-line order-divider"></div>


                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6 order-list">
                        <div class="one-dish-info"></div>
                        <div class="menu-image-container">
                            <div style="background-image: url('view/pictures/dish_pictures/${dish.image}');"
                                class="menu-item-image-box">
                            </div>
                        </div>
                        <div class="menu-item-label menu-item-amount">
                            4
                            <span class="weight-marker"> pcs</span>
                        </div>
                        <div class="menu-item-label menu-item-weight">
                            200
                            <span class="weight-marker"> g</span>
                        </div>
                        <div class="menu-item-label menu-item-price-reload">
                            4.50
                            <span class="price-marker">₴</span>
                        </div>
                        <div class="menu-item-text order-item">
                            <div class="menu-item-title order-title">
                                Black Eyed Andy
                            </div>
                            <div class="menu-item-title description order-description">
                                i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                                i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                                i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                                i i i i i i i i i i i i i i i i i i .
                            </div>
                        </div>

                        <button class="button order-edit-button">
                            Edit
                        </button>
                    </div>




                <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6 order-list">
                    <div class="one-dish-info"></div>
                    <div class="menu-image-container">
                        <div style="background-image: url('view/pictures/dish_pictures/${dish.image}');"
                            class="menu-item-image-box">
                        </div>
                    </div>
                    <div class="menu-item-label menu-item-amount">
                        4
                        <span class="weight-marker"> pcs</span>
                    </div>
                    <div class="menu-item-label menu-item-weight">
                        200
                        <span class="weight-marker"> g</span>
                    </div>
                    <div class="menu-item-label menu-item-price-reload">
                        4.50
                        <span class="price-marker">₴</span>
                    </div>
                    <div class="menu-item-text order-item">
                        <div class="menu-item-title order-title">
                            Black Eyed Andy
                        </div>
                        <div class="menu-item-title description order-description">
                            Lorem ipsum dolor sit amet consectetur
                            adipiscing.
                        </div>
                    </div>
                    <button class="button order-edit-button">
                        Edit
                    </button>
                </div>
            </div>
            <input type="text" name="address" placeholder="Enter your delivery address" maxlength="1024" class="field w-input"  placeholder="Your address" value="Kyiv, Peremohy str.">
            <input type="text" name="phone" placeholder="Enter your phone" maxlength="13" class="field w-input" placeholder="+380961150083" value="+380961150083">

            <button class="button order-confirm-button">
                Contirue ordering
            </button>
            <button class="button order-confirm-button">
                Confirm my orders
            </button>
        </div> --%>




        <div class="container w-container order-container">

            <div class="menu-page-tabs-content w-tab-content">


                <div class="menu-page-tab-pane w-tab-pane w--tab-active order-content">
                    <h3 class="order-date">December 23, 2022</h3>

                    <div class="section-divider-line order-divider"></div>

                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6 order-list">
                        <div class="one-dish-info"></div>
                        <div class="menu-image-container">
                            <div style="background-image: url('view/pictures/dish_pictures/${dish.image}');"
                                class="menu-item-image-box">
                            </div>
                        </div>
                        <div class="menu-item-label menu-item-amount">
                            4
                            <span class="weight-marker"> pcs</span>
                        </div>
                        <div class="menu-item-label menu-item-weight">
                            200
                            <span class="weight-marker"> g</span>
                        </div>
                        <div class="menu-item-label menu-item-price-reload">
                            4.50
                            <span class="price-marker">₴</span>
                        </div>
                        <div class="menu-item-text order-item">
                            <div class="menu-item-title order-title">
                                Black Eyed Andy
                            </div>
                            <div class="menu-item-title description order-description">
                                i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                                i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                                i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                                i i i i i i i i i i i i i i i i i i .
                            </div>
                        </div>
                        <button class="button order-edit-button">
                            Edit
                        </button>
                    </div>
                </div>

                <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6 order-list">
                    <div class="one-dish-info"></div>
                    <div class="menu-image-container">
                        <div style="background-image: url('view/pictures/dish_pictures/${dish.image}');"
                            class="menu-item-image-box">
                        </div>
                    </div>
                    <div class="menu-item-label menu-item-amount">
                        4
                        <span class="weight-marker"> pcs</span>
                    </div>
                    <div class="menu-item-label menu-item-weight">
                        200
                        <span class="weight-marker"> g</span>
                    </div>
                    <div class="menu-item-label menu-item-price-reload">
                        4.50
                        <span class="price-marker">₴</span>
                    </div>
                    <div class="menu-item-text order-item">
                        <div class="menu-item-title order-title">
                            Black Eyed Andy
                        </div>
                        <div class="menu-item-title description order-description">
                            Lorem ipsum dolor sit amet consectetur
                            adipiscing.
                        </div>
                    </div>
                    <button class="button order-edit-button">
                        Edit
                    </button>
                </div>
            </div>

            <div class="section-intro-title subtitle order-status">
                In delivery to address: 
            </div>

        </div>

        <div class="container w-container order-container">




            <div class="menu-page-tabs-content w-tab-content">


                <div class="menu-page-tab-pane w-tab-pane w--tab-active order-content">
                    <h3 class="order-date">December 23, 2022</h3>

                    <div class="section-divider-line order-divider"></div>

                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6 order-list">
                        <div class="one-dish-info"></div>
                        <div class="menu-image-container">
                            <div style="background-image: url('view/pictures/dish_pictures/${dish.image}');"
                                class="menu-item-image-box">
                            </div>
                        </div>
                        <div class="menu-item-label menu-item-amount">
                            4
                            <span class="weight-marker"> pcs</span>
                        </div>
                        <div class="menu-item-label menu-item-weight">
                            200
                            <span class="weight-marker"> g</span>
                        </div>
                        <div class="menu-item-label menu-item-price-reload">
                            4.50
                            <span class="price-marker">₴</span>
                        </div>
                        <div class="menu-item-text order-item">
                            <div class="menu-item-title order-title">
                                Black Eyed Andy
                            </div>
                            <div class="menu-item-title description order-description">
                                i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                                i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                                i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                                i i i i i i i i i i i i i i i i i i .
                            </div>
                        </div>
                        <button class="button order-edit-button">
                            Edit
                        </button>
                    </div>
                </div>

                <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6 order-list">
                    <div class="one-dish-info"></div>
                    <div class="menu-image-container">
                        <div style="background-image: url('view/pictures/dish_pictures/${dish.image}');"
                            class="menu-item-image-box">
                        </div>
                    </div>
                    <div class="menu-item-label menu-item-amount">
                        4
                        <span class="weight-marker"> pcs</span>
                    </div>
                    <div class="menu-item-label menu-item-weight">
                        200
                        <span class="weight-marker"> g</span>
                    </div>
                    <div class="menu-item-label menu-item-price-reload">
                        4.50
                        <span class="price-marker">₴</span>
                    </div>
                    <div class="menu-item-text order-item">
                        <div class="menu-item-title order-title">
                            Black Eyed Andy
                        </div>
                        <div class="menu-item-title description order-description">
                            Lorem ipsum dolor sit amet consectetur
                            adipiscing.
                        </div>
                    </div>
                    <button class="button order-edit-button">
                        Edit
                    </button>
                </div>
            </div>

            <div class="section-intro-title subtitle order-status">
                Delivered to address
            </div>

        </div>


    </div>



    <jsp:include page="../footer.jsp"/>

    <script src="view/js/jquery3.6.1.js" type="text/javascript" crossorigin="anonymous"></script>
    <script src="view/js/webflow.e.js" type="text/javascript"></script>
</body>

</html>