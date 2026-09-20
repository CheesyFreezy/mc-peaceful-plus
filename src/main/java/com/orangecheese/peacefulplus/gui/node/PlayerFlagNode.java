package com.orangecheese.peacefulplus.gui.node;

import com.orangecheese.peacefulplus.configuration.PeacefulPlusConfiguration;
import com.orangecheese.peacefulplus.configuration.PlayerWhitelistFlags;
import com.orangecheese.peacefulplus.gui.MenuClickEvent;
import com.orangecheese.peacefulplus.utility.StringUtility;
import com.twodevsstudio.simplejsonconfig.api.Config;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.TooltipDisplay;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public abstract class PlayerFlagNode extends MenuNode {
    private final String title;

    private final String description;

    private final ItemStack baseItem;

    private final UUID uuid;

    protected final PeacefulPlusConfiguration configuration;

    public PlayerFlagNode(
            String title,
            String description,
            ItemStack baseItem,
            UUID uuid) {
        this.title = title;
        this.description = description;
        this.baseItem = baseItem;
        this.uuid = uuid;

        configuration = Config.getConfig(PeacefulPlusConfiguration.class);

        item().set(render());
    }

    @Override
    public void onClick(MenuClickEvent event) {
        Player player = event.getPlayer();

        onToggle(player, !isEnabled(), () -> {
            player.playSound(player.getEyeLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 4);
            item().set(render());
        });
    }

    protected ItemStack render() {
        String textHexColor = "#606060";
        String enabledHexColor = "#65bc6e";
        String disabledHexColor = "#aa3f3f";
        String hexColor = isEnabled() ? enabledHexColor : disabledHexColor;
        TextColor color = TextColor.fromHexString(hexColor);

        ItemStack item = baseItem.clone();

        Set<DataComponentType> dataComponentTypesToHide = new HashSet<>();
        dataComponentTypesToHide.add(DataComponentTypes.POTION_CONTENTS);
        dataComponentTypesToHide.add(DataComponentTypes.FIREWORK_EXPLOSION);
        dataComponentTypesToHide.add(DataComponentTypes.FIREWORKS);
        dataComponentTypesToHide.add(DataComponentTypes.BUNDLE_CONTENTS);
        item.setData(
                DataComponentTypes.TOOLTIP_DISPLAY,
                TooltipDisplay.tooltipDisplay()
                        .hideTooltip(false)
                        .hiddenComponents(dataComponentTypesToHide)
                        .build()
        );

        ItemMeta meta = item.getItemMeta();

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);

        meta.setEnchantmentGlintOverride(isEnabled());

        Component itemName = MiniMessage.miniMessage()
                .deserialize(title)
                .color(NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false);
        meta.displayName(itemName);

        String[] descriptionLines = StringUtility.wrapText(description, 50);

        List<Component> lore = new ArrayList<>();
        lore.add(Component.text(""));
        if(description != null && !description.isEmpty()) {
            for(String descriptionLine : descriptionLines)
                lore.add(MiniMessage.miniMessage()
                        .deserialize(
                                descriptionLine,
                                Placeholder.styling("enabled", style -> style.color(TextColor.fromHexString(enabledHexColor))),
                                Placeholder.styling("disabled", style -> style.color(TextColor.fromHexString(disabledHexColor)))
                        )
                        .color(TextColor.fromHexString(textHexColor))
                        .decoration(TextDecoration.ITALIC, false));
            lore.add(Component.text(""));
        }
        lore.add(MiniMessage.miniMessage()
                .deserialize(isEnabled() ? "Enabled" : "Disabled")
                .color(color)
                .decoration(TextDecoration.ITALIC, false));

        meta.lore(lore);

        item.setItemMeta(meta);

        return item;
    }

    public abstract void onToggle(Player player, boolean enabled, Runnable complete);

    public abstract boolean isEnabled();

    public UUID getUuid() {
        return uuid;
    }

    public PlayerWhitelistFlags getFlags() {
        return configuration.getPlayerWhitelistFlags(uuid);
    }
}