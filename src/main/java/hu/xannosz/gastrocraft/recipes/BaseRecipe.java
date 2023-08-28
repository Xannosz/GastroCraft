package hu.xannosz.gastrocraft.recipes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;

@AllArgsConstructor
public abstract class BaseRecipe {
	@Getter
	private final int maxProgress;

	public abstract boolean isValid(BlockEntity blockEntity);

	public abstract void execute(BlockEntity blockEntity);

	protected boolean hasEnoughResource(ItemStack itemStack, Item item, int count) {
		return itemStack.getItem().equals(item) && itemStack.getCount() >= count;
	}

	protected boolean hasEnoughSpace(ItemStack itemStack, Item item, int count) {
		if (itemStack.isEmpty()) {
			return true;
		}
		return itemStack.getItem().equals(item) && (itemStack.getCount() + count) <= itemStack.getMaxStackSize();
	}

	protected void addOutput(ItemStackHandler handler, int slot, Item item, int count) {
		if(item==null){
			return;
		}
		if (handler.getStackInSlot(slot).isEmpty()) {
			handler.setStackInSlot(slot, new ItemStack(item, count));
			return;
		}
		handler.getStackInSlot(slot).grow(count);
	}
}
