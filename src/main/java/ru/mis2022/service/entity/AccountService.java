package ru.mis2022.service.entity;

import ru.mis2022.models.entity.Account;

public interface AccountService {

    Account persist(Account account);
    Account merge(Account account);
}
