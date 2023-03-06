package com.epam.havryliuk.restaurant.controller.command;

import com.epam.havryliuk.restaurant.controller.command.dishCommand.*;
import com.epam.havryliuk.restaurant.controller.command.orderCommand.*;
import com.epam.havryliuk.restaurant.controller.command.userCommand.LoginPageCommand;
import com.epam.havryliuk.restaurant.controller.command.userCommand.LoginCommand;
import com.epam.havryliuk.restaurant.controller.command.userCommand.LogoutCommand;
import com.epam.havryliuk.restaurant.controller.command.userCommand.RegisterCommand;

/**
 * Returns instances of Command by the command name.
 */

public enum CommandsHolder {
    INDEX (new IndexCommand()),
    MENU (new MenuCommand()),
    SHOW_DISH_INFO (new DishInfoCommand()),
    MAKE_ORDER (new MakeOrderCommand()),
    BASKET (new BasketCommand()),
    REMOVE_FROM_ORDER (new RemoveFromOrderCommand()),
    SET_NEXT_STATUS (new SetNextStatusCommand()),
    MANAGE_ORDERS (new ManageOrdersCommand()),
    LOGIN (new LoginCommand()),
    LOGIN_PAGE (new LoginPageCommand()),
    LOGOUT (new LogoutCommand()),
    REGISTER (new RegisterCommand()),
    ADD_DISH_PAGE (new AddDishPageCommand()),
    ADD_DISH (new AddDishCommand()),
    EDIT_DISH (new EditDishCommand());

     private final Command command;

    CommandsHolder(Command command) {
        this.command = command;
    }

    public Command getCurrentCommand() {
        return command;
    }
}