package com.github.merkurevsergei.servlets;

import com.github.merkurevsergei.model.reports.UsersDayReport;
import com.github.merkurevsergei.store.MsSqlStore;
import com.github.merkurevsergei.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class IndexController extends HttpServlet {

    @Override
    protected void
    doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void
    doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDate date = req.getParameter("startDate") == null
                ? LocalDate.now()
                : LocalDate.parse(req.getParameter("startDate"));
        Store store = new MsSqlStore();

        UsersDayReport journal = store.getJournal(
                Timestamp.valueOf(date.atStartOfDay()),
                Timestamp.valueOf(date.atTime(LocalTime.MAX))
        );
        req.setAttribute("startDate", date.toString());
        req.setAttribute("timeFormat", DateTimeFormatter.ofPattern("mm:ss"));
        req.setAttribute("journal", journal.iterator());
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
