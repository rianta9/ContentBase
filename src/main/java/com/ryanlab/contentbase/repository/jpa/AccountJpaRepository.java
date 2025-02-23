package com.ryanlab.contentbase.repository.jpa;

import org.springframework.stereotype.Repository;

import com.ryanlab.contentbase.core.repository.ICoreJpaRepository;
import com.ryanlab.contentbase.model.entity.Account;

@Repository
public interface AccountJpaRepository
  extends ICoreJpaRepository<Account, String> {

}
