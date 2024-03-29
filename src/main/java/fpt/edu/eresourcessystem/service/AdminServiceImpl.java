package fpt.edu.eresourcessystem.service;

import fpt.edu.eresourcessystem.model.Admin;
import fpt.edu.eresourcessystem.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin addAdmin(Admin admin) {
        return adminRepository.insert(admin);
    }

    @Override
    public Admin updateAdmin(Admin admin) {
        Admin checkExist = adminRepository.findByAccountId(admin.getAccount().getId());
        if (null != checkExist) {
            return adminRepository.save(admin);
        }
        return null;
    }

    @Override
    public Admin findByAccountId(String accountId) {
        return adminRepository.findByAccountId(accountId);
    }


}
