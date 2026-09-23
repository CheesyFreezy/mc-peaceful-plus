package com.orangecheese.peacefulplus;

import com.orangecheese.peacefulplus.commands.PeacefulPlusBaseCommand;
import com.orangecheese.peacefulplus.events.WhiteListProxyEvent;
import com.orangecheese.peacefulplus.events.normal.MenuHandleEvent;
import com.orangecheese.peacefulplus.events.normal.OnHostileTargetEvent;
import com.orangecheese.peacefulplus.events.normal.OnHostileTargetTNTEvent;
import com.orangecheese.peacefulplus.events.normal.OnHostileTargetWardenEvent;
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
        OnFoodLossEvent foodLossEvent = new OnFoodLossEvent(this);
        OnHealthRegenerationEvent healthRegenerationEvent = new OnHealthRegenerationEvent(this);
        OnFallDamageEvent fallDamageEvent = new OnFallDamageEvent(this);
        OnItemDamageEvent itemDamageEvent = new OnItemDamageEvent(this);
        OnElytraBoostEvent elytraBoostEvent = new OnElytraBoostEvent(this);
        OnVeinMineEvent veinMineEvent = new OnVeinMineEvent(this);
        OnTreeChopperEvent treeChopperEvent = new OnTreeChopperEvent(this);
        OnKeepInventoryOnDeathEvent keepInventoryOnDeathEvent = new OnKeepInventoryOnDeathEvent(this);
        OnRetrieveExperienceEvent retrieveExperienceEvent = new OnRetrieveExperienceEvent(this);
        OnDropVacuumEvent dropVacuumEvent = new OnDropVacuumEvent(this);

        registerWhiteListProxyEvent(foodLossEvent);
        registerWhiteListProxyEvent(healthRegenerationEvent);
        registerWhiteListProxyEvent(fallDamageEvent);
        registerWhiteListProxyEvent(itemDamageEvent);
        registerWhiteListProxyEvent(elytraBoostEvent);
        registerWhiteListProxyEvent(veinMineEvent);
        registerWhiteListProxyEvent(treeChopperEvent);
        registerWhiteListProxyEvent(keepInventoryOnDeathEvent);
        registerWhiteListProxyEvent(retrieveExperienceEvent);
        registerWhiteListProxyEvent(dropVacuumEvent);

        // Standard listeners.
        OnHostileTargetEvent hostileTargetEvent = new OnHostileTargetEvent();
        OnHostileTargetTNTEvent hostileTargetTNTEvent = new OnHostileTargetTNTEvent(hostileTargetEvent);
        OnHostileTargetWardenEvent hostileTargetWardenEvent = new OnHostileTargetWardenEvent(hostileTargetEvent);
        MenuHandleEvent menuHandleEvent = new MenuHandleEvent(this, menuManager);

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(hostileTargetEvent, this);
        pluginManager.registerEvents(hostileTargetTNTEvent, this);
        pluginManager.registerEvents(hostileTargetWardenEvent, this);
        pluginManager.registerEvents(menuHandleEvent, this);
    }

    private <T extends Event> void registerWhiteListProxyEvent(WhiteListProxyEvent<T> event) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        Listener dummyListener = new Listener() {};
        pluginManager.registerEvent(event.getEventClass(), dummyListener, EventPriority.HIGHEST, event, this, true);
    }
}