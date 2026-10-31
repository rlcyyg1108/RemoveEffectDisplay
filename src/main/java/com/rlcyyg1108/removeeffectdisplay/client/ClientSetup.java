package com.rlcyyg1108.removeeffectdisplay.client;

import com.rlcyyg1108.removeeffectdisplay.client.gui.RemoveEffectDisplayConfigScreen;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

/**
 * 客户端初始化：把自绘配置界面挂到 Forge「模组列表 → 配置」入口。
 * <p>
 * 本类只在物理客户端被加载，专用服务端不会解析任何 GUI 相关类。
 */
public final class ClientSetup {
    private ClientSetup() {
    }

    public static void registerConfigScreen() {
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(RemoveEffectDisplayConfigScreen::new));
    }
}
