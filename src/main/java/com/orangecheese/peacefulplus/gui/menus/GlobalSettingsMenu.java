package com.orangecheese.peacefulplus.gui.menus;

import com.orangecheese.peacefulplus.PeacefulPlusPlugin;
import com.orangecheese.peacefulplus.gui.Menu;
import com.orangecheese.peacefulplus.gui.layout.BasicMenuLayout;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class GlobalSettingsMenu extends Menu {
    public GlobalSettingsMenu(PeacefulPlusPlugin plugin, Player player) {
        Component title = Component.text("Global settings");
        super(plugin, player, title, new BasicMenuLayout(3));
    }

    @Override
    public void onRender(int availableSlots) {

    }
}
