package hu.xannosz.gastrocraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FoodInGlass extends Item {

	private static final int DRINK_DURATION = 32;
	private final FoodProperties foodProperty;
	private final Item returner;

	public FoodInGlass(FoodProperties foodProperty) {
		this(foodProperty, Items.GLASS_BOTTLE);
	}

	public FoodInGlass(FoodProperties foodProperty, Item returner) {
		super(new Item.Properties().stacksTo(1));
		this.foodProperty = foodProperty;
		this.returner = returner;
	}

	public @NotNull ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity entity) {
		if (entity instanceof ServerPlayer serverplayer) {
			serverplayer.getFoodData().eat(foodProperty.getNutrition(), foodProperty.getSaturationModifier());

			alcoholExecution(serverplayer, itemStack);

			CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, itemStack);
			serverplayer.awardStat(Stats.ITEM_USED.get(this));
		}

		if (entity instanceof Player && !((Player) entity).getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		return itemStack.isEmpty() ? new ItemStack(returner) : itemStack;
	}

	@SuppressWarnings("unused")
	protected void alcoholExecution(ServerPlayer serverplayer, ItemStack itemStack) {
		//default
	}

	public int getUseDuration(@NotNull ItemStack itemStack) {
		return DRINK_DURATION;
	}

	public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
		return UseAnim.DRINK;
	}

	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand) {
		return ItemUtils.startUsingInstantly(level, player, interactionHand);
	}

	public String getName(){
		String[] split = getDescriptionId().split("\\.");
		return split[split.length-1];
	}
}
