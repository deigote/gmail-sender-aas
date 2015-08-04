package com.deigote.gmailSender;

import groovy.json.JsonOutput;
import groovy.lang.Closure;
import ratpack.error.ServerErrorHandler;
import ratpack.exec.Blocking;
import ratpack.exec.Promise;
import ratpack.func.Factory;
import ratpack.func.Function;
import ratpack.http.MediaType;
import ratpack.http.Status;
import ratpack.server.RatpackServer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class GmailSenderMicroService {

   private static final Status BAD_REQUEST = Status.of(400);

   private static Email sendEmail(EmailInput input) {
      return GmailSender.getInstance().send(input.email, input.credentials);
   }

   public static void main(String[] args) throws Exception {
      RatpackServer.start(serverSpec ->
         serverSpec.handlers(chain ->
            chain.register(new EmailRenderer().register())
               .register(new ThrowableRenderer().register())
               .register(new ExceptionToResponseErrorHandler().register())
               .post("email", ctx ->
                  ctx.getRequest().getBody()
                     .map(body -> body.getText())
                     .map(EmailInput::buildFromJson)
                     .wiretap(promise -> System.out.println(promise.toString()))
                     .flatMap(input -> Blocking.get(() -> sendEmail(input)))
                     .then(email -> ctx.render(email))
            )
         )
      );
   }
}
