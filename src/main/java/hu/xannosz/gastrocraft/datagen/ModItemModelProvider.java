package hu.xannosz.gastrocraft.datagen;

import hu.xannosz.gastrocraft.GastroCraft;
import hu.xannosz.gastrocraft.item.BasicFood;
import hu.xannosz.gastrocraft.item.FoodInGlass;
import hu.xannosz.gastrocraft.item.ModItems;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

@Slf4j
public class ModItemModelProvider extends ItemModelProvider {
	public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator.getPackOutput(), GastroCraft.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		ModItems.ITEMS.getEntries().forEach(
				itemRegistryObject -> {
					Item item = itemRegistryObject.get();
					if (item instanceof BasicFood basicFood) {
						addItem(basicFood);
					}
					if (item instanceof FoodInGlass foodInGlass) {
						addItem(foodInGlass);
					}
				}
		);
	}

	private void addItem(BasicFood item) {
		ResourceLocation texture = new ResourceLocation(GastroCraft.MOD_ID, "item/" + item.getName());
		try {
			withExistingParent(item.getName(), new ResourceLocation("item/generated")).texture("layer0",
					texture);
		} catch (IllegalArgumentException exception) {
			log.error("Texture does not exist.", exception);
		}
	}

	private void addItem(FoodInGlass item) {
		ResourceLocation texture = new ResourceLocation(GastroCraft.MOD_ID, "item/" + item.getName());
		try {
			withExistingParent(item.getName(), new ResourceLocation("item/generated")).texture("layer0",
					texture);
		} catch (IllegalArgumentException exception) {
			log.error("Texture does not exist.", exception);
		}
	}
}
