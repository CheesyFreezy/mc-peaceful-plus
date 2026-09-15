package com.orangecheese.peacefulplus.gui.node.playerflags;

import com.orangecheese.peacefulplus.gui.node.PlayerFlagNode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class DropVacuumPlayerFlagNode extends PlayerFlagNode {
    public DropVacuumPlayerFlagNode(UUID uuid) {
        String title = "Drop vacuum";
        String description = "When <enabled>enabled</enabled>, all drops that came from breaking blocks by the player," +
                " will directly be added to the player's inventory.";
        ItemStack item = new ItemStack(Material.BUNDLE);

        super(title, description, item, uuid);
    }

    @Override
    public void onToggle(Player player, boolean enabled, Runnable complete) {
        getFlags().setDropVacuum(enabled);
        configuration.save();
        complete.run();
    }

    @Override
    public boolean isEnabled() {
        return configuration.getPlayerWhitelistFlags(getUuid()).isDropVacuum();
    }
}