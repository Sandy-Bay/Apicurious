package sandybay.apicurious.data;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import sandybay.apicurious.Apicurious;
import sandybay.apicurious.common.register.ApicuriousBlockRegistration;
import sandybay.apicurious.common.register.ApicuriousItemRegistration;

public class ApicuriousLangProvider extends LanguageProvider
{
    public ApicuriousLangProvider(PackOutput output)
    {
        super(output, Apicurious.MODID, "en_us");
    }

    @Override
    protected void addTranslations()
    {
        add(ApicuriousItemRegistration.DRONE.get(), "Drone");
        add(ApicuriousItemRegistration.PRINCESS.get(), "Princess");
        add(ApicuriousItemRegistration.QUEEN.get(), "Queen");
        add(ApicuriousItemRegistration.SIEVE.get(), "Sieve");

        add(ApicuriousBlockRegistration.APIARY.asItem(), "Apiary");
        add(ApicuriousBlockRegistration.BEE_HOUSING.asItem(), "Bee Housing");

        add(ApicuriousBlockRegistration.FOREST_HIVE.asItem(), "Forest Hive");
        add(ApicuriousBlockRegistration.MEADOW_HIVE.asItem(), "Meadow Hive");
        add(ApicuriousBlockRegistration.TROPICAL_HIVE.asItem(), "Tropical Hive");
        add(ApicuriousBlockRegistration.WINTRY_HIVE.asItem(), "Wintry Hive");
        add(ApicuriousBlockRegistration.MARSHY_HIVE.asItem(), "Marshy Hive");
        add(ApicuriousBlockRegistration.ROCKY_HIVE.asItem(), "Rocky Hive");
        add(ApicuriousBlockRegistration.NETHER_HIVE.asItem(), "Nether Hive");
        add(ApicuriousBlockRegistration.ENDER_HIVE.asItem(), "Ender Hive");


        add("itemGroup.apicurious.bee", "Apicurious: Bee");
        add("itemGroup.apicurious.general", "Apicurious");

        add("apicurious.menu.apiary", "Apiary");


        add("apicurious.species.undefined", "Undefined");
        add("apicurious.species.debug", "Debug");
        add("apicurious.species.forest", "Forest");
        add("apicurious.species.meadow", "Meadow");
        add("apicurious.species.modest", "Modest");
        add("apicurious.species.tropical", "Tropical");
        add("apicurious.species.wintry", "Wintry");
        add("apicurious.species.marshy", "Marshy");
        add("apicurious.species.rocky", "Rocky");
        add("apicurious.species.nether", "Nether");
        add("apicurious.species.ender", "Ender");
        add("apicurious.species.common", "Common");
        add("apicurious.species.cultivated", "Cultivated");
        add("apicurious.species.industrious", "Industrious");
        add("apicurious.species.imperial", "Imperial");
        add("apicurious.species.austere", "Austere");
    }
}
