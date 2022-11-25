package com.switchfully.eurder.domain.itemgroup;

import com.switchfully.eurder.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ItemGroupRepository extends JpaRepository<ItemGroup, Long> {
    List<ItemGroup> findByOrder(Order order);

    List<ItemGroup> findAllByShippingDateEquals(LocalDate shippingDate);
}
