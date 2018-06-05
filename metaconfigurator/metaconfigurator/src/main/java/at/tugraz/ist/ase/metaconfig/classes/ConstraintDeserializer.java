package at.tugraz.ist.ase.metaconfig.classes;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class ConstraintDeserializer implements JsonDeserializer<Constraint> {
	public Constraint deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		String type = json.getAsJsonObject().get("type").getAsString();
		
		if (type.equals("incompatible")) {
			return new Gson().fromJson(json, ConIncompatible.class);
		} else if (type.equals("requires")) {
			return new Gson().fromJson(json, ConRequires.class);
		} else if (type.equals("resource")) {
			return new Gson().fromJson(json, ConResource.class);
		} else {
			return null;
		}
	}
}
