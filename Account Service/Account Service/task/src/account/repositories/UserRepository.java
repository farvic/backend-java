package account.repositories;

import account.domain.User;

import account.dto.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Tag(name = "User", description = "User API")
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     *
     * Find all Users from the database
     *
     */
    List<User> findAll();

    /**
     * Find a user by id
     *
     * @param id the id of the user
     * @return the user
     */


    Optional<UserDto> findById(@Param("id") long id);


    /**
     * Save a user
     *
     * @param User the user to save
     * @return the saved user
     */

    @Override
//     @RestResource(exported = false)
    <S extends User> S save(S User);

    @Override
    @RestResource(exported = false)
    <S extends User> List<S> saveAll(Iterable<S> users);

    // @RestResource(exported = false)
    void deleteByEmail(String email);

    @Override
//    @RestResource(exported = false)
    void delete(User user);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends User> users);

//    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);
}
