package com.orangecheese.peacefulplus.gui.node;

import com.orangecheese.peacefulplus.gui.MenuClickEvent;
import com.orangecheese.peacefulplus.utility.ReactiveProperty;
import org.bukkit.inventory.ItemStack;

public abstract class MenuNode {
    private final ReactiveProperty<ItemStack> item;

    public MenuNode() {
        this.item = new ReactiveProperty<>();
    }

    public ReactiveProperty<ItemStack> item() {
        return item;
    }

    public void onClick(MenuClickEvent event) {}

    public boolean isLocked() {
        return true;
    }
}