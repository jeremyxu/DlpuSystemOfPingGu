/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.dlpu.eva.struts.action;

import hibernate.Admin;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dlpu.eva.service.AdminService;
import com.dlpu.eva.struts.form.ChangepasswordForm;

/**
 * MyEclipse Struts Creation date: 10-28-2014
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/changepassword" name="changepasswordForm"
 *                scope="request"
 */
@Entity
public class ChangepasswordAction extends Action {
	/*
	 * Generated Methods
	 */
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
	private static final String DERROR = "dataerror";
	private static final String PWD = "pwd";

	/**
	 * Method execute
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		ChangepasswordForm changepasswordForm = (ChangepasswordForm) form;

		if (!changepasswordForm.getPwd1().equals(changepasswordForm.getPwd2())) {
			return mapping.findForward(PWD);
		}

		Object s = request.getSession().getAttribute("adminuser");
		if (s == null) {// ��ֹ����
			return mapping.findForward(ERROR);
		}
		String admin = s.toString();

		AdminService as = new AdminService();
		Admin orga = new Admin();
		Admin a = new Admin();

		orga.setUsername(admin);
		orga.setPassword(changepasswordForm.getOrgpwd());
		a.setUsername(admin);
		a.setPassword(changepasswordForm.getPwd1());

		if (as.ChangeAdminPassword(orga, a)) {
			return mapping.findForward(SUCCESS);
		} else {
			return mapping.findForward(DERROR);
		}
	}
}