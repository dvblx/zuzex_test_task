package com.zuzex.testTask.repositories;

import com.zuzex.testTask.models.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long>, JpaSpecificationExecutor<House> {

}
