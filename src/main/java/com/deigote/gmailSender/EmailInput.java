package com.deigote.gmailSender;

import com.google.gson.Gson;

public class EmailInput {

   private static final Gson GSON = new Gson();

   final Email email;
   final Credentials credentials;

   private EmailInput(Email email, Credentials credentials) {
      this.email = email;
      this.credentials = credentials;
   }

   static EmailInput buildFromJson(String json) {
      return new EmailInput(
         GSON.fromJson(json, Email.class).validateOrFail(),
         GSON.fromJson(json, Credentials.class).validateOrFail()
      );
   }

   public String toString() {
      return "EmailInput(email: "
         .concat(credentials != null ? "Credentials(******)" : "null")
         .concat(", ")
         .concat(email.toString())
         .concat(")");
   }

}
