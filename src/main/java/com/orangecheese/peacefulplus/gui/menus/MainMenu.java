package com.orangecheese.peacefulplus.gui.menus;

import com.orangecheese.peacefulplus.PeacefulPlusPlugin;
import com.orangecheese.peacefulplus.gui.Menu;
import com.orangecheese.peacefulplus.gui.layout.CenteredMenuLayout;
import com.orangecheese.peacefulplus.gui.node.NavigationNode;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MainMenu extends Menu {
    public MainMenu(PeacefulPlusPlugin plugin, Player player) {
        Component title = Component.text("Peaceful+ settings");
        super(plugin, player, title, new CenteredMenuLayout(3, 1));
    }

    @Override
    public void onRender(int availableSlots) {
        NavigationNode playerSelectionNode = new NavigationNode(
                new PlayerSelectionMenu(getPlugin(), getPlayer()),
                "Players",
                Material.PLAYER_HEAD);

        NavigationNode globalSettingsNode = new NavigationNode(
                new GlobalSettingsMenu(getPlugin(), getPlayer()),
                "Global settings",
                Material.GRASS_BLOCK);

        addNode(playerSelectionNode);
        //addNode(globalSettingsNode);
    }
}
