package com.authetication.session.Authetication.Session.v1.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import com.authetication.session.Authetication.Session.v1.dto.CreateUserRequest;
import com.authetication.session.Authetication.Session.v1.dto.UserResponse;
import com.authetication.session.Authetication.Session.v1.entity.UserEntity;
import com.authetication.session.Authetication.Session.v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
@Transactional
public class UserService implements IUserService {
	
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
		Optional<UserEntity> user = userRepository.findByUsername(login);
		if (user.isPresent()){
			return true;
		}
		return false;
	}

	public UserResponse createUser(@Valid @NotNull CreateUserRequest req){

		if (checkEmail(req.getEmail())){
			throw new RuntimeException("Email existing");
		}
		if (checkLogin(req.getLogin())){
			throw new RuntimeException("Login existing");
		}

		UserEntity res = new UserEntity();
		res.setLogin(req.getLogin());
		res.setEmail(req.getEmail());
		res.setPassword(bCryptPasswordEncoder.encode(req.getPassword()));
		res.setName(req.getName());
		res.setAdmin(req.isAdmin());

		return fromUserEntityToUserResponse(saveUser(res));
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> user = userRepository.findByUsername(username);
		if (!user.isPresent())
			throw new UsernameNotFoundException("Usuário não encontrado, por favor cheque suas credenciais.");
		return new User(user.get().getLogin(), user.get().getPassword(), true, true, true, true, new ArrayList<>());
	}
	
	public UserEntity findUserByUserName(String userName) throws Exception {
		Optional<UserEntity> user = userRepository.findByUsername(userName);
		if (user.isPresent()) {
			return user.get();
		}
		throw new Exception("Usuario não encontrado ou não ativo");
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
}
