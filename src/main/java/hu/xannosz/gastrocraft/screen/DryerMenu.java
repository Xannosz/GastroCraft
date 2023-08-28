package hu.xannosz.gastrocraft.screen;

import hu.xannosz.gastrocraft.block.ModBlocks;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class DryerMenu extends BaseMenu {
	public DryerMenu(int containerId, Inventory inv, FriendlyByteBuf extraData) {
		this(containerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()),
				new SimpleContainerData(2));
	}

	public DryerMenu(int containerId, Inventory inv, BlockEntity blockEntity, ContainerData data) {
		super(containerId, inv, blockEntity, ModBlocks.DRYER.get(), data,
				ModMenuTypes.DRYER_MENU.get(), 89, 2);
		ItemStackHandler handler = getBlockEntity().getItemHandler();
		this.addSlot(new SlotItemHandler(handler, 0, 80, 63));
		this.addSlot(new SlotItemHandler(handler, 1, 80, 13));
	}
}
