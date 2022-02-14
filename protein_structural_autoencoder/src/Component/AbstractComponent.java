package Component;

import java.util.UUID;

public abstract class AbstractComponent {
	
	public static String getID() {
		UUID uuid = UUID.randomUUID();
        return "component_"+uuid.toString();
	}

	public abstract void update();
	
}
