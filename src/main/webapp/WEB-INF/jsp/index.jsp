<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:setBundle basename="language" />
<!DOCTYPE html>
<html lang="${sessionScope.language}">
<head>
    <meta content="text/html; charset=UTF-8">
    <title>V&amp;H VICTORY</title>
    <meta content="V&H Victory is a Restaurant website Restaurant, Cafe including the presentation of menus"
        name="description">
    <meta content="width=device-width, initial-scale=1" name="viewport">
        <link href="view/css/common.css" rel="stylesheet" type="text/css">
        <link href="view/css/menu.css" rel="stylesheet" type="text/css">
        <link href="view/css/sidebar.css" rel="stylesheet" type="text/css">
        <link href="view/css/order-info.css" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="sidebar.jsp" />
    <div data-delay="4000" data-animation="fade" class="hero-slider w-slider" data-autoplay="true" data-easing="ease"
        data-hide-arrows="false" data-disable-swipe="false" data-autoplay-limit="0" data-nav-spacing="3"
        data-duration="500" data-infinite="true" id="top">
        <div class="w-slider-mask">
            <div class="hero-slide w-slide"
                style="transform: translateX(-1201.82px); opacity: 1; z-index: 15; visibility: hidden;">
                <div class="hero-slide-overlay">
                    <div class="hero-slide-container w-container">
                        <div data-ix="slider-title" class="intro-title slider-intro-title">
                            <fmt:message key="index.cafe" />&nbsp;&amp;
                            <fmt:message key="index.restaurant" />
                        </div>
                        <div class="hero-slide-title">
                            <fmt:message key="index.welcome" />
                        </div>
                        <div data-ix="slider-title-3" class="hero-slide-title subtitle">
                            <fmt:message key="index.bestCoffee" />
                        </div>
                        <a href="#about-us" data-ix="slider-button-1" class="button slider-button">
                            <fmt:message key="index.moreAboutUs" />
                        </a>
                        <a href="#reserv-dish" data-ix="slider-button-2" class="button">
                            <fmt:message key="index.reserveDish" />
                        </a>
                    </div>
                </div>
            </div>
            <div class="hero-slide _2 w-slide"
                style="transform: translateX(-1201.82px); opacity: 1; z-index: 16; transition: opacity 500ms ease 0s;">
                <div class="hero-slide-overlay">
                    <div class="hero-slide-container w-container">
                        <div data-ix="slider-title" class="intro-title slider-intro-title">
                            <fmt:message key="index.cafe" />&nbsp;&amp;
                            <fmt:message key="index.restaurant" />
                        </div>
                        <div data-ix="slider-title-2" class="hero-slide-title">
                            <fmt:message key="index.dinerFor2" />
                        </div>
                        <div data-ix="slider-title-3" class="hero-slide-title subtitle">
                            <fmt:message key="index.chefFriday" />
                        </div>
                        <a href="#about-us" data-ix="slider-button-1" class="button slider-button">
                            <fmt:message key="index.moreAboutUs" />
                        </a>
                        <a href="#reserv-dish" data-ix="slider-button-2" class="button">
                            <fmt:message key="index.reserveDish" />
                        </a>
                    </div>
                </div>
            </div>
            <div class="hero-slide _3 w-slide"
                style="transform: translateX(-1201.82px); opacity: 1; z-index: 14; visibility: hidden;">
                <div class="hero-slide-overlay">
                    <div class="hero-slide-container w-container">
                        <div data-ix="slider-title" class="intro-title slider-intro-title">
                            <fmt:message key="index.cafe" />&nbsp;&amp;
                            <fmt:message key="index.restaurant" />
                        </div>
                        <div data-ix="slider-title-2" class="hero-slide-title">
                            <fmt:message key="index.cappuccino" />
                        </div>
                        <div data-ix="slider-title-3" class="hero-slide-title subtitle">
                            <fmt:message key="index.bestCoffee" />
                        </div>
                        <a href="#about-us" data-ix="slider-button-1" class="button slider-button">
                            <fmt:message key="index.moreAboutUs" />
                        </a>
                        <a href="#reserv-dish" data-ix="slider-button-2" class="button">
                            <fmt:message key="index.reserveDish" />
                        </a>
                    </div>
                </div>
            </div>

        </div>
        <div class="hero-slide-button w-slider-arrow-left">
            <div class="w-icon-slider-left"></div>
        </div>
        <div class="hero-slide-button w-slider-arrow-right">
            <div class="w-icon-slider-right"></div>
        </div>
    </div>
    <div id="about-us" class="section wf-section">
        <div class="container w-container">
            <div class="section-intro-block">
                <div data-ix="fade-in-on-scroll" class="intro-title">
                    <fmt:message key="index.welcome" />
                </div>
                <h2 data-ix="fade-in-on-scroll" class="section-intro-title">
                    <fmt:message key="index.aboutUs" />
                </h2>
                <h2 data-ix="fade-in-on-scroll" class="section-intro-title subtitle">
                    <fmt:message key="index.cafe" />&nbsp;&amp;
                    <fmt:message key="index.restaurant" />
                </h2>
                <div data-ix="fade-in-on-scroll" class="section-divider-line"></div>
                <p data-ix="fade-in-on-scroll">
                    Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse
                    varius enim in eros elementum tristique. Duis cursus, mi quis viverra ornare, eros dolor
                    interdum nulla, ut
                    commodo diam libero vitae erat. Aenean faucibus nibh et justo cursus id rutrum lorem
                    imperdiet.
                    Nuncut sem vitae risus tristique posuere.
                </p>
            </div>
        </div>
    </div>
    <div class="image-section wf-section">
        <div class="image-section-overlay">
            <div class="container w-container">
                <div class="section-intro-block">
                    <h2 data-ix="fade-in-on-scroll" class="section-intro-title">
                        <fmt:message key="index.didYouKnow" />
                    </h2>
                    <h2 data-ix="fade-in-on-scroll" class="section-intro-title subtitle">
                        <fmt:message key="index.aboutOurRestaurant" />
                    </h2>
                </div>
                <div class="image-section-row w-row">
                    <div data-ix="fade-in-on-scroll" class="facts-column w-col w-col-3 w-col-small-6 w-col-tiny-6">
                        <img src="view/pictures/icons/Icon-coffee.png" alt="" class="image-section-icon">
                        <div class="image-section-column-title">
                            <fmt:message key="index.famousFor" />
                        <br>
                            <fmt:message key="index.ourCoffee" />
                        </div>
                    </div>
                    <div data-ix="fade-in-on-scroll" class="facts-column w-col w-col-3 w-col-small-6 w-col-tiny-6">
                        <img src="view/pictures/icons/Icon-phone.png" alt="" class="image-section-icon">
                        <div class="image-section-column-title">
                            <fmt:message key="index.online" />
                        <br>
                            <fmt:message key="index.order" />
                        </div>
                    </div>
                    <div data-ix="fade-in-on-scroll" class="facts-column w-col w-col-3 w-col-small-6 w-col-tiny-6">
                        <img src="view/pictures/icons/Icon-clock.png" alt="" class="image-section-icon">
                        <div class="image-section-column-title">
                            <fmt:message key="index.openEveryDay" />
                        <br>
                            08:00 - 01:00
                        </div>
                    </div>
                    <div data-ix="fade-in-on-scroll" class="facts-column w-col w-col-3 w-col-small-6 w-col-tiny-6">
                        <img src="view/pictures/icons/Icon-street-white.png" alt="" class="image-section-icon">
                        <div class="image-section-column-title">
                            <fmt:message key="index.locatedIn" />
                        <br>
                            <fmt:message key="index.cityCenter" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="indexMenu.jsp" />
    <div class="section wf-section">
        <div class="container w-container">
            <div class="w-row">
                <div class="text-column-left w-col w-col-6">
                    <div class="intro-title">
                        <fmt:message key="index.authentic" />
                    </div>
                    <h2 class="section-intro-title">
                        <fmt:message key="index.tasteful" />
                    </h2>
                    <h2 class="section-intro-title subtitle">
                        <fmt:message key="index.cafe" />&nbsp;&amp;
                        <fmt:message key="index.restaurant" />
                    </h2>
                    <div class="section-divider-line"></div>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse varius enim in
                        eros
                        elementum
                        tristique. Duis cursus, mi quis viverra ornare, eros dolor.</p>
                </div>
                <div class="gallery-column-right w-col w-col-6">
                    <div class="small-gallery-row w-row">
                        <div class="small-gallery-col-left w-clearfix w-col w-col-6 w-col-small-6 w-col-tiny-6">
                            <a href="#" class="small-gallery-lightbox w-inline-block w-lightbox">
                                <img src="view/pictures/common/person-woman-coffee-cup.jpg"
                                    sizes="(max-width: 479px) 44vw, (max-width: 767px) 45vw, (max-width: 991px) 17vw, 15vw"
                                    alt="" class="gallery-thumb">
                            </a>
                        </div>
                        <div class="small-gallery-col-right w-clearfix w-col w-col-6 w-col-small-6 w-col-tiny-6">
                            <a href="#" class="small-gallery-lightbox _2 w-inline-block w-lightbox">
                                <img src="view/pictures/common/restaurant-coffee-cup-cappuccino.jpg"
                                    sizes="(max-width: 479px) 44vw, (max-width: 767px) 45vw, (max-width: 991px) 17vw, 15vw"
                                    alt="" class="gallery-thumb">
                            </a>
                        </div>
                    </div>
                    <div class="small-gallery-row w-row">
                        <div class="small-gallery-col-left w-clearfix w-col w-col-6 w-col-small-6 w-col-tiny-6">
                            <a href="#" class="small-gallery-lightbox w-inline-block w-lightbox">
                                <img src="view/pictures/common/Eating.jpg"
                                    sizes="(max-width: 479px) 44vw, (max-width: 767px) 45vw, (max-width: 991px) 17vw, 15vw"
                                    alt="" class="gallery-thumb">
                            </a>
                        </div>
                        <div class="small-gallery-col-right w-clearfix w-col w-col-6 w-col-small-6 w-col-tiny-6">
                            <a href="#" class="small-gallery-lightbox _2 w-inline-block w-lightbox">
                                <img src="view/pictures/common/beans-coffee-morning-espresso.jpg"
                                    sizes="(max-width: 479px) 44vw, (max-width: 767px) 45vw, (max-width: 991px) 17vw, 15vw"
                                    alt="" class="gallery-thumb">
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${sessionScope.loggedUser ne null}">
        <jsp:include page="order-info.jsp" />
    </c:if>
    <jsp:include page="footer.jsp" />
    <script src="view/js/jquery3.6.1.js" type="text/javascript" crossorigin="anonymous"></script>
    <script src="view/js/webflow.e.js" type="text/javascript"></script>
    <script src="view/js/menu.js" type="text/javascript"></script>
    <script src="view/js/order-info.js" type="text/javascript"></script>
</body>

</html>