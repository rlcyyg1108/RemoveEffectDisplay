package com.rlcyyg1108.removeeffectdisplay.client;

import com.rlcyyg1108.removeeffectdisplay.RemoveEffectDisplay;
import com.rlcyyg1108.removeeffectdisplay.config.ClientConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 客户端事件：控制原版 HUD 图层的显示。
 * <p>
 * 仅在物理客户端注册，服务端不会加载本类。
 */
@Mod.EventBusSubscriber(modid = RemoveEffectDisplay.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class ClientEvents {
    private static final ResourceLocation EFFECT_ICONS_OVERLAY = VanillaGuiOverlay.POTION_ICONS.id();

    private ClientEvents() {
    }

    @SubscribeEvent
    public static void onRenderGuiOverlayPre(RenderGuiOverlayEvent.Pre event) {
        if (ClientConfig.hideHudEffects() && EFFECT_ICONS_OVERLAY.equals(event.getOverlay().id())) {
            event.setCanceled(true);
        }
    }
}
