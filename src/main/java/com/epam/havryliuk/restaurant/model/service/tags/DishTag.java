package com.epam.havryliuk.restaurant.model.service.tags;

import jakarta.servlet.jsp.tagext.TagSupport;
import jakarta.servlet.jsp.*;

import java.io.IOException;

public class DishTag extends TagSupport {
    private String name;
    private String description;
    private String weight;
    private String price;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int doStartTag() throws JspException {
        DataParser parser = new DataParser();
        try {
            if (name != null) {
                pageContext.getOut().write(parser.getItem(name, "name"));
            } else if (description != null) {
                pageContext.getOut().write(parser.getItem(description, "description"));
            } else if (weight != null) {
                pageContext.getOut().write(parser.getItem(weight, "weight"));
            } else if (price != null) {
                pageContext.getOut().write(parser.getItem(price, "price"));
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
