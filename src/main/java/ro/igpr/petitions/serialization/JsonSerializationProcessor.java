package ro.igpr.petitions.serialization;

import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import com.strategicgains.hyperexpress.domain.hal.HalResource;
import com.strategicgains.hyperexpress.serialization.jackson.HalResourceDeserializer;
import com.strategicgains.hyperexpress.serialization.jackson.HalResourceSerializer;
import org.restexpress.ContentType;
import org.restexpress.serialization.json.JacksonJsonProcessor;

import java.util.UUID;

public class JsonSerializationProcessor
        extends JacksonJsonProcessor {

    public JsonSerializationProcessor() {
        super(false);
        addSupportedMediaTypes(ContentType.HAL_JSON);
    }

    @Override
    protected void initializeModule(SimpleModule module) {
        super.initializeModule(module);

        // For UUID as entity identifiers...
        module.addDeserializer(UUID.class, new UUIDDeserializer());
        module.addSerializer(UUID.class, new UUIDSerializer());

        // Support HalResource (de)serialization.
        module.addDeserializer(HalResource.class, new HalResourceDeserializer());
        module.addSerializer(HalResource.class, new HalResourceSerializer());
    }
}
