<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

<head>
    <title>Menu</title>
    <link href="./Menu_files/easy-times.webflow.303b06344.css" rel="stylesheet" type="text/css">
    <!--    <link rel="stylesheet" href="./Menu_files/css" media="all">-->
    <link rel="stylesheet" href="./Menu_files/common.css" media="all">
    <link rel="stylesheet" href="./Menu_files/menu.css" media="all">
    <link rel="stylesheet" href="./Menu_files/dishes.css" media="all">

</head>

<body>

    <div class="section menu-page-section wf-section">
        <div class="container w-container">


            <div class="dish-titles">
                <div class="menu-header dish-image">
                    Image
                </div>
                <div class="menu-header dish-name">
                    Dish
                </div>
                <div class="menu-header dish-description">
                    Description
                </div>
                <div class="menu-header dish-weight">
                    Weight
                </div>
                <div class="menu-header dish-price">
                    Price
                </div>
                <div class="menu-header dish-amount">
                    Amount
                </div>
                <div class="menu-header dish-category">
                    Category
                </div>
                <div class="menu-header dish-spirit">
                    Spirit
                </div>
                <div class="menu-header dish-special">
                    Special
                </div>

            </div>

            <div class="dish-content">
                <div class="menu-item dish-image">
                    Image
                </div>
                <div class="menu-item dish-name">
                    Single Cup Brew
                </div>
                <div class="menu-item dish-description">
                    Lorem ipsum dolor sit amet consectetur adipiscing.
                </div>
                <div class="menu-item dish-weight">
                    <input type="text" name="" data-name="" placeholder="weight" maxlength="" class="menu-item-input"
                        value="">
                    <div class="w-row">
                    </div>
                    <div class="menu-item dish-price">
                        <input type="text" name="" data-name="" placeholder="price" maxlength="" class="menu-item-input"
                            value="">
                    </div>
                    <div class="menu-item dish-amount">
                        <input type="text" name="" data-name="" placeholder="amount" maxlength=""
                            class="menu-item-input" value="">
                    </div>
                    <div class="menu-item dish-category">
                        <select name="userGender" data-name="User gender" class="field first-half w-select">
                            <option value="">Gender</option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                        </select>
                    </div>
                    <div class="menu-item dish-spirit">
                        Spirit
                    </div>
                    <div class="menu-item dish-special">
                        Special
                    </div>
                </div>


                <div data-duration-in="300" data-duration-out="100" data-current="Coffee" data-easing="ease"
                    class="menu-page-tabs w-tabs">



                    <!-- <div class="menu-page-tabs-menu w-tab-menu">


               <div data-w-tab="All" data-ix="fade-in-on-load-7"
                  class="menu-page-tab-button w-inline-block w-clearfix w-tab-link">

                   <img src="./Menu_files/56b5bc202515e2aa4d155cc3_Icon-plate-white.png" alt="" class="menu-tab-icon">
                   
                   <div class="menu-page-tab-title">All</div>
                   <div class="menu-page-tab-title subtitle">Sort by</div>
                   <select name="sort-menu-by" class="menu-page-tab-title subtitle sort-menu-by">Sort by
                       <option value="Name">Name</option>
                       <option value="Price">Price</option>
                       <option value="Category">Category</option>
                   </select>
               </div><a data-w-tab="Coffee" data-ix="fade-in-on-load-3"
                      class="menu-page-tab-button w-inline-block w-clearfix w-tab-link w&#45;&#45;current">
               <img src="./Menu_files/Icon-coffee.png" alt="" class="menu-tab-icon">
               <div class="menu-page-tab-title">Coffee</div>
               <div class="menu-page-tab-title subtitle">From Java island</div>
           </a><a data-w-tab="Lunch" data-ix="fade-in-on-load-4"
                  class="menu-page-tab-button w-inline-block w-clearfix w-tab-link">
               <img src="./Menu_files/Icon-bread.png" alt="" class="menu-tab-icon">
               <div class="menu-page-tab-title">Lunch</div>
               <div class="menu-page-tab-title subtitle">Will make your day</div>
           </a><a data-w-tab="Diner" class="menu-page-tab-button w-inline-block w-clearfix w-tab-link">
               <img src="./Menu_files/Icon-diner.png" alt="" class="menu-tab-icon">
               <div class="menu-page-tab-title">Diner</div>
               <div class="menu-page-tab-title subtitle">Think slogan</div>
           </a><a data-w-tab="Drinks" data-ix="fade-in-on-load-6"
                  class="menu-page-tab-button w-inline-block w-clearfix w-tab-link">
               <img src="./Menu_files/Icon-beer.png" alt="" class="menu-tab-icon">
               <div class="menu-page-tab-title">Drinks</div>
               <div class="menu-page-tab-title subtitle">Take it easy</div>
           </a>


           </div> -->
                    <div class="menu-page-tabs-content w-tab-content">
                        <div data-w-tab="Coffee" class="menu-page-tab-pane w-tab-pane w--tab-active">
                            <div class="w-dyn-list">
                                <div role="list" class="w-clearfix w-dyn-items w-row">
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">


                                        <div class="menu-image-container">
                                            <div style="background-image: url('view/pictures/dish_pictures/${dish.image}');"
                                                class="menu-item-image-box">
                                            </div>
                                        </div>

                                        <div class="menu-item-text">
                                            <div class="menu-item-title">
                                                Black Eyed Andy
                                                <!--                                        <c:out value="${dish.name}" />-->
                                            </div>
                                            <div class="menu-item-title description">
                                                i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                                                i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                                                i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i i
                                                i i i i i i i i i i i i i i i i i i .
                                                <!--                                        <c:out value="${dish.description}" />-->
                                            </div>

                                        </div>

                                        <div class="lable menu-item-special">
                                            SPECIAL
                                        </div>

                                        <div class="lable menu-item-weight">
                                            200
                                            <!--                                        <c:out value="${dish.weight}" />-->
                                            <span class="weight-marker"> g</span>
                                        </div>

                                        <div class="lable menu-item-price">
                                            4.50
                                            <!--                                    <c:out value="${dish.price}" />-->
                                            <span class="price-marker">₴</span>
                                        </div>
                                        
                                        <div class="lable menu-item-amount">
                                            25
                                            <!--                                    <c:out value="${dish.amount}" />-->
                                            <span class="price-marker">pc</span>
                                        </div>


                                        <button class="button menu-item-button">
                                            Edit
                                        </button>




                                        <!--                                <a href="" class="order-icon-container">-->
                                        <!--                                    <img src="view/pictures/icons/Icon-basket.png" alt="" class="order-icon-img">-->
                                        <!--                                </a>-->
                                    </div>
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-item-price">4.50</div>
                                        <div class="menu-item-title">Black Eyed Andy</div>
                                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                            adipiscing.
                                        </div>
                                    </div>
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-item-price">4.99</div>
                                        <div class="menu-item-title">Cuban Shot</div>
                                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                            adipiscing.
                                        </div>
                                    </div>
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-item-price">1.55</div>
                                        <div class="menu-item-title">Cafe au Lait</div>
                                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                            adipiscing.
                                        </div>
                                    </div>
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-item-price">3.00</div>
                                        <div class="menu-item-title">Coffee of the Day</div>
                                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                            adipiscing.
                                        </div>
                                    </div>
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-item-price">4.00</div>
                                        <div class="menu-item-title">Cappuccino</div>
                                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                            adipiscing.
                                        </div>
                                    </div>
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-item-price">5.99</div>
                                        <div class="menu-item-title">Caffé Americano</div>
                                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                            adipiscing.
                                        </div>
                                    </div>
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-item-price">4.35</div>
                                        <div class="menu-item-title">Caramel Macchiato</div>
                                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                            adipiscing.
                                        </div>
                                    </div>
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-item-price">5.99</div>
                                        <div class="menu-item-title">Café Latte</div>
                                        <div class="menu-item-title description">Lorem sit amet consectetur adipiscing.
                                        </div>
                                    </div>
                                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                        <div class="menu-item-price">5.99</div>
                                        <div class="menu-item-title">Standard black coffee</div>
                                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                            adipiscing.
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- <div data-w-tab="Lunch" class="menu-page-tab-pane w-tab-pane">
                    <div class="w-dyn-list">
                        <div role="list" class="w-clearfix w-dyn-items w-row">
                            <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                <div class="menu-item-price">14.00</div>
                                <div class="menu-item-title">Taco Salad</div>
                                <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                    adipiscing.
                                </div>
                            </div>
                            <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                <div class="menu-item-price">18.00</div>
                                <div class="menu-item-title">Chile Relleno</div>
                                <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                    adipiscing.
                                </div>
                            </div>
                            <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                <div class="menu-item-price">15.00</div>
                                <div class="menu-item-title">Fajita Lunch</div>
                                <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                    adipiscing.
                                </div>
                            </div>
                            <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                                <div class="menu-item-price">20.00</div>
                                <div class="menu-item-title">Super Burrito</div>
                                <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur
                                    adipiscing.
                                </div>
                            </div>
                        </div>
                    </div>
                </div> -->
                        <!-- <div data-w-tab="Diner" class="menu-page-tab-pane w-tab-pane">
                    <div class="w-dyn-list">
                        <div class="empty-state w-dyn-empty">
                            <div class="empty-state-wrapper"><img
                                    src="./Menu_files/56b5bc202515e2aa4d155cc3_Icon-plate-white.png"
                                    srcset="https://assets.website-files.com/559f85c38bde14cf4b3723cf/56b5bc202515e2aa4d155cc3_Icon-plate-white-p-500x500.png 500w, https://assets.website-files.com/559f85c38bde14cf4b3723cf/56b5bc202515e2aa4d155cc3_Icon-plate-white.png 512w"
                                    sizes="(max-width: 479px) 87vw, (max-width: 767px) 91vw, (max-width: 991px) 69vw, 61vw"
                                    alt="" class="empty-state-icon">
                                <div class="empty-state-title">Sorry, no items found.</div>
                                <div class="empty-state-title subtitle"><a href="http://easy-times.webflow.io/contact">Contact
                                    us</a> if you have any questions.
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
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
                </div> -->
                    </div>
                </div>
            </div>
        </div>
        <!-- <div class="image-background-section wf-section">
    <div class="image-background-overlay">
        <div class="container w-container"><h2 data-ix="fade-in-on-scroll" class="section-intro-title">EVER TRIED
            THESE?</h2>
            <h2 data-ix="fade-in-on-scroll" class="section-intro-title subtitle">THESE ARE OUR SPECIALS:</h2>
            <div class="section-divider-line"></div>
            <div class="menu-white-wrapper w-dyn-list">
                <div role="list" class="w-clearfix w-dyn-items w-row">
                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                        <div class="menu-item-price">5.99</div>
                        <div class="menu-item-title">Single Cup Brew</div>
                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur adipiscing.
                        </div>
                    </div>
                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                        <div class="menu-item-price">5.99</div>
                        <div class="menu-item-title">Black Eyed Andy</div>
                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur adipiscing.
                        </div>
                    </div>
                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                        <div class="menu-item-price">5.99</div>
                        <div class="menu-item-title">Cafe au Lait</div>
                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur adipiscing.
                        </div>
                    </div>
                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                        <div class="menu-item-price">5.99</div>
                        <div class="menu-item-title">Cappuccino</div>
                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur adipiscing.
                        </div>
                    </div>
                    <div role="listitem" class="menu-list-item w-dyn-item w-col w-col-6">
                        <div class="menu-item-price">5.99</div>
                        <div class="menu-item-title">Caramel Macchiato</div>
                        <div class="menu-item-title description">Lorem ipsum dolor sit amet consectetur adipiscing.
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
</div> -->
        <div class="footer wf-section"><a href="http://easy-times.webflow.io/menu#"
                class="go-to-top-link-block w-inline-block">
                <img src="./Menu_files/55d5bc08b4ce9258656a0ebd_Icon-Top.png" alt="" class="icon-go-to-top"></a>
            <div class="container w-container">
                <div class="footer-row w-row">
                    <div class="footer-col-left w-col w-col-6">
                        <div class="copyright-text">Copyright ©&nbsp;<a href="http://www.studiocorvus.com/"
                                target="_blank" class="bottom-footer-link">Studio Corvus</a></div>
                        <div class="copyright-text">Powered by <a href="http://www.webflow.com/" target="_blank"
                                class="bottom-footer-link">Webflow</a></div>
                    </div>
                    <div class="footer-col-right w-col w-col-6"><a href="http://www.facebook.com/" target="_blank"
                            class="footer-social-block w-inline-block"><img
                                src="./Menu_files/559f866bbcccd97730700260_Icon-facebook.png" alt=""
                                class="nav-social-icon"></a><a href="http://www.twitter.com/" target="_blank"
                            class="footer-social-block w-inline-block"><img
                                src="./Menu_files/55d5b366fa59c51977887dd3_Icon-twitter.png" alt=""
                                class="nav-social-icon"></a><a href="http://www.opentable.com/start/home"
                            target="_blank" class="footer-social-block w-inline-block"><img
                                src="./Menu_files/56b5bfa6075f9ea44d2bfe8a_Social-Opentable.png" alt=""
                                class="nav-social-icon"></a><a href="http://www.yelp.com/" target="_blank"
                            class="footer-social-block w-inline-block"><img
                                src="./Menu_files/56b5bfc493f9ac952b6b78c0_Social-Yelp.png" alt=""
                                class="nav-social-icon"></a>
                    </div>
                </div>
            </div>
        </div>
        <script src="./js/jquery-3.5.1.min.dc5e7f18c8.js" type="text/javascript"
            integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
        <script src="./js/webflow.4348bf66e.js" type="text/javascript"></script>
        <!--[if lte IE 9]>
<script src="//cdnjs.cloudflare.com/ajax/libs/placeholders/3.0.2/placeholders.min.js"></script><![endif]-->
</body>

</html>