package com.orangecheese.peacefulplus.gui.features;

import com.orangecheese.peacefulplus.gui.Menu;
import com.orangecheese.peacefulplus.gui.node.pagination.NavigationArrowNode;
import com.orangecheese.peacefulplus.utility.ReactiveProperty;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class PaginationFeature implements IMenuFeature {
    private final ReactiveProperty<Integer> page;

    public PaginationFeature() {
        page = new ReactiveProperty<>(1);
    }

    public void nextPage() {
        page.set(page.get() + 1);
    }

    public void previousPage() {
        if(page.get() == 1)
            return;
        page.set(page.get() - 1);
    }

    public int getPage() {
        return page.get();
    }

    @Override
    public void initialize(Menu menu) {
        page.subscribe(_ -> menu.rebuild(), false);
    }

    @Override
    public Inventory onRebuild(Menu menu, Inventory inventory) {
        final int newSize = inventory.getSize() + 9;
        final Component newTitle = MiniMessage.miniMessage()
                .deserialize(
                        "<title> (#<page>)",
                        Placeholder.component("title", menu.getTitle()),
                        Placeholder.parsed("page", String.valueOf(page.get()))
                );
        return Bukkit.createInventory(inventory.getHolder(), newSize, newTitle);
    }

    @Override
    public void onRender(Menu menu) {
        menu.clearNodes();

        final int rows = menu.getInventory().getSize() / 9;

        NavigationArrowNode previousArrow = new NavigationArrowNode(this, false);
        NavigationArrowNode nextArrow = new NavigationArrowNode(this, true);

        final int baseSlot = (rows - 1) * 9 - 1;
        final int previousArrowSlot = baseSlot + 1;
        final int nextArrowSlot = baseSlot + 9;

        if(page.get() > 1)
            menu.setRenderNode(previousArrowSlot, previousArrow);
        else
            menu.clearNode(previousArrowSlot);

        menu.setRenderNode(nextArrowSlot, nextArrow);
    }
}
