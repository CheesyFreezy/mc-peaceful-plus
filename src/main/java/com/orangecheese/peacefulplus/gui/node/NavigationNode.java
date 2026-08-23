package com.orangecheese.peacefulplus.gui.node;

import com.orangecheese.peacefulplus.gui.Menu;
import com.orangecheese.peacefulplus.gui.MenuClickEvent;
import com.orangecheese.peacefulplus.gui.MenuSession;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class NavigationNode extends MenuNode {
    private final Menu menu;

    public NavigationNode(Menu menu, String title, Material material) {
        this.menu = menu;

        MiniMessage miniMessage = MiniMessage.miniMessage();

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(miniMessage
                .deserialize(title)
                .color(NamedTextColor.BLUE)
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(List.of(
                miniMessage
                        .deserialize("» Left-click to open the menu.")
                        .color(NamedTextColor.DARK_GRAY)
                        .decoration(TextDecoration.ITALIC, false)
        ));

        item.setItemMeta(meta);

        item().set(item);
    }

    @Override
    public void onClick(MenuClickEvent event) {
        if(event.getClickType() != ClickType.LEFT)
            return;

        Player player = event.getPlayer();
        MenuSession session = event.getPlugin().getMenuManager().getSession(player.getUniqueId());
        session.open(menu);
    }
}
