<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale.country}" />
<fmt:setBundle basename="language" />
<!DOCTYPE html>
<html>
<head>
    <title>V&amp;H VICTORY</title>
    <meta content="text/html; charset=UTF-8">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <link href="view/css/common.css" rel="stylesheet" type="text/css">
    <link href="view/css/sidebar.css" rel="stylesheet" type="text/css">
    <link href="view/css/registration.css" rel="stylesheet" type="text/css">
    <link href="view/css/add-dish.css" rel="stylesheet" type="text/css">
</head>

<body>
    <jsp:include page="../sidebar.jsp" />
    <div id="top" class="page-header reservations">
        <div class="page-header-overlay">
            <div class="container w-container">
                <div class="section-intro-block">
                    <h2 data-ix="fade-in-on-load" class="section-intro-title">
                        <fmt:message key="dish.newDish" />
                    </h2>
                    <h2 data-ix="fade-in-on-load-2" class="section-intro-title subtitle">
                        <fmt:message key="index.cafe" />&nbsp;&amp;
                        <fmt:message key="index.restaurant" />
                    </h2>
                </div>
            </div>
        </div>
    </div>
    <div class="section">
        <div class="container w-container">
            <div class="reservation-row w-row"> 
                <div class="intro-title">
                        <fmt:message key="dish.dishData" />
                </div>
                <div class="section-divider-line"></div>

                <form method="post" action="add_dish" id="wf-form-Reservation-Form" name="addDishForm"
                    data-name="Reservation Form" enctype="multipart/form-data">
                    <c:set var="dish" scope="request" value="${sessionScope.currentDish}" />
                    <div class="reservation-image-column w-col w-col-6">
                        <img id="target" data-ix="fade-in-on-load-4" class="reservation-image-block"
                            style="background-image: url('view/pictures/dish_pictures/example-bg.png');" />
                        <input type="file" id="src" name="dishImage" class="upload-picture" />
                    </div>
                    <div data-ix="fade-in-on-load-3" class="contact-us-column form-left w-col w-col-6">
                        <div class="w-form">

                            <input type="text" id="dishName"  name="dishName"  maxlength="24"
                             class="field w-input" placeholder="<fmt:message key="dish.dishName" />"
                             value="${dish.name}" >
                            <textarea id="dishDescription" name="dishDescription"  maxlength="5000" 
                                class="field area w-input"
                                placeholder="<fmt:message key="dish.dishDescription" />"><c:out value="${dish.description}" /></textarea>
                            <div class="w-row">
                                <div class="w-clearfix w-col w-col-6 field-border left-field">
                                    <input type="text" id="dishPrice" name="dishPrice" placeholder="<fmt:message key="dish.dishPrice" />"
                                        maxlength="24" class="field w-input" value="${dish.price}" >
                                </div>
                                <div class="w-clearfix w-col w-col-6 field-border right-field">
                                    <input type="text" id="dishWeight" name="dishWeight" placeholder="<fmt:message key="dish.dishWeight" />"
                                        maxlength="24" class="field w-input" value="${dish.weight}" >
                                </div>
                            </div>
                            <select id="dish-category" name="dishCategory" data-name="Dish category"
                                class="field first-half w-select">
                                <option selected value=""><fmt:message key="dish.dishCategory" /></option>
                                <option value="Coffee"><fmt:message key="dish.coffee" /></option>
                                <option value="Lunch"><fmt:message key="dish.lunch" /></option>
                                <option value="Diner"><fmt:message key="dish.dinner" /></option>
                                <option value="Drinks"><fmt:message key="dish.drinks" /></option>
                            </select>
                            <div class="w-row">
                                <div class="w-clearfix w-col w-col-6">
                                    <div class="w-row field last-half w-select">
                                        <label for="special-ch-box" class="w-col user-age-label"><fmt:message key="dish.special" /></label>
                                        <input type="checkbox" id="special-ch-box" name="specialDish"
                                            class="w-col w-select user-age" value="">
                                    </div>
                                </div>
                                <div class="w-clearfix w-col w-col-6">
                                    <div class="w-row field last-half w-select">
                                        <label for="alcohol-ch-box" class="w-col user-age-label"><fmt:message key="dish.alcohol" /></label>
                                        <input type="checkbox" id="alcohol-ch-box" name="alcoholDish"
                                            class="w-col w-select user-age" value="">
                                    </div>
                                </div>
                            </div>
                            <div class="error-message">
                                <c:out value="${sessionScope.wrongDishFieldMessage}" />
                                <c:out value="${sessionScope.errorMessage}" />
                            </div>
                            <input type="submit" value="<fmt:message key="dish.submit" />" 
                                class="button submit-button w-button">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <jsp:include page="../footer.jsp" />
    <script src="view/js/jquery3.6.1.js" type="text/javascript" crossorigin="anonymous"></script>
    <script src="view/js/webflow.e.js" type="text/javascript"></script>
    <script src="view/js/addDish.js" type="text/javascript"></script>
</body>
</html>