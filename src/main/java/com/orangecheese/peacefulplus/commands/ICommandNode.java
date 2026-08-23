package com.orangecheese.peacefulplus.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;

public interface ICommandNode {
    LiteralArgumentBuilder<CommandSourceStack> getCommandNode();
}