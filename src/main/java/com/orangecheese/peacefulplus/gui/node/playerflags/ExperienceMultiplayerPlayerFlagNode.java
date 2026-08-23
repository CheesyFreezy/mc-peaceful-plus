package com.orangecheese.peacefulplus.gui.node.playerflags;

import com.orangecheese.peacefulplus.gui.node.PlayerFlagNode;
import com.orangecheese.peacefulplus.gui.parameters.NumberParameter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class ExperienceMultiplayerPlayerFlagNode extends PlayerFlagNode {
    public ExperienceMultiplayerPlayerFlagNode(UUID uuid) {
        String title = "Experience multiplier";
        String description = "Sets the experience multiplayer for the player. When <enabled>enabled</enabled> the player " +
                "receives experience from any source, its value will be multiplied by this value.";
        ItemStack item = new ItemStack(Material.EXPERIENCE_BOTTLE);

        super(title, description, item, uuid);
    }

    @Override
    public void onToggle(Player player, boolean enabled, Runnable complete) {
        Float currentExperienceMultiplier = getFlags().getExperienceMultiplier();

        if(!enabled) {
            getFlags().setExperienceMultiplier(null);
            configuration.save();

            complete.run();
            return;
        }

        NumberParameter amountParameter = new NumberParameter(
                "amount",
                "Please enter the experience multiplier to apply to the player.",
                "Experience multiplier",
                0.1F,
                100,
                currentExperienceMultiplier != null ? currentExperienceMultiplier : 1F,
                0.1F);

        amountParameter
                .invoke(player)
                .thenAccept(amount -> {
                    getFlags().setExperienceMultiplier(amount);
                    configuration.save();

                    complete.run();
                });
    }

    @Override
    public boolean isEnabled() {
        return configuration.getPlayerWhitelistFlags(getUuid()).getExperienceMultiplier() != null;
    }
}