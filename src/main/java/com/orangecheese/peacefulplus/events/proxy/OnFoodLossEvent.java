package com.orangecheese.peacefulplus.events.proxy;

import com.orangecheese.peacefulplus.configuration.PlayerWhitelistFlags;
import com.orangecheese.peacefulplus.events.WhiteListProxyEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.UUID;

public class OnFoodLossEvent extends WhiteListProxyEvent<FoodLevelChangeEvent> {
    public OnFoodLossEvent() {
        super(FoodLevelChangeEvent.class);
    }

    @Override
    public void onInvoke(FoodLevelChangeEvent event, PlayerWhitelistFlags flags) {
        if(!(event.getEntity() instanceof Player player))
            return;

        int currentFoodLevel = player.getFoodLevel();
        int newFoodLevel = event.getFoodLevel();

        if(currentFoodLevel <= newFoodLevel)
            return;

        event.setCancelled(true);
    }

    @Override
    public UUID getUuid(FoodLevelChangeEvent event) {
        Entity entity = event.getEntity();

        if(!(entity instanceof Player player))
            return null;

        return player.getUniqueId();
    }

    @Override
    public boolean hasFlag(PlayerWhitelistFlags flags) {
        return flags.isFoodDrain();
    }
}