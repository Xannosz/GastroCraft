package hu.xannosz.gastrocraft.recipes;

import hu.xannosz.gastrocraft.blockentity.PressBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;

public class PressRecipe extends BaseRecipe {
	private final Item input;
	private final Item output1;
	private final Item output2;
	private final boolean needBottle;
	private final boolean needBucket;
	private final boolean addBucket;

	public PressRecipe(Item input, Item output, int maxProgress) {
		super(maxProgress);
		this.input = input;
		this.output1 = output;
		this.output2 = null;
		needBottle = false;
		needBucket = false;
		addBucket = true;
	}

	public PressRecipe(Item input, Item output1, Item output2, int maxProgress) {
		super(maxProgress);
		this.input = input;
		this.output1 = output1;
		this.output2 = output2;
		needBottle = true;
		needBucket = true;
		addBucket = false;
	}

	@Override
	public boolean isValid(BlockEntity blockEntity) {
		if (blockEntity instanceof PressBlockEntity press) {
			boolean valid = hasEnoughResource(press.getItemHandler().getStackInSlot(2), input, 1) &&
					hasEnoughSpace(press.getItemHandler().getStackInSlot(0), output1, 1) &&
					hasEnoughSpace(press.getItemHandler().getStackInSlot(1), output2, 1);
			if (needBottle) {
				valid &= hasEnoughResource(press.getItemHandler().getStackInSlot(3), Items.GLASS_BOTTLE, 1);
			}
			if (needBucket) {
				valid &= hasEnoughResource(press.getItemHandler().getStackInSlot(4), Items.BUCKET, 1);
			}
			if (addBucket) {
				valid &= hasEnoughSpace(press.getItemHandler().getStackInSlot(4), Items.BUCKET, 1);
			}
			return valid;
		}
		return false;
	}

	@Override
	public void execute(BlockEntity blockEntity) {
		if (blockEntity instanceof PressBlockEntity press) {
			press.getItemHandler().getStackInSlot(2).shrink(1);
			addOutput(press.getItemHandler(), 0, output1, 1);
			addOutput(press.getItemHandler(), 1, output2, 1);
			if (needBottle) {
				press.getItemHandler().getStackInSlot(3).shrink(1);
			}
			if (needBucket) {
				press.getItemHandler().getStackInSlot(4).shrink(1);
			}
			if (addBucket) {
				addOutput(press.getItemHandler(), 4, Items.BUCKET, 1);
			}
		}
	}
}
