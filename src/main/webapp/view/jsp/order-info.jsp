<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 
    <div id="hide-order-info" class="order-info hidden">
        <div class="order-info-container">
            <div class="order-info-box">


                <div class="one-dish-info">
                    <img src="view/pictures/icons/close-icon.png" alt="Close icon" id="close-cross-button" class="colse-icon">

                    <%-- <div class="colse">
                    </div> --%>

                    <div class="menu-item-label menu-item-weight">
                        200
                        <span class="weight-marker"> g</span>
                    </div>

                    <div class="menu-item-label menu-item-price-reload">
                        4.50
                        <span class="price-marker">₴</span>
                    </div>

                    <div class="menu-item-label menu-item-special">
                        SPECIAL
                    </div>

                </div>
                    <div class="menu-item-text ordering">
                        <div class="menu-item-title ordering">
                            Black Eyed Andy
                        </div>
                        <div class="menu-item-title description ordering">
                            i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                            i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                            i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                            i i i i i i i i i i i i i i i i i i .
                        </div>

                    </div>

                <div class="order-buttons">
                    <form>
                        <div class="button order-button button-amount">
                            <div class="amount-button-label">amount</div>
                            <input type="text" name="dishes-amout" class="dishes-amout"/>
                        </div>
                        <button class="button order-button">continue ordering</button>
                        <button class="button order-button">confirm my orders</button>
                    </form>
                </div>




            </div>
        </div>
    </div>

