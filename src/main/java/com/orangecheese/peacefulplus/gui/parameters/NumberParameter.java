package com.orangecheese.peacefulplus.gui.parameters;

import io.papermc.paper.dialog.DialogResponseView;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import net.kyori.adventure.text.Component;

public class NumberParameter extends Parameter<Float> {
    private final String label;

    private final float minimumValue;

    private final float maximumValue;

    private final float initialValue;

    private final float step;

    public NumberParameter(String key, String title, String label, float minimumValue, float maximumValue, float initialValue, float step) {
        super(key, title);
        this.label = label;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.initialValue = initialValue;
        this.step = step;
    }

    @Override
    protected DialogInput dialogInput() {
        return DialogInput
                .numberRange(key, Component.text(label), minimumValue, maximumValue)
                .initial(initialValue)
                .step(step)
                .build();
    }

    @Override
    public Float mapFromDialogResponseView(DialogResponseView view) {
        Float floatValue = view.getFloat(key);
        if(floatValue == null)
            throw new IllegalArgumentException(String.format("Number value from parameter %1$s is invalid.", key));
        return floatValue;
    }
}
