package com.liyuchang.dubbo.rest.service.impl;

import com.liyuchang.dubbo.rest.entity.User;
import com.liyuchang.dubbo.rest.service.UserService;
import org.apache.dubbo.config.annotation.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("users")
@Service(version = "1.0")
public class UserServiceImpl implements UserService {

    @POST
    @Path("register")
    @Consumes({MediaType.APPLICATION_JSON})
    @Override
    public void registerUser(User user) {
        System.out.println(user);
    }

    @GET
    @Path("{id : \\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    @Override
    public User get(@PathParam("id") Long id) {
        User user = new User();
        user.setId(id);
        user.setName("李玉长");
        return user;
    }
}
