<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta content="text/html; charset=UTF-8">
    <title>Registration</title>
    <meta
        content="Easy Times is a Restaurant website Restaurant, Cafe including the presentation of menus, events, blogging functionality, multiple contact forms and more."
        name="description">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <meta content="Webflow" name="generator">
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
                    <h2 data-ix="fade-in-on-load" class="section-intro-title">Reservations</h2>
                    <h2 data-ix="fade-in-on-load-2" class="section-intro-title subtitle">Easy times
                        Cafe&nbsp;&amp;&nbsp;Restaurant</h2>
                </div>
            </div>
        </div>
    </div>
    <div class="section">
        <div class="container w-container">
            <div class="reservation-row w-row">
                <form method="post" action="add_dish" id="wf-form-Reservation-Form" name="addDishForm"
                    data-name="Reservation Form" enctype="multipart/form-data">
                    <div class="reservation-image-column w-col w-col-6">
                        <img id="target" data-ix="fade-in-on-load-4" class="reservation-image-block"
                            style="background-image: url('view/pictures/dish_pictures/example-bg.png');" />
                        <input type="file" id="src" name="dishImage" class="upload-picture" />
                    </div>
                    <div data-ix="fade-in-on-load-3" class="contact-us-column form-left w-col w-col-6">
                        <div class="w-form">
                            <div class="intro-title">Add dish</div>
                            <div class="section-divider-line"></div>
                            <input type="text" id="dishName" name="dishName" placeholder="Dish name" maxlength="24"
                                class="field w-input">
                            <textarea id="dishDescription" name="dishDescription" placeholder="Enter dish description"
                                maxlength="5000" data-name="Message" required="" class="field area w-input"></textarea>
                            <div class="w-row">
                                <div class="w-clearfix w-col w-col-6 field-border left-field">
                                    <input type="text" id="dishPrice" name="dishPrice" placeholder="Dish price"
                                        maxlength="24" class="field w-input">
                                </div>
                                <div class="w-clearfix w-col w-col-6 field-border right-field">
                                    <input type="text" id="dishWeight" name="dishWeight" placeholder="Dish weight"
                                        maxlength="24" class="field w-input">
                                </div>
                            </div>
                            <select id="dish-category" name="dishCategory" data-name="Dish category"
                                class="field first-half w-select">
                                <option selected value="">Select dish category</option>
                                <option value="Coffee">Coffee</option>
                                <option value="Lunch">Lunch</option>
                                <option value="Diner">Diner</option>
                                <option value="Drinks">Drinks</option>
                            </select>
                            <div class="w-row">
                                <div class="w-clearfix w-col w-col-6">
                                    <div class="w-row field last-half w-select">
                                        <label for="special-ch-box" class="w-col user-age-label">Special</label>
                                        <input type="checkbox" id="special-ch-box" name="specialDish"
                                            class="w-col w-select user-age" value="">
                                    </div>
                                </div>
                                <div class="w-clearfix w-col w-col-6">
                                    <div class="w-row field last-half w-select">
                                        <label for="alcohol-ch-box" class="w-col user-age-label">Alcohol</label>
                                        <input type="checkbox" id="alcohol-ch-box" name="alcoholDish"
                                            class="w-col w-select user-age" value="">
                                    </div>
                                </div>
                            </div>
                            <input type="submit" value="Submit" data-wait="Please wait..."
                                class="button submit-button w-button">
                            <div class="error-bg w-form-fail">
                                <p class="error-text">Oops! Something went wrong while submitting the
                                    form</p>
                            </div>
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