package org.example.wicketwise.repository;

import org.example.wicketwise.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

}
