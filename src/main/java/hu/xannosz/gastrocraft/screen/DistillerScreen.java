package hu.xannosz.gastrocraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import hu.xannosz.gastrocraft.blockentity.DistillerBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static net.minecraftforge.client.gui.ScreenUtils.drawTexturedModalRect;

@OnlyIn(Dist.CLIENT)
public class DistillerScreen extends BaseScreen<DistillerMenu>{

	private Gauge fluid;
	private Gauge water;
	private Gauge burn;
	private Gauge progress2;
	private Gauge progress3;
	private Gauge progress4;

	public DistillerScreen(DistillerMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title, 231, "distiller_gui.png");
		inventoryLabelY = -600;
	}

	@Override
	protected void initGauges() {
		progress = new Gauge(x + 35, y + 13, 176, 154, 17, 48);
		progress2 = new Gauge(x + 45, y + 7, 194, 154, 58, 6);
		progress3 = new Gauge(x + 100, y + 13, 194, 161, 10, 18);
		progress4 =  new Gauge(x + 94, y + 31, 205, 161, 21, 49);
		water = new Gauge(x + 94, y + 31, 199, 59, 20, 48);
		fluid = new Gauge(x + 22, y + 79, 176, 110, 45, 41);
		burn = new Gauge(x + 49, y + 128, 176, 0, 13, 13);
	}

	@Override
	protected void drawGauges(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		progress.renderYM(guiGraphics, menu.getProgress(),menu.getMaxProgress() , partialTick);
		progress2.renderXP(guiGraphics, menu.getProgress2(), DistillerBlockEntity.MAXIMUM_PROGRESS2, partialTick);
		progress3.renderYP(guiGraphics, menu.getProgress3(), DistillerBlockEntity.MAXIMUM_PROGRESS3, partialTick);
		burn.renderYM(guiGraphics, menu.getBurn(), menu.getMaxBurn(), partialTick);

		RenderSystem.setShaderColor(menu.getColorR(), menu.getColorG(), menu.getColorB(), menu.getColorA());
		fluid.renderYM(guiGraphics, menu.getFluidLevel(), DistillerBlockEntity.MAXIMUM_FLUID_LEVEL,partialTick);

		RenderSystem.setShaderColor(0.184F, 0.263F, 0.957F, 0.75F);
		water.renderYM(guiGraphics, menu.getWaterLevel(), DistillerBlockEntity.MAXIMUM_WATER_LEVEL,partialTick);

		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiGraphics, x+22, y+79 , 176, 15 , 46, 42, partialTick);
		drawTexturedModalRect(guiGraphics, x+93, y+31 , 176, 59 , 21, 49, partialTick);
		progress4.renderYP(guiGraphics, menu.getProgress4(),DistillerBlockEntity.MAXIMUM_PROGRESS4, partialTick);
	}
}
