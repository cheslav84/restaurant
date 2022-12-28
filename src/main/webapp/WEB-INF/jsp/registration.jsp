<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>





<!DOCTYPE html>
<html>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="menu"/>
<head>
    <meta content="text/html; charset=UTF-8">
    <title>Registration</title>
    <meta
        content="Easy Times is a Restaurant website Restaurant, Cafe including the presentation of menus, events, blogging functionality, multiple contact forms and more."
        name="description">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <meta content="Webflow" name="generator">
    <link href="view/css/common.css" rel="stylesheet" type="text/css">
    <link href="view/css/registration.css" rel="stylesheet" type="text/css">
    <link href="view/css/sidebar.css" rel="stylesheet" type="text/css">

</head>

<body>
    <jsp:include page="sidebar.jsp"/>

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
                <div data-ix="fade-in-on-load-3" class="contact-us-column form-left w-col w-col-6">
                    <div class="w-form">
                        <div class="intro-title">Need to make an order?</div>
                        <h2 class="section-intro-title subtitle">Log in please</h2>
                        <div class="section-divider-line"></div>


                        
                        
                        <%-- <c:out value = ""/> --%>



                        <!-- LOGING IN -->
                        <form method="post" action="login" id="wf-form-Reservation-Form" name="logInForm" data-name="Log in form">
                        <%-- <input type="hidden" name="command" value="login" /> --%>
                            <input type="email" id="Email-3" name="email" data-name="Email" placeholder="Email address"
                                maxlength="32" required="true" class="field w-input"                                          value="v_cheslav@ukr.net">

                            <input type="password" id="password" name="password" data-name="Password"
                                placeholder="Password" minlength="8" maxlength="32" required="true"
                                class="field w-input"                                                                     value="strongPass!@#123">

                            <div class="error-message"> 
                                <%-- <c:out value="${sessionScope.logInErrorMessage}"/> --%>
                                <c:out value="${sessionScope.errorMessage}"/>
                            </div>

                            <input type="submit" value="Log in" data-wait="Please wait..."
                                class="button submit-button w-button">
                        </form>
                        <div class="sing-in">
                            <div class="section-divider-line"></div>
                            <div class="intro-title">Do not have an account?</div>
                            <input type="submit" value="Just make it" data-wait="Please wait..."
                                class="button submit-button w-button">
                        </div>
              

                        <!-- REGISTRATION -->
                        <form method="post" action="register" id="wf-form-Reservation-Form" name="registrationForm" data-name="Reservation Form">
                            <input type="text" id="name" name="name" data-name="Name" placeholder="Enter your name"
                                maxlength="24" class="field w-input"                                                     value="Name">
                            <input type="text" id="name" name="surname" data-name="Surname"
                                placeholder="Enter your surname" maxlength="24" class="field w-input"                    value="Surname">
                            <div class="w-row">
                                <div class="w-clearfix w-col w-col-6">
                                    <select id="user-gender" name="userGender" data-name="User gender"
                                        class="field first-half w-select">
                                        <option value="">Gender</option>
                                        <option value="Male">Male</option>
                                        <option value="Female">Female</option>
                                    </select>
                                </div>
                                <div class="w-clearfix w-col w-col-6">
                                    <div class="w-row field last-half w-select">
                                        <label for="user-age" class="w-col user-age-label">I'm 18+</label>
                                        <input type="checkbox" id="user-age" name="userOverEighteenAge" data-name="User age" class="w-col w-select user-age" value="">
                                    </div>
                                </div>
                            </div>

                            <input type="email" id="Email-3" name="email" data-name="Email" placeholder="Email address"
                                maxlength="32" required="true" class="field w-input"                                       value="mail@com">

                            <input type="password" id="password" name="password" data-name="Password"
                                placeholder="Password" minlength="8" maxlength="32" required="true"
                                class="field w-input"                                                                      value="mail@com">

                            <div class="error-message"> 
                                <c:out value="${sessionScope.registrationErrorMessage}"/>
                            </div>
                            <input type="submit" value="Sing in" data-wait="Please wait..."
                                class="button submit-button w-button">
                        </form>
                        

                        <div class="section-divider-line"></div>

                        
                        <div class="success-bg w-form-done">
                            <p class="success-text">Thank you!</p>
                            <p class="success-text _2">Your Reservation has been received!</p>
                        </div>
                        <div class="error-bg w-form-fail">
                            <p class="error-text">Oops! Something went wrong while submitting the form</p>
                        </div>
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


    
    <jsp:include page="footer.jsp"/>

    <script src="view/js/jquery3.6.1.js" type="text/javascript" crossorigin="anonymous"></script>
    <script src="view/js/webflow.e.js" type="text/javascript"></script>

</body>




</html>