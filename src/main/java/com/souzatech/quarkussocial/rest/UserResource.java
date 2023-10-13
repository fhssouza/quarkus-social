package com.souzatech.quarkussocial.rest;

import com.souzatech.quarkussocial.rest.dto.CreateUserRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    @POST
    public Response createUser(CreateUserRequest userRequest){
        return Response.ok().build();
    }
    @GET
    public Response listAllUsers(){
        return Response.ok().build();
    }

}
