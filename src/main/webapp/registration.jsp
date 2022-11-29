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
    <link href="WEB-INF/css/common.css" rel="stylesheet" type="text/css">
    <link href="WEB-INF/css/registration.css" rel="stylesheet" type="text/css">
</head>

<body>
    <div data-animation="default" class="navbar-left w-nav" data-easing2="ease" data-easing="ease" data-collapse="small"
        role="banner" data-no-scroll="1" data-duration="400" data-doc-height="1">
        <div class="menu-overlay">
            <div class="w-container">
                <a href="index.html" aria-current="page" class="logo-container w-clearfix w-nav-brand w--current">
                    <img src="./icons/Icon-coffee.png" alt="" class="logo-icon">
                    <h1 class="logo-text">Easy Times</h1>
                    <h2 class="logo-text subtitle">Cafe&nbsp;&amp; Restaurant</h2>
                </a>
                <div class="menu-divider w-hidden-small w-hidden-tiny"></div>
                <div class="menu-button w-nav-button">
                    <div class="w-icon-nav-menu"></div>
                </div>
                <nav role="navigation" class="nav-menu w-nav-menu">
                    <a href="index.jsp" aria-current="page" class="nav-link w-nav-link w--current"
                        style="max-width: 940px;">Home</a>
                    <a href="menu.html" class="nav-link w-nav-link" style="max-width: 940px;">Menu</a>
                    <a href="reservation.html" class="nav-link w-nav-link" style="max-width: 940px;">Reservation</a>
                    <a href="gallery.html" class="nav-link w-nav-link" style="max-width: 940px;">Gallery</a>
                    <a href="parties.html" class="nav-link w-nav-link" style="max-width: 940px;">Parties &amp;
                        Events</a>
                    <a href="about-us.html" class="nav-link w-nav-link" style="max-width: 940px;">About us</a>
                    <a href="blog.html" class="nav-link w-nav-link" style="max-width: 940px;">Blog</a>
                    <a href="contact.html" class="nav-link w-nav-link" style="max-width: 940px;">Contact us</a>
                </nav>
                <div class="menu-divider w-hidden-small w-hidden-tiny"></div>
                <div class="nav-contact-block w-hidden-small w-hidden-tiny">
                    <a href="http://www.facebook.com/" target="_blank" class="nav-social-button w-inline-block">
                        <img src="WEB-INF/pictures/icons/Icon-facebook.png" alt="" class="nav-social-icon"></a>
                    <a href="http://www.twitter.com/" target="_blank" class="nav-social-button w-inline-block">
                        <img src="WEB-INF/pictures/icons/Icon-twitter.png" alt="" class="nav-social-icon">
                    </a>
                </div>
            </div>
        </div>
        <div class="w-nav-overlay" data-wf-ignore=""></div>
    </div>
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

                        <form id="wf-form-Reservation-Form" name="logInForm" data-name="Log in form">

                            <input type="email" id="Email-3" name="email" data-name="Email" placeholder="Email address"
                                maxlength="32" required="true" class="field w-input">

                            <input type="password" id="password" name="password" data-name="Password"
                                placeholder="Password" minlength="8" maxlength="32" required="true"
                                class="field w-input">


                            <input type="submit" value="Submit" data-wait="Please wait..."
                                class="button submit-button w-button">
                        </form>
                        <div class="sing-in">
                            <div class="section-divider-line"></div>
                            <div class="intro-title">Do not have account?</div>
                            <input type="submit" value="Sing in" data-wait="Please wait..."
                                class="button submit-button w-button">
                        </div>




                        <form id="wf-form-Reservation-Form" name="registrationForm" data-name="Reservation Form">
                            <input type="text" id="name" name="name" data-name="Name" placeholder="Enter your name"
                                maxlength="24" class="field w-input">
                            <input type="text" id="name" name="surname" data-name="Surname"
                                placeholder="Enter your surname" maxlength="24" class="field w-input">


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
                                        <label for="user-age" class="w-col user-age-label">I'm 18</label>
                                        <input type="checkbox" id="user-age" name="userAge" data-name="User age"
                                            class="w-col w-select user-age">
                                        </input>
                                    </div>


                                </div>
                            </div>

                            <input type="email" id="Email-3" name="email" data-name="Email" placeholder="Email address"
                                maxlength="32" required="true" class="field w-input">

                            <input type="password" id="password" name="password" data-name="Password"
                                placeholder="Password" minlength="8" maxlength="32" required="true"
                                class="field w-input">

                            <input type="submit" value="Submit" data-wait="Please wait..."
                                class="button submit-button w-button">
                        </form>



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







    <div class="footer wf-section">
        <a href="#top" class="go-to-top-link-block w-inline-block w--current">
            <img src="./icons/Icon-Top.png" alt="" class="icon-go-to-top">
        </a>
        <div class="container w-container">
            <div class="footer-row w-row">
                <div class="footer-col-left w-col w-col-6"> 
                </div>
                <div class="footer-col-right w-col w-col-6">
                    <a href="http://www.facebook.com/" target="_blank" class="footer-social-block w-inline-block">
                        <img src="./icons/Icon-facebook.png" alt="" class="nav-social-icon">
                    </a>
                    <a href="http://www.twitter.com/" target="_blank" class="footer-social-block w-inline-block">
                        <img src="./icons/Icon-twitter.png" alt="" class="nav-social-icon">
                    </a>
                </div>
            </div>
        </div>
    </div>
    <script src="./js/jquery3.6.1.js" type="text/javascript"  crossorigin="anonymous"></script>
    <script src="./js/webflow.js" type="text/javascript"></script>

</html>