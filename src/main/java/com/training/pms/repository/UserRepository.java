package com.training.pms.repository;

import com.training.pms.model.domain.User;
import org.springframework.data.repository.Repository;

@org.springframework.stereotype.Repository
public interface UserRepository extends Repository<User, Long> {
}