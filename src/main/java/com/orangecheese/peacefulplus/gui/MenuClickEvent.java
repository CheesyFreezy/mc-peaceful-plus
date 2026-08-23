package com.orangecheese.peacefulplus.gui;

import com.orangecheese.peacefulplus.PeacefulPlusPlugin;
import com.orangecheese.peacefulplus.gui.node.MenuNode;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class MenuClickEvent {
    private final PeacefulPlusPlugin plugin;

    private final Player player;

    private final Menu menu;

    private final int slot;

    private final MenuNode clickedNode;

    private final ClickType clickType;

    public MenuClickEvent(PeacefulPlusPlugin plugin, Player player, Menu menu, int slot, MenuNode clickedNode, ClickType clickType) {
        this.plugin = plugin;
        this.player = player;
        this.menu = menu;
        this.slot = slot;
        this.clickedNode = clickedNode;
        this.clickType = clickType;
    }

    public PeacefulPlusPlugin getPlugin() {
        return plugin;
    }

    public Player getPlayer() {
        return player;
    }

    public Menu getContext() {
        return menu;
    }

    public int getSlot() {
        return slot;
    }

    public MenuNode getClickedNode() {
        return clickedNode;
    }

    public ClickType getClickType() {
        return clickType;
    }
}