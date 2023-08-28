package hu.xannosz.gastrocraft.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public abstract class GrapePlantBlock extends BushBlock implements BonemealableBlock {
	public static final int MAX_AGE = 22;
	public static final int MAX_GREEN_AGE = 17;
	public static final int BACK_GREEN_AGE = 13;
	public static final IntegerProperty AGE = IntegerProperty.create("age", 1, MAX_AGE);
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),//0 invalid
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), //8
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 18.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 20.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 22.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 24.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 26.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 28.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 30.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 32.0D, 16.0D), //green max state
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 30.0D, 16.0D), //18
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 30.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 30.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 30.0D, 16.0D),
			Block.box(0.0D, 0.0D, 0.0D, 16.0D, 30.0D, 16.0D)
	};

	public GrapePlantBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 1));
	}

	@SuppressWarnings("deprecation")
	public @NotNull VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos,
										@NotNull CollisionContext collisionContext) {
		return SHAPE_BY_AGE[blockState.getValue(AGE)];
	}

	@Override
	public boolean canSurvive(@NotNull BlockState blockState, LevelReader levelReader, @NotNull BlockPos blockPos) {
		return (levelReader.getRawBrightness(blockPos, 0) >= 8 || levelReader.canSeeSky(blockPos)) &&
				super.canSurvive(blockState, levelReader, blockPos) &&
				levelReader.getBlockState(blockPos.below()).is(Blocks.FARMLAND);
	}

	@Override
	protected boolean mayPlaceOn(BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
		return blockState.is(Blocks.FARMLAND);
	}

	public BlockState getStateForAge(int age) {
		return this.defaultBlockState().setValue(AGE, age);
	}

	public boolean isRandomlyTicking(@NotNull BlockState blockState) {
		return blockState.getValue(AGE) < MAX_AGE;
	}

	@SuppressWarnings("deprecation")
	public void randomTick(@NotNull BlockState blockState, ServerLevel serverLevel, @NotNull BlockPos blockPos,
						   @NotNull RandomSource randomSource) {
		if (serverLevel.getRawBrightness(blockPos, 0) >= 9) {
			final int age = blockState.getValue(AGE);

			if (age < MAX_AGE && age != MAX_GREEN_AGE) {
				serverLevel.setBlock(blockPos, this.getStateForAge(age + 1), 2);
			}
		}
	}

	@SuppressWarnings("deprecation")
	public @NotNull ItemStack getCloneItemStack(@NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		return new ItemStack(getSeed());
	}

	public boolean isValidBonemealTarget(@NotNull LevelReader levelReader, @NotNull BlockPos blockPos, @NotNull BlockState blockState, boolean p_52261_) {
		return blockState.getValue(AGE) < MAX_AGE;
	}

	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource randomSource, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		return true;
	}

	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource randomSource, @NotNull BlockPos blockPos, @NotNull BlockState blockState) {
		final int age = blockState.getValue(AGE);

		if (age == MAX_GREEN_AGE) {
			SimpleContainer inventory = new SimpleContainer(1);
			inventory.setItem(0, new ItemStack(getSeed()));
			Containers.dropContents(level, blockPos, inventory);
			return;
		}

		int i = age + 1;
		if (i > MAX_AGE) {
			i = MAX_AGE;
		}

		level.setBlock(blockPos, this.getStateForAge(i), 2);
	}

	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
		blockBlockStateBuilder.add(AGE);
	}

	@Override
	@SuppressWarnings("deprecation")
	public @NotNull InteractionResult use(@NotNull BlockState blockState, Level level, @NotNull BlockPos pos,
										  @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
		final int age = blockState.getValue(AGE);
		if (!level.isClientSide() && (age == MAX_GREEN_AGE || age == MAX_AGE)
				&& player.getItemInHand(hand).is(Items.SHEARS)) {
			player.getItemInHand(hand).hurtAndBreak(1, player, p -> {
			});
			if (age == MAX_GREEN_AGE) {
				level.setBlock(pos, this.getStateForAge(age + 1), 2);
				return InteractionResult.SUCCESS;
			}

			if (age == MAX_AGE) {
				SimpleContainer inventory = new SimpleContainer(1);
				inventory.setItem(0, new ItemStack(getGrape(), 3));
				Containers.dropContents(level, pos, inventory);
				level.setBlock(pos, this.getStateForAge(BACK_GREEN_AGE), 2);
				return InteractionResult.SUCCESS;
			}
		}

		return super.use(blockState, level, pos, player, hand, hitResult);
	}

	public abstract Item getGrape();

	public abstract Item getSeed();
}