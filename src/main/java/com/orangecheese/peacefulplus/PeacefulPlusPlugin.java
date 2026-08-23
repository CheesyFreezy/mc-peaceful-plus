package com.orangecheese.peacefulplus;

import com.orangecheese.peacefulplus.commands.PeacefulPlusBaseCommand;
import com.orangecheese.peacefulplus.events.WhiteListProxyEvent;
import com.orangecheese.peacefulplus.events.normal.MenuHandleEvent;
import com.orangecheese.peacefulplus.events.normal.OnHostileTargetEvent;
import com.orangecheese.peacefulplus.events.proxy.*;
import com.orangecheese.peacefulplus.gui.MenuManager;
import com.orangecheese.peacefulplus.utility.PaperTasks;
import com.twodevsstudio.simplejsonconfig.SimpleJSONConfig;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PeacefulPlusPlugin extends JavaPlugin {
    private MenuManager menuManager;

    @Override
    public void onEnable() {
        PaperTasks.initialize(this);

        SimpleJSONConfig.INSTANCE.register(this);

        menuManager = new MenuManager();

        registerCommands();
        registerEvents();
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    private void registerCommands() {
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(new PeacefulPlusBaseCommand(this).createCommand());
        });
    }

    private void registerEvents() {
        // Proxy listeners.
        OnFoodLossEvent foodLossEvent = new OnFoodLossEvent();
        OnHealthRegenerationEvent healthRegenerationEvent = new OnHealthRegenerationEvent();
        OnFallDamageEvent fallDamageEvent = new OnFallDamageEvent();
        OnItemDamageEvent itemDamageEvent = new OnItemDamageEvent();
        OnElytraBoostEvent elytraBoostEvent = new OnElytraBoostEvent();
        OnVeinMineEvent veinMineEvent = new OnVeinMineEvent();
        OnTreeChopperEvent treeChopperEvent = new OnTreeChopperEvent();
        OnKeepInventoryOnDeathEvent keepInventoryOnDeathEvent = new OnKeepInventoryOnDeathEvent();
        OnRetrieveExperienceEvent retrieveExperienceEvent = new OnRetrieveExperienceEvent();

        registerWhiteListProxyEvent(foodLossEvent);
        registerWhiteListProxyEvent(healthRegenerationEvent);
        registerWhiteListProxyEvent(fallDamageEvent);
        registerWhiteListProxyEvent(itemDamageEvent);
        registerWhiteListProxyEvent(elytraBoostEvent);
        registerWhiteListProxyEvent(veinMineEvent);
        registerWhiteListProxyEvent(treeChopperEvent);
        registerWhiteListProxyEvent(keepInventoryOnDeathEvent);
        registerWhiteListProxyEvent(retrieveExperienceEvent);

        // Standard listeners.
        OnHostileTargetEvent hostileTargetEvent = new OnHostileTargetEvent();
        MenuHandleEvent menuHandleEvent = new MenuHandleEvent(this, menuManager);

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(hostileTargetEvent, this);
        pluginManager.registerEvents(menuHandleEvent, this);
    }

    private <T extends Event> void registerWhiteListProxyEvent(WhiteListProxyEvent<T> event) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Listener dummyListener = new Listener() {};
        pluginManager.registerEvent(event.getEventClass(), dummyListener, EventPriority.HIGHEST, event, this, true);
    }
}