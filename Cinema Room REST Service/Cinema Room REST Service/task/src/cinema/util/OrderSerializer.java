package cinema.util;

import cinema.domain.Order;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class OrderSerializer extends StdSerializer<Order> {
    public OrderSerializer() {
        this(null);
    }

    public OrderSerializer(Class<Order> t) {
        super(t);
    }

    @Override
    public void serialize(
            Order order, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonProcessingException {

        String fieldName = order.isReturn() ? "returned_ticket" : "token";

        jgen.writeStartObject();
        jgen.writeStringField(fieldName, order.getToken());
        jgen.writeObjectField("ticket", order.getTicket());
        jgen.writeEndObject();
    }

}
