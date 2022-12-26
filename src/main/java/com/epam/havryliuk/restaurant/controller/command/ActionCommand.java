package com.epam.havryliuk.restaurant.controller.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ActionCommand {
    void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}