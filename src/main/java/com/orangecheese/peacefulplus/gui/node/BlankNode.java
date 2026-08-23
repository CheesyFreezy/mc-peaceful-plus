package com.orangecheese.peacefulplus.gui.node;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BlankNode extends MenuNode {
    public BlankNode() {
        ItemStack blank = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = blank.getItemMeta();
        meta.displayName(Component.text(" "));
        blank.setItemMeta(meta);

        item().set(blank);
    }
}