package com.orangecheese.peacefulplus.events.normal;

import io.papermc.paper.event.entity.WardenAngerChangeEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;

import java.util.Arrays;

public class OnHostileTargetWardenEvent implements Listener {
    private final OnHostileTargetEvent hostileTargetEvent;

    public OnHostileTargetWardenEvent(OnHostileTargetEvent hostileTargetEvent) {
        this.hostileTargetEvent = hostileTargetEvent;
    }

    @EventHandler
    public void onWardenTarget(WardenAngerChangeEvent event) {
        Entity entity = event.getEntity();
        Entity target = event.getTarget();

        hostileTargetEvent.handleTargetEvent(event, target, entity, null);

        if(event.isCancelled())
            event.setNewAnger(event.getOldAnger());
    }

    @EventHandler
    public void onWardenEffect(EntityPotionEffectEvent event) {
        Entity target = event.getEntity();
        Entity entity = event.getSource();

        EntityPotionEffectEvent.Action[] relevantActions = new EntityPotionEffectEvent.Action[] {
                EntityPotionEffectEvent.Action.ADDED,
                EntityPotionEffectEvent.Action.CHANGED
        };

        if(Arrays.stream(relevantActions).noneMatch(a -> a == event.getAction()))
            return;

        if(event.getCause() != EntityPotionEffectEvent.Cause.WARDEN)
            return;

        hostileTargetEvent.handleTargetEvent(event, target, entity, null);
    }
}