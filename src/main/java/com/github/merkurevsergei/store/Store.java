package com.github.merkurevsergei.store;

import com.github.merkurevsergei.model.reports.UsersDayReport;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.Collection;

public interface Store {
    @NotNull Collection<String> getUsers();

    @NotNull UsersDayReport getJournal(Timestamp start, Timestamp end);
}
