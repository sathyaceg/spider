package com.pet.bot;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pet.bot.configuration.SpiderConfiguration;
import com.pet.bot.resources.SpiderSampleResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * Created by sathya.ganesan on 19/08/17.
 */
public class SpiderApplication extends Application<SpiderConfiguration> {

   private static final Logger logger = LoggerFactory.getLogger(SpiderApplication.class);

   public static void main(String[] args) {
      logger.info("Starting up Spider application. We will be ready to accept new requests in a while.");
      try {
         if (args.length == 0) {
            args = new String[] {"server", "src/main/resources/spider-dev.yml"};
         }
         new SpiderApplication().run(args);
      } catch (Exception e) {
         logger.error("Error in starting the server.", e);
         throw new RuntimeException(e);
      }
   }

   public void run(SpiderConfiguration spiderConfiguration, Environment environment) throws Exception {

      Injector injector = createInjector(spiderConfiguration);

      FilterRegistration.Dynamic filter =
            environment.servlets().addFilter("CORS", CrossOriginFilter.class);
      filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
      filter.setInitParameter("allowedOrigins", "*");
      filter.setInitParameter("allowedHeaders",
            "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,X-MZ-Token");
      filter.setInitParameter("allowedMethods", "GET,PUT,POST,DELETE,OPTIONS,HEAD");
      filter.setInitParameter("preflightMaxAge", "5184000");
      filter.setInitParameter("allowCredentials", "true");

      environment.jersey().register(injector.getInstance(SpiderSampleResource.class));
   }

   @Override
   public void initialize(Bootstrap<SpiderConfiguration> bootstrap) {

      /*bootstrap.addBundle(new MigrationsBundle<SpiderConfiguration>() {
         public DataSourceFactory getDataSourceFactory(SpiderConfiguration configuration) {
            return configuration.databaseConfiguration;
         }
      });
      //bootstrap.addBundle(hibernateBundle);
      bootstrap.addBundle(new SwaggerBundle<SpiderConfiguration>() {
         @Override
         public SwaggerBundleConfiguration getSwaggerBundleConfiguration(
               SpiderConfiguration configuration) {
            return configuration.swaggerBundleConfiguration;
         }
      });*/
   }

   private Injector createInjector(final SpiderConfiguration conf) {

      return Guice.createInjector(new AbstractModule() {

         @Override
         protected void configure() {
         }

      });
   }
}
