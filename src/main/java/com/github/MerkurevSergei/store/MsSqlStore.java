package com.github.MerkurevSergei.store;

import com.github.MerkurevSergei.model.Record;
import org.apache.commons.dbcp2.BasicDataSource;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class MsSqlStore implements Store {

    private final BasicDataSource pool = new BasicDataSource();

    public MsSqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
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
    public @NotNull Collection<Record> getJournal() {
        List<Record> journal = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM Securis WHERE DT > ? ORDER BY DT")
        ) {
            ps.setTimestamp(1, Timestamp.valueOf("2020-09-16 00:00:00"));
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    journal.add(new Record(
                            it.getString("Adress"),
                            Optional.ofNullable(it.getTimestamp("DT"))
                                    .map(Timestamp::toLocalDateTime)
                                    .orElse(null),
                            it.getString("StatusCode")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return journal;
    }
}
