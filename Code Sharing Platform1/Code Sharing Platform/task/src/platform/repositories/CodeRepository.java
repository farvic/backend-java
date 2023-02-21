package platform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import platform.entities.Code;

import java.util.List;
import java.util.Optional;


@Repository
public interface CodeRepository extends JpaRepository<Code, String> {

    // @Query("SELECT c FROM Code c WHERE c.id = ?1")
    @Override
    Optional<Code> findById(String id);

    List<Code> findFirst10ByTimeRestrictedIsFalseAndViewRestrictedIsFalseOrderByDateDesc();

}



