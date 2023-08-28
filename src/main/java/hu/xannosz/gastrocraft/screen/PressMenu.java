package hu.xannosz.gastrocraft.screen;

import hu.xannosz.gastrocraft.block.ModBlocks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class PressMenu extends BaseMenu {
	public PressMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
		this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()),
				new SimpleContainerData(2));
	}

	public PressMenu(int containerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
		super(containerId, inv, blockEntity, ModBlocks.PRESS.get(), data,
				ModMenuTypes.PRESS_MENU.get(), 110, 5);
		ItemStackHandler handler = getBlockEntity().getItemHandler();
		this.addSlot(new SlotItemHandler(handler, 0, 79, 86));
		this.addSlot(new SlotItemHandler(handler, 1, 110, 86));
		this.addSlot(new SlotItemHandler(handler, 2, 79, 9));
		this.addSlot(new SlotItemHandler(handler, 3, 110, 43));
		this.addSlot(new SlotItemHandler(handler, 4, 47, 86));
	}
}
