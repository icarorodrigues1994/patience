package com.wipro.registervehicle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wipro.registervehicle.model.VehicleRecord;


@Repository
public interface VehicleRepository extends JpaRepository<VehicleRecord, Long> {

	@Query(value = "SELECT MAX(id) FROM vehiclerecord", nativeQuery = true)
	public Long findLastRecordIndex();

	@Query(value = "SELECT * FROM vehiclerecord v WHERE v.id=(SELECT max(vr.id) FROM vehiclerecord vr WHERE vr.plateNumber IS NOT NULL AND vr.plateNumber != '-')", nativeQuery = true)
	public VehicleRecord findLastRecord();

	@Query(value = "SELECT * FROM vehiclerecord v WHERE v.id=(SELECT max(vr.id) FROM vehiclerecord vr "
			+ "WHERE vr.recordStatus = :status AND vr.user_id = :userId)", nativeQuery = true)
	public VehicleRecord findLastRecordPending(@Param(value = "status") String status, @Param(value = "userId") Long userId);

	@Query(value = "SELECT * FROM vehiclerecord v where (v.recordStatus = :status and v.rtoOffice = :rtoOffice)", nativeQuery = true)
	public List<VehicleRecord> findAllRecordByStatusAndOffice(@Param(value = "status") String status,
			@Param(value = "rtoOffice") String rtoOffice);

}
