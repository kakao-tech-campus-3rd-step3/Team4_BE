package com.example.demo.mission.regular.infrastructure;

import static com.example.demo.mission.regular.service.counter.MissionDelta.CounterType.COMPLETION;
import static com.example.demo.mission.regular.service.counter.MissionDelta.CounterType.EXPOSURE;
import static com.example.demo.mission.regular.service.counter.MissionDelta.CounterType.SELECTION;

import com.example.demo.mission.regular.service.counter.MissionDelta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MissionBatchUpdater {

    private final DataSource dataSource;

    public void batchUpdateCounter(Map<Long, MissionDelta> deltas) {
        String sql = "UPDATE missions "
            + "SET exposure_count = exposure_count + ?, "
            + "selection_count = selection_count + ?, "
            + "completion_count = completion_count + ? "
            + "where id = ?";

        try (Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Entry<Long, MissionDelta> entry : deltas.entrySet()) {
                MissionDelta delta = entry.getValue();
                ps.setInt(1, delta.getCount(EXPOSURE));
                ps.setInt(2, delta.getCount(SELECTION));
                ps.setInt(3, delta.getCount(COMPLETION));
                ps.setLong(4, entry.getKey());
                ps.addBatch();
            }

            ps.executeBatch();
        } catch (SQLException e) {
            log.error("Batch update mission counters failed. missionIds={} size={}",
                deltas.keySet(), deltas.size(), e);
        }

    }

}