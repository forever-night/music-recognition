package musicrecognition.dao;

import musicrecognition.entities.Track;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


@Repository
public class TrackDaoImpl implements TrackDao {
    private static final Logger LOGGER = LogManager.getLogger(TrackDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    private static final class TrackRowMapper implements RowMapper<Track> {
        @Override
        public Track mapRow(ResultSet resultSet, int i) throws SQLException {
            Track track = new Track();
            track.setId(resultSet.getInt("id"));
            track.setTitle(resultSet.getString("title"));
            track.setAlbumTitle(resultSet.getString("album_title"));
            track.setArtist(resultSet.getString("artist"));
            track.setYear(resultSet.getInt("year"));
            track.setGenre(resultSet.getString("genre"));

            return track;
        }
    }


    @Override
    public Integer insert(Track track) {
        String query = "insert into track(title, album_title, artist, year, genre)" +
                "values (:title, :albumTitle, :artist, :year, :genre)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(track);
        namedParameterJdbcTemplate.update(query, parameterSource, keyHolder);

        if (!keyHolder.getKeys().isEmpty())
            return (Integer) keyHolder.getKeys().get("id");
        else
            return null;
    }

    @Override
    public Track getById(int id) {
        String query = "select * from track where id = :id";

        Map<String,Integer> parameterMap = Collections.singletonMap("id", id);
        List<Track> result = namedParameterJdbcTemplate.query(query, parameterMap, new TrackRowMapper());

        if (result.isEmpty())
            return null;
        else
            return result.get(0);
    }

    @Override
    public boolean checkIfExists(Track track) {
        String query = "select count(*) from track where title = :title and artist = :artist";

        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(track);
        Integer count = namedParameterJdbcTemplate.queryForObject(query, parameterSource, Integer.class);

        return count > 0;
    }

    @Override
    public List<Map<String, Integer>> getTracksByFingerprints(final int limit, Integer[] fingerprints) {
        String query = "select f.track_id, count(f.track_id) as match_count from track_fingerprint f" +
                "    where f.fingerprint = any(?)" +
                "    group by f.track_id" +
                "    order by match_count desc" +
                "    limit ?";


        java.sql.Array fingerprintsArray = null;

        try {
            fingerprintsArray = jdbcTemplate.getDataSource().getConnection()
                    .createArrayOf("integer", fingerprints);
        } catch (SQLException e) {
            LOGGER.error("couldn't create sql array", e);
        }


        if (fingerprintsArray != null) {
            java.sql.Array finalFingerprintsArray = fingerprintsArray;

            List<Map<String, Integer>> result = jdbcTemplate.query(query,
                preparedStatement -> {
                    preparedStatement.setArray(1, finalFingerprintsArray);
                    preparedStatement.setInt(2, limit);
                }, (resultSet, i) -> {
                    Map<String, Integer> map = new HashMap<>();

                    map.put("trackId", resultSet.getInt("track_id"));
                    map.put("matchCount", resultSet.getInt("match_count"));

                    return map;
                });

            if (result != null && !result.isEmpty())
                return result;
        }

        return null;
    }
}
