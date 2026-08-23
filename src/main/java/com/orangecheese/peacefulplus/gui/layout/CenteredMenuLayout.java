package com.orangecheese.peacefulplus.gui.layout;

import com.orangecheese.peacefulplus.gui.node.MenuNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CenteredMenuLayout implements IMenuLayout {
    private final int rows;

    private final int spacing;

    private Map<Integer, MenuNode> slots = Map.of();

    public CenteredMenuLayout(int rows, int spacing) {
        this.rows = rows;
        this.spacing = spacing;
    }

    @Override
    public Map<Integer, MenuNode> build(MenuNode[] nodes) {
        if (nodes.length == 0) {
            slots = Map.of();
            return slots;
        }

        int columns = 7;
        int requiredColumns = nodes.length + (nodes.length - 1) * spacing;
        int startColumn = 1 + (columns - requiredColumns) / 2;
        int row = rows / 2;

        Map<Integer, MenuNode> result = new HashMap<>();

        for (int i = 0; i < nodes.length; i++) {
            int column = startColumn + i * (spacing + 1);
            int slot = row * 9 + column;

            result.put(slot, nodes[i]);
        }

        slots = Map.copyOf(result);
        return slots;
    }

    @Override
    public MenuNode getNode(int slot) {
        return slots.get(slot);
    }

    @Override
    public Optional<Integer> getSlot(MenuNode node) {
        return slots.entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(node))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    @Override
    public int getSize() {
        return rows * 9;
    }

    @Override
    public int getAvailableSlots() {
        return rows * 7;
    }
}