package com.orangecheese.peacefulplus.events.normal;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashSet;

public class OnHostileTargetTNTEvent implements Listener {
    private final OnHostileTargetEvent hostileTargetEvent;

    public OnHostileTargetTNTEvent(OnHostileTargetEvent hostileTargetEvent) {
        this.hostileTargetEvent = hostileTargetEvent;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Mob mob))
            return;

        if (!(event.getDamager() instanceof TNTPrimed tnt))
            return;

        Entity source = tnt.getSource();

        if (!(source instanceof Player player))
            return;

        hostileTargetEvent.getIgnoredTargets()
                .computeIfAbsent(player.getUniqueId(), _ -> new HashSet<>())
                .add(mob.getUniqueId());
    }
}