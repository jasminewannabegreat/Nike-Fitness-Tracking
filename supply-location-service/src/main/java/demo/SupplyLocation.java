package demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;


//JSON <--> Class <--> DB
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Document //to tell mongodb this model will be mapped to document, //上次JPA与H2通信是用的Entity, 这里用document
@Data


//下面这个的作用:
//技能: 只有当class里面有field被标记为"final"时生效:
//效果: 被动: 当constructor被调用时, 构造函数上会被加上@XXX的标记, 此处是@PersistenceConstructor
//附带: @PersistenceConstructor作用: 当声明构造函数时, 会从数据库中将数据取出来实例化成对象, 并将此数据传入构造函数
//之所以t用这个, 是因为Point是一个对象, 而我们要往数据库里存的时候, 要调用Point的构造函数
//而如果点进Point, 会发现它里面的构造函数上室友这个@PersistenceConstructor的,所以这里每次调用构造函数也需要打上这个annotation
@RequiredArgsConstructor(onConstructor = @__(@PersistenceConstructor))
public class SupplyLocation {
    @Id
    private String id;  //对应数据库中unique的id

    private String address1;
    private String address2;
    private String city;

    @GeoSpatialIndexed              //mongodb自带一个个geoLocaiton, 可以通过point类型直接检索经纬度, 加这个annotaion就行, speed up the searching
    @JsonIgnore                     //因为json传进来的是两个点, 我们不希望json去 direct mapping, 固定用法
    private final Point location;   //在json中, 经纬度是两个单独的field, 但是mongo中可以用point来查询


    private String state;
    private String zip;
    private String type;

    public SupplyLocation(){
        this.location = new Point(0,0);
    }

    @JsonCreator
    public SupplyLocation(@JsonProperty("longitude") double longitude, @JsonProperty("latitude") double latitude ){
        this.location = new Point(longitude, latitude);
    }

    public double getLongitude () {
        return this.location.getX();
    }

    public double getLatitude () {
        return this.location.getY();
    }

    //@JsonProperty connect Json -> Java field
    //@JsonCreator deserialized JSON

}
