package com.orangecheese.peacefulplus.events.proxy;

import com.orangecheese.peacefulplus.configuration.PlayerWhitelistFlags;
import com.orangecheese.peacefulplus.events.WhiteListProxyEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;

import java.util.UUID;

public class OnItemDamageEvent extends WhiteListProxyEvent<PlayerItemDamageEvent> {
    public OnItemDamageEvent() {
        super(PlayerItemDamageEvent.class);
    }

    @Override
    public void onInvoke(PlayerItemDamageEvent event, PlayerWhitelistFlags flags) {
        event.setCancelled(true);
    }

    @Override
    public UUID getUuid(PlayerItemDamageEvent event) {
        return event.getPlayer().getUniqueId();
    }

    @Override
    public boolean hasFlag(PlayerWhitelistFlags flags) {
        return flags.isInfiniteDurability();
    }
}
