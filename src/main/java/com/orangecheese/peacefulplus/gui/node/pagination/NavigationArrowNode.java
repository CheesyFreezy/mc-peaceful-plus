package com.orangecheese.peacefulplus.gui.node.pagination;

import com.orangecheese.peacefulplus.gui.MenuClickEvent;
import com.orangecheese.peacefulplus.gui.features.PaginationFeature;
import com.orangecheese.peacefulplus.gui.node.MenuNode;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NavigationArrowNode extends MenuNode {
    private final PaginationFeature paginationFeature;

    private final boolean forward;

    public NavigationArrowNode(PaginationFeature paginationFeature, boolean forward) {
        this.paginationFeature = paginationFeature;
        this.forward = forward;

        ItemStack arrow = new ItemStack(Material.ARROW);
        ItemMeta meta = arrow.getItemMeta();

        String title = forward ? "Next page »" : "« Previous page";
        meta.displayName(MiniMessage.miniMessage()
                .deserialize(title)
                .decoration(TextDecoration.ITALIC, false)
                .color(NamedTextColor.BLUE));

        arrow.setItemMeta(meta);

        item().set(arrow);
    }

    @Override
    public void onClick(MenuClickEvent event) {
        if(event.getClickType() != ClickType.LEFT)
            return;

        if(forward)
            paginationFeature.nextPage();
        else
            paginationFeature.previousPage();
    }
}