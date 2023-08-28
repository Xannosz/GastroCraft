package hu.xannosz.gastrocraft.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PressScreen extends BaseScreen<PressMenu> {
	public PressScreen(PressMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title, 192, "press_gui.png");
	}

	@Override
	protected void initGauges() {
		progress = new Gauge(x + 64, y + 29, 177, 1, 47, 56);
	}

	@Override
	protected void drawGauges(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		progress.renderYP(guiGraphics, menu.getProgress(), menu.getMaxProgress(), partialTick);
	}
}
