package com.epam.havryliuk.restaurant.controller.command;

import com.epam.havryliuk.restaurant.controller.command.dishCommand.IndexCommand;
import com.epam.havryliuk.restaurant.controller.command.dishCommand.MenuCommand;
import com.epam.havryliuk.restaurant.controller.command.orderCommand.BasketCommand;
import com.epam.havryliuk.restaurant.controller.command.orderCommand.MakeOrderCommand;
import com.epam.havryliuk.restaurant.controller.command.orderCommand.OrderInfoCommand;
import com.epam.havryliuk.restaurant.controller.command.orderCommand.RemoveFromOrderCommand;
import com.epam.havryliuk.restaurant.controller.command.userCommand.*;

public enum CommandEnum {
    PAGE {
        { this.command = new PageCommand(); }
    }, INDEX {
        { this.command = new IndexCommand(); }
    }, MENU {
        { this.command = new MenuCommand(); }
    }, SHOWORDERINFO {
        { this.command = new OrderInfoCommand(); }
    }, MAKEORDER {
        { this.command = new MakeOrderCommand(); }
    }, BASKET {
        { this.command = new BasketCommand(); }
    }, REMOVEFROMORDER {
        { this.command = new RemoveFromOrderCommand(); }
    }, LOGIN {
        { this.command = new LoginCommand(); }
    }, LOGOUT {
        { this.command = new LogoutCommand(); }
    }, REGISTER {
        { this.command = new RegisterCommand(); }
    };

    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}