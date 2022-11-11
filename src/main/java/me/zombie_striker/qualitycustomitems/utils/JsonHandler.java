package me.zombie_striker.qualitycustomitems.utils;

import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonHandler {

    private JsonWriter writer;

    public JsonHandler(File file) {
        try {
            if(file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if(!file.exists())
                file.createNewFile();
            writer = new JsonWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeJsonStream(HashMap<String, Object> messages) throws IOException {
        writer.setIndent("    ");
        writeMessagesArray(messages);
        writer.close();
    }

    public void writeMessagesArray(HashMap<String, Object> messages) throws IOException {
        writer.beginObject();
        for (Map.Entry<String, Object> message : messages.entrySet()) {
            writeObjects(message);
        }
        writer.endObject();
    }

    private void writeObjects(Map.Entry<String, Object> message) throws IOException {
        if (message.getValue() instanceof String) {
            writer.name(message.getKey()).value((String) message.getValue());
        } else if (message.getValue() instanceof Integer) {
            writer.name(message.getKey()).value((Integer) message.getValue());
        } else if (message.getValue() instanceof Double) {
            writer.name(message.getKey()).value((Double) message.getValue());
        } else if (message.getValue() instanceof Boolean) {
            writer.name(message.getKey()).value((Boolean) message.getValue());
        } else if (message.getValue() instanceof List) {
            writer.name(message.getKey());
            writer.beginArray();
            for (Object val : (List<Object>) message.getValue()) {
                if (val instanceof HashMap) {
                    writeMessagesArray((HashMap<String, Object>) val);
                } else if (val instanceof Boolean) {
                    writer.value((Boolean) val);
                } else if (val instanceof Double) {
                    writer.value((Double) val);
                } else if (val instanceof Integer) {
                    writer.value((Integer) val);
                } else {
                    writer.value((String) val);
                }
            }
            writer.endArray();
        } else if (message.getValue() instanceof HashMap) {
            writer.name(message.getKey());
            writeMessagesArray((HashMap<String, Object>) message.getValue());

        }
    }

    public void writeDoublesArray(JsonWriter writer, List<Double> doubles) throws IOException {
        writer.beginArray();
        for (Double value : doubles) {
            writer.value(value);
        }
        writer.endArray();
    }
}
