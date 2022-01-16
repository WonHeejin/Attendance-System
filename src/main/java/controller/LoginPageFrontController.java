package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import beans.ActionBean;
import login.LoginManagement;



@WebServlet({"/Access", "/AccessOut", "/f" , "/s"})
public class LoginPageFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public LoginPageFrontController() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		this.doProcess(request, response);
	}


	protected void doProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		ActionBean action = new ActionBean();
		LoginManagement log;
		String jobCode = req.getRequestURI().substring(req.getContextPath().length()+1);

		HttpSession session = req.getSession();




		if(session.getAttribute("emCode")!= null || session.getAttribute("stCode")!= null) {
			if(jobCode.equals("f")) {
				log = new LoginManagement(req);
				action = log.backController(1);
			}else if(jobCode.equals("Access")) {
				log = new LoginManagement(req);
				action = log.backController(2);
			}else if(jobCode.equals("AccessOut")) {
				log = new LoginManagement(req);
				action = log.backController(3);
			}else {
				action = new ActionBean();
				action.setRedirect(true);
				action.setPage("index.html");
			}
		}else {
			if(jobCode.equals("Access")) {
				log = new LoginManagement(req);
				action = log.backController(2);
			}else {
				action = new ActionBean();
				action.setRedirect(true);
				action.setPage("index.html");
			}
		}


		if(action.isRedirect()) {
			res.sendRedirect(action.getPage());
		}else {
			RequestDispatcher dp = req.getRequestDispatcher(action.getPage());
			dp.forward(req, res);
		}
	}






}
