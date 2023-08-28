package hu.xannosz.gastrocraft.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class MillScreen extends BaseScreen<MillMenu> {
	public MillScreen(MillMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title, 171, "mill_gui.png");
	}

	@Override
	protected void initGauges() {
		progress = new Gauge(x + 64, y + 31, 177, 1, 47, 29);
	}

	@Override
	protected void drawGauges(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		progress.renderYP(guiGraphics, menu.getProgress(), menu.getMaxProgress(), partialTick);
	}
}
