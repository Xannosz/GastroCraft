package hu.xannosz.gastrocraft.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class Alcohol extends FoodInGlass {
	private final Random random = new Random();
	private final boolean isMethyl;
	private final int alcoholStrong;

	public Alcohol(FoodProperties foodProperty, Item returner, boolean isMethyl, int alcoholStrong) {
		super(foodProperty, returner);
		this.isMethyl = isMethyl;
		this.alcoholStrong = alcoholStrong;
	}

	@Override
	protected void alcoholExecution(ServerPlayer serverplayer, ItemStack itemStack) {
		int probabilityBonus = alcoholStrong;
		int durationBonus = alcoholStrong * 50;

		if (isMethyl) {
			addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.HUNGER,
					4000 + durationBonus, 0), 20 + probabilityBonus);
			addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.BLINDNESS,
					40000 + durationBonus, 0), 20 + probabilityBonus);
			addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.CONFUSION,
					5000 + durationBonus, 0), 20 + probabilityBonus);
			addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
					5000 + durationBonus, 0), 20 + probabilityBonus);
			addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.POISON,
					10000 + durationBonus, 0), 20 + probabilityBonus);
			return;
		}

		addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.BLINDNESS,
				2500, 0), probabilityBonus - 5);
		addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.CONFUSION,
				2500, 0), probabilityBonus);
		addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
				5000, 0), probabilityBonus);
		addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.POISON,
				500, 0), probabilityBonus - 5);

		addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.MOVEMENT_SPEED,
				500 + durationBonus, 1), 10 + probabilityBonus);
		addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.DIG_SPEED,
				500 + durationBonus, 1), 10 + probabilityBonus);
		addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.DAMAGE_BOOST,
				500 + durationBonus, 1), 10 + probabilityBonus);
		addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.HEAL,
				500 + durationBonus, 1), 10 + probabilityBonus);
		addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.REGENERATION,
				500 + durationBonus, 1), 10 + probabilityBonus);
		addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,
				500 + durationBonus, 1), 10 + probabilityBonus);
		addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.HEALTH_BOOST,
				500 + durationBonus, 1), 10 + probabilityBonus);
		addEffectWithProbability(serverplayer, new MobEffectInstance(MobEffects.LUCK,
				500 + durationBonus, 1), 10 + probabilityBonus);
	}

	private void addEffectWithProbability(ServerPlayer serverplayer, MobEffectInstance effect, int probability) {
		if (random.nextInt(100) <= probability) {
			serverplayer.addEffect(effect);
		}
	}
}
