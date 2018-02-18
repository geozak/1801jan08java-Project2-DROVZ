package com.revature.project2.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.revature.project2.servlets.RequestHelper.Response;

/**
 * Servlet implementation class MasterServlet
 */
public class MasterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5800084843621566437L;

	/**
	 * Default constructor.
	 */
	public MasterServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Response rhResponse = RequestHelper.processGET(request);
		handleResponse(request, response, rhResponse);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Response rhResponse = RequestHelper.processPOST(request);
		handleResponse(request, response, rhResponse);
	}

	private void handleResponse(HttpServletRequest request, HttpServletResponse response,
			Response rhResponse) throws ServletException, IOException {
		try {
			if (rhResponse == null) {
				response.sendError(404);
				return;
			}
			switch (rhResponse.getResponseType()) {
			case FORWARD:
				request.getRequestDispatcher(rhResponse.getResponse())
					.forward(request, response);
				return;
			case REDIRECT:
				response.sendRedirect(rhResponse.getResponse());
				return;
			case PRINTWRITER:
				rhResponse.getHeaders().forEach(
						(name, value) -> response.setHeader(name, value));
				return;
			default:
				response.sendError(404);
				return;
			}
		} finally {
			if (request.getAttribute("hibSess") instanceof Session) {
				Session hibSess = (Session) request.getAttribute("hibSess");
				if (hibSess.isOpen()) {
					hibSess.close();
				}
			}
		}
	}

}
