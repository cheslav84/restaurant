<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

        <div class="container w-container">



            <div class="menu-page-tabs-content w-tab-content">


                <div class="menu-page-tab-pane w-tab-pane w--tab-active order-content">
                    <h3 class="order-date">December 23, 2022</h3>

                    <!-- <div class="intro-title order-date">December 23, 2022</div> -->
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
                            <!-- <c:out value="${dish.weight}" /> -->
                            200
                            <span class="weight-marker"> g</span>
                        </div>
                        <div class="menu-item-label menu-item-price-reload">
                            <!-- <c:out value="${dish.price}" /> -->
                            4.50
                            <span class="price-marker">₴</span>
                        </div>
                        <div class="menu-item-text order-item">
                            <div class="menu-item-title order-title">
                                <!-- <c:out value="${dish.name}" /> -->
                                Black Eyed Andy
                            </div>
                            <div class="menu-item-title description order-description">
                                <!-- <c:out value="${dish.description}" /> -->
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
                        <!-- <c:out value="${dish.weight}" /> -->
                        200
                        <span class="weight-marker"> g</span>
                    </div>
                    <div class="menu-item-label menu-item-price-reload">
                        <!-- <c:out value="${dish.price}" /> -->
                        4.50
                        <span class="price-marker">₴</span>
                    </div>
                    <div class="menu-item-text order-item">
                        <div class="menu-item-title order-title">
                            <!-- <c:out value="${dish.name}" /> -->
                            Black Eyed Andy
                        </div>
                        <div class="menu-item-title description order-description">
                            <!-- <c:out value="${dish.description}" /> -->
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
        </div>

        <div class="container w-container">




            <div class="menu-page-tabs-content w-tab-content">


                <div class="menu-page-tab-pane w-tab-pane w--tab-active order-content">
                    <h3 class="order-date">December 23, 2022</h3>

                    <!-- <div class="intro-title order-date">December 23, 2022</div> -->
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
                            <!-- <c:out value="${dish.weight}" /> -->
                            200
                            <span class="weight-marker"> g</span>
                        </div>
                        <div class="menu-item-label menu-item-price-reload">
                            <!-- <c:out value="${dish.price}" /> -->
                            4.50
                            <span class="price-marker">₴</span>
                        </div>
                        <div class="menu-item-text order-item">
                            <div class="menu-item-title order-title">
                                <!-- <c:out value="${dish.name}" /> -->
                                Black Eyed Andy
                            </div>
                            <div class="menu-item-title description order-description">
                                <!-- <c:out value="${dish.description}" /> -->
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
                        <!-- <c:out value="${dish.weight}" /> -->
                        200
                        <span class="weight-marker"> g</span>
                    </div>
                    <div class="menu-item-label menu-item-price-reload">
                        <!-- <c:out value="${dish.price}" /> -->
                        4.50
                        <span class="price-marker">₴</span>
                    </div>
                    <div class="menu-item-text order-item">
                        <div class="menu-item-title order-title">
                            <!-- <c:out value="${dish.name}" /> -->
                            Black Eyed Andy
                        </div>
                        <div class="menu-item-title description order-description">
                            <!-- <c:out value="${dish.description}" /> -->
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

        <div class="container w-container">




            <div class="menu-page-tabs-content w-tab-content">


                <div class="menu-page-tab-pane w-tab-pane w--tab-active order-content">
                    <h3 class="order-date">December 23, 2022</h3>

                    <!-- <div class="intro-title order-date">December 23, 2022</div> -->
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
                            <!-- <c:out value="${dish.weight}" /> -->
                            200
                            <span class="weight-marker"> g</span>
                        </div>
                        <div class="menu-item-label menu-item-price-reload">
                            <!-- <c:out value="${dish.price}" /> -->
                            4.50
                            <span class="price-marker">₴</span>
                        </div>
                        <div class="menu-item-text order-item">
                            <div class="menu-item-title order-title">
                                <!-- <c:out value="${dish.name}" /> -->
                                Black Eyed Andy
                            </div>
                            <div class="menu-item-title description order-description">
                                <!-- <c:out value="${dish.description}" /> -->
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
                        <!-- <c:out value="${dish.weight}" /> -->
                        200
                        <span class="weight-marker"> g</span>
                    </div>
                    <div class="menu-item-label menu-item-price-reload">
                        <!-- <c:out value="${dish.price}" /> -->
                        4.50
                        <span class="price-marker">₴</span>
                    </div>
                    <div class="menu-item-text order-item">
                        <div class="menu-item-title order-title">
                            <!-- <c:out value="${dish.name}" /> -->
                            Black Eyed Andy
                        </div>
                        <div class="menu-item-title description order-description">
                            <!-- <c:out value="${dish.description}" /> -->
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