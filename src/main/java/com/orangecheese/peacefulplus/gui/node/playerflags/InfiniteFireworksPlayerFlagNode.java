package com.orangecheese.peacefulplus.gui.node.playerflags;

import com.orangecheese.peacefulplus.gui.node.PlayerFlagNode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class InfiniteFireworksPlayerFlagNode extends PlayerFlagNode {
    public InfiniteFireworksPlayerFlagNode(UUID uuid) {
        String title = "Infinite fireworks";
        String description = "Disables the consumption of firework rockets when using them while flying with an elytra.";
        ItemStack item = new ItemStack(Material.FIREWORK_ROCKET);

        super(title, description, item, uuid);
    }

    @Override
    public void onToggle(Player player, boolean enabled, Runnable complete) {
        getFlags().setInfiniteFireworks(enabled);
        configuration.save();
        complete.run();
    }

    @Override
    public boolean isEnabled() {
        return configuration.getPlayerWhitelistFlags(getUuid()).isInfiniteFireworks();
    }
}
