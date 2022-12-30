<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



    <!DOCTYPE html>
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu</title>
        <meta content="width=device-width, initial-scale=1" name="viewport">
        <meta content="Webflow" name="generator">

        <link href="view/css/common.css" rel="stylesheet" type="text/css">
        <link href="view/css/sidebar.css" rel="stylesheet" type="text/css">
        <link href="view/css/menu.css" rel="stylesheet" type="text/css">
        <%-- <link href="view/css/easy-times.css" rel="stylesheet" type="text/css"> --%>



    </head>

    <body>


        <jsp:include page="sidebar.jsp" />



        <div id="top" class="page-header wf-section">
            <div class="page-header-overlay menu">
                <div class="container w-container">
                    <div class="section-intro-block">
                        <h2 data-ix="fade-in-on-load" class="section-intro-title">Our menu</h2>
                        <h2 data-ix="fade-in-on-load-2" class="section-intro-title subtitle">Select menu below:</h2>
                    </div>
                </div>
            </div>
        </div>


        <div class="section menu-page-section wf-section">
            <div class="container w-container">
                <div data-duration-in="300" data-duration-out="100" data-current="Coffee" data-easing="ease"
                    class="menu-page-tabs w-tabs">


                    <div class="menu-page-tabs-menu w-tab-menu">

                        <div data-w-tab="All" data-ix="fade-in-on-load-7"
                            class="menu-page-tab-button w-inline-block w-clearfix w-tab-link">
                            <img src="view/pictures/icons/Icon-plate-white.png" alt="" class="menu-tab-icon">
                            <div class="menu-page-tab-title">All</div>
                            <div class="menu-page-tab-title subtitle">Sort by</div>
                            <select name="sort-menu-by" class="menu-page-tab-title subtitle sort-menu-by">Sort by
                                <option value="Name">Name</option>
                                <option value="Price">Price</option>
                                <option value="Category">Category</option>
                            </select>
                        </div>
                        

                        <a href="menu?menuCategory=COFFEE" data-w-tab="COFFEE" data-ix="fade-in-on-load-3"
                            class="menu-page-tab-button w-inline-block w-clearfix w-tab-link w--current">
                            <img src="view/pictures/icons/Icon-coffee.png" alt="" class="menu-tab-icon">
                            <div class="menu-page-tab-title">Coffee</div>
                            <div class="menu-page-tab-title subtitle">From Java island</div>
                        </a>

                        <a href="menu?menuCategory=LUNCH" data-w-tab="LUNCH" data-ix="fade-in-on-load-4"
                            class="menu-page-tab-button w-inline-block w-clearfix w-tab-link">
                            <img src="view/pictures/icons/Icon-bread.png" alt="" class="menu-tab-icon">
                            <div class="menu-page-tab-title">Lunch</div>
                            <div class="menu-page-tab-title subtitle">Will make your day</div>
                        </a>

                        <a href="menu?menuCategory=DINER" data-w-tab="DINER" class="menu-page-tab-button w-inline-block w-clearfix w-tab-link">
                            <img src="view/pictures/icons/Icon-diner.png" alt="" class="menu-tab-icon">
                            <div class="menu-page-tab-title">Diner</div>
                            <div class="menu-page-tab-title subtitle">Think slogan</div>
                        </a>
                        
                        
                        <a href="menu?menuCategory=DRINKS" data-w-tab="DRINKS" data-ix="fade-in-on-load-6"
                            class="menu-page-tab-button w-inline-block w-clearfix w-tab-link">
                            <img src="view/pictures/icons/Icon-beer.png" alt="" class="menu-tab-icon">
                            <div class="menu-page-tab-title">Drinks</div>
                            <div class="menu-page-tab-title subtitle">Take it easy</div>
                        </a>


                    </div>

                <div class="w-tab-content">

                    <div data-w-tab="${sessionScope.menuCategory}" class="menu-tab-pane w-tab-pane w--tab-active">
                        <c:forEach var="dish" items="${requestScope.dishes}">
                            <div role="listitem"  class="menu-list-item w-dyn-item w-col w-col-6">
                                <div class="menu-image-container">
                                    <div style="background-image: url('view/pictures/dish_pictures/${dish.image}');"
                                        class="menu-item-image-box">
                                    </div>
                                </div>
                                        <div class="menu-item-text">
                                            <div class="menu-item-title">
                                                <c:out value="${dish.name}" />
                                            </div>
                                            <div class="menu-item-title description">
                                                <c:out value="${dish.description}" />
                                            </div>
                                            <div class="menu-item-title weight">
                                                <c:out value="${dish.weight}" />
                                                <span class="weight-marker"> g</span>
                                            </div>
                                        </div>
                                        <div class="menu-item-price">
                                            <c:out value="${dish.price}" />
                                            <span class="price-marker">₴</span>
                                        </div>
                                        
                                <form method="get" action="show_order_info" name="dishOrder">
                                    <button value="${dish.id}" name="dishId" class="button order-menu-button">Order</button>
                                </form>

                            </div>
                        </c:forEach>
                    </div>
                    <div class="error-message"> 
                        <c:out value="${requestScope.errorMessage}"/>
                    </div>

                </div>

