package com.rlcyyg1108.removeeffectdisplay.config;

import com.rlcyyg1108.removeeffectdisplay.RemoveEffectDisplay;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

/**
 * 客户端配置：三个功能开关，默认全部开启。
 * <p>
 * 配置文件：{@code config/removeeffectdisplay-client.toml}，
 * 也可在游戏内「模组列表 → 配置」界面修改。
 * <p>
 * 热路径（每帧/每个粒子）只读取静态缓存字段，避免反复访问配置对象。
 */
@Mod.EventBusSubscriber(modid = RemoveEffectDisplay.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ClientConfig {
    public static final ForgeConfigSpec SPEC;

    private static final ForgeConfigSpec.BooleanValue HIDE_INVENTORY_EFFECTS;
    private static final ForgeConfigSpec.BooleanValue HIDE_HUD_EFFECTS;
    private static final ForgeConfigSpec.BooleanValue DISABLE_POTION_PARTICLES;

    private static volatile boolean hideInventoryEffects = true;
    private static volatile boolean hideHudEffects = true;
    private static volatile boolean disablePotionParticles = true;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        HIDE_INVENTORY_EFFECTS = builder
                .comment("Hide the effect list rendered on the right side of the player inventory.")
                .define("hideInventoryEffects", true);

        HIDE_HUD_EFFECTS = builder
                .comment("Hide the effect icons rendered in the top-right corner of the HUD.")
                .define("hideHudEffects", true);

        DISABLE_POTION_PARTICLES = builder
                .comment("Disable the effect particles swirling around entities.")
                .define("disablePotionParticles", true);

        SPEC = builder.build();
    }

    private ClientConfig() {
    }

    public static boolean hideInventoryEffects() {
        return hideInventoryEffects;
    }

    public static boolean hideHudEffects() {
        return hideHudEffects;
    }

    public static boolean disablePotionParticles() {
        return disablePotionParticles;
    }

    public static void setHideInventoryEffects(boolean value) {
        hideInventoryEffects = value;
        persist("hideInventoryEffects", HIDE_INVENTORY_EFFECTS, value);
    }

    public static void setHideHudEffects(boolean value) {
        hideHudEffects = value;
        persist("hideHudEffects", HIDE_HUD_EFFECTS, value);
    }

    public static void setDisablePotionParticles(boolean value) {
        disablePotionParticles = value;
        persist("disablePotionParticles", DISABLE_POTION_PARTICLES, value);
    }

    /**
     * 写入并保存单个配置项。缓存已由调用方更新，因此即使保存失败，本次会话仍然立即生效。
     */
    private static void persist(String name, ForgeConfigSpec.BooleanValue configValue, boolean value) {
        if (!SPEC.isLoaded()) {
            RemoveEffectDisplay.LOGGER.warn(
                    "[{}] config is not loaded yet, '{}' only applies to the current session",
                    RemoveEffectDisplay.MODID, name);
            return;
        }

        try {
            configValue.set(value);
            configValue.save();
            RemoveEffectDisplay.LOGGER.debug("[{}] saved '{}' = {}", RemoveEffectDisplay.MODID, name, value);
        } catch (Exception e) {
            RemoveEffectDisplay.LOGGER.warn(
                    "[{}] failed to save config '{}', the change only applies to the current session",
                    RemoveEffectDisplay.MODID, name, e);
        }
    }

    @SubscribeEvent
    static void onConfigEvent(ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC) {
            return;
        }

        hideInventoryEffects = HIDE_INVENTORY_EFFECTS.get();
        hideHudEffects = HIDE_HUD_EFFECTS.get();
        disablePotionParticles = DISABLE_POTION_PARTICLES.get();

        RemoveEffectDisplay.LOGGER.debug(
                "[{}] config applied: hideInventoryEffects={}, hideHudEffects={}, disablePotionParticles={}",
                RemoveEffectDisplay.MODID, hideInventoryEffects, hideHudEffects, disablePotionParticles);
    }
}
