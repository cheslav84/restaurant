package com.epam.havryliuk.restaurant.controller.responseManager;

import com.epam.havryliuk.restaurant.controller.dispatchers.MenuDispatcher;
import com.epam.havryliuk.restaurant.model.entity.Category;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.epam.havryliuk.restaurant.controller.constants.RequestAttributes.MENU_CATEGORY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MenuDispatcherTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;

    private MenuDispatcher menuDispatcher;

    @BeforeAll
    public void setup() {
        menuDispatcher = new MenuDispatcher();
    }

    @Test
    void getCurrentMenuPageVisitedForTheFirstTime() {
        Category defaultMenu = Category.COFFEE;
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(MENU_CATEGORY)).thenReturn(null);
        Category actual = menuDispatcher.getCurrentMenu(request);
        assertEquals(defaultMenu, actual);
    }

}