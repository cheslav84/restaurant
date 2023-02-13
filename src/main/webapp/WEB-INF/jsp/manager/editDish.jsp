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
                                <fmt:message key="dish.dishName" />
                            </div>
                            <input type="text" id="dishName" name="dishName" 
                            value="<c:out value="${dish.name}" />"
                            maxlength="24" class="field w-input">
                            <div class="dish-item-label dish-item-description">
                               <fmt:message key="dish.dishDescription" />
                            </div>
                            <textarea id="dishDescription" name="dishDescription" 
                            maxlength="5000" data-name="Message" required="" class="field area w-input"><c:out value="${dish.description}" /></textarea>
                            <div class="w-row">
                                <div class="dish-item-label dish-item-price">
                                    <fmt:message key="dish.dishPrice" />
                                </div>
                                <div class="w-clearfix w-col w-col-6 field-border left-field">
                                    <input type="text" id="dishPrice" name="dishPrice"
                                    value="<c:out value="${dish.price}" />"
                                    maxlength="24" class="field w-input">
                                </div>
                                <div class="dish-item-label dish-item-weight">
                                    <fmt:message key="dish.dishWeight" />  
                                </div>
                                <div class="w-clearfix w-col w-col-6 field-border right-field">
                                    <input type="text" id="dishWeight" name="dishWeight"  
                                    value="<c:out value="${dish.weight}" />"
                                    maxlength="24" class="field w-input">
                                </div>
                            </div>
                            <div class="dish-item-label dish-item-amount">
                                <fmt:message key="dish.amount" />
                            </div>
                            <input type="text" id="dishAmount" name="dishAmount"
                            value="<c:out value="${dish.amount}" />"
                            class="field w-input">
                            <select id="dish-category" name="dishCategory" data-name="Dish category" value="<c:out value="${dish.category}" />"
                                class="field first-half w-select category">
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
                                        class="w-col w-select user-age" >
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
                            <input type="submit" value="Save" data-wait="Please wait..."
                                class="button submit-button w-button">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</c:if>