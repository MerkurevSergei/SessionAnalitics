package com.github.MerkurevSergei.servlets;

import com.github.MerkurevSergei.model.Record;
import com.github.MerkurevSergei.store.MsSqlStore;
import com.github.MerkurevSergei.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class IndexController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Store store = new MsSqlStore();
        Collection<Record> journal = store.getJournal();
        req.setAttribute("journal", journal);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
