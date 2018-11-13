package com.michal.garageshare.common.generic.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.michal.garageshare.common.generic.domain.EntityInterface;

@NoRepositoryBean
public interface AbstractRepository<ENTITY extends EntityInterface, ID extends Serializable> extends JpaRepository<ENTITY, ID> {

//    List<ENTITY> save(List<ENTITY> entities);

}
