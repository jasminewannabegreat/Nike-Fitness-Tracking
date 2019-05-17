package demo;

import org.springframework.data.geo.Point;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.RequestMapping;

@RepositoryRestResource(path = "supplylocations")
public interface SupplyLocationRepository extends PagingAndSortingRepository<SupplyLocation, String> {

    SupplyLocation findFirstByLocationNear(@Param("location") Point location);

//note: 之所以不需要我么你手动注入bean, 是因为
//我们extend的父类里面, 已经注入了bean containery
}
