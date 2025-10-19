package org.example.wicketwise.repository;

import org.example.wicketwise.entity.User;
import org.example.wicketwise.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    
    // Basic CRUD operations are provided by JpaRepository
    // Override findById to return Optional<User> for better null safety
    @Override
    Optional<User> findById(Long id);
    
    // Find user by username (case-sensitive)
    Optional<User> findByUsername(String username);
    
    // Find user by email (case-insensitive)
    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    Optional<User> findByEmail(@Param("email") String email);
    
    // Find users by role
    List<User> findByRole(Role role);
    
    // Find users by active status
    List<User> findByActive(boolean active);
    

    // Find users by email containing (case-insensitive)
    @Query("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    List<User> findByEmailContainingIgnoreCase(@Param("email") String email);
    
    // Find users by username containing (case-insensitive)
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%'))")
    List<User> findByUsernameContainingIgnoreCase(@Param("username") String username);
    
    // Check if a user with the given username exists (case-insensitive)
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
           "FROM User u WHERE LOWER(u.username) = LOWER(:username)")
    boolean existsByUsernameIgnoreCase(@Param("username") String username);
    
    // Check if a user with the given email exists (case-insensitive)
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
           "FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    boolean existsByEmailIgnoreCase(@Param("email") String email);
    
    // Find user by reset token
    Optional<User> findByResetToken(String resetToken);
    
    // Find users by account locked status
    List<User> findByAccountNonLocked(boolean accountNonLocked);
    
    // Find users by account enabled status
    List<User> findByEnabled(boolean enabled);
    
    // Count users by role
    long countByRole(Role role);
    
    // Count active/inactive users
    long countByActive(boolean active);
    
    // Search users by multiple fields (username, email, first name, last name)
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<User> searchUsers(@Param("query") String query);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
