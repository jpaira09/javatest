package org.dtna.repository;

import org.dtna.model.ProductInventoryImsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInventoryIMSRepo extends JpaRepository<ProductInventoryImsModel, Integer> {
}
