package hu.xannosz.gastrocraft.blockentity;

import hu.xannosz.gastrocraft.screen.MillMenu;
import hu.xannosz.gastrocraft.util.WrappedHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
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

public class MillBlockEntity extends BaseMachineBlockEntity {
	public MillBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntities.MILL_ENTITY.get(), blockPos, blockState);
	}

	@Override
	protected ItemStackHandler createItemHandler() {
		return new ItemStackHandler(2) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				return switch (slot) {
					case 0 -> false;
					case 1 -> stack.getItem() == Items.WHEAT;
					default -> super.isItemValid(slot, stack);
				};
			}
		};
	}

	@Override
	public @NotNull Component getDisplayName() {
		return Component.translatable("gui.mill");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
		return new MillMenu(containerId, inventory, this, data);
	}

	@Override
	public void setData() {

	}

	@SuppressWarnings("unused")
	public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		((MillBlockEntity) blockEntity).tick();
	}

	@Override
	protected Map<Direction, LazyOptional<WrappedHandler>> getDirectionWrappedHandlerMap(ItemStackHandler itemHandler) {
		return Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> i == 0, (i, s) -> false)),
				Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> i == 1 && itemHandler.isItemValid(1, s))),
				Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> i == 1 && itemHandler.isItemValid(1, s))),
				Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> i == 1 && itemHandler.isItemValid(1, s))),
				Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> i == 1 && itemHandler.isItemValid(1, s))),
				Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> i == 1 && itemHandler.isItemValid(1, s))));
	}
}
