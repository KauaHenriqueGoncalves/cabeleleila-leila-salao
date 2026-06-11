package com.cabeleleira.leila.salao.repository;

import com.cabeleleira.leila.salao.domain.Scheduling;
import com.cabeleleira.leila.salao.enums.SchedulingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SchedulingRepository extends JpaRepository<Scheduling, UUID> {
    List<Scheduling> findAllByClientId(UUID clientId);

    List<Scheduling> findAllByDateHoursBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    @Query("""
        SELECT COUNT(s) FROM Scheduling s
        WHERE s.dateHours BETWEEN :start AND :end
    """)
    Long countByPeriod(LocalDateTime start, LocalDateTime end);

    @Query("""
        SELECT COUNT(s) FROM Scheduling s
        WHERE s.dateHours BETWEEN :start AND :end
        AND s.status = :status
    """)
    Long countByPeriodAndStatus(LocalDateTime start, LocalDateTime end, SchedulingStatus status);

    @Query("""
        SELECT COALESCE(SUM(s.priceCharged), 0) FROM Scheduling s
        WHERE s.dateHours BETWEEN :start AND :end
        AND s.status = 'DONE'
    """)
    Double sumRevenue(LocalDateTime start, LocalDateTime end);
}
