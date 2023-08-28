package hu.xannosz.gastrocraft.recipes;

import hu.xannosz.gastrocraft.blockentity.DryerBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;

public class DryerRecipe extends BaseRecipe {
	private final Item input;
	private final int inCount;
	private final Item output;
	private final int outCount;

	public DryerRecipe(Item input, Item output) {
		this(input, 1, output, 1, 1500);
	}

	public DryerRecipe(Item input, int inCount, Item output, int outCount, int maxProgress) {
		super(maxProgress);
		this.input = input;
		this.inCount = inCount;
		this.output = output;
		this.outCount = outCount;
	}

	@Override
	public boolean isValid(BlockEntity blockEntity) {
		if (blockEntity instanceof DryerBlockEntity dryer) {
			return hasEnoughResource(dryer.getItemHandler().getStackInSlot(1), input, inCount) &&
					hasEnoughSpace(dryer.getItemHandler().getStackInSlot(0), output, outCount);
		}
		return false;
	}

	@Override
	public void execute(BlockEntity blockEntity) {
		if (blockEntity instanceof DryerBlockEntity dryer) {
			dryer.getItemHandler().getStackInSlot(1).shrink(inCount);
			addOutput(dryer.getItemHandler(), 0, output, outCount);
		}
	}
}
