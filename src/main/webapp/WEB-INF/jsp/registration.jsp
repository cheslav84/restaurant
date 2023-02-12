<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale.country}" />
<fmt:setBundle basename="language" />
<!DOCTYPE html>
<html>
<head>
    <meta content="text/html; charset=UTF-8">
    <title>
        <fmt:message key="registration.registration" />
    </title>
    <meta content="V&H Victory is a Restaurant website Restaurant, Cafe including the presentation of menus"
        name="description">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <meta content="Webflow" name="generator">
    <link href="view/css/common.css" rel="stylesheet" type="text/css">
    <link href="view/css/registration.css" rel="stylesheet" type="text/css">
    <link href="view/css/sidebar.css" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="sidebar.jsp" />
    <div id="top" class="page-header reservations">
        <div class="page-header-overlay">
            <div class="container w-container">
                <div class="section-intro-block">
                    <h2 data-ix="fade-in-on-load" class="section-intro-title">
                        <fmt:message key="registration.registration" />
                    </h2>
                    <h2 data-ix="fade-in-on-load-2" class="section-intro-title subtitle">
                        <fmt:message key="registration.registration" />
                        V&amp;H VICTORY
                        <fmt:message key="index.cafe" />&nbsp;&amp;
                        <fmt:message key="index.restaurant" />
                </div>
            </div>
        </div>
    </div>
    <div class="section">
        <div class="container w-container">
            <div class="reservation-row w-row">
                <div data-ix="fade-in-on-load-3" class="contact-us-column form-left w-col w-col-6">
                    <div class="w-form">
                        <div id="login-form">
                            <div class="intro-title">
                                <fmt:message key="registration.needAnOrder" />
                            </div>
                            <h2 class="section-intro-title subtitle">
                                <fmt:message key="registration.logInPlease" />
                            </h2>
                            <div class="section-divider-line"></div>
                            <!-- LOGING IN -->
                            <form method="post" action="login" name="logInForm" data-name="Log in form">
                                    <input type="email" name="email" data-name="Email"
                                        placeholder="<fmt:message key="registration.ph.email" />" 
                                        maxlength="32" required="true" class="field w-input" value="${sessionScope.email}">
                                        <div class="password-wrap">
                                            <input type="password" id="log-password" name="password"
                                                data-name="Password" 
                                                placeholder="<fmt:message key="registration.ph.password" />" 
                                                minlength="8" maxlength="32"
                                                required="true" class="field w-input" value="strongPass!123">
                                                <button type="button" id="show-log-passwd">
                                                    <img src="view/pictures/icons/visibility.png" alt="Show password" />
                                                </button>
                                        </div>
                                        <div class="error-message">
                                            <c:out value="${sessionScope.errorMessage}" />
                                        </div>
                                        <input type="submit" 
                                            value="<fmt:message key="registration.logIn" />" 
                                            data-wait="Please wait..." class="button submit-button w-button">
                            </form>
                            <div class="sing-in">
                                <div class="section-divider-line"></div>
                                <div class="intro-title">
                                    <fmt:message key="registration.doNotHaveAnAccount" />
                                </div>
                                <input type="button" id="hide-login-btn" 
                                value="<fmt:message key="registration.makeAccount" />"
                                 data-wait="Please wait..." class="button submit-button w-button">
                            </div>
                        </div>

                        <!-- REGISTRATION -->
                        <div id="registration-form"
                            class="registration-form ${sessionScope.registrationProcess} hidden">
                            <form method="post" action="register" name="registrationForm" data-name="Reservation Form">
                                <input type="text" id="name" name="name" data-name="Name"
                                    placeholder="<fmt:message key="registration.ph.name" />" 
                                    maxlength="24" class="field w-input" value="${sessionScope.loggingUser.name}">
                                <input type="text" id="name" name="surname" data-name="Surname"
                                    placeholder="<fmt:message key="registration.ph.surname" />" 
                                    maxlength="24" class="field w-input"
                                    value="${sessionScope.loggingUser.surname}">
                                <div class="w-row">
                                    <div class="w-clearfix w-col w-col-6">
                                        <select id="user-gender" name="userGender" data-name="User gender"
                                            class="field first-half w-select">
                                            <option value="">
                                                <fmt:message key="registration.gender" />
                                            </option>
                                            <option value="Male">
                                                <fmt:message key="registration.male" />
                                            </option>
                                            <option value="Female">
                                                <fmt:message key="registration.female" />
                                            </option>
                                        </select>
                                    </div>
                                    <div class="w-clearfix w-col w-col-6">
                                        <div class="w-row field last-half w-select">
                                            <label for="user-age" class="w-col user-age-label">
                                                <fmt:message key="registration.Im18" />
                                            </label>
                                            <input type="checkbox" id="user-age" name="userOverEighteenAge"
                                                data-name="User age" class="w-col w-select user-age" value="">
                                        </div>
                                    </div>
                                </div>
                                <input type="email" id="Email-3" name="email" data-name="Email"
                                    placeholder="<fmt:message key="registration.ph.email" />" 
                                    maxlength="32" required="true" class="field w-input"
                                    value="${sessionScope.loggingUser.email}">
                                <div class="password-wrap">
                                    <input type="password" id="reg-password" name="password" data-name="Password"
                                        placeholder="<fmt:message key="registration.ph.password" />" 
                                        minlength="8" maxlength="32" required="true"
                                        class="field w-input">
                                    <button type="button" id="show-reg-passwd">
                                        <img src="view/pictures/icons/visibility.png" alt="Show password" />
                                    </button>
                                </div>
                                <div class="password-wrap">
                                    <input type="password" id="conf-password" name="passwordConfirmation"
                                        data-name="Password" 
                                        placeholder="<fmt:message key="registration.ph.passwordConfirm" />" 
                                        minlength="8" maxlength="32"
                                        required="true" class="field w-input">
                                    <button type="button" id="show-conf-passwd">
                                        <img src="view/pictures/icons/visibility.png" alt="Show password" />
                                    </button>
                                </div>
                                <div class="error-message">
                                    <c:out value="${sessionScope.registrationErrorMessage}" />
                                </div>
                                <input type="submit" class="button submit-button w-button"
                                    value="<fmt:message key="registration.signIn" />" >
                            </form>
                            <div class="section-divider-line"></div>
                            <input type="button" id="show-login-btn" class="button submit-button w-button"
                                value="<fmt:message key="registration.haveAnAccount" />" >
                        </div>
                        <div class="section-divider-line"></div>
                </div>
            </div>
            <div class="reservation-image-column w-col w-col-3">
                <div data-ix="fade-in-on-load-4" class="reservation-image-block"></div>
            </div>
            <div class="reservation-image-column w-hidden-small w-hidden-tiny w-col w-col-3">
                <div data-ix="fade-in-on-load-5" class="reservation-image-block _2"></div>
            </div>
        </div>
    </div>
    </div>
    <jsp:include page="footer.jsp" />
    <script src="view/js/jquery3.6.1.js" type="text/javascript" crossorigin="anonymous"></script>
    <script src="view/js/webflow.e.js" type="text/javascript"></script>
    <script src="view/js/registration.js" type="text/javascript"></script>
</body>
</html>