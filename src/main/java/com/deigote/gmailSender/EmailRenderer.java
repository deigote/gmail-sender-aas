package com.deigote.gmailSender;

import com.google.gson.Gson;
import ratpack.handling.Context;
import ratpack.http.MediaType;
import ratpack.render.RendererSupport;

import java.util.Collections;

public class EmailRenderer extends RendererSupport<Email> {

   @Override
   public void render(Context context, Email email) throws Exception {
      context.getResponse()
         .contentType(MediaType.APPLICATION_JSON)
         .send(new Gson().toJson(Collections.singletonMap("status", "success")));
   }
}
