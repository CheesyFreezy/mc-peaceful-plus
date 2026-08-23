package com.orangecheese.peacefulplus.configuration;

import com.twodevsstudio.simplejsonconfig.api.Config;
import com.twodevsstudio.simplejsonconfig.interfaces.Configuration;

import java.util.*;

@Configuration("config.json")
public class PeacefulPlusConfiguration extends Config {
    private final Map<UUID, PlayerWhitelistFlags> playerWhitelistFlags;

    public PeacefulPlusConfiguration() {
        this.playerWhitelistFlags = new HashMap<>();
    }

    public PlayerWhitelistFlags getPlayerWhitelistFlags(UUID name) {
        if(!playerWhitelistFlags.containsKey(name)) {
            playerWhitelistFlags.put(name, new PlayerWhitelistFlags());
            save();
        }

        return playerWhitelistFlags.get(name);
    }
}