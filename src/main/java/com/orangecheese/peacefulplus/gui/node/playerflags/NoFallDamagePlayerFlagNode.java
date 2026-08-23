package com.orangecheese.peacefulplus.gui.node.playerflags;

import com.orangecheese.peacefulplus.gui.node.PlayerFlagNode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class NoFallDamagePlayerFlagNode extends PlayerFlagNode {
    public NoFallDamagePlayerFlagNode(UUID uuid) {
        String title = "No fall damage";
        String description = "Disables all sources of falling damage.";
        ItemStack item = new ItemStack(Material.FEATHER);

        super(title, description, item, uuid);
    }

    @Override
    public void onToggle(Player player, boolean enabled, Runnable complete) {
        getFlags().setNoFallDamage(enabled);
        configuration.save();
        complete.run();
    }

    @Override
    public boolean isEnabled() {
        return configuration.getPlayerWhitelistFlags(getUuid()).isNoFallDamage();
    }
}