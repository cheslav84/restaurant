package com.epam.havryliuk.restaurant.model.service.validation.tags;

import java.io.IOException;
import java.util.regex.Pattern;

import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.*;
import jakarta.servlet.jsp.tagext.BodyContent;
import jakarta.servlet.jsp.tagext.BodyTagSupport;
import jakarta.servlet.jsp.tagext.TagSupport;

public class MailTag extends TagSupport {
    private static final String MAIL_PATTERN = "(\\w{6,})@(\\w+\\.)(\\w{2,4})";
    private String name;
    public void setName(String name) {
        this.name = name;
    }

//    private String clazz;
    public void setClass(String clazz) {
//        this.clazz = clazz;
    }


    @Override
    public int doStartTag() throws JspException {



        try {
            JspWriter out = pageContext.getOut();

            out.write("<input type=\"email\" id=\"Email-3\" name=\"email\" data-name=\"Email\" placeholder=\"Email address\"\n" +
                " maxlength=\"32\" required=\"true\" class=\"field w-input\" value=\"v_cheslav@ukr.net\">");


//            out.write("<input>");
//            out.write("<input class=\"field w-input\">");

            System.err.println(pageContext.getRequest().getParameter("email"));

//            out.write("<input ");
//            out.write("type=\"text\" ");
//            out.write("class=\"field w-input\" ");
//
//            out.write("name=\"" + RequestParameters.EMAIL + "\" ");
//            out.write("placeholder=\"Email address\" ");
////            out.append("value=\"v_cheslav@ukr.net\" ");
//            out.write(">");


        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }


}