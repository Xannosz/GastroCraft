package hu.xannosz.gastrocraft.blockentity;

import hu.xannosz.gastrocraft.item.ModItems;
import hu.xannosz.gastrocraft.screen.DistillerMenu;
import hu.xannosz.gastrocraft.util.DistillerFluidType;
import hu.xannosz.gastrocraft.util.WrappedHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static net.minecraft.world.item.crafting.RecipeType.SMELTING;
import static net.minecraftforge.common.ForgeHooks.getBurnTime;

public class DistillerBlockEntity extends BaseMachineBlockEntity {
	public static final int COLOR_R_KEY = 2;
	public static final int COLOR_G_KEY = 3;
	public static final int COLOR_B_KEY = 4;
	public static final int COLOR_A_KEY = 5;
	public static final int FLUID_LEVEL = 6;
	public static final int WATER_LEVEL = 7;
	public static final int BURN = 8;
	public static final int MAX_BURN = 9;
	public static final int PROGRESS2 = 10;
	public static final int PROGRESS3 = 11;
	public static final int PROGRESS4 = 12;
	public static final int DATA_SIZE = 13;
	public static final int MAXIMUM_FLUID_LEVEL = 4000;
	public static final int MAXIMUM_WATER_LEVEL = 4000;
	public static final int MAXIMUM_PROGRESS2 = 100;
	public static final int MAXIMUM_PROGRESS3 = 100;
	public static final int MAXIMUM_PROGRESS4 = 100;

	private int fluidLevel = 0;
	private int waterLevel = 0;
	private int burn = 0;
	private int maxBurn = 0;
	private int progress2 = 0;
	private int progress3 = 0;
	private int progress4 = 0;
	private DistillerFluidType fluidType;
	private boolean firstRun;

