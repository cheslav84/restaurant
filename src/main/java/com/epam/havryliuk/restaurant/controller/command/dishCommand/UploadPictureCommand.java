package com.epam.havryliuk.restaurant.controller.command.dishCommand;

import com.epam.havryliuk.restaurant.controller.command.Command;
import com.epam.havryliuk.restaurant.model.constants.RequestParameters;
import com.epam.havryliuk.restaurant.model.constants.ResponseMessages;
import com.epam.havryliuk.restaurant.model.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.Category;
import com.epam.havryliuk.restaurant.model.entity.Dish;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.util.BundleManager;
import com.epam.havryliuk.restaurant.model.service.DishService;
import com.epam.havryliuk.restaurant.model.util.annotations.ApplicationServiceContext;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.epam.havryliuk.restaurant.model.constants.RequestAttributes.*;
//@MultipartConfig
public class UploadPictureCommand implements Command {
    private static final Logger LOG = LogManager.getLogger(UploadPictureCommand.class);
    @SuppressWarnings("FieldMayBeFinal")
    private DishService dishService;

    public UploadPictureCommand () {
        ApplicationServiceContext appContext = new ApplicationServiceContext();
        dishService = appContext.getInstance(DishService.class);
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        BundleManager bundleManager = BundleManager.valueOf(((Locale) request.getSession().getAttribute(LOCALE)).getCountry());
        HttpSession session = request.getSession();

        Part part = request.getPart("dishImage");

        String fileName = part.getSubmittedFileName();

//        String lineSeparator = System.;

        System.err.println(part.getContentType());
        System.err.println(part.getName());
        System.err.println(fileName);

        String path = "view/pictures/dish_pictures/" + fileName;
        System.err.println(path);
        path = request.getServletContext().getRealPath(path);


        InputStream is = part.getInputStream();

        byte[] imageBytes = new byte[is.available()];
        is.read(imageBytes);


        response.setContentType("image/jpeg");
        response.setContentLength(imageBytes.length);

        response.getOutputStream().write(imageBytes);


//        Files.copy(is, Paths.get(path), StandardCopyOption.REPLACE_EXISTING);






//        session.setAttribute();

//        File dishImage = (File) request.getParameter("dishImage");

//        try {


            LOG.debug("List of dishes received by servlet and going to be sending to client side.");
//        } catch (ServiceException e) {
//            request.setAttribute(ERROR_MESSAGE,
//                    bundleManager.getProperty(ResponseMessages.MENU_UNAVAILABLE));
//            LOG.error(e);
//        }

        request.getRequestDispatcher(AppPagesPath.FORWARD_ADD_DISH_PAGE).forward(request, response);
    }


}