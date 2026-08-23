package com.orangecheese.peacefulplus.events.proxy;

import com.orangecheese.peacefulplus.configuration.PlayerWhitelistFlags;
import com.orangecheese.peacefulplus.events.WhiteListProxyEvent;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class OnTreeChopperEvent extends WhiteListProxyEvent<BlockBreakEvent> {
    private static final Material[] LOG_WHITELIST = new Material[]{
            Material.ACACIA_LOG,
            Material.BIRCH_LOG,
            Material.CHERRY_LOG,
            Material.CRIMSON_STEM,
            Material.DARK_OAK_LOG,
            Material.JUNGLE_LOG,
            Material.MANGROVE_LOG,
            Material.OAK_LOG,
            Material.PALE_OAK_LOG,
            Material.SPRUCE_LOG,
            Material.WARPED_STEM
    };

    private static final BlockFace[] DIRECTIONS = {
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.SOUTH,
            BlockFace.EAST,
            BlockFace.WEST
    };

    public OnTreeChopperEvent() {
        super(BlockBreakEvent.class);
    }

    @Override
    public void onInvoke(BlockBreakEvent event, PlayerWhitelistFlags flags) {
        Player player = event.getPlayer();
        Block start = event.getBlock();

        if (!isLog(start.getType()))
            return;

        if (!player.isSneaking())
            return;

        if(player.getGameMode() != GameMode.SURVIVAL)
            return;

        Set<Block> visited = new HashSet<>();
        Queue<Block> queue = new ArrayDeque<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Block current = queue.poll();

            ItemStack handItem = player.getInventory().getItemInMainHand();
            current.breakNaturally(handItem);

            for (BlockFace face : DIRECTIONS) {
                Block neighbor = current.getRelative(face);

                if (!visited.add(neighbor))
                    continue;

                if (!isLog(neighbor.getType()))
                    continue;

                queue.add(neighbor);
            }
        }
    }

    private boolean isLog(Material material) {
        return Arrays.stream(LOG_WHITELIST)
                .anyMatch(whitelisted -> whitelisted == material);
    }

    @Override
    public UUID getUuid(BlockBreakEvent event) {
        return event.getPlayer().getUniqueId();
    }

    @Override
    public boolean hasFlag(PlayerWhitelistFlags flags) {
        return flags.isInstantTreeChopper();
    }
}
