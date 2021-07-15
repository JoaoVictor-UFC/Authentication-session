package com.authetication.session.Authetication.Session.v1.repository;

import com.authetication.session.Authetication.Session.v1.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<RoleEntity, Long>, JpaSpecificationExecutor<RoleEntity> {
}
