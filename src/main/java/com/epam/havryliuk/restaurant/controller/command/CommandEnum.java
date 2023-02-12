package com.epam.havryliuk.restaurant.controller.command;

import com.epam.havryliuk.restaurant.controller.command.dishCommand.*;
import com.epam.havryliuk.restaurant.controller.command.orderCommand.*;
import com.epam.havryliuk.restaurant.controller.command.userCommand.*;

/**
 * Returns instances of Command by the command name.
 */

public enum CommandEnum {
    INDEX {
        { this.command = new IndexCommand(); }
    }, MENU {
        { this.command = new MenuCommand(); }
    }, SHOW_DISH_INFO {
        { this.command = new DishInfoCommand(); }
    }, MAKE_ORDER {
        { this.command = new MakeOrderCommand(); }
    }, BASKET {
        { this.command = new BasketCommand(); }
    }, REMOVE_FROM_ORDER {
        { this.command = new RemoveFromOrderCommand(); }
    }, SET_NEXT_STATUS {
        { this.command = new SetNextStatusCommand(); }
    }, MANAGE_ORDERS {
        { this.command = new ManageOrdersCommand(); }
    }, LOGIN {
        { this.command = new LoginCommand(); }
    }, LOGIN_PAGE {
        { this.command = new LoginPageCommand(); }
    }, LOGOUT {
        { this.command = new LogoutCommand(); }
    }, REGISTER {
        { this.command = new RegisterCommand(); }
    }, ADD_DISH_PAGE {
        { this.command = new AddDishPageCommand(); }
    }, ADD_DISH {
        { this.command = new AddDishCommand(); }
    }, EDIT_DISH {
        { this.command = new EditDishCommand(); }
    }
    ;

    Command command;

    public Command getCurrentCommand() {
        return command;
    }
}