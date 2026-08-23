package com.orangecheese.peacefulplus.gui.layout;

import com.orangecheese.peacefulplus.gui.node.MenuNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BasicMenuLayout implements IMenuLayout {
    private final int rows;

    private final HashMap<Integer, MenuNode> nodes;

    private final HashMap<MenuNode, Integer> slots;

    public BasicMenuLayout(int rows) {
        this.rows = rows;
        nodes = new HashMap<>();
        slots = new HashMap<>();
    }

    @Override
    public Map<Integer, MenuNode> build(MenuNode[] nodes) {
        this.nodes.clear();

        for(int i = 0; i < nodes.length; i++) {
            this.nodes.put(i, nodes[i]);
            slots.put(nodes[i], i);
        }

        return this.nodes;
    }

    @Override
    public MenuNode getNode(int slot) {
        return this.nodes.get(slot);
    }

    @Override
    public Optional<Integer> getSlot(MenuNode node) {
        return slots.containsKey(node) ? Optional.of(slots.get(node)) : Optional.empty();
    }

    @Override
    public int getSize() {
        return rows * 9;
    }

    @Override
    public int getAvailableSlots() {
        return getSize();
    }
}