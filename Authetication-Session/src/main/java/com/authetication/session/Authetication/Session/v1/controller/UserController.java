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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Api(value = "User Service", tags = { "User Service" })
@RestController
@Valid
@RequestMapping("/user")
public class UserController {

    @Autowired private UserService userService;

    @ApiOperation(value = "Criar Usuario",notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = UserResponse.class),
            @ApiResponse(code = 401, message = "UNAUTHORIZATED", response = MessageErrorCustom.class),
            @ApiResponse(code = 403, message = "FORBIDDEN", response = MessageErrorCustom.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = MessageErrorCustom.class)
    })
    @PostMapping(consumes = { "application/json", "application/xml" })
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid CreateUserRequest req){
        UserResponse user = userService.createUser(req);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/id").buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Atualizar Senhas",notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = UserResponse.class),
            @ApiResponse(code = 401, message = "UNAUTHORIZATED", response = MessageErrorCustom.class),
            @ApiResponse(code = 403, message = "FORBIDDEN", response = MessageErrorCustom.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = MessageErrorCustom.class)
    })
    @PutMapping(consumes = { "application/json", "application/xml" },value ="/update-passoword/id={id}")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordResquest req, @PathVariable("id") Long id){
        return ResponseEntity.ok().body(userService.recoverPassword(req,id));
    }

    @ApiOperation(value = "Lista usuarios",notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = UserEntity.class),
            @ApiResponse(code = 401, message = "UNAUTHORIZATED", response = MessageErrorCustom.class),
            @ApiResponse(code = 403, message = "FORBIDDEN", response = MessageErrorCustom.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = MessageErrorCustom.class)
    })
    @GetMapping(produces = { "application/json", "application/xml" }, value = "/pagination")
    private ResponseEntity<Page<UserEntity>> listUserPagination(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                @RequestParam(value = "limit", required = false, defaultValue = "10") int size) {
        return ResponseEntity.ok().body(userService.listUserPagination(PageRequest.of(page, size)));
    }
}
