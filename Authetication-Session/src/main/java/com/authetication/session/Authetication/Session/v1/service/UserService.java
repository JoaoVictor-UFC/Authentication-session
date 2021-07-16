package com.authetication.session.Authetication.Session.v1.service;

import com.authetication.session.Authetication.Session.errorExceptions.ResourceBadRequestException;
import com.authetication.session.Authetication.Session.v1.dto.CreateUserRequest;
import com.authetication.session.Authetication.Session.v1.dto.UpdatePasswordResponse;
import com.authetication.session.Authetication.Session.v1.dto.UpdatePasswordResquest;
import com.authetication.session.Authetication.Session.v1.dto.UserResponse;
import com.authetication.session.Authetication.Session.v1.entity.UserEntity;
import com.authetication.session.Authetication.Session.v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {

	@Autowired private UserRepository userRepository;

	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserEntity saveUser(UserEntity user) {
		user.setCreatedAt(LocalDateTime.now());
		return userRepository.save(user);
	}

	public boolean checkEmail(@Valid @NotNull String email){
		Optional<UserEntity> user = userRepository.findByEmail(email);
		if (user.isPresent()){
			return true;
		}
		return false;
	}

	public boolean checkLogin(@Valid @NotNull String login){
		Optional<UserEntity> user = userRepository.findByLogin(login);
		if (user.isPresent()){
			return true;
		}
		return false;
	}

	@Transactional
	public UserResponse createUser(@Valid @NotNull CreateUserRequest req){

		if (checkEmail(req.getEmail())){
			throw new ResourceBadRequestException("Email existing");
		}
		if (checkLogin(req.getLogin())){
			throw new ResourceBadRequestException("Login existing");
		}

		UserEntity res = new UserEntity();
		res.setLogin(req.getLogin());
		res.setEmail(req.getEmail());
		res.setPassword(bCryptPasswordEncoder.encode(req.getPassword()));
		res.setName(req.getName());
		res.setAdmin(req.isAdmin());

		return fromUserEntityToUserResponse(saveUser(res));
	}

	public Page<UserEntity> listUserPagination(Pageable page) {
		return userRepository.findAll(page);
	}

	public UserResponse fromUserEntityToUserResponse(@Valid @NotBlank UserEntity user) {
		userRepository.findById(user.getId());
		UserResponse res = new UserResponse();

		res.setId(user.getId());
		res.setAdmin(user.isAdmin());
		res.setEmail(user.getEmail());
		res.setLogin(user.getLogin());
		res.setName(user.getName());

		return res;
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Optional<UserEntity> user = userRepository.findByLogin(login);
		if (!user.isPresent())
			throw new UsernameNotFoundException("Usuário não encontrado, por favor cheque suas credenciais.");
		return new User(user.get().getLogin(), user.get().getPassword(),
				true, true, true, true, new ArrayList<>());
	}

	public UpdatePasswordResponse recoverPassword(@Valid UpdatePasswordResquest req, Long idUser){
		Optional<UserEntity> user = userRepository.findById(idUser);
		UpdatePasswordResponse res = new UpdatePasswordResponse();
		try {
			if (user.get().isAdmin()){
				Optional<UserEntity> u = userRepository.findByEmail(req.getEmail());
				u.get().setPassword(bCryptPasswordEncoder.encode(req.getPassword()));
				saveUser(u.get());
				res.setMessage("Senha Alterada com sucesso");
				return res;
			}else {res.setMessage("Usuario sem Privilegios ");}
		}catch (Exception e){
			throw new ResourceBadRequestException("Aconteceu um error durante o processo");
		}
		return res;
	}

	public @Valid @NotBlank UserEntity findUserByLogin(String login) {
		return userRepository.findByLogin(login).get();
	}
}
