package com.pet.bot.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by sathya.ganesan on 19/08/17.
 */
@Path("/v1/spider/sample")
public class SpiderSampleResource {

   @GET
   public String get() {

      return "hello";
   }
}
