/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.dlpu.eva.struts.action;

import java.sql.Timestamp;

import hibernate.Policy;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dlpu.eva.file.FileNameFactory;
import com.dlpu.eva.file.FileOperate;
import com.dlpu.eva.service.GetSourceUrlService;
import com.dlpu.eva.service.PolicyService;
import com.dlpu.eva.struts.form.PolicyupForm;
import com.dlpu.eva.time.GetSystemTime;

/**
 * MyEclipse Struts Creation date: 10-27-2014
 * 
 * XDoclet definition:
 * 
 * @struts.action
 */
@Entity
public class PolicyupAction extends Action {
	/*
	 * Generated Methods
	 */
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
	private static final String FERROR = "fileerror";
	private static final String DERROR = "dataerror";

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
		Object s = request.getSession().getAttribute("adminuser");
		if (s == null) {// ��ֹ����
			return mapping.findForward(ERROR);
		}
		PolicyupForm pf = (PolicyupForm) form;
		Policy p = new Policy();

		// ��ȡ��ǰϵͳʱ��(��ȷ����)��Ϊ���ű��
		GetSystemTime gst = new GetSystemTime();
		String lt = gst.GetFullTimeSQLHelper();
		Timestamp timestamp = gst.GetFullTimeStamp();

		p.setPolicyid(lt);
		p.setPolicydate(timestamp);
		p.setPolicytitle(pf.getPolicytitle());
//		if(pf.getFiletitle().substring(0, 1).equals(" ") || pf.getFiletitle().equals("")||pf.getFiletitle().equals(null)){
//			p.setPolicyfilename(pf.getPolicytitle());
//		}else{
//			p.setPolicyfilename(pf.getFiletitle());
//		}
		
		p.setPolicycontent(pf.getMytext());
		String filenameString = pf.getPolicyfile().getFileName();
		if(!filenameString.equals(null) && !filenameString.equals("")){
			if(pf.getFiletitle().equals("") || pf.getFiletitle().equals(null)){
				p.setPolicyfilename(filenameString);
			}else{
				p.setPolicyfilename(pf.getFiletitle());
			}
		}
		if (filenameString != null && !filenameString.equals("")) {
			FileNameFactory fnf = new FileNameFactory();
			filenameString = fnf.changeFileName(lt, filenameString);
			if (filenameString == null) {
				return mapping.findForward(FERROR);
			}
		}
		p.setPolicyurl(filenameString);

		if (p.getPolicyurl() != null && !p.getPolicyurl().equals("")) {// ����и������ϴ���Ƭ
			GetSourceUrlService gsus = new GetSourceUrlService();
			FileOperate fo = new FileOperate();
			if (!fo.AddOneFile(gsus.GetPolicyFileURL(), p.getPolicyurl(), pf.getPolicyfile())) {
				return mapping.findForward(FERROR);
			}
		}

		// ����һ�����߾���
		PolicyService ps = new PolicyService();
		if (ps.AddOnePolicy(p)) {
			return mapping.findForward(SUCCESS);
		} else {
			return mapping.findForward(DERROR);
		}
	}
}