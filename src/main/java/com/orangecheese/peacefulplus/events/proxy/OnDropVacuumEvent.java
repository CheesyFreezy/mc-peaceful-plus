package com.orangecheese.peacefulplus.events.proxy;

import com.orangecheese.peacefulplus.PeacefulPlusPlugin;
import com.orangecheese.peacefulplus.configuration.PlayerWhitelistFlags;
import com.orangecheese.peacefulplus.events.WhiteListProxyEvent;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;

public class OnDropVacuumEvent extends WhiteListProxyEvent<BlockDropItemEvent> {
    public OnDropVacuumEvent(PeacefulPlusPlugin plugin) {
        super(BlockDropItemEvent.class, plugin);
    }

    @Override
    public void onInvoke(BlockDropItemEvent event, PlayerWhitelistFlags flags) {
        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();

        List<Item> drops = event.getItems();
        for (Item drop : drops) {
            drop.setPickupDelay(0);
            drop.setOwner(player.getUniqueId());

            startVacuum(drop, player);
        }
    }

    private void startVacuum(Item item, Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!item.isValid() || !player.isOnline()) {
                    cancel();
                    return;
                }

                Location itemLocation = item.getLocation();
                Location playerLocation = player.getLocation().add(0, 0.5, 0);

                double distance = itemLocation.distance(playerLocation);

                if (distance < 0.75) {
                    item.setVelocity(new Vector(0, 0, 0));
                    cancel();
                    return;
                }

                Vector direction = playerLocation.toVector()
                        .subtract(itemLocation.toVector())
                        .normalize();

                double speed = Math.min(0.6, distance * 0.25);

                item.setVelocity(direction.multiply(speed));
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    @Override
    public UUID getUuid(BlockDropItemEvent event) {
        return event.getPlayer().getUniqueId();
    }

    @Override
    public boolean hasFlag(PlayerWhitelistFlags flags) {
        return flags.isDropVacuum();
    }
}
