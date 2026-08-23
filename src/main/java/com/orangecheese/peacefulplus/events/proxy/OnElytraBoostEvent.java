package com.orangecheese.peacefulplus.events.proxy;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import com.orangecheese.peacefulplus.configuration.PlayerWhitelistFlags;
import com.orangecheese.peacefulplus.events.WhiteListProxyEvent;

import java.util.UUID;

public class OnElytraBoostEvent extends WhiteListProxyEvent<PlayerElytraBoostEvent> {
    public OnElytraBoostEvent() {
        super(PlayerElytraBoostEvent.class);
    }

    @Override
    public void onInvoke(PlayerElytraBoostEvent event, PlayerWhitelistFlags flags) {
        event.setShouldConsume(false);
    }

    @Override
    public UUID getUuid(PlayerElytraBoostEvent event) {
        return event.getPlayer().getUniqueId();
    }

    @Override
    public boolean hasFlag(PlayerWhitelistFlags flags) {
        return flags.isInfiniteFireworks();
    }
}
