<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>






        <!DOCTYPE html>
        <html lang="${sessionScope.language}">
        <head>
            <meta content="text/html; charset=UTF-8">
            <title>V&amp;H VICTORY</title>
            <meta
                content="V&H Victory is a Restaurant website Restaurant, Cafe including the presentation of menus"
                name="description">
            <meta content="width=device-width, initial-scale=1" name="viewport">
            <%-- <meta content="Webflow" name="generator"> --%>
   
            <link href="view/css/common.css" rel="stylesheet" type="text/css">
            <link href="view/css/menu.css" rel="stylesheet" type="text/css">
            <link href="view/css/sidebar.css" rel="stylesheet" type="text/css">
            <link href="view/css/order-info.css" rel="stylesheet" type="text/css">

            <!-- <script src="view/js/menu.js" type="text/javascript"></script> -->

        </head>

        <body>
            <jsp:include page="sidebar.jsp"/>

            <div data-delay="4000" data-animation="fade" class="hero-slider w-slider" data-autoplay="true"
                data-easing="ease" data-hide-arrows="false" data-disable-swipe="false" data-autoplay-limit="0"
                data-nav-spacing="3" data-duration="500" data-infinite="true" id="top">
                <div class="w-slider-mask">


                    Request from ${pageContext.errorData.requestURI} is failed
                    <br/>
                    Servlet name or type: ${pageContext.errorData.servletName}
                    <br/>
                    Status code: ${pageContext.errorData.statusCode}
                    <%-- <br/>
                    Exception: ${pageContext.errorData.throwable} --%>

                    

                
                </div>

            </div>




 

            <%-- <jsp:include page="footer.jsp"/> --%>

            <script src="view/js/jquery3.6.1.js" type="text/javascript" crossorigin="anonymous"></script>


        </body>

        </html>












