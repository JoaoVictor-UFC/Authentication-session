package com.authetication.session.Authetication.Session.v1.repository;

import java.util.List;
import java.util.Optional;

import com.authetication.session.Authetication.Session.v1.dto.UserResponse;
import com.authetication.session.Authetication.Session.v1.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

	Optional<UserEntity> findByLogin(String login);

	Optional<UserEntity> findByEmail(String email);
}
