package com.deigote.gmailSender;

import ratpack.error.ServerErrorHandler;
import ratpack.func.Action;
import ratpack.handling.Context;
import ratpack.registry.RegistrySpec;

public class ExceptionToResponseErrorHandler implements ServerErrorHandler {

   @Override
   public void error(Context context, Throwable throwable) throws Exception {
      throwable.printStackTrace();
      context.render(throwable);
   }

}
