package com.epam.havryliuk.restaurant.controller.command;

import com.epam.havryliuk.restaurant.controller.command.dishCommand.AddDishPageCommand;
import com.epam.havryliuk.restaurant.controller.command.dishCommand.IndexCommand;
import com.epam.havryliuk.restaurant.controller.command.dishCommand.MenuCommand;
import com.epam.havryliuk.restaurant.controller.command.orderCommand.*;
import com.epam.havryliuk.restaurant.controller.command.userCommand.*;

public enum CommandEnum {
    INDEX {
        { this.command = new IndexCommand(); }
    }, MENU {
        { this.command = new MenuCommand(); }
    }, SHOW_ORDER_INFO {
        { this.command = new OrderInfoCommand(); }
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
    }, UPLOAD_IMAGE {
        { this.command = new RegisterCommand(); }
    }
    ;

    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}