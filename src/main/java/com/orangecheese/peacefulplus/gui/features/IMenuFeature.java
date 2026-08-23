package com.orangecheese.peacefulplus.gui.features;

import com.orangecheese.peacefulplus.gui.Menu;
import org.bukkit.inventory.Inventory;

public interface IMenuFeature {
    void initialize(Menu menu);

    default Inventory onRebuild(Menu menu, Inventory inventory) {
        return inventory;
    }

    default void onRender(Menu menu) {}
}