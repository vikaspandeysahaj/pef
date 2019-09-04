package com.pef.models.repo;

import com.pef.models.Amount;
import com.pef.models.OrderData;
import org.springframework.data.repository.CrudRepository;

public interface AmountRepository extends CrudRepository<Amount, Long> {
}
