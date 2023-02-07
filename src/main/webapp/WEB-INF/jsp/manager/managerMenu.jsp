<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale.country}" />
<fmt:setBundle basename="language" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            <fmt:message key="menu.menu" />
        </title>
        <meta content="width=device-width, initial-scale=1" name="viewport">
        <link href="view/css/common.css" rel="stylesheet" type="text/css">
        <link href="view/css/sidebar.css" rel="stylesheet" type="text/css">
        <link href="view/css/menu.css" rel="stylesheet" type="text/css">
        <link href="view/css/order-info.css" rel="stylesheet" type="text/css">
        <link href="view/css/edit-dish.css" rel="stylesheet" type="text/css">
    </head>
    <body>
        <jsp:include page="../sidebar.jsp"/>
        <div id="top" class="page-header wf-section">
            <div class="page-header-overlay menu">
                <div class="container w-container">
                    <div class="section-intro-block">
                        <h2 data-ix="fade-in-on-load" class="section-intro-title">
                            <fmt:message key="menu.ourMenu" />
                        </h2>
                        <h2 data-ix="fade-in-on-load-2" class="section-intro-title subtitle">
                            <fmt:message key="menu.selectMenu" />
                        </h2>
                    </div>
                </div>
            </div>
        </div>
        <div class="section menu-page-section wf-section">
            <div class="container w-container">
                <div data-duration-in="300" data-duration-out="100" data-current="Coffee" data-easing="ease"
                    class="menu-page-tabs w-tabs">
                    <div class="menu-page-tabs-menu w-tab-menu">
                        <form id="sort-menu-by-form" href="menu?menuCategory=ALL" data-w-tab="ALL"
                            data-ix="fade-in-on-load-7" class="menu-page-tab-button w-inline-block w-clearfix w-tab-link ">
                            <img src="view/pictures/icons/Icon-plate-white.png" alt="" class="menu-tab-icon">
                            <div class="menu-page-tab-title">
                                <fmt:message key="menu.all" />
                            </div>
                            <div class="menu-page-tab-title subtitle">
                                <fmt:message key="menu.sortBy" />
                            </div>
                            <select id="sort-menu-by-selector" name="menuSortingOption"
                                class="menu-page-tab-title subtitle sort-menu-by" value="${sessionScope.sortParameter}">
                                <fmt:message key="menu.sortBy" />
                                <option value="Name" ${"Name" == sessionScope.menuSortingOption ? 'selected="selected"' : ''}>
                                    <fmt:message key="menu.sortByName" />
                                </option>
                                <option value="Price" ${"Price" == sessionScope.menuSortingOption ? 'selected="selected"' : ''}>
                                    <fmt:message key="menu.sortByPrice" />
                                </option>
                                <option value="Category" ${"Category" == sessionScope.menuSortingOption ? 'selected="selected"' : ''}>
                                    <fmt:message key="menu.sortByCategory" />
                                </option>
                            </select>
                            <%-- <select id="sort-menu-by-selector" name="menuSortingOption"
                                class="menu-page-tab-title subtitle sort-menu-by">
                                <fmt:message key="menu.sortBy" />
                                <option selected value="Name" selected>
                                    <fmt:message key="menu.sortByName" />
                                </option>
                                <option value="Price">
                                    <fmt:message key="menu.sortByPrice" />
                                </option>
                                <option value="Category">
                                    <fmt:message key="menu.sortByCategory" />
                                </option>
                            </select> --%>
                            <input type="submit" value="ALL" name="menuCategory" data-w-tab="ALL"
                                class="menu-hidden-input menu-tab-button transparent-button" />
                        </form>
                        <a href="menu?menuCategory=COFFEE" data-w-tab="COFFEE" data-ix="fade-in-on-load-3"
                            class="menu-page-tab-button w-inline-block w-clearfix w-tab-link w--current">
                            <img src="view/pictures/icons/Icon-coffee.png" alt="" class="menu-tab-icon">
                            <div class="menu-page-tab-title">
                                <fmt:message key="menu.coffee" />
                            </div>
                            <div class="menu-page-tab-title subtitle">
                                <fmt:message key="menu.fromJavaIsland" />
                            </div>
                        </a>
                        <a href="menu?menuCategory=LUNCH" data-w-tab="LUNCH" data-ix="fade-in-on-load-4"
                            class="menu-page-tab-button w-inline-block w-clearfix w-tab-link">
                            <img src="view/pictures/icons/Icon-bread.png" alt="" class="menu-tab-icon">
                            <div class="menu-page-tab-title">
                                <fmt:message key="menu.lunch" />
                            </div>
                            <div class="menu-page-tab-title subtitle">
                                <fmt:message key="menu.willMakeDay" />
                            </div>
                        </a>
                        <a href="menu?menuCategory=DINER" data-w-tab="DINER" data-ix="fade-in-on-load-5"
                            class="menu-page-tab-button w-inline-block w-clearfix w-tab-link">
                            <img src="view/pictures/icons/Icon-diner.png" alt="" class="menu-tab-icon">
                            <div class="menu-page-tab-title">
                                <fmt:message key="menu.diner" />
                            </div>
                            <div class="menu-page-tab-title subtitle">
                                <fmt:message key="menu.fillBetter" />
                            </div>
                        </a>
                        <a href="menu?menuCategory=DRINKS" data-w-tab="DRINKS" data-ix="fade-in-on-load-6"
                            class="menu-page-tab-button w-inline-block w-clearfix w-tab-link">
                            <img src="view/pictures/icons/Icon-beer.png" alt="" class="menu-tab-icon">
                            <div class="menu-page-tab-title">
                                <fmt:message key="menu.drinks" />
                            </div>
                            <div class="menu-page-tab-title subtitle">
                                <fmt:message key="menu.takeItEasy" />
                            </div>
                        </a>
                    </div>
                    <div class="w-tab-content">
                        <div data-w-tab="${sessionScope.menuCategory}" class="menu-tab-pane w-tab-pane w--tab-active">
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
                                            <span class="weight-marker"> <fmt:message key="menu.weightMarker" /></span>
                                        </div>
                                    </div>
                                    <div class="menu-item-price">
                                        <c:out value="${dish.price}" />
                                        <span class="price-marker">₴</span>
                                    </div>
                                    <%-- <div class="menu-item-amount">
                                        <c:out value="${dish.amount}" />
                                        <span class="price-marker">₴</span>
                                    </div> --%>
                                    <form method="get" action="show_dish_info" name="dishOrder">
                                        <button value="${dish.id}" name="dishId" class="button order-menu-button">
                                            <fmt:message key="menu.editDishBtn" />
                                        </button>
                                    </form>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="error-message">
                            <c:out value="${requestScope.menuMessage}" />
                            <c:out value="${requestScope.errorMessage}" />
                        </div>
                    </div>
                    <c:if test="${requestScope.dishesSpecials ne null}">
                        <div class="image-background-section wf-section">
                            <div class="image-background-overlay">
                                <div class="container w-container">
                                    <h2 data-ix="fade-in-on-scroll" class="section-intro-title">
                                        <fmt:message key="menu.everTried" />
                                    </h2>
                                    <h2 data-ix="fade-in-on-scroll" class="section-intro-title subtitle">
                                        <fmt:message key="menu.theseOurSpecials" />
                                    </h2>
                                    <div class="section-divider-line"></div>
                                    <div class="menu-white-wrapper w-dyn-list"></div>
                                    <div role="list" class="w-clearfix w-dyn-items w-row">
                                        <c:forEach var="dishSpecial" items="${requestScope.dishesSpecials}">
                                            <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                                <div class="menu-image-container">
                                                    <div style="background-image: url('view/pictures/dish_pictures/${dishSpecial.image}');"
                                                        class="menu-item-image-box">
                                                    </div>
                                                </div>
                                                <div class="menu-item-text">
                                                    <div class="menu-item-title">
                                                        <c:out value="${dishSpecial.name}" />
                                                    </div>
                                                    <div class="menu-item-title description">
                                                        <c:out value="${dishSpecial.description}" />
                                                    </div>
                                                    <div class="menu-item-title weight">
                                                        <c:out value="${dishSpecial.weight}" />
                                                        <span class="weight-marker"> g</span>
                                                    </div>
                                                </div>
                                                <div class="menu-item-price">
                                                    <c:out value="${dishSpecial.price}" />
                                                    <span class="price-marker">₴</span>
                                                </div>
                                                <%-- <c:if test="${sessionScope.loggedUser.role == 'MANAGER'}"> --%>
                                                    <form method="get" action="show_dish_info" name="dishOrder">
                                                        <button value="${dishSpecial.id}" name="dishId" class="button order-menu-button">
                                                            <fmt:message key="menu.editDishBtn" />
                                                        </button>
                                                    </form>
                                                <%-- </c:if> --%>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <%-- <c:if test="${sessionScope.loggedUser ne null}"> --%>
            <jsp:include page="editDish.jsp" />
        <%-- </c:if> --%>
        <jsp:include page="../footer.jsp"/>
        <script src="view/js/jquery3.6.1.js" type="text/javascript" crossorigin="anonymous"></script>
        <script src="view/js/webflow.e.js" type="text/javascript"></script>
        <script src="view/js/menu.js" type="text/javascript"></script>
        <script src="view/js/order-info.js" type="text/javascript"></script>
    </body>
</html>