package musicrecognition.dao.interfaces;

public interface FingerprintDao {
    /**
     * Insert a list of fingerprints with a given track id.
     * @param id track id to reference
     * @param fingerprints*/
    int batchInsertFingerprintsById(int id, Integer[] fingerprints);
}
