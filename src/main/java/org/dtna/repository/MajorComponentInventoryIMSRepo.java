package org.dtna.repository;

import org.dtna.model.MajorComponentInventoryIMSModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorComponentInventoryIMSRepo extends JpaRepository<MajorComponentInventoryIMSModel, Integer> {
}
