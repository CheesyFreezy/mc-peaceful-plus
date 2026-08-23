package com.orangecheese.peacefulplus.gui;

import java.util.Stack;

public class MenuSession {
    private Menu menu;

    private final Stack<Menu> history;

    private boolean transitioning;

    public MenuSession(Menu menu) {
        this.menu = menu;
        history = new Stack<>();
    }

    public void open() {
        open(this.menu);
    }

    public void open(Menu menu) {
        if (this.menu != null)
            history.push(this.menu);

        this.menu = menu;
        this.menu.setOnBeforeReopen(() -> transitioning = true);
        this.menu.setOnAfterReopen(() -> transitioning = false);

        _open();
    }

    public void back() {
        if (history.isEmpty())
            return;

        this.menu = history.pop();

        _open();
    }

    private void _open() {
        transitioning = true;
        menu.open();
        transitioning = false;
    }

    public Menu getMenu() {
        return menu;
    }

    public boolean isTransitioning() {
        return transitioning;
    }
}