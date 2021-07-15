package com.authetication.session.Authetication.Session.v1.controller;

import com.authetication.session.Authetication.Session.v1.dto.CreateUserRequest;
import com.authetication.session.Authetication.Session.v1.dto.UpdatePasswordResquest;
import com.authetication.session.Authetication.Session.v1.dto.UserResponse;
import com.authetication.session.Authetication.Session.v1.entity.UserEntity;
import com.authetication.session.Authetication.Session.v1.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Api(value = "User Service", tags = { "User Service" })
@Valid
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired private UserService userService;

    @PostMapping(consumes = { "application/json", "application/xml" })
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest req){
        UserResponse user = userService.createUser(req);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/id").buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(consumes = { "application/json", "application/xml" },value ="/update-passoword")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordResquest req){
        return null;
    }

    @GetMapping(produces = { "application/json", "application/xml" })
    private ResponseEntity<Page<UserEntity>> listUserPagination(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(userService.listUserPagination(PageRequest.of(page, size)));
    }
}
