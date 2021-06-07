package com.revature.initiative.repository;

import com.revature.initiative.model.File;
import com.revature.initiative.model.Initiative;
import com.revature.initiative.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

    File findFileByFileURLAndInitiativeIdAndUploadedBy(String fileName, Initiative initiative, User user);
    List<File> findAllByUploadedById(Long id);
    List<File> findAllByFileInitiativeId(Long id);
}
