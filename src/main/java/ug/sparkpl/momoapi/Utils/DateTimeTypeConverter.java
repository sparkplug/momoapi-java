package ug.sparkpl.momoapi.Utils;


import com.google.gson.*;
import lombok.NonNull;
import org.joda.time.DateTime;

import java.lang.reflect.Type;

public class DateTimeTypeConverter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {
    @Override
    public JsonElement serialize(final @NonNull DateTime src, final @NonNull Type srcType,
                                 final @NonNull JsonSerializationContext context) {
        return new JsonPrimitive(src.getMillis() / 1000);
    }

    @Override
    public DateTime deserialize(final @NonNull JsonElement json, final @NonNull Type type,
                                final @NonNull JsonDeserializationContext context) {
        return new DateTime(json.getAsInt() * 1000L);
    }
}
