package fpt.edu.eresourcessystem.repository;

import fpt.edu.eresourcessystem.model.Admin;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminRepository")
public interface AdminRepository extends MongoRepository<Admin, String> {

    Admin findByAccountId(String accountId);

    @Override
    @Query("{ 'deleteFlg' : 'PRESERVED' }")
    @NotNull
    List<Admin> findAll();
}
