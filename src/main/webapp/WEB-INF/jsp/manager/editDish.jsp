<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale.country}" />
<fmt:setBundle basename="language" />
<c:if test="${sessionScope.currentDish ne null}">
    <c:set var="dish" scope="request" value="${sessionScope.currentDish}" />
    <div id="hide-order-info" class="order-info hidden ${requestScope.showDishInfo} ">
        <div class="order-info-container">
            <div class="order-info-box">
                <div class="one-dish-info">
                    <img src="view/pictures/icons/close-icon.png" alt="Close icon" id="close-cross-button"
                        class="close-icon">
                </div>
                <form method="post" action="edit_dish">
                    <div data-ix="fade-in-on-load-3" class="contact-us-column form-left w-col">
                        <div class="w-form">
                            <div class="dish-item-label dish-item-name">
                                Dish name
                            </div>
                            <input type="text" id="dishName" name="dishName" 
                            value="<c:out value="${dish.name}" />"
                            maxlength="24" class="field w-input">
                            <div class="dish-item-label dish-item-description">
                                Dish description
                            </div>
                            <textarea id="dishDescription" name="dishDescription" 
                            placeholder="Enter dish description"
                            maxlength="5000" data-name="Message" required="" class="field area w-input"><c:out value="${dish.description}" /></textarea>
                            <div class="w-row">
                                <div class="dish-item-label dish-item-price">
                                    Price
                                </div>
                                <div class="w-clearfix w-col w-col-6 field-border left-field">
                                    <input type="text" id="dishPrice" name="dishPrice"
                                    value="<c:out value="${dish.price}" />"
                                    maxlength="24" class="field w-input">
                                </div>
                                <div class="dish-item-label dish-item-weight">
                                    Weight  
                                </div>
                                <div class="w-clearfix w-col w-col-6 field-border right-field">
                                    <input type="text" id="dishWeight" name="dishWeight"  
                                    value="<c:out value="${dish.weight}" />"
                                    maxlength="24" class="field w-input">
                                </div>
                            </div>
                            <div class="dish-item-label dish-item-amount">
                                Amount in menu
                            </div>
                            <input type="text" id="dishAmount" name="dishAmount"
                            value="<c:out value="${dish.amount}" />"
                            class="field w-input">
                            <select id="dish-category" name="dishCategory" data-name="Dish category"
                                class="field first-half w-select category">
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
                                        class="w-col w-select user-age" >
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
                            <input type="submit" value="Save" data-wait="Please wait..."
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
</c:if>