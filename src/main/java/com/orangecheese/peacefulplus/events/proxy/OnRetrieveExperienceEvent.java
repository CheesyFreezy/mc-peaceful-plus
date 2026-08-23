package com.orangecheese.peacefulplus.events.proxy;

import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import com.orangecheese.peacefulplus.configuration.PlayerWhitelistFlags;
import com.orangecheese.peacefulplus.events.WhiteListProxyEvent;
import org.bukkit.entity.ExperienceOrb;

import java.util.UUID;

public class OnRetrieveExperienceEvent extends WhiteListProxyEvent<PlayerPickupExperienceEvent> {
    public OnRetrieveExperienceEvent() {
        super(PlayerPickupExperienceEvent.class);
    }

    @Override
    public void onInvoke(PlayerPickupExperienceEvent event, PlayerWhitelistFlags flags) {
        ExperienceOrb orb = event.getExperienceOrb();
        int newXp = (int) (orb.getExperience() * flags.getExperienceMultiplier());
        orb.setExperience(newXp);
    }

    @Override
    public UUID getUuid(PlayerPickupExperienceEvent event) {
        return event.getPlayer().getUniqueId();
    }

    @Override
    public boolean hasFlag(PlayerWhitelistFlags flags) {
        return true;
    }
}
