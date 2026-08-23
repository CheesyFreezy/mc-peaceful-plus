package com.orangecheese.peacefulplus.gui.node.playerflags;

import com.orangecheese.peacefulplus.gui.node.PlayerFlagNode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class InfiniteDurabilityPlayerFlagNode extends PlayerFlagNode {
    public InfiniteDurabilityPlayerFlagNode(UUID uuid) {
        String title = "Infinite durability";
        String description = "Disables the ability for weapons, tools, armor and other equipment to have the durability drained." +
                " While <enabled>enabled</enabled>, the item will remain its current durability.";
        ItemStack item = new ItemStack(Material.ANVIL);

        super(title, description, item, uuid);
    }

    @Override
    public void onToggle(Player player, boolean enabled, Runnable complete) {
        getFlags().setInfiniteDurability(enabled);
        configuration.save();
        complete.run();
    }

    @Override
    public boolean isEnabled() {
        return configuration.getPlayerWhitelistFlags(getUuid()).isInfiniteDurability();
    }
}