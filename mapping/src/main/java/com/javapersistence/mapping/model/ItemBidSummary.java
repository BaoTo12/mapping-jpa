package com.javapersistence.mapping.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

@Entity
@Immutable
@Subselect("select i.id as itemId, i.name as NAME, " +
        "count(b.id) as NUMBEROFBIDS " +
        "from ITEM i left outer join BID b on i.id = b.ITEM_ID" +
        " group by i.id, i.name"
)
@Synchronize({"ITEM", "BID"})
public class ItemBidSummary {
    @Id
    private Long itemId;

    private String name;

    private long numberOfBids;

    public Long getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public long getNumberOfBids() {
        return numberOfBids;
    }
}
