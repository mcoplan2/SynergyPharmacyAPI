package com.revature.repository;

import com.revature.model.Request;
import com.revature.model.enums.RequestType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> getAllByRequestType(RequestType requestType);
    List<Request> getAllByUser_UserId(Integer userId);
    List<Request> getAllByRequestTypeAndUser_UserId(RequestType requestType, Integer userId);
    Optional<Request> findByUser_UserIdAndMed_MedIdAndDosageCountAndDosageFreqAndRequestType(Integer userId, Integer medId, Integer dosageCount, Integer dosageFreq, RequestType requestType);
    List<Request> findByUser_UserIdAndRequestTypeAndMed_NameStartingWith(Integer userId, RequestType requestType, String letter);
    List<Request> findByUser_UserIdAndRequestTypeAndMed_NameContainingIgnoreCase(Integer userId, RequestType requestType, String query);
    List<Request> findByMed_NameStartingWith(String letter);
    List<Request> findByMed_NameContainingIgnoreCase(String query);
}
