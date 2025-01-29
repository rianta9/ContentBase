package com.ryanlab.contentbase.repository.jpa;

import org.springframework.stereotype.Repository;

import com.ryanlab.contentbase.core.repository.ICoreJpaRepository;
import com.ryanlab.contentbase.model.entity.ResponseMessage;

@Repository
public interface ResponseMessageJpaRepository
  extends ICoreJpaRepository<ResponseMessage, String> {

}
