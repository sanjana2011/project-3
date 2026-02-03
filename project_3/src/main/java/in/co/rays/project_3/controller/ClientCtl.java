package in.co.rays.project_3.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.ast.Clinit;

import in.co.rays.project_3.dto.BaseDTO;
import in.co.rays.project_3.dto.ClientDTO;
import in.co.rays.project_3.dto.PositionDTO;
import in.co.rays.project_3.exception.ApplicationException;
import in.co.rays.project_3.exception.DuplicateRecordException;
import in.co.rays.project_3.model.ClientModelInt;
import in.co.rays.project_3.model.ModelFactory;
import in.co.rays.project_3.model.PositionModelInt;
import in.co.rays.project_3.util.DataUtility;
import in.co.rays.project_3.util.DataValidator;
import in.co.rays.project_3.util.HTMLUtility;
import in.co.rays.project_3.util.PropertyReader;
import in.co.rays.project_3.util.ServletUtility;

@WebServlet(urlPatterns = { "/ctl/ClientCtl" })
public class ClientCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("ContactName"))) {
			request.setAttribute("ContactName", PropertyReader.getValue("error.require", "ContactName"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("ContactName"))) {
			request.setAttribute("ContactName", "ContactName must contain alphabets only");
			pass = false;
		} else if (DataValidator.isTooLong(request.getParameter("ContactName"), 50)) {
			request.setAttribute("ContactName", "Strategy contain 50 characters");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Dob"))) {
			request.setAttribute("Dob", PropertyReader.getValue("error.require", "Dob"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Location"))) {
			request.setAttribute("Location", PropertyReader.getValue("error.require", "Location"));
			pass = false;

		} else if (DataValidator.isTooLong(request.getParameter("Location"), 20)) {
			request.setAttribute("Location", "Strategy contain 20 characters");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("Phone"))) {
			request.setAttribute("Phone", PropertyReader.getValue("error.require", "Phone"));
			pass = false;
		}
		return pass;

	}

	@Override
	protected void preload(HttpServletRequest request) {
		HashMap map = new HashMap();
		map.put("indore", "indore");
		map.put("Dewas", "Dewas");
		map.put("Bhopal", "Bhopal");
		request.setAttribute("Hashmap", map);

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		ClientModelInt model = ModelFactory.getInstance().getClientModel();
		if (id > 0 || op != null) {
			ClientDTO dto;
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

		ClientModelInt model = ModelFactory.getInstance().getClientModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			ClientDTO dto = (ClientDTO) populateDTO(request);

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
					ServletUtility.setDto(dto, request);
					ServletUtility.setSuccessMessage("Record Successfully Saved", request);
				}
			} catch (DuplicateRecordException e) {
				ServletUtility.setDto(dto, request);
				ServletUtility.setErrorMessage("ProductName Already Exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.setErrorMessage(e.getMessage(), request); // message from handleException
				ServletUtility.forward(getView(), request, response);
				return;
			} catch (Exception e) {
				ServletUtility.setErrorMessage("Database Server is down. Please try after some time", request);
				ServletUtility.forward(getView(), request, response);
				return;
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.CLIENT_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.CLIENT_LIST_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.CLIENT_VIEW;
	}

}
