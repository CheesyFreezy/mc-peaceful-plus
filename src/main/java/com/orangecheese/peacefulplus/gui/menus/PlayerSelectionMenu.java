package com.orangecheese.peacefulplus.gui.menus;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.orangecheese.peacefulplus.PeacefulPlusPlugin;
import com.orangecheese.peacefulplus.gui.Menu;
import com.orangecheese.peacefulplus.gui.MenuManager;
import com.orangecheese.peacefulplus.gui.MenuSession;
import com.orangecheese.peacefulplus.gui.features.IMenuFeature;
import com.orangecheese.peacefulplus.gui.features.PaginationFeature;
import com.orangecheese.peacefulplus.gui.layout.BasicMenuLayout;
import com.orangecheese.peacefulplus.gui.node.PlayerHeadNode;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PlayerSelectionMenu extends Menu {
    private final PaginationFeature paginationFeature;

    public PlayerSelectionMenu(PeacefulPlusPlugin plugin, Player player) {
        super(plugin, player, Component.text("Choose a player:"), new BasicMenuLayout(3));

        paginationFeature = new PaginationFeature();
    }

    @Override
    public void onRender(int availableSlots) {
        int page = paginationFeature.getPage();

        List<OfflinePlayer> offlinePlayers = Arrays.stream(Bukkit.getOfflinePlayers())
                .sorted(Comparator.comparing(
                        OfflinePlayer::getName,
                        Comparator.nullsLast(String::compareToIgnoreCase)
                ))
                .skip((long) (page - 1) * availableSlots)
                .limit(availableSlots)
                .toList();

        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            offlinePlayer.getPlayerProfile().update()
                    .thenAccept(playerProfile -> {
                        if (playerProfile.getName() == null)
                            return;

                        PlayerHeadNode playerHeadNode = new PlayerHeadNode(
                                playerProfile,
                                (player, clickedPlayer) -> {
                                    PlayerMenu playerMenu =
                                            new PlayerMenu(getPlugin(), player, clickedPlayer);

                                    MenuManager menuManager = getPlugin().getMenuManager();
                                    MenuSession session =
                                            menuManager.getSession(player.getUniqueId());

                                    session.open(playerMenu);
                                }
                        );

                        addNode(playerHeadNode);
                    });
        }
    }

    @Override
    public IMenuFeature[] getFeatures() {
        return new IMenuFeature[] { paginationFeature };
    }
}
