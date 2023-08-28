package hu.xannosz.gastrocraft.blockentity;

import hu.xannosz.gastrocraft.screen.PressMenu;
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

public class PressBlockEntity extends BaseMachineBlockEntity {
	public PressBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(ModBlockEntities.PRESS_ENTITY.get(), blockPos, blockState);
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
					case 0, 1 -> false;
					case 2 -> true;
					case 3 -> stack.getItem() == Items.GLASS_BOTTLE;
					case 4 -> stack.getItem() == Items.BUCKET;
					default -> super.isItemValid(slot, stack);
				};
			}
		};
	}

	@Override
	public @NotNull Component getDisplayName() {
		return Component.translatable("gui.press");
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory, @NotNull Player player) {
		return new PressMenu(containerId, inventory, this, data);
	}

	@Override
	public void setData() {

	}

	@SuppressWarnings("unused")
	public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
		((PressBlockEntity) blockEntity).tick();
	}

	@Override
	protected Map<Direction, LazyOptional<WrappedHandler>> getDirectionWrappedHandlerMap(ItemStackHandler itemHandler) {
		return Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> i == 0 || i == 1, (i, s) -> false)),
				Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> i == 2 && itemHandler.isItemValid(2, s))),
				Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> sideInv(i, s, itemHandler))),
				Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> sideInv(i, s, itemHandler))),
				Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> sideInv(i, s, itemHandler))),
				Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i, s) -> false, (i, s) -> sideInv(i, s, itemHandler))));
	}

	private boolean sideInv(int i, ItemStack s, ItemStackHandler itemHandler) {
		return (i == 3 && itemHandler.isItemValid(3, s)) || (i == 4 && itemHandler.isItemValid(4, s));
	}
}
