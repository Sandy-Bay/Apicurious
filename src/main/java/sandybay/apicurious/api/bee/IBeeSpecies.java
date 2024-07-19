package sandybay.apicurious.api.bee;

import net.minecraft.network.chat.Component;
import sandybay.apicurious.common.bee.species.VisualData;

public interface IBeeSpecies {
    Component getReadableName();
    VisualData getVisualData();

    //ProductionData getProductionData();
    //EnvironmentalData getEnvironmentalData();
}
