package hu.xannosz.gastrocraft.screen;

import hu.xannosz.gastrocraft.block.ModBlocks;
import hu.xannosz.gastrocraft.blockentity.DistillerBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class DistillerMenu extends BaseMenu {
	public DistillerMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
		this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()),
				new SimpleContainerData(DistillerBlockEntity.DATA_SIZE));
	}

	public DistillerMenu(int containerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
		super(containerId, inv, blockEntity, ModBlocks.DISTILLER.get(), data,
				ModMenuTypes.DISTILLER_MENU.get(), 149, 5);
		ItemStackHandler handler = getBlockEntity().getItemHandler();
		this.addSlot(new SlotItemHandler(handler, 0, 96, 127));
		this.addSlot(new SlotItemHandler(handler, 1,136,25 ));
		this.addSlot(new SlotItemHandler(handler, 2, 14, 33));
		this.addSlot(new SlotItemHandler(handler, 3, 96, 84));
		this.addSlot(new SlotItemHandler(handler, 4, 26, 127));
	}

	public float getColorR() {
		return data.get(DistillerBlockEntity.COLOR_R_KEY) / 255f;
	}

	public float getColorG() {
		return data.get(DistillerBlockEntity.COLOR_G_KEY) / 255f;
	}

	public float getColorB() {
		return data.get(DistillerBlockEntity.COLOR_B_KEY) / 255f;
	}

	public float getColorA() {
		return data.get(DistillerBlockEntity.COLOR_A_KEY) / 255f;
	}

	public int getFluidLevel() {
		return data.get(DistillerBlockEntity.FLUID_LEVEL);
	}

	public int getWaterLevel() {
		return data.get(DistillerBlockEntity.WATER_LEVEL);
	}

	public int getBurn() {
		return data.get(DistillerBlockEntity.BURN);
	}

	public int getMaxBurn() {
		return data.get(DistillerBlockEntity.MAX_BURN);
	}

	public int getProgress2() {
		return data.get(DistillerBlockEntity.PROGRESS2);
	}

	public int getProgress3() {
		return data.get(DistillerBlockEntity.PROGRESS3);
	}

	public int getProgress4() {
		return data.get(DistillerBlockEntity.PROGRESS4);
	}
}
