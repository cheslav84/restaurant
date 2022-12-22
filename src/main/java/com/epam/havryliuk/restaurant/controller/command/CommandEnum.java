package com.epam.havryliuk.restaurant.controller.command;

import com.epam.havryliuk.restaurant.controller.command.userCommand.LoginCommand;
import com.epam.havryliuk.restaurant.controller.command.userCommand.LogoutCommand;

public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    }, LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    };
    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}