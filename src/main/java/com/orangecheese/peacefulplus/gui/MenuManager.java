package com.orangecheese.peacefulplus.gui;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MenuManager {
    private final Map<UUID, MenuSession> sessions;

    public MenuManager() {
        sessions = new HashMap<>();
    }

    public void startSession(Menu menu) {
        Player player = menu.getPlayer();
        MenuSession session = new MenuSession(menu);
        sessions.put(player.getUniqueId(), session);
        session.open();
    }

    public void stopSession(UUID uuid) {
        sessions.remove(uuid);
    }

    public MenuSession getSession(UUID uuid) {
        return sessions.get(uuid);
    }
}