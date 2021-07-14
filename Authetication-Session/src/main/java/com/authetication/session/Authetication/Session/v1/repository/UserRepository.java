package com.authetication.session.Authetication.Session.v1.repository;

import java.util.Optional;

import com.authetication.session.Authetication.Session.v1.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

	@Query("SELECT u FROM UserEntity u WHERE u.login = ?1")
	Optional<UserEntity> findByUsername(String login);

	@Query("SELECT u FROM UserEntity u WHERE u.email = ?1")
	Optional<UserEntity> findByEmail(String email);

}
