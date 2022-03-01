package display;

import java.util.UUID;

public class Identifier {

	public static String getID() {
		UUID uuid = UUID.randomUUID();
        return "identifier_"+uuid.toString();
	}
	
}
