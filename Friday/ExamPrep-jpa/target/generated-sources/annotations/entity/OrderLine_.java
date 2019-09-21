package entity;

import entity.ItemType;
import entity.Order;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-20T12:18:37")
@StaticMetamodel(OrderLine.class)
public class OrderLine_ { 

    public static volatile SingularAttribute<OrderLine, Integer> quantity;
    public static volatile SingularAttribute<OrderLine, ItemType> itemtype;
    public static volatile SingularAttribute<OrderLine, Integer> id;
    public static volatile SingularAttribute<OrderLine, Order> order;

}