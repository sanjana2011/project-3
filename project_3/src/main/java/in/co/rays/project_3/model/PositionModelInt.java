package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.PositionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;


public interface PositionModelInt {
	public long add(PositionDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(PositionDTO dto)throws ApplicationException;
	public void update(PositionDTO dto)throws ApplicationException,DuplicateRecordException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(PositionDTO dto)throws ApplicationException;
	public List search(PositionDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public PositionDTO findByPK(long pk)throws ApplicationException;
	public PositionDTO findByRollNo(String rollNo)throws ApplicationException;
	public List getMeritList(int pageNo,int pageSize)throws ApplicationException;
	


}
