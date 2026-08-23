package com.orangecheese.peacefulplus.gui.layout;

import com.orangecheese.peacefulplus.gui.node.MenuNode;

import java.util.Map;
import java.util.Optional;

public interface IMenuLayout {
    Map<Integer, MenuNode> build(MenuNode[] nodes);

    MenuNode getNode(int slot);

    Optional<Integer> getSlot(MenuNode node);

    int getSize();

    int getAvailableSlots();
}