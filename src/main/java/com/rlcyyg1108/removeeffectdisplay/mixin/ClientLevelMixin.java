package com.rlcyyg1108.removeeffectdisplay.mixin;

import com.rlcyyg1108.removeeffectdisplay.config.ClientConfig;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 拦截客户端状态效果粒子。
 * <p>
 * 只过滤实体身上环绕的效果粒子（{@code entity_effect} 与 {@code ambient_entity_effect}），
 * 不触碰喷溅/滞留药水的爆炸粒子，也不影响效果本身的计时与音效。
 * <p>
 * {@link ClientLevel} 仅存在于客户端，因此该逻辑天然不会在服务端执行。
 */
@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin {
    @Inject(method = "addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V", at = @At("HEAD"), cancellable = true)
    private void removeeffectdisplay$blockEffectParticle(ParticleOptions options, double x, double y, double z,
                                                          double xSpeed, double ySpeed, double zSpeed, CallbackInfo ci) {
        if (removeeffectdisplay$isHiddenEffectParticle(options)) {
            ci.cancel();
        }
    }

    @Inject(method = "addParticle(Lnet/minecraft/core/particles/ParticleOptions;ZDDDDDD)V", at = @At("HEAD"), cancellable = true)
    private void removeeffectdisplay$blockEffectParticleForced(ParticleOptions options, boolean force, double x, double y,
                                                               double z, double xSpeed, double ySpeed, double zSpeed,
                                                               CallbackInfo ci) {
        if (removeeffectdisplay$isHiddenEffectParticle(options)) {
            ci.cancel();
        }
    }

    @Inject(method = "addAlwaysVisibleParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V", at = @At("HEAD"), cancellable = true)
    private void removeeffectdisplay$blockEffectParticleAlwaysVisible(ParticleOptions options, double x, double y, double z,
                                                                      double xSpeed, double ySpeed, double zSpeed,
                                                                      CallbackInfo ci) {
        if (removeeffectdisplay$isHiddenEffectParticle(options)) {
            ci.cancel();
        }
    }

    @Inject(method = "addAlwaysVisibleParticle(Lnet/minecraft/core/particles/ParticleOptions;ZDDDDDD)V", at = @At("HEAD"), cancellable = true)
    private void removeeffectdisplay$blockEffectParticleAlwaysVisibleForced(ParticleOptions options, boolean force,
                                                                           double x, double y, double z, double xSpeed,
                                                                           double ySpeed, double zSpeed, CallbackInfo ci) {
        if (removeeffectdisplay$isHiddenEffectParticle(options)) {
            ci.cancel();
        }
    }

    private static boolean removeeffectdisplay$isHiddenEffectParticle(ParticleOptions options) {
        if (!ClientConfig.disablePotionParticles()) {
            return false;
        }

        ParticleType<?> type = options.getType();
        return type == ParticleTypes.ENTITY_EFFECT || type == ParticleTypes.AMBIENT_ENTITY_EFFECT;
    }
}
