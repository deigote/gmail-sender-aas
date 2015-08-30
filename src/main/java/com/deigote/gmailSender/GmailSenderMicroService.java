package com.deigote.gmailSender;

import ratpack.exec.Blocking;
import ratpack.server.RatpackServer;

public class GmailSenderMicroService {

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
                     .wiretap(result -> System.out.println(result.getValue().toString()))
                     .flatMap(input -> Blocking.get(() -> sendEmail(input)))
                     .then(email -> ctx.render(email))
               )
         )
      );
   }
}
