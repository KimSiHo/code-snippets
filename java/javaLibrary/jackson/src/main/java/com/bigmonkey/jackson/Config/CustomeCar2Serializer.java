package com.bigmonkey.jackson.Config;

import java.io.IOException;

import com.bigmonkey.jackson.dto.objectMapper.Car;
import com.bigmonkey.jackson.dto.objectMapper.Car2;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomeCar2Serializer extends StdSerializer<Car2> {

    public CustomeCar2Serializer() {
        this(null);
    }

    protected CustomeCar2Serializer(Class<Car2> t) {
        super(t);
    }

    @Override
    public void serialize(Car2 car2, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("car_brand", car2.getType());
        jsonGenerator.writeEndObject();

    }
}
