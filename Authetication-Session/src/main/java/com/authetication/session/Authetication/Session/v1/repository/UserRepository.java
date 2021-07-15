package com.authetication.session.Authetication.Session.v1.repository;

import java.util.List;
import java.util.Optional;

import com.authetication.session.Authetication.Session.v1.dto.UserResponse;
import com.authetication.session.Authetication.Session.v1.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

	@Query("SELECT u FROM UserEntity u WHERE u.login = ?1")
	Optional<UserEntity> findByLogin(String login);

	@Query("SELECT u FROM UserEntity u WHERE u.email = ?1")
	Optional<UserEntity> findByEmail(String email);

    @Query(value = "SELECT * FROM public.authent_user", nativeQuery = true)
	List<UserResponse> findAllUsers();

    @Query(value = "SELECT * FROM public.authent_user u where cast(created_at as DATE) between CAST( ?1 AS DATE) and CAST( ?2 AS DATE)\n" +
			"order by created_at desc", nativeQuery = true)
    List<UserResponse> findUsersByRange(String start, String end);
}
