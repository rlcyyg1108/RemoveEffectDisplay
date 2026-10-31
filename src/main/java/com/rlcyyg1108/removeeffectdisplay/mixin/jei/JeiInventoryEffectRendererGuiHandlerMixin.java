package com.rlcyyg1108.removeeffectdisplay.mixin.jei;

import com.rlcyyg1108.removeeffectdisplay.config.ClientConfig;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * 老版本 JEI（如 15.62.x）兼容。
 * <p>
 * 这些版本由 {@code InventoryEffectRendererGuiHandler#getGuiExtraAreas} 直接按玩家激活效果
 * 计算右侧效果区，不检查 {@code canSeeEffects()}，因此仅隐藏绘制仍会被 JEI 计入布局。
 * 这里在开关开启时直接返回空区域列表。
 * <p>
 * 使用 {@link Pseudo} + 字符串目标，未安装 JEI 或新版 JEI 已移除该类时静默跳过；
 * {@code require = 0} 保证 JEI 内部签名变化时不影响启动。
 */
@Pseudo
@Mixin(targets = "mezz.jei.library.plugins.vanilla.gui.InventoryEffectRendererGuiHandler", remap = false)
public abstract class JeiInventoryEffectRendererGuiHandlerMixin {
    @Inject(
            method = "getGuiExtraAreas(Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;)Ljava/util/List;",
            at = @At("HEAD"),
            cancellable = true,
            require = 0,
            remap = false
    )
    @SuppressWarnings("rawtypes")
    private void removeeffectdisplay$hideEffectAreas(EffectRenderingInventoryScreen screen,
                                                     CallbackInfoReturnable<List> cir) {
        if (ClientConfig.hideInventoryEffects()) {
            cir.setReturnValue(List.of());
        }
    }
}
