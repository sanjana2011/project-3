package in.co.rays.project_3.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.OrderDTO;
import in.co.rays.project_3.dto.PositionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.OrderModelInt;
import in.co.rays.project_3.model.PositionModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/PositionCtl" })
public class PositionCtl extends BaseCtl {
	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("Designation"))) {
			request.setAttribute("Designation", PropertyReader.getValue("error.require", "Designation"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("Designation"))) {
			request.setAttribute("Designation", "Designation must contain alphabets only");
			pass = false;
		} else if (DataValidator.isTooLong(request.getParameter("Designation"), 50)) {
			request.setAttribute("Designation", "Strategy contain 50 characters");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "Dob"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("ReqExp"))) {
			request.setAttribute("ReqExp", PropertyReader.getValue("error.require", "ReqExp"));
			pass = false;
		} else if (!DataValidator.isAlphanumeric(request.getParameter("ReqExp"))) {
			request.setAttribute("ReqExp", "ReqExp must contain Alphanumeric value  only");
			pass = false;
		} else if (DataValidator.isTooLong(request.getParameter("ReqExp"), 20)) {
			request.setAttribute("ReqExp", "Strategy contain 20 characters");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Conditions"))) {
			request.setAttribute("Conditions", PropertyReader.getValue("error.require", "Conditions"));
			pass = false;
		}
		return pass;
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		PositionDTO dto = new PositionDTO();
		dto.setDesignation(request.getParameter("Designation"));
		dto.setDob(DataUtility.getDate(request.getParameter("Dob")));
		dto.setReqExp(request.getParameter("ReqExp"));
		dto.setConditions(request.getParameter("Conditions"));

		populateBean(dto, request);
		return dto;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		PositionModelInt model = ModelFactory.getInstance().getPositionModel();
		if (id > 0 || op != null) {
			PositionDTO dto;
			try {
				dto = model.findByPK(id);

				ServletUtility.setDto(dto, request);

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			}

		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));

		PositionModelInt model = ModelFactory.getInstance().getPositionModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			PositionDTO dto = (PositionDTO) populateDTO(request);

			try {
				if (id > 0) {
					dto.setId(id);
					model.update(dto);
					ServletUtility.setDto(dto, request);

					ServletUtility.setSuccessMessage("Record Successfully Updated", request);

				} else {
					System.out.println("college add" + dto + "id...." + id);
					// long pk
					model.add(dto);
					ServletUtility.setSuccessMessage("Record Successfully Saved", request);
				}
				ServletUtility.setDto(dto, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("ProductName Already Exists", request);
			}catch (Exception e) {
				ServletUtility.setErrorMessage("Database Server is down. Please try after some time", request);
				ServletUtility.forward(getView(), request, response);
				return;}
			} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.POSITION_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.POSITION_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.POSITION_VIEW;
	}

}
