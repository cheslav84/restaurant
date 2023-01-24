package com.epam.havryliuk.restaurant.controller.command;

import com.epam.havryliuk.restaurant.controller.command.dishCommand.IndexCommand;
import com.epam.havryliuk.restaurant.controller.command.orderCommand.ManageOrdersCommand;
import com.epam.havryliuk.restaurant.controller.command.orderCommand.OrderInfoCommand;
import com.epam.havryliuk.restaurant.controller.command.orderCommand.RemoveFromOrderCommand;
import com.epam.havryliuk.restaurant.controller.command.userCommand.LoginPageCommand;
import com.epam.havryliuk.restaurant.controller.command.userCommand.RegisterCommand;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommandFactoryTest {

    public static Stream<Arguments> testCases () {
        return Stream.of(
                Arguments.of ("/Restaurant/index", new IndexCommand()),
                Arguments.of ("/Restaurant/show_order_info", new OrderInfoCommand()),
                Arguments.of ("/Restaurant/login_page", new LoginPageCommand()),
                Arguments.of ("/Restaurant/manage_orders", new ManageOrdersCommand()),
                Arguments.of ("/Restaurant/register", new RegisterCommand()),
                Arguments.of ("/Restaurant/remove_from_order", new RemoveFromOrderCommand())
        );
    }

    @Mock
    private HttpServletRequest request;

    @ParameterizedTest
    @MethodSource("testCases")
    void defineCommandSeries(String URI, Command command) {
        when(request.getRequestURI()).thenReturn(URI);
        CommandFactory factory = new CommandFactory();
        assertEquals(command.getClass(), factory.defineCommand(request).getClass());
    }

    @Test
    void defineCommandDefault() {
        String URI = "/Restaurant";
        when(request.getRequestURI()).thenReturn(URI);
        CommandFactory factory = new CommandFactory();
        assertEquals(IndexCommand.class, factory.defineCommand(request).getClass());
    }
}
