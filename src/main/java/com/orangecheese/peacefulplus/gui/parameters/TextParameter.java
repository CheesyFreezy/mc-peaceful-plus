package com.orangecheese.peacefulplus.gui.parameters;

import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.input.TextDialogInput;
import net.kyori.adventure.text.Component;

public class TextParameter extends Parameter<String> {
    private final boolean multiLine;

    public TextParameter(String key, String title, boolean multiLine) {
        super(key, title);
        this.multiLine = multiLine;
    }

    @Override
    public String mapFromDialogResponseView(DialogResponseView view) {
        return view.getText(key);
    }

    @Override
    protected DialogInput dialogInput() {
        TextDialogInput.Builder builder = DialogInput.text(key, Component.text(title));

        if(multiLine)
            builder.multiline(TextDialogInput.MultilineOptions.create(5, 100));

        return builder.build();
    }
}
