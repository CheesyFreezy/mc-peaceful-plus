package com.orangecheese.peacefulplus.gui.parameters;

import com.orangecheese.peacefulplus.utility.PaperTasks;
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class Parameter<T> {
    protected final String key;

    protected final String title;

    public Parameter(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public CompletableFuture<T> invoke(Player player) {
        CompletableFuture<T> callback = new CompletableFuture<>();

        Dialog dialog = Dialog.create(builder -> builder
                .empty()
                .base(DialogBase.builder(Component.text(title))
                        .inputs(List.of(
                                dialogInput()
                        ))
                        .afterAction(DialogBase.DialogAfterAction.CLOSE)
                        .build())
                .type(DialogType.multiAction(List.of(ActionButton.create(
                                Component.text("Confirm"),
                                Component.text("Click to confirm."),
                                100,
                                DialogAction.customClick((response, audience) -> {
                                    T value = mapFromDialogResponseView(response);
                                    audience.closeDialog();
                                    PaperTasks.nextTick(() -> callback.complete(value));
                                }, ClickCallback.Options.builder().build())
                        ))).build()
                )
        );

        player.showDialog(dialog);

        return callback;
    }

    public String getKey() {
        return key;
    }

    public abstract T mapFromDialogResponseView(DialogResponseView view);

    protected abstract DialogInput dialogInput();
}