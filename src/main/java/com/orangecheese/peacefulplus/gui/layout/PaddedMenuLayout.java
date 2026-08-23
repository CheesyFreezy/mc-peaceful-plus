package com.orangecheese.peacefulplus.gui.layout;

import com.orangecheese.peacefulplus.gui.node.BlankNode;
import com.orangecheese.peacefulplus.gui.node.MenuNode;

import java.util.*;

public class PaddedMenuLayout implements IMenuLayout {
    private final int rows;

    private final boolean showPadding;

    private final List<MenuNode> nodes;

    public PaddedMenuLayout(int rows, boolean showPadding) {
        this.rows = Math.min(rows, 3);
        this.showPadding = showPadding;
        this.nodes = new ArrayList<>();
    }

    @Override
    public Map<Integer, MenuNode> build(MenuNode[] nodes) {
        this.nodes.clear();
        this.nodes.addAll(Arrays.asList(nodes));

        Map<Integer, MenuNode> nodesMap = new HashMap<>();

        if (showPadding) {
            nodesMap.putAll(fillRow(0));
            nodesMap.putAll(fillRow(rows + 1));
        }

        int nodeIndex = 0;

        for (int row = 0; row < rows; row++) {
            int baseSlot = (row + 1) * 9;

            if(showPadding) {
                nodesMap.put(baseSlot, new BlankNode());
                nodesMap.put(baseSlot + 8, new BlankNode());
            }

            for (int slot = 1; slot <= 7; slot++) {
                if (nodeIndex >= nodes.length)
                    continue;
                nodesMap.put(baseSlot + slot, nodes[nodeIndex++]);
            }
        }

        return nodesMap;
    }

    private Map<Integer, MenuNode> fillRow(int rowIndex) {
        Map<Integer, MenuNode> rowNodes = new HashMap<>();

        for(int i = 0; i < 9; i++) {
            int offset = rowIndex * 9;
            rowNodes.put(i + offset, new BlankNode());
        }

        return rowNodes;
    }

    @Override
    public MenuNode getNode(int slot) {
        int row = slot / 9 - 1;
        int column = slot % 9 - 1;

        if (row < 0 || row >= rows || column < 0 || column >= 7)
            return null;

        return nodes.get(row * 7 + column);
    }

    @Override
    public Optional<Integer> getSlot(MenuNode node) {
        int index = nodes.indexOf(node);

        if (index < 0)
            return Optional.empty();

        int row = index / 7;
        int column = index % 7;

        return Optional.of((row + 1) * 9 + column + 1);
    }

    @Override
    public int getSize() {
        return (rows + 2) * 9;
    }

    @Override
    public int getAvailableSlots() {
        return rows * 7;
    }
}
