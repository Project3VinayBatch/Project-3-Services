package com.revature.initiative.repository;
import com.revature.initiative.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

    File findFileByFileURL(String fileName);

}
