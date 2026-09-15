package com.orangecheese.peacefulplus.events.proxy;

import com.orangecheese.peacefulplus.PeacefulPlusPlugin;
import com.orangecheese.peacefulplus.configuration.PlayerWhitelistFlags;
import com.orangecheese.peacefulplus.events.WhiteListProxyEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.UUID;

public class OnKeepInventoryOnDeathEvent extends WhiteListProxyEvent<PlayerDeathEvent> {
    public OnKeepInventoryOnDeathEvent(PeacefulPlusPlugin plugin) {
        super(PlayerDeathEvent.class, plugin);
    }

    @Override
    public void onInvoke(PlayerDeathEvent event, PlayerWhitelistFlags flags) {
        event.setKeepInventory(true);
        event.getDrops().clear();

        event.setKeepLevel(true);
        event.setDroppedExp(0);
    }

    @Override
    public UUID getUuid(PlayerDeathEvent event) {
        return event.getPlayer().getUniqueId();
    }

    @Override
    public boolean hasFlag(PlayerWhitelistFlags flags) {
        return flags.isKeepInventory();
    }
}
