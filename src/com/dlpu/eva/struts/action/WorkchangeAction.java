/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.dlpu.eva.struts.action;

import hibernate.Work;


import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dlpu.eva.service.WorkService;
import com.dlpu.eva.struts.form.WorkuploadForm;

/** 
 * MyEclipse Struts
 * Creation date: 10-28-2014
 * 
 * XDoclet definition:
 * @struts.action path="/workchange" name="workuploadForm" scope="request"
 */
@Entity
public class WorkchangeAction extends Action {
	/*
	 * Generated Methods
	 */
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
	private static final String DERROR = "dataerror";

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		WorkuploadForm workuploadForm = (WorkuploadForm) form;// TODO Auto-generated method stub
		Object s = request.getSession().getAttribute("adminuser");
		if (s == null) {// ��ֹ����
			return mapping.findForward(ERROR);
		}
		
		String id = request.getParameter("id");
		if (id == null) {// ��ֹ����
			return mapping.findForward(ERROR);
		}
		
		WorkService ws = new WorkService();
		Work w = new Work();
		Work orgw = ws.GetWorkById(id);

		// ����µ�������Դ�����ݿ�
		w.setWorkid(id);
		w.setWorktitle(workuploadForm.getThetitle());
		w.setWorkdate(orgw.getWorkdate());
		w.setWorkcontent(workuploadForm.getMytext());
		
		if (ws.UpdateOneWork(w)) {
			return mapping.findForward(SUCCESS);
		} else {
			return mapping.findForward(DERROR);
		}
	}
}