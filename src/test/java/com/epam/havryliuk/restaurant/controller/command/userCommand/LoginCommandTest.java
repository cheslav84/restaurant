package com.epam.havryliuk.restaurant.controller.command.userCommand;

import com.epam.havryliuk.restaurant.controller.constants.RequestParameters;
import com.epam.havryliuk.restaurant.controller.constants.paths.AppPagesPath;
import com.epam.havryliuk.restaurant.model.entity.User;
import com.epam.havryliuk.restaurant.model.exceptions.EntityNotFoundException;
import com.epam.havryliuk.restaurant.model.exceptions.ServiceException;
import com.epam.havryliuk.restaurant.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Locale;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginCommandTest {
    private final Locale locale = new Locale("en", "EN");
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private UserService userService;

    @InjectMocks
    private LoginCommand login;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeAccess() throws ServiceException, IOException {
        String email = "some@mail.com";
        String password = "strongPass!123";
        User user = getUserForTest();
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(RequestParameters.EMAIL)).thenReturn(email);
        when(request.getParameter(RequestParameters.PASSWORD)).thenReturn(password);
        when(userService.getUserFromDatabase(email)).thenReturn(user);
        login.execute(request, response);
        verify(session).setAttribute(LOGGED_USER, user);
        verify(response).sendRedirect(AppPagesPath.REDIRECT_INDEX);
    }

    @Test
    void executeWrongEmail() throws ServiceException, IOException {
        String email = "some@mail.com";
        String password = "strongPass!124";
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(RequestParameters.EMAIL)).thenReturn(email);
        when(request.getParameter(RequestParameters.PASSWORD)).thenReturn(password);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(userService.getUserFromDatabase(email)).thenThrow(EntityNotFoundException.class);
        login.execute(request, response);
        verify(session).setAttribute(ERROR_MESSAGE, "The user with such login doesn't exist.");
        verify(response).sendRedirect(AppPagesPath.REDIRECT_REGISTRATION);
    }

    @Test
    void executeWrongPassword() throws ServiceException, IOException {
        String email = "some@mail.com";
        String password = "strongPass!124";
        User user = getUserForTest();
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(RequestParameters.EMAIL)).thenReturn(email);
        when(request.getParameter(RequestParameters.PASSWORD)).thenReturn(password);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(userService.getUserFromDatabase(email)).thenReturn(user);
        login.execute(request, response);
        verify(session).setAttribute(ERROR_MESSAGE, "The password is incorrect.");
        verify(response).sendRedirect(AppPagesPath.REDIRECT_REGISTRATION);
    }

    private User getUserForTest() {
        User user = new User.UserBuilder()
                .withEmail("some@mail.com")
                .withPassword("$argon2i$v=19$m=15360,t=2,p=1$bQjJSajx2zklOm1oyOQEkg$LKjzTVXK2krz65T6SpsBhjdrOErB7OeMX6y7zl+rhP8")
                .withName("name")
                .withSurname("surname")
                .withGender("Male")
                .withOverEighteen(true)
                .build();
        return user;
    }

    @Test
    void executeError() throws ServiceException, IOException {
        String email = "some@mail.com";
        String password = "strongPass!124";
        when(request.getSession()).thenReturn(session);
        when(request.getParameter(RequestParameters.EMAIL)).thenReturn(email);
        when(request.getParameter(RequestParameters.PASSWORD)).thenReturn(password);
        when(session.getAttribute(LOCALE)).thenReturn(locale);
        when(userService.getUserFromDatabase(email)).thenThrow(ServiceException.class);
        login.execute(request, response);
        verify(session).setAttribute(ERROR_MESSAGE, "Something went wrong. Try again later or contact your admin.");
        verify(response).sendRedirect(AppPagesPath.REDIRECT_ERROR);
    }

}