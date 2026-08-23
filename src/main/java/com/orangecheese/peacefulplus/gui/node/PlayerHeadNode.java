package com.orangecheese.peacefulplus.gui.node;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.orangecheese.peacefulplus.gui.MenuClickEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;

public class PlayerHeadNode extends MenuNode {
    private final BiConsumer<Player, UUID> onClick;

    private final PlayerProfile playerProfile;

    public PlayerHeadNode(PlayerProfile playerProfile, BiConsumer<Player, UUID> onClick) {
        this.playerProfile = playerProfile;
        this.onClick = onClick;

        MiniMessage miniMessage = MiniMessage.miniMessage();

        Component title = miniMessage
                .deserialize(Objects.requireNonNull(playerProfile.getName()))
                .color(NamedTextColor.GOLD)
                .decoration(TextDecoration.BOLD, true)
                .decoration(TextDecoration.ITALIC, false);

        List<Component> lore = new ArrayList<>();
        lore.add(miniMessage
                .deserialize("Left-click to change settings.")
                .color(NamedTextColor.GRAY)
                .decoration(TextDecoration.ITALIC, false));

        setMetaData(title, lore);
    }

    private void setMetaData(Component title, List<Component> lore) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);

        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();

        skullMeta.displayName(title);
        skullMeta.lore(lore);
        skullMeta.setPlayerProfile(playerProfile);
        item.setItemMeta(skullMeta);

        item().set(item);
    }

    @Override
    public void onClick(MenuClickEvent event) {
        if(event.getClickType() != ClickType.LEFT)
            return;

        Player player = event.getPlayer();

        onClick.accept(player, playerProfile.getId());
    }
}