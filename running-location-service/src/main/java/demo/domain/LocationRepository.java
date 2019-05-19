package demo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestResource(path = "locations")//repository works with the database
public interface LocationRepository extends JpaRepository<Location, Long> {
    //extends keyword first param - Location is the entity/object name we need to covert, and second params is the data type of id of the object

    //in relational database, we store runnerMovementType into the column movementType

    //Page is the how much information that are sent in a page, Param is the corresponding name stored in the database

    //select * from Locations where RunnerMovementType = movementType

    //after adding rest resourecs and setting rest path for this repository, by adding locations/search/runners?movementType=STOPPED&page=0&size=5
    //this localrepository can expose some functions to users without need to interactive with backend and come back to respond to front end
    @RestResource(path = "runners")
    Page<Location> findByRunnerMovementType(@Param("movementType") Location.RunnerMovementType runnerMovementType, Pageable pageable);
    //param means the the column in the database is called movementType
    Page<Location> findByUnitInfoRunningId(@Param("runningId") String runningId, Pageable pageable);
}
