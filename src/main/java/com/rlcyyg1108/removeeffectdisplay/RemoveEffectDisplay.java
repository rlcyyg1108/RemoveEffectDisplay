package com.rlcyyg1108.removeeffectdisplay;

import com.mojang.logging.LogUtils;
import com.rlcyyg1108.removeeffectdisplay.client.ClientSetup;
import com.rlcyyg1108.removeeffectdisplay.config.ClientConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

/**
 * Remove Effect Display
 * <p>
 * 客户端显示类模组：隐藏原版的状态效果 HUD 显示，并移除效果粒子。
 */
@Mod(RemoveEffectDisplay.MODID)
public class RemoveEffectDisplay {
    public static final String MODID = "removeeffectdisplay";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RemoveEffectDisplay() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSetup::registerConfigScreen);
        LOGGER.debug("[{}] initialized", MODID);
    }
}
