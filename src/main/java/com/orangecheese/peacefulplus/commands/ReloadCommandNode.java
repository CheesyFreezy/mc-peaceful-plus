package com.orangecheese.peacefulplus.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.twodevsstudio.simplejsonconfig.api.Config;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ReloadCommandNode implements ICommandNode {
    @Override
    public LiteralArgumentBuilder<CommandSourceStack> getCommandNode() {
        return Commands.literal("reload")
                .requires(context ->
                        (context.getSender() instanceof Player player &&
                                (player.isOp() || player.hasPermission("pp.reload")))
                            || context.getSender() instanceof ConsoleCommandSender)
                .executes(context -> {
                    org.bukkit.command.CommandSender sender = context.getSource().getSender();

                    Config.reloadAll();

                    MiniMessage miniMessage = MiniMessage.miniMessage();
                    sender.sendMessage(miniMessage.deserialize("<gold>Config files reloaded!</gold>"));

                    return Command.SINGLE_SUCCESS;
                });
    }
}
