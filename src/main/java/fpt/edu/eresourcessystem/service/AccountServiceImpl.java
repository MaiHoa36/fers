package fpt.edu.eresourcessystem.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import fpt.edu.eresourcessystem.common.AccountNotExistedException;
import fpt.edu.eresourcessystem.common.AccountNotFoundException;
import fpt.edu.eresourcessystem.model.Account;
import fpt.edu.eresourcessystem.repository.AccountRepository;

import java.util.List;


@Service("accountService")
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void addAccount(Account account) {
        accountRepository.insert(account);
    }

    @Override
    public Account updateAccount(Account account) throws AccountNotExistedException {
        Account savedAccount = accountRepository.findById(account.getAccountId())
                .orElseThrow(() -> new AccountNotExistedException("Account Not Existed."));
        savedAccount.setISBN(account.getISBN());
        savedAccount.setEmail(account.getEmail());
        savedAccount.setRole(account.getRole());
        return accountRepository.save(savedAccount);
    }



    @Override
    public Account findByEmail(String email) throws  AccountNotFoundException{
        return accountRepository.findByEmail().orElseThrow(
                () -> new AccountNotFoundException("Account not existed.")
        );
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Page<Account> findAll(int pageIndex, int pageSize, String search) {
        return null;
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }

    @Override
    public boolean delete(Account product) {
        return false;
    }

    @Override
    public Page<Account> findAll(int pageIndex, int pageSize) {
        return null;
    }
}
