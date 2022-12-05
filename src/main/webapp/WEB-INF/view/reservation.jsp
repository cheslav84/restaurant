<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<head>
    <meta content="text/html; charset=UTF-8">
    <title>Registration</title>
    <meta
        content="Easy Times is a Restaurant website Restaurant, Cafe including the presentation of menus, events, blogging functionality, multiple contact forms and more."
        name="description">
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <meta content="Webflow" name="generator">
    <link href="css/common.css" rel="stylesheet" type="text/css">
    <link href="css/registration.css" rel="stylesheet" type="text/css">
</head>
<body>
<div data-collapse="small" data-animation="default" data-duration="400" data-doc-height="1" data-no-scroll="1"
     class="navbar-left w-nav">
    <div class="menu-overlay">
        <div class="w-container"><a href="http://easy-times.webflow.io/"
                                    class="logo-container w-clearfix w-nav-brand"><img
                src="./Reservation_files/559f87ea0ddfe0783061dffb_Icon-coffee.png" alt="" class="logo-icon">
            <h1 class="logo-text">Easy Times</h1>
            <h2 class="logo-text subtitle">Cafe&nbsp;&amp; Restaurant</h2></a>
            <div class="menu-divider w-hidden-small w-hidden-tiny"></div>
            <div class="menu-button w-nav-button">
                <div class="w-icon-nav-menu"></div>
            </div>
            <nav role="navigation" class="nav-menu w-nav-menu">
                <a href="index.html" class="nav-link w-nav-link w--current" style="max-width: 940px;">Home</a>
                <a href="menu.html" aria-current="page" class="nav-link w-nav-link" style="max-width: 940px;">Menu</a>
                <a href="reservation.html" aria-current="page" class="nav-link w-nav-link" style="max-width: 940px;">Reservation</a>
                <a href="gallery.html" class="nav-link w-nav-link" style="max-width: 940px;">Gallery</a>
                <a href="parties.html" class="nav-link w-nav-link" style="max-width: 940px;">Parties &amp; Events</a>
                <a href="about-us.html" class="nav-link w-nav-link" style="max-width: 940px;">About us</a>
                <a href="blog.html" class="nav-link w-nav-link" style="max-width: 940px;">Blog</a>
                <a href="contact.html" class="nav-link w-nav-link" style="max-width: 940px;">Contact us</a>
            </nav>
            <div class="menu-divider w-hidden-small w-hidden-tiny"></div>
            <div class="nav-contact-block w-hidden-small w-hidden-tiny"><a href="http://www.facebook.com/"
                                                                           target="_blank"
                                                                           class="nav-social-button w-inline-block"><img
                    src="./Reservation_files/559f866bbcccd97730700260_Icon-facebook.png" alt="" class="nav-social-icon"></a><a
                    href="http://www.twitter.com/" target="_blank" class="nav-social-button w-inline-block"><img
                    src="./Reservation_files/55d5b366fa59c51977887dd3_Icon-twitter.png" alt="" class="nav-social-icon"></a><a
                    href="http://www.opentable.com/start/home" target="_blank" class="nav-social-button w-inline-block"><img
                    src="./Reservation_files/56b5bfa6075f9ea44d2bfe8a_Social-Opentable.png" alt=""
                    class="nav-social-icon"></a><a href="http://www.yelp.com/" target="_blank"
                                                   class="nav-social-button w-inline-block"><img
                    src="./Reservation_files/56b5bfc493f9ac952b6b78c0_Social-Yelp.png" alt=""
                    class="nav-social-icon"></a>
                <div class="nav-bottom-text">Copyright © Restuarant</div>
                <div class="nav-bottom-text _2">Template by <a target="_blank"
                                                               href="https://webflow.com/templates/designer/corvus"
                                                               class="link white">Studio Corvus</a></div>
            </div>
        </div>
    </div>
    <div class="w-nav-overlay" data-wf-ignore=""></div>
</div>
<div id="top" class="page-header reservations">
    <div class="page-header-overlay">
        <div class="container w-container">
            <div class="section-intro-block"><h2 data-ix="fade-in-on-load" class="section-intro-title">Reservations</h2>
                <h2 data-ix="fade-in-on-load-2" class="section-intro-title subtitle">Easy times Cafe&nbsp;&amp;&nbsp;Restaurant</h2>
            </div>
        </div>
    </div>
