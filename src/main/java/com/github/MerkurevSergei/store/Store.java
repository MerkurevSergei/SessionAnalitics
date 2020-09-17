package com.github.MerkurevSergei.store;

import com.github.MerkurevSergei.model.Record;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface Store {
    @NotNull Collection<Record> getJournal();
}
