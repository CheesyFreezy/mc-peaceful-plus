package com.orangecheese.peacefulplus.events.proxy;

import com.orangecheese.peacefulplus.PeacefulPlusPlugin;
import com.orangecheese.peacefulplus.configuration.PlayerWhitelistFlags;
import com.orangecheese.peacefulplus.events.WhiteListProxyEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.UUID;

public class OnFallDamageEvent extends WhiteListProxyEvent<EntityDamageEvent> {
    public OnFallDamageEvent(PeacefulPlusPlugin plugin) {
        super(EntityDamageEvent.class, plugin);
    }

    @Override
    public void onInvoke(EntityDamageEvent event, PlayerWhitelistFlags flags) {
        if(event.getCause() != EntityDamageEvent.DamageCause.FALL)
            return;

        event.setCancelled(true);
    }

    @Override
    public UUID getUuid(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player player))
            return null;

        return player.getUniqueId();
    }

    @Override
    public boolean hasFlag(PlayerWhitelistFlags flags) {
        return flags.isNoFallDamage();
    }
}
