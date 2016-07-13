package musicrecognition.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.IntStream;


@Repository
public class FingerprintDaoImpl implements FingerprintDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Override
    @Transactional
    public int batchInsertFingerprintsById(int id, Integer[] fingerprints) {
        if (fingerprints == null || fingerprints.length == 0)
            return 0;
        
        
        String query = "insert into track_fingerprint values (?, ?)";
        
        int[] insertCounts = jdbcTemplate.batchUpdate(query,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        preparedStatement.setInt(1, id);
                        preparedStatement.setInt(2, fingerprints[i]);
                    }
                    
                    @Override
                    public int getBatchSize() {
                        return fingerprints.length;
                    }
                });
        
        return IntStream.of(insertCounts).sum();
    }
}
