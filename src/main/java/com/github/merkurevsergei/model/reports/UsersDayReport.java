package com.github.merkurevsergei.model.reports;

import com.github.merkurevsergei.model.Event;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class UsersDayReport implements Iterable<Map.Entry<String, List<Event>>> {
    private final Map<String, List<Event>> sessions = new LinkedHashMap<>();

    public void addEvent(String user, Event event) {
        sessions.putIfAbsent(user, new ArrayList<>());
        List<Event> events = sessions.get(user);
        events.add(event);
    }

    public List<Event> getEvents(String user) {
        return sessions.getOrDefault(user, new ArrayList<>());
    }

    @NotNull
    @Override
    public Iterator<Map.Entry<String, List<Event>>> iterator() {
        return sessions.entrySet().iterator();
    }
}
