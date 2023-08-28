package hu.xannosz.gastrocraft.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DryerScreen extends BaseScreen<DryerMenu> {
	public DryerScreen(DryerMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title, 171,"dryer_gui.png");
	}

	@Override
	protected void initGauges() {
		progress = new Gauge(x + 77, y + 35, 177, 1, 22, 22);
	}

	@Override
	protected void drawGauges(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		progress.renderXP(guiGraphics, menu.getProgress(), menu.getMaxProgress(), partialTick);
	}
}
