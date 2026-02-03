package in.co.rays.project_3.controller;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ClientDTO;
import in.co.rays.project_3.dto.PositionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.model.ClientModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PositionModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(name = "ClientListCtl", urlPatterns = { "/ctl/ClientListCtl" })
public class ClientListCtl extends BaseCtl {
	@Override
	protected void preload(HttpServletRequest request) {
		ClientModelInt model = ModelFactory.getInstance().getClientModel();
		try {

			HashMap map = new HashMap();
			map.put("Indore", "Indore");
			map.put("Bhopal", "Bhopal");
			map.put("Dewas", "Dewas");

			request.setAttribute("Hashmap", map);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		ClientDTO dto = new ClientDTO();
		dto.setContactName(request.getParameter("ContactName"));
		dto.setDob(DataUtility.getDate(request.getParameter("Dob")));
		dto.setLocation(request.getParameter("Location"));
		dto.setPhone(request.getParameter("Phone"));

		populateBean(dto, request);
		return dto;
	}
	
	 @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 System.out.println("in do get method");
			List list;
			List next;
			int pageNo = 1;
			int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
			System.out.println("==========" + pageSize);
			ClientDTO dto = (ClientDTO) populateDTO(request);
			ClientModelInt model = ModelFactory.getInstance().getClientModel();
			try {
				System.out.println("in ctllllllllll search");
				list = model.search(dto, pageNo, pageSize);

				System.out.println("dogetlist ==== > " + list.size());

				next = model.search(dto, pageNo + 1, pageSize);

				System.out.println("dogetlist ==== > " + next.size());

				ServletUtility.setList(list, request);
				if (list == null || list.size() == 0) {
					System.out.println("list is null");
					ServletUtility.setErrorMessage("No record found ", request);
				}
				if (next == null || next.size() == 0) {
					System.out.println("next is null");
					request.setAttribute("nextListSize", 0);

				} else {
					System.out.println("next lis aii === > " + next.size());
					request.setAttribute("nextListSize", next.size());
				}
				ServletUtility.setList(list, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);
				ServletUtility.forward(getView(), request, response);
			} catch (ApplicationException e) {
				System.out.println(e.getMessage());
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

	}

	 
	  @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  List list = null;
			List next = null;
			int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
			int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

			pageNo = (pageNo == 0) ? 1 : pageNo;
			pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
			PositionDTO dto = (PositionDTO) populateDTO(request);
			PositionModelInt model = ModelFactory.getInstance().getPositionModel();
			String op = DataUtility.getString(request.getParameter("operation"));
			System.out.println("op---->" + op);

			String[] ids = request.getParameterValues("ids");
			try {

				if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

					if (OP_SEARCH.equalsIgnoreCase(op)) {
						pageNo = 1;
					} else if (OP_NEXT.equalsIgnoreCase(op)) {
						pageNo++;
					} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
						pageNo--;
					}

				} else if (OP_NEW.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.CLIENT_CTL, request, response);
					return;
				} else if (OP_RESET.equalsIgnoreCase(op)) {

					ServletUtility.redirect(ORSView.CLIENT_LIST_CTL, request, response);
					return;
				} else if (OP_DELETE.equalsIgnoreCase(op)) {
					pageNo = 1;
					if (ids != null && ids.length > 0) {
						PositionDTO deletedto = new PositionDTO();
						for (String id : ids) {
							deletedto.setId(DataUtility.getLong(id));
							model.delete(deletedto);
							ServletUtility.setSuccessMessage("Data Successfully Deleted!", request);
						}
					} else {
						ServletUtility.setErrorMessage("Select atleast one record", request);
					}
				}
				if (OP_BACK.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.CLIENT_LIST_CTL, request, response);
					return;
				}
				dto = (PositionDTO) populateDTO(request);

				list = model.search(dto, pageNo, pageSize);

				ServletUtility.setDto(dto, request);
				next = model.search(dto, pageNo + 1, pageSize);

				ServletUtility.setList(list, request);
				ServletUtility.setList(list, request);
				if (list == null || list.size() == 0) {
					if (!OP_DELETE.equalsIgnoreCase(op)) {
						ServletUtility.setErrorMessage("No record found ", request);
					}
				}
				if (next == null || next.size() == 0) {
					request.setAttribute("nextListSize", 0);

				} else {
					request.setAttribute("nextListSize", next.size());
				}
				ServletUtility.setList(list, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);
				ServletUtility.forward(getView(), request, response);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.CLIENT_LIST_VIEW;
	}

}
