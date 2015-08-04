package com.deigote.gmailSender;

import com.google.gson.Gson;
import groovy.json.JsonOutput;
import ratpack.handling.Context;
import ratpack.http.MediaType;
import ratpack.render.RendererSupport;

import java.util.Collections;
import java.util.HashMap;

public class ThrowableRenderer extends RendererSupport<Throwable> {
   @Override
   public void render(Context context, Throwable throwable) throws Exception {
      context.getResponse()
         .contentType(MediaType.APPLICATION_JSON)
         .send(JsonOutput.toJson(Collections.unmodifiableMap(
               new HashMap<String, String>() {{
                  put("status", "error");
                  put("type", throwable.getClass().getSimpleName());
                  put("message", throwable.getMessage());
               }}
         )));
   }
}
