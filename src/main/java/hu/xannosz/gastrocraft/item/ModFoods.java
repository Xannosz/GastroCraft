package hu.xannosz.gastrocraft.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
	public static FoodProperties fastFood(int nutrition, float saturation) {
		return (new FoodProperties.Builder()).nutrition(nutrition).saturationMod(saturation).fast().build();
	}

	public static FoodProperties normalFood(int nutrition, float saturation) {
		return (new FoodProperties.Builder()).nutrition(nutrition).saturationMod(saturation).build();
	}

	public static FoodProperties rawFood(int nutrition, float saturation, float probability) {
		return (new FoodProperties.Builder()).nutrition(nutrition).saturationMod(saturation)
				.effect(() -> new MobEffectInstance(MobEffects.HUNGER, 200, 0), probability).build();
	}

	public static FoodProperties meat() {
		return (new FoodProperties.Builder()).nutrition(6).saturationMod(0.8F).meat().build();
	}

	public static FoodProperties meatBon() {
		return (new FoodProperties.Builder()).nutrition(8).saturationMod(1.2F).meat().build();
	}

	public static FoodProperties rawMeat() {
		return (new FoodProperties.Builder()).nutrition(3).saturationMod(0.3F)
				.effect(() -> new MobEffectInstance(MobEffects.HUNGER, 200, 0), 0.6F)
				.meat().build();
	}

	public static FoodProperties filledPasta(int count) {
		return normalFood(count * 3, count * 0.4F);
	}

	public static FoodProperties pasta(int count) {
		return normalFood(count * 2, count * 0.2F);
	}

	public static FoodProperties rawPasta(int count) {
		return rawFood(count, count * 0.1F, 0.1F);
	}
}
