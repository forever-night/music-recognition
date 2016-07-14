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
        if (validate(fingerprints)) {
            String selectQuery = "select count(*) from track t where t.id = ?";
            
            int exists = jdbcTemplate.queryForObject(selectQuery, new Integer[]{id}, Integer.class);
            
            if (exists == 0)
                return 0;
            
            
            String insertQuery = "INSERT INTO track_fingerprint VALUES (?, ?)";
    
            int[] insertCounts = jdbcTemplate.batchUpdate(insertQuery,
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
        
        return 0;
    }
    
    /**
     * @return true if valid, false if otherwise
     * */
    private boolean validate(Integer[] fingerprints) {
        return fingerprints != null && fingerprints.length > 0;
    }
}
