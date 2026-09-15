package com.orangecheese.peacefulplus.events;

import com.orangecheese.peacefulplus.PeacefulPlusPlugin;
import com.orangecheese.peacefulplus.configuration.PeacefulPlusConfiguration;
import com.orangecheese.peacefulplus.configuration.PlayerWhitelistFlags;
import com.twodevsstudio.simplejsonconfig.api.Config;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

import javax.annotation.Nonnull;
import java.util.UUID;

public abstract class WhiteListProxyEvent<T extends Event> implements EventExecutor {
    private final Class<T> eventClass;

    protected final PeacefulPlusPlugin plugin;

    protected final PeacefulPlusConfiguration configuration;

    public WhiteListProxyEvent(Class<T> eventClass, PeacefulPlusPlugin plugin) {
        this.plugin = plugin;
        this.eventClass = eventClass;
        this.configuration = Config.getConfig(PeacefulPlusConfiguration.class);
    }

    @Override
    public void execute(@Nonnull Listener listener, @Nonnull Event event) {
        if(!eventClass.isAssignableFrom(event.getClass()))
            return;

        T typedEvent = eventClass.cast(event);

        UUID uuid = getUuid(typedEvent);

        PlayerWhitelistFlags flags = configuration.getPlayerWhitelistFlags(uuid);

        if(uuid != null && !hasFlag(flags))
            return;

        onInvoke(typedEvent, flags);
    }

    public abstract void onInvoke(T event, PlayerWhitelistFlags flags);

    public abstract UUID getUuid(T event);

    public abstract boolean hasFlag(PlayerWhitelistFlags flags);

    public Class<T> getEventClass() {
        return eventClass;
    }
}