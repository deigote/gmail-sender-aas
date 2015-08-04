package com.deigote.gmailSender;

import com.google.gson.Gson;

public class EmailInput {

   final Email email;
   final Credentials credentials;

   private EmailInput(Email email, Credentials credentials) {
      this.email = email;
      this.credentials = credentials;
   }

   static EmailInput buildFromJson(String json) {
      return new EmailInput(
         new Gson().fromJson(json, Email.class),
         new Gson().fromJson(json, Credentials.class)
      );
   }

   public String toString() {
      return "EmailInput(email: "
         .concat(credentials.toString())
         .concat(", ")
         .concat(email.toString())
         .concat(")");
   }
}
