package demo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;


//repository works with the database
//first param - Location is the entity/object name we need to covert, and second params is the data type of id of the object
public interface LocationRepository extends JpaRepository<Location, Long> {

    //in relational database, we store runnerMovementType into the column movementType

    //Page is the how much information that are sent in a page, Param is the corresponding name stored in the database

    //select * from Locations where RunnerMovementType = movementType
    Page<Location> findByRunnerMovementType(@Param("movementType") Location.RunnerMovementType runnerMovementType, Pageable pageable);

    Page<Location> findByUnitInfoRunningId(@Param("runningId") String runningId, Pageable pageable);
}
