package com.switchfully.eurder.domain.itemgroup;

import com.switchfully.eurder.domain.order.Order;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemGroupRepository extends JpaRepository<ItemGroup, Integer> {

    List<ItemGroup> findByOrder(Order order);
}
