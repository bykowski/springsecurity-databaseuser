package pl.bykowski.springsecuritydatabaseuser;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo extends CrudRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);
}
