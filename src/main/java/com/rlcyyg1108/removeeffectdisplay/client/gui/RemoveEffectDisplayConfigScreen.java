package com.rlcyyg1108.removeeffectdisplay.client.gui;

import com.rlcyyg1108.removeeffectdisplay.config.ClientConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

/**
 * 游戏内配置界面：三个功能开关，点击立即生效并写入 TOML。
 * <p>
 * 入口为 Forge「模组列表 → 配置」，父界面由 Forge 传入，关闭时返回。
 */
public class RemoveEffectDisplayConfigScreen extends Screen {
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;
    private static final int ROW_GAP = 24;
    private static final int TITLE_COLOR = 0xFFFFFF;

    private final Screen parent;

    public RemoveEffectDisplayConfigScreen(Screen parent) {
        super(Component.translatable("removeeffectdisplay.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int x = this.width / 2 - BUTTON_WIDTH / 2;
        int y = this.height / 4;

        this.addRenderableWidget(createToggle(x, y, "hideInventoryEffects",
                ClientConfig::hideInventoryEffects, ClientConfig::setHideInventoryEffects));
        this.addRenderableWidget(createToggle(x, y + ROW_GAP, "hideHudEffects",
                ClientConfig::hideHudEffects, ClientConfig::setHideHudEffects));
        this.addRenderableWidget(createToggle(x, y + ROW_GAP * 2, "disablePotionParticles",
                ClientConfig::disablePotionParticles, ClientConfig::setDisablePotionParticles));

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose())
                .bounds(this.width / 2 - BUTTON_WIDTH / 2, this.height - 40, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build());
    }

    private Button createToggle(int x, int y, String key, BooleanSupplier getter, Consumer<Boolean> setter) {
        Component label = Component.translatable("removeeffectdisplay.configuration." + key);

        return Button.builder(toggleText(label, getter.getAsBoolean()), button -> {
            boolean value = !getter.getAsBoolean();
            setter.accept(value);
            button.setMessage(toggleText(label, value));
        }).bounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();
    }

    private static Component toggleText(Component label, boolean value) {
        return Component.empty()
                .append(label)
                .append(": ")
                .append(Component.translatable(value ? "options.on" : "options.off"));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 16, TITLE_COLOR);
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.parent);
        }
    }

    @Override
    public boolean isPauseScreen() {
        // 与暂停菜单一致：单人游戏打开本界面时暂停集成服务器
        return true;
    }
}
