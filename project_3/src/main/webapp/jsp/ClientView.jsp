<%@page import="in.co.rays.project_3.controller.ClientCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.project_3.util.HTMLUtility"%>
<%@page import="in.co.rays.project_3.util.ServletUtility"%>
<%@page import="in.co.rays.project_3.util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>position View</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<style type="text/css">
i.css {
	border: 2px solid #8080803b;
	padding-left: 10px;
	padding-bottom: 11px;
	background-color: #ebebe0;
}

.p4 {
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/user1.jpg');
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	padding-top: 75px;

	/* background-size: 100%; */
}
</style>
<script>
	function limitInputLength(input, maxLength) {
		if (input.value.length > maxLength) {
			input.value = input.value.slice(0, maxLength);
		}
	}
</script>
</head>
<body class="p4">
	<div class="header">
		<%@include file="Header.jsp"%>
		<%@include file="calendar.jsp"%>

	</div>
	<div>

		<main>
		<form action="<%=ORSView.CLIENT_CTL%>" method="post">

			<div class="row pt-3 pb-4">
				<!-- Grid column -->
				<jsp:useBean id="dto" class="in.co.rays.project_3.dto.ClientDTO"
					scope="request"></jsp:useBean>
				<div class="col-md-4 mb-4"></div>
				<div class="col-md-4 mb-4">
					<div class="card">
						<div class="card-body">
							<%
								long id = DataUtility.getLong(request.getParameter("id"));

								if (dto.getId() != null) {
							%>
							<h3 class="text-center text-primary">Update Client</h3>
							<%
								} else {
							%>
							<h3 class="text-center text-primary">Add Client</h3>
							<%
								}
							%>
							<!--Body-->
							<div>

								<H4 align="center">
									<%
										if (!ServletUtility.getSuccessMessage(request).equals("")) {
									%>
									<div class="alert alert-success alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getSuccessMessage(request)%>
									</div>
									<%
										}
									%>
								</H4>

								<H4 align="center">
									<%
										if (!ServletUtility.getErrorMessage(request).equals("")) {
									%>
									<div class="alert alert-danger alert-dismissible">
										<button type="button" class="close" data-dismiss="alert">&times;</button>
										<%=ServletUtility.getErrorMessage(request)%>
									</div>
									<%
										}
									%>

								</H4>

								<input type="hidden" name="id" value="<%=dto.getId()%>">
								<input type="hidden" name="createdBy"
									value="<%=dto.getCreatedBy()%>"> <input type="hidden"
									name="modifiedBy" value="<%=dto.getModifiedBy()%>"> <input
									type="hidden" name="createdDatetime"
									value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
								<input type="hidden" name="modifiedDatetime"
									value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">
							</div>
							<div class="md-form">
								<span class="pl-sm-5"><b>ContactName</b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-address-book grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" name="ContactName" class="form-control"
											placeholder="Enter ContactName"
											value="<%=DataUtility.getStringData(dto.getContactName())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("ContactName", request)%></font></br>

								<span class="pl-sm-5"><b>Dob</b><span style="color: red;">*</span></span>
								</br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-university grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>
										<input type="text" name="Dob" class="form-control"
											id="datepicker2" readonly="readonly" id="defaultForm-email"
											placeholder="Enter Dob"
											value="<%=DataUtility.getDateString(dto.getDob())%>">
									</div>
								</div>
								<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("Dob", request)%></font></br>




								<span class="pl-sm-5"><b>Location</b><span
									style="color: red;">*</span></span> </br>
								<div class="col-sm-12">
									<div class="input-group">
										<div class="input-group-prepend">
											<div class="input-group-text">
												<i class="fa fa-address-card grey-text"
													style="font-size: 1rem;"></i>
											</div>
										</div>

										<%
											HashMap map = new HashMap();
											map.put("indore", "indore");
											map.put("Dewas", "Dewas");
											map.put("Bhopal", "Bhopal");

											String htmlList = HTMLUtility.getList("Location", dto.getLocation(), map);
										%>
										<%=htmlList%></div>

									<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("Location", request)%></font></br>

									<div class="md-form">
										<span class="pl-sm-5"><b>Phone</b><span
											style="color: red;">*</span></span> </br>
										<div class="col-sm-12">
											<div class="input-group">
												<div class="input-group-prepend">
													<div class="input-group-text">
														<i class="fa fa-address-book grey-text"
															style="font-size: 1rem;"></i>
													</div>
												</div>
												<input type="number" style="width: 195px"
													oninput="limitInputLength(this, 10)" name="Phone"
													class="form-control" placeholder="Enter Phone"
													value="<%=DataUtility.getStringData(dto.getPhone())%>">
											</div>
										</div>
										<font color="red" class="pl-sm-5"> <%=ServletUtility.getErrorMessage("Phone", request)%></font></br>

									</div>
									</br>

									<%
										if (id > 0) {
									%>
									<div class="text-center">

										<input type="submit" name="operation"
											class="btn btn-success btn-md" style="font-size: 17px"
											value="<%=ClientCtl.OP_UPDATE%>"> <input
											type="submit" name="operation" class="btn btn-warning btn-md"
											style="font-size: 17px" value="<%=ClientCtl.OP_CANCEL%>">
									</div>
									<%
										} else {
									%>
									<div class="text-center">

										<input type="submit" name="operation"
											class="btn btn-success btn-md" style="font-size: 17px"
											value="<%=ClientCtl.OP_SAVE%>"> <input type="submit"
											name="operation" class="btn btn-warning btn-md"
											style="font-size: 17px" value="<%=ClientCtl.OP_RESET%>">
									</div>
									<%
										}
									%>
								</div>
							</div>
						</div>
						<div class="col-md-4 mb-4"></div>
					</div>
		</form>
		</main>


	</div>

</body>
<%@include file="FooterView.jsp"%>

</body>
</html>