package com.orangecheese.peacefulplus.gui.node.playerflags;

import com.orangecheese.peacefulplus.gui.node.PlayerFlagNode;
import com.orangecheese.peacefulplus.gui.parameters.NumberParameter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class MaximumEntityTargetsPlayerFlagNode extends PlayerFlagNode {
    public MaximumEntityTargetsPlayerFlagNode(UUID uuid) {
        String title = "Maximum entity targets";
        String description = "Sets the maximum amount of monsters that can concurrently attack the player at the same time." +
                " When <enabled>enabled</enabled>, the set amount is the maximum amount of monsters that can attack you." +
                " When <disabled>disabled</disabled>, there will be no limit on the maximum amount of monsters that can attack you.";
        ItemStack item = new ItemStack(Material.ZOMBIE_SPAWN_EGG);

        super(title, description, item, uuid);
    }

    @Override
    public void onToggle(Player player, boolean enabled, Runnable complete) {
        Integer maximumEntityTargets = getFlags().getMaximumEntityTargets();

        if(!enabled) {
            getFlags().setMaximumEntityTargets(null);
            configuration.save();

            complete.run();
            return;
        }

        NumberParameter amountParameter = new NumberParameter(
                "amount",
                "Please enter the maximum amount of allowed entities to target the player.",
                "Maximum targets",
                0,
                64,
                maximumEntityTargets != null ? maximumEntityTargets : 0,
                1);

        amountParameter
                .invoke(player)
                .thenAccept(amount -> {
                    getFlags().setMaximumEntityTargets(amount.intValue());
                    configuration.save();

                    complete.run();
                });
    }

    @Override
    public boolean isEnabled() {
        return configuration.getPlayerWhitelistFlags(getUuid()).getMaximumEntityTargets() != null;
    }
}