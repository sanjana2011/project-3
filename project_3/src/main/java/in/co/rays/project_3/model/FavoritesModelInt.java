package in.co.rays.project_3.model;

import java.util.List;

import in.co.rays.project_3.dto.FavoritesDTO;
import in.co.rays.project_3.dto.UserDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.exception.RecordNotFoundException;

public interface FavoritesModelInt {
	public long add(FavoritesDTO dto)throws ApplicationException,DuplicateRecordException;
	public void delete(FavoritesDTO dto)throws ApplicationException;
	public void update(FavoritesDTO dto)throws ApplicationException,DuplicateRecordException;
	public FavoritesDTO findByPK(long pk)throws ApplicationException;
	public FavoritesDTO findByLogin(String login)throws ApplicationException;
	public List list()throws ApplicationException;
	public List list(int pageNo,int pageSize)throws ApplicationException;
	public List search(FavoritesDTO dto,int pageNo,int pageSize)throws ApplicationException;
	public List search(FavoritesDTO dto)throws ApplicationException;
	
}
