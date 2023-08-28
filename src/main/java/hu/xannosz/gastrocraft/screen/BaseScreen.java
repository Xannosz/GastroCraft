package hu.xannosz.gastrocraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import hu.xannosz.gastrocraft.GastroCraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public abstract class BaseScreen<T extends BaseMenu> extends AbstractContainerScreen<T> {
	private final ResourceLocation texture;
	protected int x;
	protected int y;
	protected Gauge progress;

	public BaseScreen(T menu, Inventory inventory, Component title, int imageHeight, String texture) {
		super(menu, inventory, title);
		this.imageHeight = imageHeight;
		this.texture = new ResourceLocation(GastroCraft.MOD_ID, "textures/gui/" + texture);
	}

	@Override
	protected void init() {
		super.init();

		x = (width - imageWidth) / 2;
		y = (height - imageHeight) / 2;

		initGauges();
	}

	@Override
	protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, texture);
		guiGraphics.blit(texture, x, y, 0, 0, imageWidth, imageHeight);

		drawGauges(guiGraphics, partialTick, mouseX, mouseY);
	}

	@Override
	public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
		//call built-in functions
		renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, delta);

		//call built-in function
		renderTooltip(guiGraphics, mouseX, mouseY);
	}

	protected abstract void initGauges();

	protected abstract void drawGauges(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY);
}
