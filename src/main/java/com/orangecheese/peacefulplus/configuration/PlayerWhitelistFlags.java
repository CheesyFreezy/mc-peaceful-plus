package com.orangecheese.peacefulplus.configuration;

public class PlayerWhitelistFlags {
    private Integer maximumEntityTargets;

    private boolean foodDrain;

    private boolean increasedHealthRegeneration;

    private boolean noFallDamage;

    private boolean infiniteDurability;

    private boolean infiniteFireworks;

    private boolean instantVeinMine;

    private boolean instantTreeChopper;

    private boolean keepInventory;

    private Float experienceMultiplier;

    private Float breakingSpeedMultiplier;

    public PlayerWhitelistFlags() {
        maximumEntityTargets = null;
        foodDrain = false;
        increasedHealthRegeneration = false;
        noFallDamage = false;
        infiniteDurability = false;
        infiniteFireworks = false;
        instantVeinMine = false;
        instantTreeChopper = false;
        keepInventory = false;
        experienceMultiplier = null;
        breakingSpeedMultiplier = null;
    }

    public Integer getMaximumEntityTargets() {
        return maximumEntityTargets;
    }

    public void setMaximumEntityTargets(Integer maximumEntityTargets) {
        this.maximumEntityTargets = maximumEntityTargets;
    }

    public boolean isFoodDrain() {
        return foodDrain;
    }

    public void setFoodDrain(boolean foodDrain) {
        this.foodDrain = foodDrain;
    }

    public boolean isIncreasedHealthRegeneration() {
        return increasedHealthRegeneration;
    }

    public void setIncreasedHealthRegeneration(boolean increasedHealthRegeneration) {
        this.increasedHealthRegeneration = increasedHealthRegeneration;
    }

    public boolean isNoFallDamage() {
        return noFallDamage;
    }

    public void setNoFallDamage(boolean noFallDamage) {
        this.noFallDamage = noFallDamage;
    }

    public boolean isInfiniteDurability() {
        return infiniteDurability;
    }

    public void setInfiniteDurability(boolean infiniteDurability) {
        this.infiniteDurability = infiniteDurability;
    }

    public boolean isInfiniteFireworks() {
        return infiniteFireworks;
    }

    public void setInfiniteFireworks(boolean infiniteFireworks) {
        this.infiniteFireworks = infiniteFireworks;
    }

    public boolean isInstantVeinMine() {
        return instantVeinMine;
    }

    public void setInstantVeinMine(boolean instantVeinMine) {
        this.instantVeinMine = instantVeinMine;
    }

    public boolean isInstantTreeChopper() {
        return instantTreeChopper;
    }

    public void setInstantTreeChopper(boolean instantTreeChopper) {
        this.instantTreeChopper = instantTreeChopper;
    }

    public boolean isKeepInventory() {
        return keepInventory;
    }

    public void setKeepInventory(boolean keepInventory) {
        this.keepInventory = keepInventory;
    }

    public Float getExperienceMultiplier() {
        return experienceMultiplier;
    }

    public void setExperienceMultiplier(Float experienceMultiplier) {
        this.experienceMultiplier = experienceMultiplier;
    }

    public Float getBreakingSpeedMultiplier() {
        return breakingSpeedMultiplier;
    }

    public void setBreakingSpeedMultiplier(Float breakingSpeedMultiplier) {
        this.breakingSpeedMultiplier = breakingSpeedMultiplier;
    }
}