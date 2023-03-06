package com.epam.havryliuk.restaurant.controller.dispatchers;


import com.epam.havryliuk.restaurant.controller.constants.RequestParameters;
import com.epam.havryliuk.restaurant.controller.constants.paths.AppPagesPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ImageDispatcher {
    private static final Logger LOG = LogManager.getLogger(ImageDispatcher.class);
    private Part part;
    private String imageFileName;
    private String realPath;

    public ImageDispatcher(HttpServletRequest request, String requestedParameter, String relativePath)
            throws ServletException, IOException {
        this.part = request.getPart(requestedParameter);
        this.imageFileName = part.getSubmittedFileName();
        this.realPath = request.getServletContext()
                .getRealPath(relativePath + imageFileName);
    }

    public Part getPart() {
        return part;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String getRealPath() {
        return realPath;
    }
}
