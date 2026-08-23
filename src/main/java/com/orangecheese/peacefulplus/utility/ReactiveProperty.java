package com.orangecheese.peacefulplus.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ReactiveProperty<T> {
    private T value;

    private ISubscription binding;

    private final List<Consumer<T>> listeners;

    public ReactiveProperty() {
        listeners = new ArrayList<>();
    }

    public ReactiveProperty(T initialValue) {
        this();
        value = initialValue;
    }

    public void set(T value) {
        this.value = value;
        notifyListeners();
    }

    public T get() {
        return value;
    }

    public ISubscription subscribe(Consumer<T> listener, boolean immediate) {
        listeners.add(listener);

        if(immediate)
            listener.accept(value);

        return () -> listeners.remove(listener);
    }

    public void bind(ReactiveProperty<T> other) {
        if(binding != null)
            binding.unsubscribe();

        binding = other.subscribe(this::set, true);
    }

    public void notifyListeners() {
        listeners.forEach(listener -> listener.accept(value));
    }
}