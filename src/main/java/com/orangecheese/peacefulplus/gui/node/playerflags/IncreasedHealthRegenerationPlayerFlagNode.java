package com.orangecheese.peacefulplus.gui.node.playerflags;

import com.orangecheese.peacefulplus.gui.node.PlayerFlagNode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.util.UUID;

public class IncreasedHealthRegenerationPlayerFlagNode extends PlayerFlagNode {
    public IncreasedHealthRegenerationPlayerFlagNode(UUID uuid) {
        String title = "Increased health regeneration";
        String description = "Increases the default health regeneration by twice the amount the source would normally give.";

        ItemStack healingPotionItem = new ItemStack(Material.POTION);
        PotionMeta healingPotionMeta = (PotionMeta) healingPotionItem.getItemMeta();
        healingPotionMeta.setBasePotionType(PotionType.HEALING);
        healingPotionItem.setItemMeta(healingPotionMeta);

        super(title, description, healingPotionItem, uuid);
    }

    @Override
    public void onToggle(Player player, boolean enabled, Runnable complete) {
        getFlags().setIncreasedHealthRegeneration(enabled);
        configuration.save();
        complete.run();
    }

    @Override
    public boolean isEnabled() {
        return configuration.getPlayerWhitelistFlags(getUuid()).isIncreasedHealthRegeneration();
    }
}