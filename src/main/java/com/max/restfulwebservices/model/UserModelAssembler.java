package com.max.restfulwebservices.model;

import com.max.restfulwebservices.controller.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, EntityModel<User>> {

    public UserModelAssembler() {
        super(UserController.class, (Class<EntityModel<User>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<User> toModel(User user) {
        EntityModel<User> model = EntityModel.of(user);
        model.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"));
        return model;
    }
}
