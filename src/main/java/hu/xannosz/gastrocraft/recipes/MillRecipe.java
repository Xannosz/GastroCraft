package hu.xannosz.gastrocraft.recipes;

import hu.xannosz.gastrocraft.blockentity.MillBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MillRecipe extends BaseRecipe {
	private final Item input;
	private final int inCount;
	private final Item output;
	private final int outCount;

	public MillRecipe(Item input, Item output, int outCount) {
		this(input, 1, output, outCount, 600);
	}

	public MillRecipe(Item input, int inCount, Item output, int outCount, int maxProgress) {
		super(maxProgress);
		this.input = input;
		this.inCount = inCount;
		this.output = output;
		this.outCount = outCount;
	}

	@Override
	public boolean isValid(BlockEntity blockEntity) {
		if (blockEntity instanceof MillBlockEntity mill) {
			return hasEnoughResource(mill.getItemHandler().getStackInSlot(1), input, inCount) &&
					hasEnoughSpace(mill.getItemHandler().getStackInSlot(0), output, outCount);
		}
		return false;
	}

	@Override
	public void execute(BlockEntity blockEntity) {
		if (blockEntity instanceof MillBlockEntity mill) {
			mill.getItemHandler().getStackInSlot(1).shrink(inCount);
			addOutput(mill.getItemHandler(), 0, output, outCount);
		}
	}
}
