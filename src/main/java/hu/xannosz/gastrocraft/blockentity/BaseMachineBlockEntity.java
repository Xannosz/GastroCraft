package hu.xannosz.gastrocraft.blockentity;

import hu.xannosz.gastrocraft.block.BaseBlockWithBlockEntity;
import hu.xannosz.gastrocraft.recipes.ModRecipes;
import hu.xannosz.gastrocraft.util.WrappedHandler;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Map;

public abstract class BaseMachineBlockEntity extends BlockEntity implements MenuProvider {
	@Getter
	private final ItemStackHandler itemHandler;

	protected int progress;
	protected int maxProgress;
	private int recipeId;
	@Getter
	private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
	protected final ContainerData data;

	public BaseMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos,
								  BlockState blockState) {
		this(blockEntityType, blockPos, blockState, 2);
	}

	public BaseMachineBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos,
								  BlockState blockState, int dataSize) {
		super(blockEntityType, blockPos, blockState);
		data = new SimpleContainerData(dataSize);
		this.itemHandler = createItemHandler();
	}

	protected abstract ItemStackHandler createItemHandler();

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag) {
		tag.put("inventory", itemHandler.serializeNBT());
		tag.putInt("progress", progress);
		tag.putInt("maxProgress", maxProgress);
		tag.putInt("recipeId", recipeId);
		super.saveAdditional(tag);
	}

	@Override
	public void load(@NotNull CompoundTag nbt) {
		super.load(nbt);
		itemHandler.deserializeNBT(nbt.getCompound("inventory"));
		progress = nbt.getInt("progress");
		maxProgress = nbt.getInt("maxProgress");
		recipeId = nbt.getInt("recipeId");
		setDataInter();
	}

	protected abstract void setData();

	protected void setDataInter() {
		data.set(0, progress);
		data.set(1, maxProgress);
		setData();
	}

	public void drops() {
		SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
		for (int i = 0; i < itemHandler.getSlots(); i++) {
			inventory.setItem(i, itemHandler.getStackInSlot(i));
		}
	}

	public void tick() {
		if (level != null && level.isClientSide()) {
			return;
		}

		if (isRecipeStillValid()) {
			progress++;
			if (progress >= maxProgress) {
				ModRecipes.executeRecipe(this, recipeId);
				progress = 0;
			}
		} else {
			progress = 0;
			recipeId = ModRecipes.getValidRecipeId(this);
			maxProgress = ModRecipes.getMaxProgress(this, recipeId);
		}
		setDataInter();
		setChanged();
	}

	private boolean isRecipeStillValid() {
		return recipeId != -1 && recipeId == ModRecipes.getValidRecipeId(this);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		lazyItemHandler = LazyOptional.of(() -> itemHandler);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		lazyItemHandler.invalidate();
	}

	@Nonnull
	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == ForgeCapabilities.ITEM_HANDLER) {

			if (level == null) {
				return super.getCapability(cap, side);
			}

			final LazyOptional<IItemHandler> lazyItemHandler = getLazyItemHandler().cast();
			final ItemStackHandler itemHandler = this.getItemHandler();

			final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
					getDirectionWrappedHandlerMap(itemHandler);

			if (side == null) {
				return lazyItemHandler.cast();
			}

			Direction localDir = this.getBlockState().getValue(BaseBlockWithBlockEntity.FACING);

			if (side == Direction.UP || side == Direction.DOWN) {
				return directionWrappedHandlerMap.get(side).cast();
			}

			return switch (localDir) {
				default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
				case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
				case SOUTH -> directionWrappedHandlerMap.get(side).cast();
				case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
			};
		}

		return super.getCapability(cap, side);
	}

	protected abstract Map<Direction, LazyOptional<WrappedHandler>> getDirectionWrappedHandlerMap(ItemStackHandler itemHandler);
}
