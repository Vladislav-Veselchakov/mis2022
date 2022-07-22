package ru.mis2022.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mis2022.models.entity.PersonalHistory;

public interface PersonalHistoryRepository extends JpaRepository<PersonalHistory, Long> {
}
