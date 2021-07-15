package com.authetication.session.Authetication.Session.v1.controller;

import com.authetication.session.Authetication.Session.errorExceptions.MessageErrorCustom;
import com.authetication.session.Authetication.Session.v1.dto.CreateUserRequest;
import com.authetication.session.Authetication.Session.v1.dto.UpdatePasswordResquest;
import com.authetication.session.Authetication.Session.v1.dto.UserResponse;
import com.authetication.session.Authetication.Session.v1.entity.UserEntity;
import com.authetication.session.Authetication.Session.v1.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired private UserService userService;

    @ApiOperation(value = "Adiciona um novo User ao banco",notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = UserResponse.class),
            @ApiResponse(code = 401, message = "UNAUTHORIZATED", response = MessageErrorCustom.class),
            @ApiResponse(code = 403, message = "FORBIDDEN", response = MessageErrorCustom.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = MessageErrorCustom.class)
    })
    @PostMapping(consumes = { "application/json", "application/xml" })
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest req){
        UserResponse user = userService.createUser(req);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/id").buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Um user ADMIN pode trocar a senha de usuarios do bando",notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = UserResponse.class),
            @ApiResponse(code = 401, message = "UNAUTHORIZATED", response = MessageErrorCustom.class),
            @ApiResponse(code = 403, message = "FORBIDDEN", response = MessageErrorCustom.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = MessageErrorCustom.class)
    })
    @PutMapping(consumes = { "application/json", "application/xml" },value ="/update-passoword")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordResquest req){
        return null;
    }

    @ApiOperation(value = "Lista usuarios do banco User, paginado",notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = UserEntity.class),
            @ApiResponse(code = 401, message = "UNAUTHORIZATED", response = MessageErrorCustom.class),
            @ApiResponse(code = 403, message = "FORBIDDEN", response = MessageErrorCustom.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = MessageErrorCustom.class)
    })
    @GetMapping(produces = { "application/json", "application/xml" }, value = "/pagination")
    private ResponseEntity<Page<UserEntity>> listUserPagination(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(userService.listUserPagination(PageRequest.of(page, size)));
    }
}
