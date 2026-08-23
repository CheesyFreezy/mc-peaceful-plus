package com.orangecheese.peacefulplus.gui.node.playerflags;

import com.orangecheese.peacefulplus.gui.node.PlayerFlagNode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class NoFoodDrainPlayerFlagNode extends PlayerFlagNode {
    public NoFoodDrainPlayerFlagNode(UUID uuid) {
        String title = "No food drain";
        String description = "Disables the hotbar's food drain that would be caused by running, regeneration or other relevant activities.";
        ItemStack item = new ItemStack(Material.BREAD);

        super(title, description, item, uuid);
    }

    @Override
    public void onToggle(Player player, boolean enabled, Runnable complete) {
        getFlags().setFoodDrain(enabled);
        configuration.save();
        complete.run();
    }

    @Override
    public boolean isEnabled() {
        return configuration.getPlayerWhitelistFlags(getUuid()).isFoodDrain();
    }
}