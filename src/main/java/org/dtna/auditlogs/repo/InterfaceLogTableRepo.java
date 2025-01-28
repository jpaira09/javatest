package org.dtna.auditlogs.repo;

import org.dtna.auditlogs.model.InterfaceLogTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterfaceLogTableRepo extends JpaRepository<InterfaceLogTable,Integer> {
     List<InterfaceLogTable> findByStatus(String status);

}
