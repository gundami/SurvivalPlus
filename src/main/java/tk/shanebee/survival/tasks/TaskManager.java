package tk.shanebee.survival.tasks;

import tk.shanebee.survival.Survival;
import tk.shanebee.survival.config.Config;

/**
 * Internal task manager
 */
public class TaskManager {

	public TaskManager(Survival plugin) {
		final int ALERT_INTERVAL = plugin.getSurvivalConfig().MECHANICS_ALERT_INTERVAL;
		Config config = plugin.getSurvivalConfig();
		if (config.MECHANICS_ENERGY_ENABLED) {
		    new EnergyDrain(plugin);
        }
		if (config.MECHANICS_THIRST_ENABLED) {
			new ThirstDrain(plugin);
			if (!config.MECHANICS_STATUS_SCOREBOARD && ALERT_INTERVAL > 0) {
				new ThirstAlert(plugin);
			}
		}
		if (config.MECHANICS_FOOD_DIVERSITY_ENABLED) {
			new NutrientsDrain(plugin);
			new NutrientsEffect(plugin);
			if (!config.MECHANICS_STATUS_SCOREBOARD && ALERT_INTERVAL > 0) {
				new NutrientsAlert(plugin);
			}
		}

		if (config.MECHANICS_WEATHER_ENABLED) {
            new WeatherTask(plugin);
        }
	}

}
