package com.opticalix.opticalixtemplate.net.test;

import android.text.TextUtils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.opticalix.opticalixtemplate.net.test.HttpBinModel;

import java.io.IOException;

/**
 * Created by opticalix@gmail.com on 15/12/28.
 */
public class HttpBinTypeAdapter extends TypeAdapter<HttpBinModel> {
    @Override
    public void write(JsonWriter out, HttpBinModel value) throws IOException {
        //value write to json
        if (value == null) {
            out.nullValue();
        } else {
            out.beginObject();

            out.name("args").value(value.args);
            out.name("headers").value(value.headers);
            out.name("origin").value(value.origin);
            out.name("url").value(value.url);

            out.endObject();
        }
    }

    @Override
    public HttpBinModel read(JsonReader in) throws IOException {
        //read json, return model
        if (in.peek() == JsonToken.NULL) {
            return null;
        } else {
            in.beginObject();
            HttpBinModel model = new HttpBinModel();

            if (in.nextName().equals("args")) {
                in.beginObject();
                String args = "";
                while (in.hasNext()) {
                    if (!TextUtils.isEmpty(args)) args += ", ";
                    args += in.nextName() + "=" + in.nextString();
                }

                model.args = args;
                in.endObject();
            }

            if (in.nextName().equals("headers")) {
                in.beginObject();
                String headers = "";
                while (in.hasNext()) {
                    if (!TextUtils.isEmpty(headers)) headers += ", ";
                    headers += in.nextName() + "=" + in.nextString();
                }

                model.headers = headers;
                in.endObject();
            }

            if (in.nextName().equals("origin")) {
                model.origin = in.nextString();
            }
            if (in.nextName().equals("url")) {
                model.url = in.nextString();
            }

            in.endObject();
            return model;
        }
    }

    private HttpBinModel handleObject(HttpBinModel model, JsonReader in, String name) throws IOException {
        if (in.nextName().equals(name)) {
            in.beginObject();
            String args = "";
            while (in.hasNext()) {
                if (!TextUtils.isEmpty(args)) args += ", ";
                args += in.nextName() + "=" + in.nextString();
            }

            in.endObject();
        }
        return model;
    }
}
