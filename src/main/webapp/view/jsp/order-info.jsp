<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 
<c:if test="${sessionScope.currentDish ne null}">

<c:set var = "dish" scope = "request" value = "${sessionScope.currentDish}"/>
    <div id="hide-order-info" class="order-info hidden ${requestScope.showDishInfo} ">
        <div class="order-info-container">
            <div class="order-info-box">


                <div class="one-dish-info">
                    <img src="view/pictures/icons/close-icon.png" alt="Close icon" id="close-cross-button" class="close-icon">

                    <%-- <div class="close">
                    </div> --%>

                    <div class="menu-item-label menu-item-weight">

                        <c:out value="${dish.weight}" />
                        <%-- 200 --%>
                        <span class="weight-marker"> g</span>
                    </div>

                    <div class="menu-item-label menu-item-price-reload">
                        <c:out value="${dish.price}" />
                        <%-- 4.50 --%>
                        <span class="price-marker">₴</span>
                    </div>

                    <%-- <c:if test="${dish.special}"> 
                        <div class="menu-item-label menu-item-special">
                            SPECIAL
                        </div>
                    </c:if> --%>

                </div>
                    <div class="menu-item-text ordering">
                        <div class="menu-item-title ordering">
                            <c:out value="${dish.name}" />
                            <%-- Black Eyed Andy --%>
                        </div>
                        <div class="menu-item-title description ordering">
                             <c:out value="${dish.description}" />
                            <%-- i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                            i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                            i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                            i i i i i i i i i i i i i i i i i i . --%>
                        </div>

                    </div>

                <%-- <div class="order-buttons"> --%>
                    <form method="post" action="addToOrder">

                        <c:if test="${sessionScope.order eq null}">
                            <div class="address-info">
                                <label for="address" class="order-label">Delivery address:</label>
                                <input type="text" id="address" name="deliveryAddress" placeholder="Enter your delivery address" minlength="13" maxlength="100" class="field w-input order-field"  placeholder="Address" value="${sessionScope.deliveryAddress}">
                            </div>

                            <div class="phone-info">
                                <label for="phone" class="order-label">Cell phone:</label>
                                <input type="text" id="phone" name="deliveryPhone" placeholder="Enter your cell phone" minlength="8" maxlength="13" class="field w-input order-field" placeholder="Phone number" value="${sessionScope.deliveryPhone}">
                            </div>
                        </c:if>

                        <div class="error-message" >
                            <c:out value="${sessionScope.errorMessage}" />
                        </div>   

                        <div class="button order-button button-amount">
                            <div class="amount-button-label">amount</div>
                            <input type="text" name="amount" class="dishes-amout"/>
                        </div>
         

                        <%-- <input type="hidden" name="dishId" value="${dish.id}" /> --%>

                            <%-- <input type="submit" name="continue" value="continue" data-wait="Please wait..." class="button order-button">
                            <input type="submit" name="confirm"  value="confirm" data-wait="Please wait..." class="button order-button"> --%>

                        <button name="continue" value="continue" class="button order-button">continue ordering</button>
                        <button class="button order-button">order this dish</button>
                    </form>
                <%-- </div> --%>


            </div>
        </div>
    </div>
</c:if>

