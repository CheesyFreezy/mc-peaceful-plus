package com.orangecheese.peacefulplus.gui;

import com.orangecheese.peacefulplus.PeacefulPlusPlugin;
import com.orangecheese.peacefulplus.gui.features.IMenuFeature;
import com.orangecheese.peacefulplus.gui.layout.IMenuLayout;
import com.orangecheese.peacefulplus.gui.node.MenuNode;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class Menu {
    private final PeacefulPlusPlugin plugin;

    private final Player player;

    protected Component title;

    private final IMenuLayout layout;

    private Inventory inventory;

    private final List<MenuNode> nodes;

    private final Map<Integer, MenuNode> renderNodes;

    private Runnable onBeforeReopen;

    private Runnable onAfterReopen;

    public Menu(PeacefulPlusPlugin plugin, Player player, Component title, IMenuLayout layout) {
        this.plugin = plugin;
        this.player = player;
        this.title = title;
        this.layout = layout;
        nodes = new ArrayList<>();
        renderNodes = new HashMap<>();
    }

    public void addNode(MenuNode node) {
        nodes.add(node);

        layout.build(nodes.toArray(MenuNode[]::new));

        node.item().subscribe(itemStack -> {
            if(itemStack == null)
                return;

            Optional<Integer> optionalSlot = layout.getSlot(node);
            if(optionalSlot.isEmpty())
                return;

            inventory.setItem(optionalSlot.get(), itemStack);
        }, true);
    }

    public void setRenderNode(int slot, MenuNode node) {
        renderNodes.put(slot, node);

        node.item().subscribe(itemStack -> {
            if(itemStack == null)
                return;

            inventory.setItem(slot, itemStack);
        }, true);
    }

    public void clearNode(int slot) {
        if(slot < 0 || slot >= nodes.size())
            return;
        nodes.remove(slot);
    }

    public void clearNodes() {
        nodes.clear();
    }

    public MenuNode getNode(int slot) {
        MenuNode potentialRenderNode = renderNodes.get(slot);
        if(potentialRenderNode != null)
            return potentialRenderNode;

        return layout.getNode(slot);
    }

    public void render() {
        for(IMenuFeature feature : getFeatures())
            feature.onRender(this);

        onRender(layout.getAvailableSlots());

        Map<Integer, MenuNode> layoutNodes = layout.build(nodes.toArray(MenuNode[]::new));

        for(int i = 0; i < inventory.getSize(); i++) {
            if(!layoutNodes.containsKey(i)) {
                inventory.clear(i);
                continue;
            }

            MenuNode node = layoutNodes.get(i);
            ItemStack itemStack = node.item().get();

            if(i > inventory.getSize())
                continue;

            inventory.setItem(i, itemStack);
        }

        for (Map.Entry<Integer, MenuNode> entry : renderNodes.entrySet()) {
            int slot = entry.getKey();

            MenuNode node = entry.getValue();
            ItemStack itemStack = node.item().get();

            if(slot > inventory.getSize())
                continue;

            inventory.setItem(slot, itemStack);
        }
    }

    public void rebuild() {
        Inventory oldInventory = inventory;
        Inventory newInventory = createInventory();

        for (IMenuFeature feature : getFeatures())
            newInventory = feature.onRebuild(this, newInventory);

        inventory = newInventory;

        render();

        if(player.getOpenInventory().getTopInventory() == oldInventory)
            _reopen();
    }

    public void open() {
        for (IMenuFeature feature : getFeatures())
            feature.initialize(this);

        rebuild();
        _reopen();
    }

    private void _reopen() {
        if(onBeforeReopen != null)
            onBeforeReopen.run();

        player.openInventory(inventory);

        if(onAfterReopen != null)
            onAfterReopen.run();
    }

    public void setTitle(Component title) {
        this.title = title;
        rebuild();
    }

    public Inventory createInventory() {
        final int size = layout.getSize();
        inventory = Bukkit.createInventory(player, size, title);

        return inventory;
    }

    public void setOnBeforeReopen(Runnable onBeforeReopen) {
        this.onBeforeReopen = onBeforeReopen;
    }

    public void setOnAfterReopen(Runnable onAfterReopen) {
        this.onAfterReopen = onAfterReopen;
    }

    public void onRender(int availableSlots) {}

    public IMenuFeature[] getFeatures() {
        return new IMenuFeature[0];
    }

    public PeacefulPlusPlugin getPlugin() {
        return plugin;
    }

    public Player getPlayer() {
        return player;
    }

    public Component getTitle() {
        return title;
    }

    public Inventory getInventory() {
        return inventory;
    }
}