	public DistillerBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntities.DISTILLER_ENTITY.get(), blockPos, blockState, DATA_SIZE);
		maxProgress = 100;
	}

	@Override
	protected ItemStackHandler createItemHandler() {
		return new ItemStackHandler(5) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				return switch (slot) {
					case 0 -> false;
					case 1 -> stack.getItem() == Items.WATER_BUCKET;
					case 2 -> true;
					case 3 -> stack.getItem() == Items.GLASS_BOTTLE;
					case 4 -> getBurnTime(stack, SMELTING) > 0;
					default -> super.isItemValid(slot, stack);
				};
			}
		};
	}

	@Override
	public @NotNull Component getDisplayName() {
		return Component.translatable("gui.distiller");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
		return new DistillerMenu(containerId, inventory, this, data);
	}

	@Override
	public void setData() {
		data.set(FLUID_LEVEL, fluidLevel);
		data.set(WATER_LEVEL, waterLevel);
		setFluidColor();
		data.set(BURN, burn);
		data.set(PROGRESS2, progress2);
		data.set(PROGRESS3, progress3);
		data.set(PROGRESS4, progress4);
		data.set(MAX_BURN, maxBurn);
	}

	@SuppressWarnings("unused")
	public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		((DistillerBlockEntity) blockEntity).tick();
	}

	@Override
	public void tick() {
		if (level != null && level.isClientSide()) {
			return;
		}

		burn();
		water();
		inTake();
		output();

		setDataInter();
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		tag.putInt("fluidLevel", fluidLevel);
		tag.putInt("waterLevel", waterLevel);
		tag.putInt("burn", burn);
		tag.putInt("maxBurn", maxBurn);
		tag.putInt("progress2", progress2);
		tag.putInt("progress3", progress3);
		tag.putInt("progress4", progress4);
		tag.putBoolean("firstRun", firstRun);
		if (fluidType != null) {
			tag.putString("fluidType", fluidType.name());
		}
		super.saveAdditional(tag);
	}

	@Override
	public void load(@NotNull CompoundTag nbt) {
		super.load(nbt);
		fluidLevel = nbt.getInt("fluidLevel");
		waterLevel = nbt.getInt("waterLevel");
		burn = nbt.getInt("burn");
		maxBurn = nbt.getInt("maxBurn");
		progress2 = nbt.getInt("progress2");
		progress3 = nbt.getInt("progress3");
		progress4 = nbt.getInt("progress4");
		firstRun = nbt.getBoolean("firstRun");
		if (nbt.contains("fluidType")) {
			fluidType = DistillerFluidType.valueOf(nbt.getString("fluidType"));
		}
		setDataInter();
	}

	@Override
	protected Map<Direction, LazyOptional<WrappedHandler>> getDirectionWrappedHandlerMap(ItemStackHandler itemHandler) {
		return Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> i == 0, (i, s) -> false)),
				Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> i == 2 && itemHandler.isItemValid(2, s))),
				Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> i != 2 && itemHandler.isItemValid(i, s))),
				Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> i != 2 && itemHandler.isItemValid(i, s))),
				Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> i != 2 && itemHandler.isItemValid(i, s))),
				Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> i != 2 && itemHandler.isItemValid(i, s))));
	}

	private void burn() {
		burn--;
		if (burn < 0) {
			burn = 0;
		}

		if (fluidLevel > 0 && burn == 0) {
			ItemStack stack = getItemHandler().getStackInSlot(4);
			if ((stack.getItem().equals(Items.LAVA_BUCKET) && hasPlace(Items.BUCKET)) || getBurnTime(stack, SMELTING) > 0) {
				maxBurn = getBurnTime(stack, SMELTING);
				burn = maxBurn;
				if (stack.getItem().equals(Items.LAVA_BUCKET)) {
					addOutput(Items.BUCKET);
				}
				stack.shrink(1);
			}
		}

		if (burn == 0) {
			firstRun = true;
		}
	}

	private void water() {
		if (burn > 0) {
			waterLevel--;
		}
		if (waterLevel < 0) {
			waterLevel = 0;
		}
		if (getItemHandler().getStackInSlot(1).getItem().equals(Items.WATER_BUCKET) &&
				hasPlace(Items.BUCKET) && MAXIMUM_WATER_LEVEL - waterLevel >= 1000) {
			getItemHandler().getStackInSlot(1).shrink(1);
			waterLevel += 1000;
			addOutput(Items.BUCKET);
		}
	}

	private void inTake() {
		if (burn > 0) {
			fluidLevel--;
		}
		if (fluidLevel < 0) {
			fluidLevel = 0;
			fluidType = null;
		}
		if (getItemHandler().getStackInSlot(2).getItem().equals(ModItems.APPLE_MASH.get()) &&
				hasPlace(Items.BUCKET) && MAXIMUM_FLUID_LEVEL - fluidLevel >= 1000) {
			if (fluidType == null) {
				fluidType = DistillerFluidType.APPLE_MASH;
			}
			if (fluidType.equals(DistillerFluidType.APPLE_MASH)) {
				getItemHandler().getStackInSlot(2).shrink(1);
				fluidLevel += 1000;
				addOutput(Items.BUCKET);
			}
		}
		if (getItemHandler().getStackInSlot(2).getItem().equals(ModItems.PEAR_MASH.get()) &&
				hasPlace(Items.BUCKET) && MAXIMUM_FLUID_LEVEL - fluidLevel >= 1000) {
			if (fluidType == null) {
				fluidType = DistillerFluidType.PEAR_MASH;
			}
			if (fluidType.equals(DistillerFluidType.PEAR_MASH)) {
				getItemHandler().getStackInSlot(2).shrink(1);
				fluidLevel += 1000;
				addOutput(Items.BUCKET);
			}
		}
		if (getItemHandler().getStackInSlot(2).getItem().equals(ModItems.MIX_MASH.get()) &&
				hasPlace(Items.BUCKET) && MAXIMUM_FLUID_LEVEL - fluidLevel >= 1000) {
			if (fluidType == null) {
				fluidType = DistillerFluidType.MIX_MASH;
			}
			if (fluidType.equals(DistillerFluidType.MIX_MASH)) {
				getItemHandler().getStackInSlot(2).shrink(1);
				fluidLevel += 1000;
				addOutput(Items.BUCKET);
			}
		}
		if (getItemHandler().getStackInSlot(2).getItem().equals(ModItems.POMACE.get()) &&
				hasPlace(Items.BUCKET) && MAXIMUM_FLUID_LEVEL - fluidLevel >= 1000) {
			if (fluidType == null) {
				fluidType = DistillerFluidType.POMACE_MASH;
			}
			if (fluidType.equals(DistillerFluidType.POMACE_MASH)) {
				getItemHandler().getStackInSlot(2).shrink(1);
				fluidLevel += 1000;
				addOutput(Items.BUCKET);
			}
		}

		if (getItemHandler().getStackInSlot(2).getItem().equals(ModItems.APPLE_PALINKA.get()) &&
				hasPlace(Items.GLASS_BOTTLE) && MAXIMUM_FLUID_LEVEL - fluidLevel >= 200) {
			if (fluidType == null) {
				fluidType = DistillerFluidType.ETHYL;
			}
			if (fluidType.equals(DistillerFluidType.ETHYL)) {
				getItemHandler().getStackInSlot(2).shrink(1);
				fluidLevel += 200;
				addOutput(Items.GLASS_BOTTLE);
			}
		}
		if (getItemHandler().getStackInSlot(2).getItem().equals(ModItems.PEAR_PALINKA.get()) &&
				hasPlace(Items.GLASS_BOTTLE) && MAXIMUM_FLUID_LEVEL - fluidLevel >= 200) {
			if (fluidType == null) {
				fluidType = DistillerFluidType.ETHYL;
			}
			if (fluidType.equals(DistillerFluidType.ETHYL)) {
				getItemHandler().getStackInSlot(2).shrink(1);
				fluidLevel += 200;
				addOutput(Items.GLASS_BOTTLE);
			}
		}
		if (getItemHandler().getStackInSlot(2).getItem().equals(ModItems.MIX_PALINKA.get()) &&
				hasPlace(Items.GLASS_BOTTLE) && MAXIMUM_FLUID_LEVEL - fluidLevel >= 200) {
			if (fluidType == null) {
				fluidType = DistillerFluidType.ETHYL;
			}
			if (fluidType.equals(DistillerFluidType.ETHYL)) {
				getItemHandler().getStackInSlot(2).shrink(1);
				fluidLevel += 200;
				addOutput(Items.GLASS_BOTTLE);
			}
		}
		if (getItemHandler().getStackInSlot(2).getItem().equals(ModItems.POMACE_PALINKA.get()) &&
				hasPlace(Items.GLASS_BOTTLE) && MAXIMUM_FLUID_LEVEL - fluidLevel >= 200) {
			if (fluidType == null) {
				fluidType = DistillerFluidType.ETHYL;
			}
			if (fluidType.equals(DistillerFluidType.ETHYL)) {
				getItemHandler().getStackInSlot(2).shrink(1);
				fluidLevel += 200;
				addOutput(Items.GLASS_BOTTLE);
			}
		}
	}

	private void output() {
		if (burn > 0 && waterLevel > 0 && fluidLevel > 0 && !getItemHandler().getStackInSlot(3).isEmpty()) {
			increaseProgress();
		} else {
			setProgressZero();
		}
		if (isProgressDone()) {
			if (firstRun && fluidType != DistillerFluidType.ETHYL && hasPlace(ModItems.METHYL_ALCOHOL.get())) {
				firstRun = false;
				addOutput(ModItems.METHYL_ALCOHOL.get());
				getItemHandler().getStackInSlot(3).shrink(1);
				setProgressZero();
			}
			switch (fluidType) {
				case APPLE_MASH -> {
					if (hasPlace(ModItems.APPLE_PALINKA.get())) {
						addOutput(ModItems.APPLE_PALINKA.get());
						getItemHandler().getStackInSlot(3).shrink(1);
						setProgressZero();
					}
				}
				case PEAR_MASH -> {
					if (hasPlace(ModItems.PEAR_PALINKA.get())) {
						addOutput(ModItems.PEAR_PALINKA.get());
						getItemHandler().getStackInSlot(3).shrink(1);
						setProgressZero();
					}
				}
				case POMACE_MASH -> {
					if (hasPlace(ModItems.POMACE_PALINKA.get())) {
						addOutput(ModItems.POMACE_PALINKA.get());
						getItemHandler().getStackInSlot(3).shrink(1);
						setProgressZero();
					}
				}
				case MIX_MASH -> {
					if (hasPlace(ModItems.MIX_PALINKA.get())) {
						addOutput(ModItems.MIX_PALINKA.get());
						getItemHandler().getStackInSlot(3).shrink(1);
						setProgressZero();
					}
				}
				case ETHYL -> {
					if (hasPlace(ModItems.ETHYL_ALCOHOL.get())) {
						addOutput(ModItems.ETHYL_ALCOHOL.get());
						getItemHandler().getStackInSlot(3).shrink(1);
						setProgressZero();
					}
				}
			}
		}
	}

	private boolean hasPlace(Item item) {
		return getItemHandler().getStackInSlot(0).isEmpty() || (
				getItemHandler().getStackInSlot(0).getItem().equals(item) &&
						getItemHandler().getStackInSlot(0).getMaxStackSize() > getItemHandler().getStackInSlot(0).getCount());
	}

	private void addOutput(Item item) {
		if (getItemHandler().getStackInSlot(0).isEmpty()) {
			getItemHandler().setStackInSlot(0, new ItemStack(item, 1));
		} else {
			getItemHandler().getStackInSlot(0).grow(1);
		}
	}

	private void setFluidColor() {
		setFluidColor(15, 25, 18, 159);
		if (fluidType == null) {
			return;
		}
		switch (fluidType) {
			case APPLE_MASH -> setFluidColor(237, 87, 95, 255);
			case PEAR_MASH -> setFluidColor(255, 197, 14, 255);
			case POMACE_MASH -> setFluidColor(202, 154, 81, 255);
			case MIX_MASH -> setFluidColor(165, 118, 46, 255);
			case ETHYL -> setFluidColor(195, 195, 220, 110);
		}
	}

	private void setFluidColor(int red, int green, int blue, int alpha) {
		data.set(COLOR_R_KEY, red);
		data.set(COLOR_G_KEY, green);
		data.set(COLOR_B_KEY, blue);
		data.set(COLOR_A_KEY, alpha);
	}

	private void setProgressZero() {
		progress = 0;
		progress2 = 0;
		progress3 = 0;
		progress4 = 0;
	}

	private void increaseProgress() {
		if (progress < maxProgress) {
			progress++;
		} else {
			if (progress2 < MAXIMUM_PROGRESS2) {
				progress2++;
			} else {
				if (progress3 < MAXIMUM_PROGRESS3) {
					progress3++;
				} else {
					if (progress4 < MAXIMUM_PROGRESS4) {
						progress4++;
					}
				}
			}
		}
	}

	private boolean isProgressDone() {
		return progress4 == MAXIMUM_PROGRESS4;
	}
}
