package com.orangecheese.peacefulplus.gui.node.playerflags;

import com.orangecheese.peacefulplus.gui.node.PlayerFlagNode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class InstantTreeChopperPlayerFlagNode extends PlayerFlagNode {
    public InstantTreeChopperPlayerFlagNode(UUID uuid) {
        String title = "Instant tree chopper";
        String description = "When <enabled>enabled</enabled>, while the player is sneaking, all connected logs to a" +
                " specific type of log will be chopped instantly.";
        ItemStack item = new ItemStack(Material.DIAMOND_AXE);

        super(title, description, item, uuid);
    }

    @Override
    public void onToggle(Player player, boolean enabled, Runnable complete) {
        getFlags().setInstantTreeChopper(enabled);
        configuration.save();
        complete.run();
    }

    @Override
    public boolean isEnabled() {
        return configuration.getPlayerWhitelistFlags(getUuid()).isInstantTreeChopper();
    }
}