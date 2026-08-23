package com.orangecheese.peacefulplus.events.normal;

import com.orangecheese.peacefulplus.configuration.PeacefulPlusConfiguration;
import com.orangecheese.peacefulplus.configuration.PlayerWhitelistFlags;
import com.twodevsstudio.simplejsonconfig.api.Config;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.*;

public class OnHostileTargetEvent implements Listener {
    private final PeacefulPlusConfiguration configuration;

    private final Map<Player, Set<Entity>> playerTargets;

    public OnHostileTargetEvent() {
        configuration = Config.getConfig(PeacefulPlusConfiguration.class);
        this.playerTargets = new HashMap<>();
    }

    @EventHandler
    public void onEntityTarget(EntityTargetLivingEntityEvent event) {
        Entity entity = event.getEntity();

        if(!(event.getEntity() instanceof Mob))
            return;

        if (event.getTarget() == null) {
            removeTarget(entity);
            return;
        }

        if (!(event.getTarget() instanceof Player player)) {
            removeTarget(entity);
            return;
        }

        removeTarget(entity);

        EntityTargetEvent.TargetReason[] ignoredTargetReasons = new EntityTargetEvent.TargetReason[] {
                EntityTargetEvent.TargetReason.CLOSEST_ENTITY,
                EntityTargetEvent.TargetReason.CLOSEST_PLAYER,
                EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY,
                EntityTargetEvent.TargetReason.TARGET_ATTACKED_NEARBY_ENTITY
        };

        if(Arrays.stream(ignoredTargetReasons).noneMatch(reason -> reason == event.getReason()))
            return;

        PlayerWhitelistFlags playerWhitelistFlags = configuration.getPlayerWhitelistFlags(player.getUniqueId());
        Integer maximumEntityTargets = playerWhitelistFlags.getMaximumEntityTargets();
        if(maximumEntityTargets == null)
            return;

        Set<Entity> targets = playerTargets.computeIfAbsent(player, _ -> new HashSet<>());

        if (targets.size() >= maximumEntityTargets) {
            event.setCancelled(true);
            return;
        }

        targets.add(entity);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        removeTarget(entity);
    }

    private void removeTarget(Entity entity) {
        playerTargets.values().removeIf(targets -> {
            targets.remove(entity);
            return targets.isEmpty();
        });
    }
}
