package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mis2022.models.entity.Appeal;

public interface AppealRepository extends JpaRepository<Appeal, Long> {
}
