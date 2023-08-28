package hu.xannosz.gastrocraft.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class BasicFood extends Item {
	public BasicFood(FoodProperties foodProperty) {
		this(16, foodProperty);
	}

	public BasicFood(int stack, FoodProperties foodProperty) {
		super(new Item.Properties().stacksTo(stack).food(foodProperty));
	}

	public String getName(){
		String[] split = getDescriptionId().split("\\.");
		return split[split.length-1];
	}
}
