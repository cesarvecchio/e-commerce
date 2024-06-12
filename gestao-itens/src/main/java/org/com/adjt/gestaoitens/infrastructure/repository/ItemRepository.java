package org.com.adjt.gestaoitens.infrastructure.repository;

import org.com.adjt.gestaoitens.domain.entity.ItemEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<ItemEntity, String> {
}
