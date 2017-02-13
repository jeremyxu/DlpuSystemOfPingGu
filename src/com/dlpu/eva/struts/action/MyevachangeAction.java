/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.dlpu.eva.struts.action;

import hibernate.Myeva;

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
import com.dlpu.eva.service.MyevaService;
import com.dlpu.eva.struts.form.MyevauploadForm;

/**
 * MyEclipse Struts Creation date: 10-28-2014
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/myevachange" name="myevauploadForm" scope="request"
 */
@Entity
public class MyevachangeAction extends Action {
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
		MyevauploadForm myevauploadForm = (MyevauploadForm) form;
		Object s = request.getSession().getAttribute("adminuser");
		if (s == null) {// ��ֹ����
			return mapping.findForward(ERROR);
		}

		String id = request.getParameter("id");
		if (id == null) {// ��ֹ����
			return mapping.findForward(ERROR);
		}

		MyevaService ms = new MyevaService();
		Myeva m = new Myeva();
		Myeva orgm = ms.GetMyevaById(id);

		m.setMyevaid(id);
		m.setMyevadate(orgm.getMyevadate());
		m.setMyevatitle(myevauploadForm.getThetitle());
//		if(myevauploadForm.getFiletitle().substring(0, 1).equals(" ") || myevauploadForm.getFiletitle().substring(0, 1).equals("")||myevauploadForm.getFiletitle().equals(null)){
//			m.setMyevafilename(myevauploadForm.getThetitle());
//		}else{
//			m.setMyevafilename(myevauploadForm.getFiletitle());
//		}
		//m.setMyevafilename(myevauploadForm.getFiletitle());
		String filenameString = myevauploadForm.getThefile().getFileName();
		
		if(!filenameString.equals(null) && !filenameString.equals("")){
			if(myevauploadForm.getFiletitle().substring(0, 1).equals(" ") || myevauploadForm.getFiletitle().equals("") || myevauploadForm.getFiletitle().equals(null)){
				m.setMyevafilename(filenameString);
			}else{
				m.setMyevafilename(myevauploadForm.getFiletitle());
			}
		}
		
		if (filenameString != null && !filenameString.equals("")) {
			FileNameFactory fnf = new FileNameFactory();
			filenameString = fnf.changeFileName(id, filenameString);
			if (filenameString == null) {
				return mapping.findForward(FERROR);
			}
			m.setMyevafileurl(filenameString);
			// ȡ���ļ������Ӧ�ĵ�ַ
			GetSourceUrlService gsus = new GetSourceUrlService();
			FileOperate fo = new FileOperate();
			fo.DelOneFile(gsus.GetMyevafileURL(), orgm.getMyevafileurl(), false);// ɾ��ԭ���ĸ���
			if (!fo.AddOneFile(gsus.GetMyevafileURL(), m.getMyevafileurl(), myevauploadForm.getThefile())) {
				return mapping.findForward(FERROR);
			}
		} else {
			m.setMyevafileurl(orgm.getMyevafileurl());
		}

		if (ms.UpdateMyeva(m)) {
			return mapping.findForward(SUCCESS);
		} else {
			return mapping.findForward(DERROR);
		}
	}
}