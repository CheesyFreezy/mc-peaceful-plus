package com.orangecheese.peacefulplus.events.normal;

import com.orangecheese.peacefulplus.configuration.PeacefulPlusConfiguration;
import com.orangecheese.peacefulplus.configuration.PlayerWhitelistFlags;
import com.twodevsstudio.simplejsonconfig.api.Config;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

import java.util.*;

public class OnHostileTargetEvent implements Listener {
    private final PeacefulPlusConfiguration configuration;

    private final Map<Player, Set<Entity>> playerTargets;

    private final Map<UUID, Set<UUID>> ignoredTargets = new HashMap<>();

    public OnHostileTargetEvent() {
        configuration = Config.getConfig(PeacefulPlusConfiguration.class);
        this.playerTargets = new HashMap<>();
    }

    @EventHandler
    public void onEntityTarget(EntityTargetLivingEntityEvent event) {
        Entity entity = event.getEntity();
        Entity target = event.getTarget();

        // Check whether the entity targeting is a mob.
        if (!(event.getEntity() instanceof Mob mob))
            return;

        // Check whether there is any target to be ignored.
        if (event.getTarget() instanceof Player player) {
            Set<UUID> ignored = ignoredTargets.get(player.getUniqueId());

            if (ignored != null && ignored.remove(mob.getUniqueId())) {
                if (ignored.isEmpty())
                    ignoredTargets.remove(player.getUniqueId());

                event.setCancelled(true);
                return;
            }
        }

        handleTargetEvent(event, target, entity, event.getReason());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        removeTarget(entity);
    }

    public void handleTargetEvent(Cancellable cancellable, Entity target, Entity entity, EntityTargetEvent.TargetReason reason) {
        // Check whether the target exists.
        if (target == null) {
            removeTarget(entity);
            return;
        }

        // Check if the target is not a player. If not, show normal targeting behavior.
        if (!(target instanceof Player player)) {
            removeTarget(entity);
            return;
        }

        // Remove the target for all players where this entity exists in.
        removeTarget(entity);

        // Validate the target reason, if given.
        if(reason != null) {
            // Prepare a collection of targeting reasons to ignore by this event.
            EntityTargetEvent.TargetReason[] ignoredTargetReasons = new EntityTargetEvent.TargetReason[] {
                    EntityTargetEvent.TargetReason.CLOSEST_ENTITY,
                    EntityTargetEvent.TargetReason.CLOSEST_PLAYER,
                    EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY,
                    EntityTargetEvent.TargetReason.TARGET_ATTACKED_NEARBY_ENTITY
            };

            // Ignore any target reasons from the set of ignored target reasons.
            if(Arrays.stream(ignoredTargetReasons).noneMatch(r -> r == reason))
                return;
        }

        // Check whether the player has their maximum entity targets flag set.
        PlayerWhitelistFlags playerWhitelistFlags = configuration.getPlayerWhitelistFlags(player.getUniqueId());
        Integer maximumEntityTargets = playerWhitelistFlags.getMaximumEntityTargets();
        if(maximumEntityTargets == null)
            return;

        // Retrieve the collection of entities that are currently targeting the player.
        Set<Entity> targets = playerTargets.computeIfAbsent(player, _ -> new HashSet<>());

        // Check the maximum allowed targets the player is set to have.
        if (targets.size() >= maximumEntityTargets) {
            cancellable.setCancelled(true);
            return;
        }

        // Add the entity to be targeting the player.
        targets.add(entity);
    }

    public Map<UUID, Set<UUID>> getIgnoredTargets() {
        return ignoredTargets;
    }

    private void removeTarget(Entity entity) {
        playerTargets.values().removeIf(targets -> {
            targets.remove(entity);
            return targets.isEmpty();
        });
    }
}
