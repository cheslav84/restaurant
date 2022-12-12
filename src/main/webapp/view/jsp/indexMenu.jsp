<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="image-background-section wf-section">
    <div class="image-background-overlay">
        <div class="container w-container">
            <h2 data-ix="fade-in-on-scroll" class="section-intro-title">Our menu</h2>
            <h2 data-ix="fade-in-on-scroll" class="section-intro-title subtitle">These are our specials:
            </h2>
            <div data-duration-in="300" data-duration-out="100" data-current="Coffee" data-easing="ease"
                class="menu-tabs w-tabs">
                <div class="menu-tabs-menu w-tab-menu">


                    <form method="get" name="menu" data-w-tab="Coffee"
                        class="menu-tab-button w-inline-block w-tab-link w--current">
                        <div>Coffee</div>
                        <input type="submit" name="menuCategory" value="COFFEE" class="menu-hidden-input menu-tab-button ${sessionScope.menuCategory}" />
                    </form>
                    <form method="get" id="lunchMenuButton" data-w-tab="Lunch"
                        class="menu-tab-button w-inline-block w-tab-link">
                        <div>Lunch</div>
                        <input type="submit" name="menuCategory" value="LUNCH" class="menu-hidden-input menu-tab-button ${sessionScope.menuCategory}" />
                    </form>
                    <form method="get" id="specialsMenuButton" data-w-tab="Every Day Specials"
                        class="menu-tab-button w-inline-block w-tab-link">
                        <div>Every Day Specials</div>
                        <input type="submit" name="menuCategory" value="SPECIALS" class="menu-hidden-input menu-tab-button  ${sessionScope.menuCategory}" />
                    </form>

                </div>
                <div class="w-tab-content">

                    <div data-w-tab="Coffee" class="menu-tab-pane w-tab-pane w--tab-active">
                        <div class="menu-white-wrapper w-dyn-list">


                            <div role="list" class="w-clearfix w-dyn-items w-row">


                                <c:forEach var="dish" items="${requestScope.dishes}">
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-image-container">
                                            <div style="background-image: url('view/pictures/dish_pictures/${dish.image}');"
                                                class="menu-item-image-box">
                                            </div>
                                        </div>
                                        <div class="menu-item-text">
                                            <div class="menu-item-title">
                                                <c:out value="${dish.name}" />
                                            </div>
                                            <div class="menu-item-title description">
                                                <c:out value="${dish.description}" />
                                            </div>
                                            <div class="menu-item-title weight">
                                                <c:out value="${dish.weight}" />
                                                <span class="weight-marker"> g</span>
                                            </div>
                                        </div>
                                        <div class="menu-item-price">
                                            <c:out value="${dish.price}" />
                                            <span class="price-marker">₴</span>
                                        </div>
                                        <a href="" class="order-icon-container">
                                            <img src="view/pictures/icons/shopping_basket_active.png" alt="" class="order-icon-img">
                                        </a>
                                    </div>
                                </c:forEach>


                            </div>
                        </div>
                    </div>
                    <div data-w-tab="Lunch" class="menu-tab-pane w-tab-pane">
                        <div class="menu-white-wrapper w-dyn-list">


                            <div role="list" class="w-clearfix w-dyn-items w-row">


                                <c:forEach var="dish" items="${requestScope.dishes}">
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-image-container">
                                            <div style="background-image: url('pictures/dish_pictures/brew-coffee.webp');"
                                                class="menu-item-image-box">
                                            </div>
                                        </div>
                                        <div class="menu-item-text">
                                            <div class="menu-item-title">
                                                <c:out value="${dish.name}" />
                                            </div>
                                            <div class="menu-item-title description">
                                                <c:out value="${dish.description}" />
                                            </div>
                                            <div class="menu-item-title weight">
                                                <c:out value="${dish.weight}" />
                                                <span class="weight-marker"> g</span>
                                            </div>
                                        </div>
                                        <div class="menu-item-price">
                                            <c:out value="${dish.price}" />
                                            <span class="price-marker">₴</span>
                                        </div>
                                        <a href="" class="order-icon-container">
                                            <img src="pictures/icons/Icon-basket.png" alt="" class="order-icon-img">
                                        </a>
                                    </div>
                                </c:forEach>


                            </div>

                        </div>
                    </div>
                    <div data-w-tab="Every Day Specials" class="menu-tab-pane w-tab-pane">
                        <div class="menu-white-wrapper w-dyn-list">


                            <div role="list" class="w-clearfix w-dyn-items w-row">


                                <c:forEach var="dish" items="${requestScope.dishes}">
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-image-container">
                                            <div style="background-image: url('pictures/dish_pictures/brew-coffee.webp');"
                                                class="menu-item-image-box">
                                            </div>
                                        </div>
                                        <div class="menu-item-text">
                                            <div class="menu-item-title">
                                                <c:out value="${dish.name}" />
                                            </div>
                                            <div class="menu-item-title description">
                                                <c:out value="${dish.description}" />
                                            </div>
                                            <div class="menu-item-title weight">
                                                <c:out value="${dish.weight}" />
                                                <span class="weight-marker"> g</span>
                                            </div>
                                        </div>
                                        <div class="menu-item-price">
                                            <c:out value="${dish.price}" />
                                            <span class="price-marker">₴</span>
                                        </div>
                                        <a href="" class="order-icon-container">
                                            <img src="pictures/icons/Icon-basket.png" alt="" class="order-icon-img">
                                        </a>
                                    </div>
                                </c:forEach>





                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <a href="menu.html" class="button">View our complete menu</a>
        </div>
    </div>
</div>