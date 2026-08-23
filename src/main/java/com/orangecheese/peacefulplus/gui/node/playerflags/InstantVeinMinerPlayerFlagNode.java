package com.orangecheese.peacefulplus.gui.node.playerflags;

import com.orangecheese.peacefulplus.gui.node.PlayerFlagNode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class InstantVeinMinerPlayerFlagNode extends PlayerFlagNode {
    public InstantVeinMinerPlayerFlagNode(UUID uuid) {
        String title = "Instant vein miner";
        String description = "When <enabled>enabled</enabled>, while the player is sneaking, all connected ores to a" +
                " specific type of ore will be mined instantly.";
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);

        super(title, description, item, uuid);
    }

    @Override
    public void onToggle(Player player, boolean enabled, Runnable complete) {
        getFlags().setInstantVeinMine(enabled);
        configuration.save();
        complete.run();
    }

    @Override
    public boolean isEnabled() {
        return configuration.getPlayerWhitelistFlags(getUuid()).isInstantVeinMine();
    }
}