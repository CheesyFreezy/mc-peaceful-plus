package com.orangecheese.peacefulplus.events.proxy;

import com.orangecheese.peacefulplus.PeacefulPlusPlugin;
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

public class OnVeinMineEvent extends WhiteListProxyEvent<BlockBreakEvent> {
    private static final Material[] VEIN_WHITELIST = new Material[]{
            Material.COAL_ORE,
            Material.DEEPSLATE_COAL_ORE,
            Material.COPPER_ORE,
            Material.DEEPSLATE_COPPER_ORE,
            Material.LAPIS_ORE,
            Material.DEEPSLATE_LAPIS_ORE,
            Material.IRON_ORE,
            Material.DEEPSLATE_IRON_ORE,
            Material.GOLD_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.REDSTONE_ORE,
            Material.DEEPSLATE_REDSTONE_ORE,
            Material.DIAMOND_ORE,
            Material.DEEPSLATE_DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.DEEPSLATE_EMERALD_ORE
    };

    private static final BlockFace[] DIRECTIONS = {
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.SOUTH,
            BlockFace.EAST,
            BlockFace.WEST
    };

    public OnVeinMineEvent(PeacefulPlusPlugin plugin) {
        super(BlockBreakEvent.class, plugin);
    }

    @Override
    public void onInvoke(BlockBreakEvent event, PlayerWhitelistFlags flags) {
        Player player = event.getPlayer();
        Block start = event.getBlock();

        if (!isVeinOre(start.getType()))
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

                if (!isVeinOre(neighbor.getType()))
                    continue;

                queue.add(neighbor);
            }
        }
    }

    private boolean isVeinOre(Material material) {
        return Arrays.stream(VEIN_WHITELIST)
                .anyMatch(whitelisted -> whitelisted == material);
    }

    @Override
    public UUID getUuid(BlockBreakEvent event) {
        return event.getPlayer().getUniqueId();
    }

    @Override
    public boolean hasFlag(PlayerWhitelistFlags flags) {
        return flags.isInstantVeinMine();
    }
}
