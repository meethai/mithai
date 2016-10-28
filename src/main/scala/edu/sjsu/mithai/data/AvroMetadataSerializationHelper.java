package edu.sjsu.mithai.data;

import com.google.gson.Gson;

import java.util.Base64;

public class AvroMetadataSerializationHelper implements SerializationHelper<AvroGraphMetadata> {

    private Gson gson;

    public AvroMetadataSerializationHelper() {
        this.gson = new Gson();
    }

    @Override
    public String serialize(AvroGraphMetadata data) throws Exception {
        String sdata = gson.toJson(data);
        return Base64.getEncoder().encodeToString(sdata.getBytes());
    }

    @Override
    public AvroGraphMetadata deserialize(String stream) throws Exception {
        String message = new String(Base64.getDecoder().decode(stream.getBytes()));
        AvroGraphMetadata metadata =  gson.fromJson(message, AvroGraphMetadata.class);
        return metadata;
    }
}
