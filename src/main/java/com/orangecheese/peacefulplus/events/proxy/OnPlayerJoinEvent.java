package com.orangecheese.peacefulplus.events.proxy;

import com.orangecheese.peacefulplus.configuration.PlayerWhitelistFlags;
import com.orangecheese.peacefulplus.events.WhiteListProxyEvent;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;
import java.util.UUID;

public class OnPlayerJoinEvent extends WhiteListProxyEvent<PlayerJoinEvent> {
    public OnPlayerJoinEvent() {
        super(PlayerJoinEvent.class);
    }

    @Override
    public void onInvoke(PlayerJoinEvent event, PlayerWhitelistFlags flags) {
        Player player = event.getPlayer();

        // Sets the block-break speed attribute for the player.
        Float miningSpeedMultiplier = flags.getBreakingSpeedMultiplier();
        Objects.requireNonNull(player.getAttribute(Attribute.BLOCK_BREAK_SPEED)).setBaseValue(miningSpeedMultiplier);
    }

    @Override
    public UUID getUuid(PlayerJoinEvent event) {
        return event.getPlayer().getUniqueId();
    }

    @Override
    public boolean hasFlag(PlayerWhitelistFlags flags) {
        return flags.getBreakingSpeedMultiplier() != null;
    }
}
