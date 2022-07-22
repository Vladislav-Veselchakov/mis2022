package ru.mis2022.service.entity.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mis2022.models.entity.Account;
import ru.mis2022.repositories.AccountRepository;
import ru.mis2022.service.entity.AccountService;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    @Override
    public Account persist(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account merge(Account account) {
        return accountRepository.save(account);
    }
}
