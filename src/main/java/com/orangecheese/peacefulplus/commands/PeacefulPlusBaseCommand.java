package com.orangecheese.peacefulplus.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.orangecheese.peacefulplus.PeacefulPlusPlugin;
import com.orangecheese.peacefulplus.gui.menus.MainMenu;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;

public class PeacefulPlusBaseCommand {
    private final PeacefulPlusPlugin plugin;

    private final ICommandNode[] nodes;

    public PeacefulPlusBaseCommand(PeacefulPlusPlugin plugin) {
        this.plugin = plugin;

        nodes = new ICommandNode[]{
                new ReloadCommandNode()
        };
    }

    public LiteralCommandNode<CommandSourceStack> createCommand() {
        LiteralArgumentBuilder<CommandSourceStack> commandTree = Commands.literal("peacefulplus");

        for(ICommandNode node : nodes)
            commandTree = commandTree.then(node.getCommandNode());

        commandTree
                .requires(context ->
                        context.getSender() instanceof Player player &&
                                (player.isOp() || player.hasPermission("pp.admin")))
                .executes(context -> {
                    Player player = (Player) context.getSource().getSender();

                    MainMenu mainMenu = new MainMenu(plugin, player);
                    plugin.getMenuManager().startSession(mainMenu);

                    return Command.SINGLE_SUCCESS;
                });

        return commandTree.build();
    }
}