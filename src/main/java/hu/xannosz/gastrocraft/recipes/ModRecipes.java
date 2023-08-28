package hu.xannosz.gastrocraft.recipes;

import hu.xannosz.gastrocraft.blockentity.DryerBlockEntity;
import hu.xannosz.gastrocraft.blockentity.MillBlockEntity;
import hu.xannosz.gastrocraft.blockentity.PressBlockEntity;
import hu.xannosz.gastrocraft.item.ModItems;
import lombok.experimental.UtilityClass;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
public class ModRecipes {
	private static final Map<Class<? extends BlockEntity>,
			List<BaseRecipe>> RECIPES = new HashMap<>();

	public static int getValidRecipeId(BlockEntity entity) {
		List<BaseRecipe> recipes = RECIPES.get(entity.getClass());
		for (int i = 0; i < recipes.size(); i++) {
			if (recipes.get(i).isValid(entity)) {
				return i;
			}
		}
		return -1;
	}

	public static int getMaxProgress(BlockEntity entity, int id) {
		if (id != -1) {
			return RECIPES.get(entity.getClass()).get(id).getMaxProgress();
		}
		return 100;
	}

	public static void executeRecipe(BlockEntity entity, int id) {
		if (id != -1) {
			RECIPES.get(entity.getClass()).get(id).execute(entity);
		}
	}

	public static void addRecipe(Class<? extends BlockEntity> clazz, BaseRecipe recipe) {
		if (!RECIPES.containsKey(clazz)) {
			RECIPES.put(clazz, new ArrayList<>());
		}
		RECIPES.get(clazz).add(recipe);
	}

	static {
		addRecipe(MillBlockEntity.class, new MillRecipe(Items.WHEAT, ModItems.FLOUR.get(), 3));
		addRecipe(DryerBlockEntity.class, new DryerRecipe(ModItems.RED_GRAPE.get(), ModItems.RAISIN.get()));
		addRecipe(DryerBlockEntity.class, new DryerRecipe(ModItems.WHITE_GRAPE.get(), ModItems.RAISIN.get()));
		addRecipe(DryerBlockEntity.class, new DryerRecipe(Items.MILK_BUCKET, ModItems.CURD.get()));
		addRecipe(PressBlockEntity.class, new PressRecipe(Items.MILK_BUCKET, ModItems.CHEESE.get(), 900));
		addRecipe(PressBlockEntity.class, new PressRecipe(ModItems.CURD.get(), ModItems.COTTAGE.get(), 900));
		addRecipe(PressBlockEntity.class, new PressRecipe(ModItems.WHITE_GRAPE.get(),
				ModItems.RAW_WHITE_POMACE.get(), ModItems.WHITE_STUM.get(), 600));
		addRecipe(PressBlockEntity.class, new PressRecipe(ModItems.RED_GRAPE.get(),
				ModItems.RAW_RED_POMACE.get(), ModItems.RED_STUM.get(), 600));
		addRecipe(PressBlockEntity.class, new PressRecipe(Items.APPLE,
				ModItems.RAW_APPLE_MASH.get(), ModItems.APPLE_JUICE.get(), 600));
		addRecipe(PressBlockEntity.class, new PressRecipe(ModItems.PEAR.get(),
				ModItems.RAW_PEAR_MASH.get(), ModItems.PEAR_JUICE.get(), 600));
	}
}
