package com.revature.repository;

import com.revature.model.Request;
import com.revature.model.enums.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> getAllByRequestType(RequestType requestType);
}