</div>
<div class="section">
    <div class="container w-container">
        <div class="reservation-row w-row">
            <div data-ix="fade-in-on-load-3" class="contact-us-column form-left w-col w-col-6">
                <div class="w-form">
                    <form id="wf-form-Reservation-Form" name="wf-form-Reservation-Form" data-name="Reservation Form">
                        <div class="intro-title">Need a reservation?</div>
                        <h2 class="section-intro-title">Book a table</h2>
                        <div class="section-divider-line"></div>
                        <input type="text" id="name" name="name" data-name="Name" placeholder="Enter your name"
                               maxlength="256" class="field w-input"><input type="email" id="Email-3" name="Email"
                                                                            data-name="Email"
                                                                            placeholder="Email address" maxlength="256"
                                                                            required="" class="field w-input">
                        <div class="w-row">
                            <div class="w-clearfix w-col w-col-6"><select id="Star-time" name="Star-time"
                                                                          data-name="Star time"
                                                                          class="field first-half w-select">
                                <option value="">9:00</option>
                                <option value="First">First Choice</option>
                                <option value="Second">Second Choice</option>
                                <option value="Third">Third Choice</option>
                            </select></div>
                            <div class="w-clearfix w-col w-col-6"><select id="Number-of-guests" name="Number-of-guests"
                                                                          data-name="Number of guests"
                                                                          class="field last-half w-select">
                                <option value="">Number of guests</option>
                                <option value="First">First Choice</option>
                                <option value="Second">Second Choice</option>
                                <option value="Third">Third Choice</option>
                            </select></div>
                        </div>
                        <textarea id="Message" name="Message" placeholder="Enter message..." maxlength="5000"
                                  data-name="Message" required="" class="field area w-input"></textarea><input
                            type="submit" value="Submit" data-wait="Please wait..."
                            class="button submit-button w-button"></form>
                    <div class="success-bg w-form-done"><p class="success-text">Thank you!</p>
                        <p class="success-text _2">Your Reservation has been received!</p></div>
                    <div class="error-bg w-form-fail"><p class="error-text">Oops! Something went wrong while submitting
                        the form</p></div>
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
<div class="footer"><a href="http://easy-times.webflow.io/reservation#" class="go-to-top-link-block w-inline-block"><img
        src="./Reservation_files/55d5bc08b4ce9258656a0ebd_Icon-Top.png" alt="" class="icon-go-to-top"></a>
    <div class="container w-container">
        <div class="footer-row w-row">
            <div class="footer-col-left w-col w-col-6">
                <div class="copyright-text">Copyright ©&nbsp;<a href="http://www.studiocorvus.com/" target="_blank"
                                                                class="bottom-footer-link">Studio Corvus</a></div>
                <div class="copyright-text">Powered by <a href="http://www.webflow.com/" target="_blank"
                                                          class="bottom-footer-link">Webflow</a></div>
            </div>
            <div class="footer-col-right w-col w-col-6"><a href="http://www.facebook.com/" target="_blank"
                                                           class="footer-social-block w-inline-block"><img
                    src="./Reservation_files/559f866bbcccd97730700260_Icon-facebook.png" alt="" class="nav-social-icon"></a><a
                    href="http://www.twitter.com/" target="_blank" class="footer-social-block w-inline-block"><img
                    src="./Reservation_files/55d5b366fa59c51977887dd3_Icon-twitter.png" alt="" class="nav-social-icon"></a><a
                    href="http://www.opentable.com/start/home" target="_blank"
                    class="footer-social-block w-inline-block"><img
                    src="./Reservation_files/56b5bfa6075f9ea44d2bfe8a_Social-Opentable.png" alt=""
                    class="nav-social-icon"></a><a href="http://www.yelp.com/" target="_blank"
                                                   class="footer-social-block w-inline-block"><img
                    src="./Reservation_files/56b5bfc493f9ac952b6b78c0_Social-Yelp.png" alt=""
                    class="nav-social-icon"></a></div>
        </div>
    </div>
</div>
<script src="./js/jquery-3.5.1.min.dc5e7f18c8.js" type="text/javascript"
        integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
        crossorigin="anonymous"></script>
<script src="./js/webflow.4348bf66e.js"
        type="text/javascript"></script><!--[if lte IE 9]>
<script src="//cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif]--></body>
</html>