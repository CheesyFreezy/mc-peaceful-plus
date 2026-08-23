package com.orangecheese.peacefulplus.gui.node.playerflags;

import com.orangecheese.peacefulplus.gui.node.PlayerFlagNode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class KeepInventoryPlayerFlagNode extends PlayerFlagNode {
    public KeepInventoryPlayerFlagNode(UUID uuid) {
        String title = "Keep inventory on death";
        String description = "When <enabled>enabled</enabled>, the player's inventory will be restored on death. All items will be retained.";
        ItemStack item = new ItemStack(Material.SKULL_BANNER_PATTERN);

        super(title, description, item, uuid);
    }

    @Override
    public void onToggle(Player player, boolean enabled, Runnable complete) {
        getFlags().setKeepInventory(enabled);
        configuration.save();
        complete.run();
    }

    @Override
    public boolean isEnabled() {
        return configuration.getPlayerWhitelistFlags(getUuid()).isKeepInventory();
    }
}