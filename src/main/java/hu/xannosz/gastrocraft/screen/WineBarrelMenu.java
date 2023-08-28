package hu.xannosz.gastrocraft.screen;

import hu.xannosz.gastrocraft.block.ModBlocks;
import hu.xannosz.gastrocraft.blockentity.WineBarrelBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class WineBarrelMenu  extends BaseMenu {
	public WineBarrelMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
		this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()),
				new SimpleContainerData(WineBarrelBlockEntity.DATA_SIZE));
	}

	public WineBarrelMenu(int containerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
		super(containerId, inv, blockEntity, ModBlocks.WINE_BARREL.get(), data,
				ModMenuTypes.WINE_BARREL_MENU.get(), 110, 3);
		ItemStackHandler handler = getBlockEntity().getItemHandler();
		this.addSlot(new SlotItemHandler(handler, 0, 147, 47));
		this.addSlot(new SlotItemHandler(handler, 1, 147, 12));
		this.addSlot(new SlotItemHandler(handler, 2, 147, 82));
	}

	public float getColorR() {
		return data.get(WineBarrelBlockEntity.COLOR_R_KEY)/255f;
	}

	public float getColorG() {
		return data.get(WineBarrelBlockEntity.COLOR_G_KEY)/255f;
	}

	public float getColorB() {
		return data.get(WineBarrelBlockEntity.COLOR_B_KEY)/255f;
	}

	public float getColorA() {
		return data.get(WineBarrelBlockEntity.COLOR_A_KEY)/255f;
	}

	public int getFluidLevel() {
		return data.get(WineBarrelBlockEntity.FLUID_LEVEL);
	}
}
