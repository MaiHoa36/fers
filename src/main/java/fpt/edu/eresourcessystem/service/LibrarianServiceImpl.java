package fpt.edu.eresourcessystem.service;

import fpt.edu.eresourcessystem.model.Librarian;
import fpt.edu.eresourcessystem.repository.LibrarianRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("librarianService")
public class LibrarianServiceImpl implements LibrarianService{
    private final LibrarianRepository librarianRepository;

    public LibrarianServiceImpl(LibrarianRepository librarianRepository) {
        this.librarianRepository = librarianRepository;
    }

    @Override
    public Librarian addLibrarian(Librarian librarian) {
        if(null==librarian.getAccountId()){
            Librarian result = librarianRepository.save(librarian);
            return result;
        }else {
            Librarian checkExist = librarianRepository.findByAccountId(librarian.getAccountId());
            if(null!=checkExist){
                Librarian result = librarianRepository.save(librarian);
                return result;
            }
        }
        return null;
    }

    @Override
    public Librarian updateLibrarian(Librarian librarian) {
        if(null==librarian.getAccountId()){
            return null;
        }else {
            Librarian checkExist = librarianRepository.findByAccountId(librarian.getAccountId());
            if(null!=checkExist){
                Librarian result = librarianRepository.save(librarian);
                return result;
            }
        }
        return null;
    }

    @Override
    public Librarian findByAccountId(String accountId) {
        Librarian librarian = librarianRepository.findByAccountId(accountId);
        return librarian;
    }
}