package com.orangecheese.peacefulplus.events.normal;

import com.orangecheese.peacefulplus.PeacefulPlusPlugin;
import com.orangecheese.peacefulplus.gui.Menu;
import com.orangecheese.peacefulplus.gui.MenuClickEvent;
import com.orangecheese.peacefulplus.gui.MenuManager;
import com.orangecheese.peacefulplus.gui.MenuSession;
import com.orangecheese.peacefulplus.gui.node.MenuNode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.UUID;

public class MenuHandleEvent implements Listener {
    private final PeacefulPlusPlugin plugin;

    private final MenuManager menuManager;

    public MenuHandleEvent(PeacefulPlusPlugin plugin, MenuManager menuManager) {
        this.plugin = plugin;
        this.menuManager = menuManager;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        MenuSession session = menuManager.getSession(player.getUniqueId());
        if(session == null)
            return;

        Menu menu = session.getMenu();

        int slot = event.getRawSlot();

        MenuNode menuNode = menu.getNode(slot);
        if(menuNode != null && menuNode.isLocked())
            event.setCancelled(true);

        ClickType clickType = event.getClick();
        MenuClickEvent clickEvent = new MenuClickEvent(plugin, player, menu, slot, menuNode, clickType);

        if(menuNode != null)
            menuNode.onClick(clickEvent);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        UUID uuid = player.getUniqueId();

        MenuSession session = menuManager.getSession(uuid);
        if(session == null)
            return;

        if(session.isTransitioning())
            return;

        menuManager.stopSession(uuid);
    }
}