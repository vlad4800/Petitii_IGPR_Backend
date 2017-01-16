package ro.igpr.petitions.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.UUID;

public class UuidSerializer
extends JsonSerializer<UUID>
{
	@Override
	public void serialize(UUID objectId, JsonGenerator json, SerializerProvider provider)
	throws IOException, JsonProcessingException
	{
		json.writeString(objectId.toString());
	}
}
