package com.rlcyyg1108.removeeffectdisplay.mixin;

import com.rlcyyg1108.removeeffectdisplay.config.ClientConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 隐藏背包界面右侧的效果列表。
 * <p>
 * 1.20.1 中该方法实际定义在 {@link EffectRenderingInventoryScreen}（{@code InventoryScreen}
 * 与创造模式物品栏均继承自它），且为 private，Forge 未提供对应事件，因此使用 Mixin。
 * <p>
 * 同时让 {@code canSeeEffects()} 返回 false：JEI 等模组用该方法判断是否需要为效果区让位，
 * 仅取消绘制仍会被当成“效果区存在”而挤压界面布局。
 */
@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectRenderingInventoryScreenMixin {
    @Inject(method = "renderEffects", at = @At("HEAD"), cancellable = true)
    private void removeeffectdisplay$hideEffectList(GuiGraphics guiGraphics, int mouseX, int mouseY, CallbackInfo ci) {
        if (ClientConfig.hideInventoryEffects()) {
            ci.cancel();
        }
    }

    @Inject(method = "canSeeEffects", at = @At("HEAD"), cancellable = true)
    private void removeeffectdisplay$hideEffectArea(CallbackInfoReturnable<Boolean> cir) {
        if (ClientConfig.hideInventoryEffects()) {
            cir.setReturnValue(false);
        }
    }
}
