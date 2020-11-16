package com.github.merkurevsergei.store;

import com.github.merkurevsergei.model.Event;
import com.github.merkurevsergei.model.reports.UsersDayReport;
import org.apache.commons.dbcp2.BasicDataSource;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

public class MsSqlStore implements Store {

    private final BasicDataSource pool = new BasicDataSource();

    public MsSqlStore() {
        Properties cfg = new Properties();
        URL resource = getClass().getClassLoader().getResource("db.properties");
        try (BufferedReader io = new BufferedReader(
                new FileReader(Objects.requireNonNull(resource).getFile())
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new MsSqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public @NotNull Collection<String> getUsers() {
        List<String> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement("SELECT UserName FROM UserPC ORDER BY UserName")
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(
                            it.getString("UserName")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public @NotNull UsersDayReport getJournal(Timestamp start, Timestamp end) {
        UsersDayReport journal = new UsersDayReport();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT UserPC.UserName, Sec.Adress, Sec.DT, Sec.StatusCode "
                             + "FROM UserPC "
                             + "LEFT JOIN (SELECT Adress, DT, StatusCode "
                             + "    FROM Securis"
                             + "    WHERE DT BETWEEN CAST(? AS DATETIME) "
                             + "    AND CAST(? AS DATETIME)) Sec ON UserPC.PSName = Sec.Adress "
                             + "ORDER BY UserName, DT"
             )
        ) {
            ps.setTimestamp(1, start);
            ps.setTimestamp(2, end);
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {

                    journal.addEvent(
                            it.getString("UserName"),
                            new Event(
                                    Optional.ofNullable(it.getTimestamp("DT"))
                                            .map(Timestamp::toLocalDateTime).orElse(null),
                                    it.getString("StatusCode")
                            )
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return journal;
    }
}
