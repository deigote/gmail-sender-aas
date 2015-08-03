package com.deigote.gmailSender;

import ratpack.error.ServerErrorHandler;
import ratpack.exec.Blocking;
import ratpack.http.MediaType;
import ratpack.http.Status;
import ratpack.server.RatpackServer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class GmailSenderMicroService {

   private static final Status BAD_REQUEST = Status.of(400);

   public static void main(String[] args) throws Exception {
      RatpackServer.start(serverSpec ->
            serverSpec.handlers(chain ->
                  chain.register(registrySpec ->
                     registrySpec.add(ServerErrorHandler.class, (ctx, throwable) -> {
                           ctx.getResponse()
                              .contentType(MediaType.APPLICATION_JSON)
                              .status(BAD_REQUEST)
                              .send(new Gson().toJson(Collections.unmodifiableMap(new HashMap<String, String>() {{
                                 put("status", "error");
                                 put("message", throwable.getMessage());
                              }})));
                        }
                     )
                  ).post(ctx ->
                     ctx.getRequest().getBody().then(body -> {
                        Map<String, String> input = new Gson().fromJson(body.getText(), Map.class);
                        System.out.println(input);
                        Blocking.get(() -> {
                           GmailSender.getInstance().generateAndSendEmail(
                              input.get("username"), input.get("password"), input.get("to"),
                              input.get("subject"), input.get("body")
                           );
                           return input;
                        }).then(r ->
                           ctx.getResponse()
                              .contentType(MediaType.APPLICATION_JSON)
                              .send(new Gson().toJson(Collections.singletonMap("status", "success")))
                        );
                     })
                  )
            )
      );
   }
}
