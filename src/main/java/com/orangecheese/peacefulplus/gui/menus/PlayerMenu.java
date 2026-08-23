package com.orangecheese.peacefulplus.gui.menus;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.orangecheese.peacefulplus.PeacefulPlusPlugin;
import com.orangecheese.peacefulplus.gui.Menu;
import com.orangecheese.peacefulplus.gui.features.IMenuFeature;
import com.orangecheese.peacefulplus.gui.features.PaginationFeature;
import com.orangecheese.peacefulplus.gui.layout.PaddedMenuLayout;
import com.orangecheese.peacefulplus.gui.node.MenuNode;
import com.orangecheese.peacefulplus.gui.node.playerflags.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerMenu extends Menu {
    private final PaginationFeature paginationFeature;

    private PlayerProfile playerProfile;

    public PlayerMenu(PeacefulPlusPlugin plugin, Player player, UUID targetUUID) {
        Component title = MiniMessage.miniMessage().deserialize("Loading profile...").color(NamedTextColor.GRAY);
        super(plugin, player, title, new PaddedMenuLayout(3, false));

        paginationFeature = new PaginationFeature();

        PlayerProfile playerProfile = Bukkit.getOfflinePlayer(targetUUID).getPlayerProfile();
        CompletableFuture<PlayerProfile> playerProfileFetch = playerProfile.update();

        playerProfileFetch
                .thenAcceptAsync(
                        fetchedPlayerProfile -> {
                            this.playerProfile = fetchedPlayerProfile;

                            Component profileTitle = Component.text(String.format("%1$s's settings", this.playerProfile.getName()));
                            setTitle(profileTitle);
                        }, runnable -> Bukkit.getScheduler().runTask(getPlugin(), runnable))
                .exceptionally(exception -> {
                    Component errorTitle = MiniMessage.miniMessage()
                            .deserialize("Unable to fetch player profile.")
                            .color(NamedTextColor.DARK_RED);
                    setTitle(errorTitle);
                    return null;
        });
    }

    @Override
    public void onRender(int availableSlots) {
        if(playerProfile == null)
            return;

        UUID uuid = playerProfile.getId();

        ItemStack healingPotionItem = new ItemStack(Material.POTION);
        PotionMeta healingPotionMeta = (PotionMeta) healingPotionItem.getItemMeta();
        healingPotionMeta.setBasePotionType(PotionType.HEALING);
        healingPotionItem.setItemMeta(healingPotionMeta);

        MaximumEntityTargetsPlayerFlagNode maximumEntityTargetsNode = new MaximumEntityTargetsPlayerFlagNode(uuid);
        NoFoodDrainPlayerFlagNode noFoodDrainNode = new NoFoodDrainPlayerFlagNode(uuid);
        IncreasedHealthRegenerationPlayerFlagNode increasedHealthRegenerationNode = new IncreasedHealthRegenerationPlayerFlagNode(uuid);
        NoFallDamagePlayerFlagNode noFallDamageNode = new NoFallDamagePlayerFlagNode(uuid);
        InfiniteDurabilityPlayerFlagNode infiniteDurabilityNode = new InfiniteDurabilityPlayerFlagNode(uuid);
        InfiniteFireworksPlayerFlagNode infiniteFireworksNode = new InfiniteFireworksPlayerFlagNode(uuid);
        InstantVeinMinerPlayerFlagNode instantVeinMinerNode = new InstantVeinMinerPlayerFlagNode(uuid);
        InstantTreeChopperPlayerFlagNode instantTreeChopperNode = new InstantTreeChopperPlayerFlagNode(uuid);
        KeepInventoryPlayerFlagNode keepInventoryNode = new KeepInventoryPlayerFlagNode(uuid);
        ExperienceMultiplayerPlayerFlagNode experienceMultiplayerNode = new ExperienceMultiplayerPlayerFlagNode(uuid);

        int page = paginationFeature.getPage();

        MenuNode[] nodes = Arrays.stream(new MenuNode[] {
                maximumEntityTargetsNode,
                noFoodDrainNode,
                increasedHealthRegenerationNode,
                noFallDamageNode,
                infiniteDurabilityNode,
                infiniteFireworksNode,
                instantVeinMinerNode,
                instantTreeChopperNode,
                keepInventoryNode,
                experienceMultiplayerNode
        })
                .skip((long) (page - 1) * availableSlots)
                .limit(availableSlots)
                .toArray(MenuNode[]::new);

        for(MenuNode node : nodes)
            addNode(node);
    }

    @Override
    public IMenuFeature[] getFeatures() {
        return new IMenuFeature[] { paginationFeature };
    }
}
