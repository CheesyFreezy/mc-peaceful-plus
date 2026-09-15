package com.orangecheese.peacefulplus.gui.node.playerflags;

import com.orangecheese.peacefulplus.gui.node.PlayerFlagNode;
import com.orangecheese.peacefulplus.gui.parameters.NumberParameter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;

public class BreakingSpeedPlayerFlagNode extends PlayerFlagNode {
    public BreakingSpeedPlayerFlagNode(UUID uuid) {
        String title = "Block break speed";
        String description = "Sets the mining speed multiplier at which the player mines. The further the speed multiplier" +
                " is increased, the faster the player will mine using their mining tools.";
        ItemStack item = new ItemStack(Material.WOODEN_SHOVEL);

        super(title, description, item, uuid);
    }

    @Override
    public void onToggle(Player player, boolean enabled, Runnable complete) {
        Float miningSpeedMultiplier = getFlags().getBreakingSpeedMultiplier();

        if(!enabled) {
            getFlags().setBreakingSpeedMultiplier(null);
            configuration.save();

            updatePlayerBlockBreakSpeed(null);

            complete.run();
            return;
        }

        NumberParameter amountParameter = new NumberParameter(
                "amount",
                "Please enter the mining speed multiplier. The default is \"1\".",
                "Mining speed multiplier",
                0.1F,
                100,
                miningSpeedMultiplier != null ? miningSpeedMultiplier : 1,
                1);

        amountParameter
                .invoke(player)
                .thenAccept(amount -> {
                    getFlags().setBreakingSpeedMultiplier(amount);
                    configuration.save();

                    updatePlayerBlockBreakSpeed(amount);

                    complete.run();
                });
    }

    @Override
    public boolean isEnabled() {
        return configuration.getPlayerWhitelistFlags(getUuid()).getBreakingSpeedMultiplier() != null;
    }

    private void updatePlayerBlockBreakSpeed(Float miningSpeedMultiplier) {
        Player player = Bukkit.getPlayer(getUuid());
        if(player == null)
            return;

        if(miningSpeedMultiplier == null)
            miningSpeedMultiplier = 1F;

        Objects.requireNonNull(player.getAttribute(Attribute.BLOCK_BREAK_SPEED)).setBaseValue(miningSpeedMultiplier);
    }
}
