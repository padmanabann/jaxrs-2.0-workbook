package com.restfully.shop.services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriInfo;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@Path("/cars")
public class CarResource
{
   public static enum Color
   {
      red,
      white,
      blue,
      black
   }
   
   //http://127.0.0.1:8025/jaxrs-2.0-workbook-ex05_1/services/cars/matrix/mercedes/e55;color=black/2006
   //A black 2006 mercedes e55
   @GET
   @Path("/matrix/{make}/{model}/{year}")
   @Produces("text/plain")
   public String getFromMatrixParam(@PathParam("make") String make,
                                    @PathParam("model") PathSegment car,
                                    @MatrixParam("color") Color color,
                                    @PathParam("year") String year)
   {
      return "A " + color + " " + year + " " + make + " " + car.getPath();
   }

   //http://127.0.0.1:8025/jaxrs-2.0-workbook-ex05_1/services/cars/segment/mercedes/e55;color=black/2006
   //A black 2006 mercedes e55
   @GET
   @Path("/segment/{make}/{model}/{year}")
   @Produces("text/plain")
   public String getFromPathSegment(@PathParam("make") String make,
                                    @PathParam("model") PathSegment car,
                                    @PathParam("year") String year)
   {
      String carColor = car.getMatrixParameters().getFirst("color");
      return "A " + carColor + " " + year + " " + make + " " + car.getPath();
   }
   
   //http://127.0.0.1:8025/jaxrs-2.0-workbook-ex05_1/services/cars/segments/mercedes/e55/amg/year/2006
   //A 2006 mercedes e55 amg
   @GET
   @Path("/segments/{make}/{model : .+}/year/{year}")
   @Produces("text/plain")
   public String getFromMultipleSegments(@PathParam("make") String make,
                                         @PathParam("model") List<PathSegment> car,
                                         @PathParam("year") String year)
   {
      String output = "A " + year + " " + make;
      for (PathSegment segment : car)
      {
         output += " " + segment.getPath();
      }
      return output;
   }
   //http://127.0.0.1:8025/jaxrs-2.0-workbook-ex05_1/services/cars/uriinfo/mercedes/e55;color=black/2006
   //A black 2006 mercedes e55
   @GET
   @Path("/uriinfo/{make}/{model}/{year}")
   @Produces("text/plain")
   public String getFromUriInfo(@Context UriInfo info)
   {
      String make = info.getPathParameters().getFirst("make");
      String year = info.getPathParameters().getFirst("year");
      PathSegment model = info.getPathSegments().get(3);
      String color = model.getMatrixParameters().getFirst("color");

      return "A " + color + " " + year + " " + make + " " + model.getPath();
   }
}
