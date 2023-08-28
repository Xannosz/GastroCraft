package hu.xannosz.gastrocraft.blockentity;

import hu.xannosz.gastrocraft.item.ModItems;
import hu.xannosz.gastrocraft.screen.WineBarrelMenu;
import hu.xannosz.gastrocraft.util.BarrelFluidType;
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

public class WineBarrelBlockEntity extends BaseMachineBlockEntity {
	public static final int COLOR_R_KEY = 2;
	public static final int COLOR_G_KEY = 3;
	public static final int COLOR_B_KEY = 4;
	public static final int COLOR_A_KEY = 5;
	public static final int FLUID_LEVEL = 6;
	public static final int DATA_SIZE = 7;
	public static final int MAXIMUM_FLUID_LEVEL = 2000;

	private int fluidLevel = 0;
	private int process = 0;
	private boolean locked = false;
	private BarrelFluidType fluidType;

	public WineBarrelBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntities.WINE_BARREL_ENTITY.get(), blockPos, blockState, DATA_SIZE);
	}

	@Override
	protected ItemStackHandler createItemHandler() {
		return new ItemStackHandler(3) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				return switch (slot) {
					case 0 -> false;
					case 1 -> true;
					case 2 -> stack.getItem() == Items.GLASS_BOTTLE || stack.getItem() == Items.BUCKET;
					default -> super.isItemValid(slot, stack);
				};
			}
		};
	}

	@Override
	public @NotNull Component getDisplayName() {
		return Component.translatable("gui.wine_barrel");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
		return new WineBarrelMenu(containerId, inventory, this, data);
	}

	@Override
	public void setData() {
		data.set(FLUID_LEVEL, fluidLevel);
		setFluidColor();
	}

	@SuppressWarnings("unused")
	public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		((WineBarrelBlockEntity) blockEntity).tick();
	}

	@Override
	public void tick() {
		if (level != null && level.isClientSide()) {
			return;
		}

		if (fluidLevel < 100) {
			fluidLevel = 0;
			fluidType = null;
			locked = false;
		}
		if (process < 0) {
			process = 0;
		}

		if (!locked) {
			if (fluidType != null && fluidType.isMash() && hasPlace(Items.BUCKET) && fluidLevel + 500 <= MAXIMUM_FLUID_LEVEL &&
					getItemHandler().getStackInSlot(1).getItem().equals(ModItems.RAW_RED_POMACE.get()) &&
					!fluidType.equals(BarrelFluidType.RAW_POMACE_MASH)) {
				fluidType = BarrelFluidType.RAW_MIX_MASH;
			}
			if (fluidType != null && fluidType.isMash() && hasPlace(Items.BUCKET) && fluidLevel + 500 <= MAXIMUM_FLUID_LEVEL &&
					getItemHandler().getStackInSlot(1).getItem().equals(ModItems.RAW_WHITE_POMACE.get()) &&
					!fluidType.equals(BarrelFluidType.RAW_POMACE_MASH)) {
				fluidType = BarrelFluidType.RAW_MIX_MASH;
			}
			if (fluidType != null && fluidType.isMash() && hasPlace(Items.BUCKET) && fluidLevel + 500 <= MAXIMUM_FLUID_LEVEL &&
					getItemHandler().getStackInSlot(1).getItem().equals(ModItems.RAW_APPLE_MASH.get()) &&
					!fluidType.equals(BarrelFluidType.RAW_APPLE_MASH)) {
				fluidType = BarrelFluidType.RAW_MIX_MASH;
			}
			if (fluidType != null && fluidType.isMash() && hasPlace(Items.BUCKET) && fluidLevel + 500 <= MAXIMUM_FLUID_LEVEL &&
					getItemHandler().getStackInSlot(1).getItem().equals(ModItems.RAW_PEAR_MASH.get()) &&
					!fluidType.equals(BarrelFluidType.RAW_PEAR_MASH)) {
				fluidType = BarrelFluidType.RAW_MIX_MASH;
			}

			handleInput(ModItems.RED_STUM.get(), BarrelFluidType.ROSE_STUM, 100, Items.GLASS_BOTTLE);
			handleOutput(ModItems.RED_STUM.get(), BarrelFluidType.ROSE_STUM, 100, Items.GLASS_BOTTLE);
			handleInput(ModItems.RED_STUM.get(), BarrelFluidType.RED_STUM, 100, Items.GLASS_BOTTLE);
			handleOutput(ModItems.RED_STUM.get(), BarrelFluidType.RED_STUM, 100, Items.GLASS_BOTTLE);
			handleInput(ModItems.WHITE_STUM.get(), BarrelFluidType.WHITE_STUM, 100, Items.GLASS_BOTTLE);
			handleOutput(ModItems.WHITE_STUM.get(), BarrelFluidType.WHITE_STUM, 100, Items.GLASS_BOTTLE);

			handleInput(ModItems.RED_WINE.get(), BarrelFluidType.RED_WINE, 100, Items.GLASS_BOTTLE);
			handleOutput(ModItems.RED_WINE.get(), BarrelFluidType.RED_WINE, 100, Items.GLASS_BOTTLE);
			handleInput(ModItems.ROSE_WINE.get(), BarrelFluidType.ROSE_WINE, 100, Items.GLASS_BOTTLE);
			handleOutput(ModItems.ROSE_WINE.get(), BarrelFluidType.ROSE_WINE, 100, Items.GLASS_BOTTLE);
			handleInput(ModItems.WHITE_WINE.get(), BarrelFluidType.WHITE_WINE, 100, Items.GLASS_BOTTLE);
			handleOutput(ModItems.WHITE_WINE.get(), BarrelFluidType.WHITE_WINE, 100, Items.GLASS_BOTTLE);

			handleInput(ModItems.RAW_RED_POMACE.get(), BarrelFluidType.RAW_POMACE_MASH, 500, Items.BUCKET);
			handleInput(ModItems.RAW_RED_POMACE.get(), BarrelFluidType.RAW_MIX_MASH, 500, Items.BUCKET);
			handleInput(ModItems.RAW_WHITE_POMACE.get(), BarrelFluidType.RAW_POMACE_MASH, 500, Items.BUCKET);
			handleInput(ModItems.RAW_WHITE_POMACE.get(), BarrelFluidType.RAW_MIX_MASH, 500, Items.BUCKET);
			handleInput(ModItems.RAW_PEAR_MASH.get(), BarrelFluidType.RAW_PEAR_MASH, 500, Items.BUCKET);
			handleInput(ModItems.RAW_PEAR_MASH.get(), BarrelFluidType.RAW_MIX_MASH, 500, Items.BUCKET);
			handleInput(ModItems.RAW_APPLE_MASH.get(), BarrelFluidType.RAW_APPLE_MASH, 500, Items.BUCKET);
			handleInput(ModItems.RAW_APPLE_MASH.get(), BarrelFluidType.RAW_MIX_MASH, 500, Items.BUCKET);

			handleOutput(ModItems.POMACE.get(), BarrelFluidType.POMACE_MASH, 500, Items.BUCKET);
			handleOutput(ModItems.PEAR_MASH.get(), BarrelFluidType.PEAR_MASH, 500, Items.BUCKET);
			handleOutput(ModItems.APPLE_MASH.get(), BarrelFluidType.APPLE_MASH, 500, Items.BUCKET);
			handleOutput(ModItems.MIX_MASH.get(), BarrelFluidType.MIX_MASH, 500, Items.BUCKET);

			ItemStack itemStack = getItemHandler().getStackInSlot(1);
			if (itemStack.getItem().equals(ModItems.SULFUR.get()) && fluidType != null && fluidType.isStum()) {
				itemStack.shrink(1);
				locked = true;
				process = 3 * 20 * 60 * 20; // 3 day * 20 minutes * 60 seconds * 20 ticks
			}
			if (itemStack.getItem().equals(Items.SUGAR) && fluidType != null && fluidType.isMash()) {
				itemStack.shrink(1);
				locked = true;
				process = 2 * 20 * 60 * 20; // 2 day * 20 minutes * 60 seconds * 20 ticks
			}
			if (itemStack.getItem().equals(ModItems.RAW_RED_POMACE.get()) && fluidType != null &&
					fluidType.equals(BarrelFluidType.ROSE_STUM) &&
					hasPlace(Items.BUCKET)) {
				itemStack.shrink(1);
				fluidType = BarrelFluidType.RED_STUM;
				addItem(Items.BUCKET);
			}
		}

		if (locked && fluidType != null) {
			if (process > 0) {
				process--;
			}
			if (process == 0 && fluidType.equals(BarrelFluidType.RED_STUM)) {
				fluidType = BarrelFluidType.RED_WINE;
				locked = false;
			}
			if (process == 0 && fluidType.equals(BarrelFluidType.ROSE_STUM)) {
				fluidType = BarrelFluidType.ROSE_WINE;
				locked = false;
			}
			if (process == 0 && fluidType.equals(BarrelFluidType.WHITE_STUM)) {
				fluidType = BarrelFluidType.WHITE_WINE;
				locked = false;
			}
			if (process == 0 && fluidType.equals(BarrelFluidType.RAW_POMACE_MASH)) {
				fluidType = BarrelFluidType.POMACE_MASH;
				locked = false;
			}
			if (process == 0 && fluidType.equals(BarrelFluidType.RAW_APPLE_MASH)) {
				fluidType = BarrelFluidType.APPLE_MASH;
				locked = false;
			}
			if (process == 0 && fluidType.equals(BarrelFluidType.RAW_PEAR_MASH)) {
				fluidType = BarrelFluidType.PEAR_MASH;
				locked = false;
			}
			if (process == 0 && fluidType.equals(BarrelFluidType.RAW_MIX_MASH)) {
				fluidType = BarrelFluidType.MIX_MASH;
				locked = false;
			}
		}

		setDataInter();
	}

	private void handleInput(Item item, BarrelFluidType fluidTypeIn, int amount, Item leftover) {
		ItemStack itemStack = getItemHandler().getStackInSlot(1);

		if (itemStack.getItem().equals(item) && hasPlace(leftover) && fluidLevel + amount <= MAXIMUM_FLUID_LEVEL) {
			if (fluidType == null) {
				fluidType = fluidTypeIn;
			}
			if (fluidType.equals(fluidTypeIn)) {
				fluidLevel += amount;
				itemStack.shrink(1);
				addItem(leftover);
			}
		}
	}

	private void handleOutput(Item item, BarrelFluidType fluidTypeOut, int amount, Item container) {
		ItemStack itemStack = getItemHandler().getStackInSlot(2);

		if (itemStack.getItem().equals(container) && hasPlace(item) && fluidLevel > 0) {
			if (fluidType == null) {
				return;
			}
			if (fluidType.equals(fluidTypeOut)) {
				fluidLevel -= amount;
				itemStack.shrink(1);
				addItem(item);
			}
		}
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		tag.putInt("fluidLevel", fluidLevel);
		tag.putInt("process", process);
		if (fluidType != null) {
			tag.putString("fluidType", fluidType.name());
		}
		tag.putBoolean("locked", locked);
		super.saveAdditional(tag);
	}

	@Override
	public void load(@NotNull CompoundTag nbt) {
		super.load(nbt);
		fluidLevel = nbt.getInt("fluidLevel");
		process = nbt.getInt("process");
		if (nbt.contains("fluidType")) {
			fluidType = BarrelFluidType.valueOf(nbt.getString("fluidType"));
		}
		locked = nbt.getBoolean("locked");
		setDataInter();
	}

	private void setFluidColor() {
		setFluidColor(15, 25, 18, 159);
		if (fluidType == null) {
			return;
		}
		switch (fluidType) {
			case RED_STUM -> setFluidColor(145, 99, 142, 255);
			case ROSE_STUM -> setFluidColor(196, 187, 226, 255);
			case WHITE_STUM -> setFluidColor(121, 109, 13, 255);
			case RED_WINE -> setFluidColor(94, 10, 0, 255);
			case ROSE_WINE -> setFluidColor(94, 10, 0, 135);
			case WHITE_WINE -> setFluidColor(202, 178, 1, 137);
			case RAW_APPLE_MASH -> setFluidColor(255, 113, 119, 255);
			case RAW_PEAR_MASH -> setFluidColor(255, 222, 55, 255);
			case RAW_POMACE_MASH -> setFluidColor(239, 188, 114, 255);
			case RAW_MIX_MASH -> setFluidColor(207, 156, 83, 255);
			case APPLE_MASH -> setFluidColor(237, 87, 95, 255);
			case PEAR_MASH -> setFluidColor(255, 197, 14, 255);
			case POMACE_MASH -> setFluidColor(202, 154, 81, 255);
			case MIX_MASH -> setFluidColor(165, 118, 46, 255);
		}
	}

	private void setFluidColor(int red, int green, int blue, int alpha) {
		data.set(COLOR_R_KEY, red);
		data.set(COLOR_G_KEY, green);
		data.set(COLOR_B_KEY, blue);
		data.set(COLOR_A_KEY, alpha);
	}

	private boolean hasPlace(Item item) {
		ItemStack outStack = getItemHandler().getStackInSlot(0);
		return outStack.getItem().equals(Items.AIR) ||
				(outStack.getItem().equals(item) && outStack.getCount() < outStack.getMaxStackSize());
	}

	private void addItem(Item item) {
		ItemStack outStack = getItemHandler().getStackInSlot(0);
		if (outStack.getItem().equals(Items.AIR)) {
			getItemHandler().setStackInSlot(0, new ItemStack(item, 1));
		} else {
			outStack.grow(1);
		}
	}

	@Override
	protected Map<Direction, LazyOptional<WrappedHandler>> getDirectionWrappedHandlerMap(ItemStackHandler itemHandler) {
		return Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> i == 0, (i, s) -> false)),
				Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> i == 1 && itemHandler.isItemValid(1, s))),
				Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> i == 2 && itemHandler.isItemValid(2, s))),
				Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> i == 2 && itemHandler.isItemValid(2, s))),
				Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> i == 2 && itemHandler.isItemValid(2, s))),
				Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> i == 2 && itemHandler.isItemValid(2, s))));
	}
}
