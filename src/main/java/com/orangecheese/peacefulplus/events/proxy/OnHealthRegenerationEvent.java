package com.orangecheese.peacefulplus.events.proxy;

import com.orangecheese.peacefulplus.configuration.PlayerWhitelistFlags;
import com.orangecheese.peacefulplus.events.WhiteListProxyEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.UUID;

public class OnHealthRegenerationEvent extends WhiteListProxyEvent<EntityRegainHealthEvent> {
    public OnHealthRegenerationEvent() {
        super(EntityRegainHealthEvent.class);
    }

    @Override
    public void onInvoke(EntityRegainHealthEvent event, PlayerWhitelistFlags flags) {
        event.setAmount(event.getAmount() * 2);
    }

    @Override
    public UUID getUuid(EntityRegainHealthEvent event) {
        if(!(event.getEntity() instanceof Player player))
            return null;

        return player.getUniqueId();
    }

    @Override
    public boolean hasFlag(PlayerWhitelistFlags flags) {
        return flags.isIncreasedHealthRegeneration();
    }
}
