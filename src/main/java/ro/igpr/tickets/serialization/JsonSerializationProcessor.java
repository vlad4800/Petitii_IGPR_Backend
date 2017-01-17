package ro.igpr.tickets.serialization;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.strategicgains.hyperexpress.domain.hal.HalResource;
import com.strategicgains.hyperexpress.serialization.jackson.HalResourceDeserializer;
import com.strategicgains.hyperexpress.serialization.jackson.HalResourceSerializer;
import org.restexpress.ContentType;
import org.restexpress.serialization.json.JacksonJsonProcessor;

public class JsonSerializationProcessor
        extends JacksonJsonProcessor {

    public JsonSerializationProcessor() {
        super(false);
        addSupportedMediaTypes(ContentType.HAL_JSON);
    }

    @Override
    protected void initializeModule(SimpleModule module) {
        super.initializeModule(module);

        // Support HalResource (de)serialization.
        module.addDeserializer(HalResource.class, new HalResourceDeserializer());
        module.addSerializer(HalResource.class, new HalResourceSerializer());
    }
}