<%-- 

                    <div class="menu-page-tabs-content w-tab-content">

                        <div data-w-tab="Lunch" class="menu-page-tab-pane w-tab-pane">
                                                <c:forEach var="dish" items="${requestScope.dishes}">
                            <div role="listitem"  class="menu-list-item w-dyn-item w-col w-col-6">
                                <div class="menu-image-container">
                                    <div style="background-image: url('view/pictures/dish_pictures/${dish.image}');"
                                        class="menu-item-image-box">
                                    </div>
                                </div>
                                        <div class="menu-item-text">
                                            <div class="menu-item-title">
                                                <c:out value="${dish.name}" />
                                            </div>
                                            <div class="menu-item-title description">
                                                <c:out value="${dish.description}" />
                                            </div>
                                            <div class="menu-item-title weight">
                                                <c:out value="${dish.weight}" />
                                                <span class="weight-marker"> g</span>
                                            </div>
                                        </div>
                                        <div class="menu-item-price">
                                            <c:out value="${dish.price}" />
                                            <span class="price-marker">₴</span>
                                        </div>
                                        
                                <form method="get" action="show_order_info" name="dishOrder">
                                    <button value="${dish.id}" name="dishId" class="button order-menu-button">Order</button>
                                </form>

                            </div>
                        </c:forEach>
                    </div>
                    <div class="error-message"> 
                        <c:out value="${requestScope.errorMessage}"/>
                    </div>

                        </div>


                        <div data-w-tab="Diner" class="menu-page-tab-pane w-tab-pane">
                                                <c:forEach var="dish" items="${requestScope.dishes}">
                            <div role="listitem"  class="menu-list-item w-dyn-item w-col w-col-6">
                                <div class="menu-image-container">
                                    <div style="background-image: url('view/pictures/dish_pictures/${dish.image}');"
                                        class="menu-item-image-box">
                                    </div>
                                </div>
                                        <div class="menu-item-text">
                                            <div class="menu-item-title">
                                                <c:out value="${dish.name}" />
                                            </div>
                                            <div class="menu-item-title description">
                                                <c:out value="${dish.description}" />
                                            </div>
                                            <div class="menu-item-title weight">
                                                <c:out value="${dish.weight}" />
                                                <span class="weight-marker"> g</span>
                                            </div>
                                        </div>
                                        <div class="menu-item-price">
                                            <c:out value="${dish.price}" />
                                            <span class="price-marker">₴</span>
                                        </div>
                                        
                                <form method="get" action="show_order_info" name="dishOrder">
                                    <button value="${dish.id}" name="dishId" class="button order-menu-button">Order</button>
                                </form>

                            </div>
                        </c:forEach>
                    </div>
                    <div class="error-message"> 
                        <c:out value="${requestScope.errorMessage}"/>
                    </div> --%>
                            <%-- <div class="w-dyn-list">
                                <div class="empty-state w-dyn-empty">
                                    <div class="empty-state-wrapper"><img src="view/pictures/icons/Icon-plate-white.png"
                                            alt="" class="empty-state-icon">
                                        <div class="empty-state-title">Sorry, no items found.</div>
                                        <div class="empty-state-title subtitle"><a
                                                href="http://easy-times.webflow.io/contact">Contact us</a> if you have
                                            any questions.
                                        </div>
                                    </div>
                                </div>
                            </div> --%>
                        <%-- </div>


                        <div data-w-tab="Drinks" class="menu-page-tab-pane w-tab-pane">
                            <div class="w-dyn-list">
                                <div role="list" class="w-clearfix w-dyn-items w-row">
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-item-price">3.50</div>
                                        <div class="menu-item-title">Ice Tea</div>
                                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                            adipiscing.
                                        </div>
                                    </div>
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-item-price">12.00</div>
                                        <div class="menu-item-title">Milkshakes</div>
                                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                            adipiscing.
                                        </div>
                                    </div>
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-item-price">8.00</div>
                                        <div class="menu-item-title">Fruit Juice</div>
                                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                            adipiscing.
                                        </div>
                                    </div>
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-item-price">8.00</div>
                                        <div class="menu-item-title">Coke</div>
                                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                            adipiscing.
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div> --%>



        <div class="image-background-section wf-section">
            <div class="image-background-overlay">
                <div class="container w-container">
                    <h2 data-ix="fade-in-on-scroll" class="section-intro-title">EVER TRIED THESE?</h2>
                    <h2 data-ix="fade-in-on-scroll" class="section-intro-title subtitle">THESE ARE OUR SPECIALS:</h2>
                    <div class="section-divider-line"></div>
                    <div class="menu-white-wrapper w-dyn-list">


                        <div role="list" class="w-clearfix w-dyn-items w-row">
                            <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                <div class="menu-item-price">5.99</div>
                                <div class="menu-item-title">Single Cup Brew</div>
                                <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                    adipiscing.
                                </div>
                            </div>
                            <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                <div class="menu-item-price">5.99</div>
                                <div class="menu-item-title">Black Eyed Andy</div>
                                <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                    adipiscing.
                                </div>
                            </div>
                            <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                <div class="menu-item-price">5.99</div>
                                <div class="menu-item-title">Cafe au Lait</div>
                                <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                    adipiscing.
                                </div>
                            </div>
                            <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                <div class="menu-item-price">5.99</div>
                                <div class="menu-item-title">Cappuccino</div>
                                <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                    adipiscing.
                                </div>
                            </div>
                            <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                <div class="menu-item-price">5.99</div>
                                <div class="menu-item-title">Caramel Macchiato</div>
                                <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                    adipiscing.
                                </div>
                            </div>
                            <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                <div class="menu-item-price">5.99</div>
                                <div class="menu-item-title">Café Latte</div>
                                <div class="menu-item-title description">Lorem sit amet consectetur adipiscing.</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>



        <jsp:include page="footer.jsp" />

        <script src="view/js/jquery3.6.1.js" type="text/javascript" crossorigin="anonymous"></script>
        <script src="view/js/webflow.e.js" type="text/javascript"></script>
        <script src="view/js/menu.js" type="text/javascript"></script>
        <script src="view/js/order-info.js" type="text/javascript"></script>

    </body>

    </html>