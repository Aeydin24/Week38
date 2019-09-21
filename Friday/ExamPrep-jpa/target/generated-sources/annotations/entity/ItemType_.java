package entity;

import entity.OrderLine;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-20T12:18:37")
@StaticMetamodel(ItemType.class)
public class ItemType_ { 

    public static volatile SingularAttribute<ItemType, OrderLine> orderline;
    public static volatile SingularAttribute<ItemType, Integer> price;
    public static volatile SingularAttribute<ItemType, String> name;
    public static volatile SingularAttribute<ItemType, String> description;
    public static volatile SingularAttribute<ItemType, Integer> id;

}