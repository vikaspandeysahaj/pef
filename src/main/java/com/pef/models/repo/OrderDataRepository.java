package com.pef.models.repo;

import com.pef.models.OrderData;
import org.springframework.data.repository.CrudRepository;

public interface OrderDataRepository extends CrudRepository<OrderData, Long> {
}
