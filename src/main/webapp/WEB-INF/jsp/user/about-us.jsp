<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale.country}" />
<fmt:setBundle basename="language" />

<div class="section">
    <div class="container w-container">
        <div class="contact-us-row w-row">
            <div class="about-us-image-column w-col w-col-6">
                <div class="about-us-image"></div>
            </div>
            <div class="about-us-text-col-right w-col w-col-6">
                <div class="intro-title">
                    <fmt:message key="aboutUs.aboutUs" />
                </div>
                <h2 class="section-intro-title">V&amp;H VICTORY</h2>
                <div class="section-divider-line"></div>
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse varius enim in eros elementum
                    tristique. Duis cursus, mi quis viverra ornare, eros dolor interdum nulla, ut commodo diam libero
                    vitae erat. Aenean faucibus nibh et justo cursus.&nbsp;Lorem ipsum dolor sit amet, consectetur
                    adipiscing elit. Suspendisse varius enim in eros elementum tristique. Duis cursus, mi quis viverra
                    ornare, eros dolor interdum nulla, ut commodo diam libero vitae erat.</p>
                <div class="rating-block">
                    <div class="rating-title">
                        <fmt:message key="aboutUs.bestCafe" />
                    </div>
                    <p>“Duis cursus, mi quis viverra ornare, eros dolor interdum nulla, ut commodo diam libero vitae
                        erat. Aenean faucibus nibh et justo cursus.”</p>
                    <div class="rating-name">
                       <fmt:message key="aboutUs.chef" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
