package hu.xannosz.gastrocraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import hu.xannosz.gastrocraft.blockentity.WineBarrelBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import static net.minecraftforge.client.gui.ScreenUtils.drawTexturedModalRect;

@OnlyIn(Dist.CLIENT)
public class WineBarrelScreen extends BaseScreen<WineBarrelMenu>{

	Gauge progress2;

	public WineBarrelScreen(WineBarrelMenu menu, Inventory inventory, Component title) {
		super(menu, inventory, title, 192, "wine_barrel_gui.png");
		inventoryLabelY = 99;
	}

	@Override
	protected void initGauges() {
		progress = new Gauge(x + 11, y + 15, 177, 80, 74, 77);
		progress2 = new Gauge(x + 85, y + 15, 177, 80, 52, 77);
	}

	@Override
	protected void drawGauges(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShaderColor(menu.getColorR(), menu.getColorG(), menu.getColorB(), menu.getColorA());
		progress.renderYM(guiGraphics, menu.getFluidLevel(), WineBarrelBlockEntity.MAXIMUM_FLUID_LEVEL, partialTick);
		progress2.renderYM(guiGraphics, menu.getFluidLevel(), WineBarrelBlockEntity.MAXIMUM_FLUID_LEVEL, partialTick);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiGraphics, x+10, y+15 , 176, 0 , 31, 78, partialTick);
		drawTexturedModalRect(guiGraphics, x+107, y+15 , 208, 0 , 31, 78, partialTick);
	}
}
