package com.authetication.session.Authetication.Session.v1.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.authetication.session.Authetication.Session.errorExceptions.ResourceBadRequestException;
import com.authetication.session.Authetication.Session.v1.dto.CreateUserRequest;
import com.authetication.session.Authetication.Session.v1.dto.UserResponse;
import com.authetication.session.Authetication.Session.v1.entity.UserEntity;
import com.authetication.session.Authetication.Session.v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
@Transactional
public class UserService{

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

	public Page<UserResponse> listUserPagination(Pageable page, String end,String start, String name) {
		if (!end.isEmpty() && start.isEmpty()) {
			throw new ResourceBadRequestException("Range de períodos inválido");
		}
		List<UserResponse> userModels = new ArrayList<>();
		Page<UserResponse> userModelPage = null;

		if (end.isEmpty() && start.isEmpty()) {
			userModels = userRepository.findAllUsers();
		}

		if (end.isEmpty() && !start.isEmpty()) {
			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String end_new = LocalDateTime.now().format(formatterDate);
			userModels = userRepository.findUsersByRange(start, end_new);
		}

		if (!end.isEmpty() && !start.isEmpty()) {
			userModels = userRepository.findUsersByRange(start, end);
		}

		Long start2 = page.getOffset();
		Long end2 = (start2 + page.getPageSize()) > userModels.size() ? Integer.toUnsignedLong(userModels.size())
				: (start2 + page.getPageSize());
		userModelPage = new PageImpl<>(userModels.subList(start2.intValue(), end2.intValue()), page, userModels.size());

		return userModelPage;
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
