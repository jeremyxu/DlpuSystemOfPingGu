/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.dlpu.eva.struts.action;

import java.sql.Timestamp;

import hibernate.Schoolfile;

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
import com.dlpu.eva.service.SchoolfileService;
import com.dlpu.eva.struts.form.SchoolfileuploadForm;
import com.dlpu.eva.time.GetSystemTime;

/**
 * MyEclipse Struts Creation date: 10-27-2014
 * 
 * XDoclet definition:
 * 
 * @struts.action
 */
@Entity
public class SchoolfileuploadAction extends Action {
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
		SchoolfileuploadForm sf = (SchoolfileuploadForm) form;

		Object s = request.getSession().getAttribute("adminuser");
		if (s == null) {// ��ֹ����
			return mapping.findForward(ERROR);
		}

		SchoolfileService ss = new SchoolfileService();
		Schoolfile schoolfile = new Schoolfile();

		// ��ȡ��ǰϵͳʱ��(��ȷ����)��Ϊ���ű��
		GetSystemTime gst = new GetSystemTime();
		String lt = gst.GetFullTimeSQLHelper();

		schoolfile.setFilenumber(sf.getThenumber());
		schoolfile.setFileid(lt);
		schoolfile.setFiledate(sf.getThedate());
		schoolfile.setFiletitle(sf.getThetitle());
//		if(sf.getFiletitle().substring(0, 1).equals(" ") || sf.getFiletitle().substring(0, 1).equals("")||sf.getFiletitle().equals(null)){
//			schoolfile.setFilename(sf.getThetitle());
//		}else{
//			schoolfile.setFilename(sf.getFiletitle());
//		}
		schoolfile.setFilename(sf.getFiletitle());
		schoolfile.setFilecontent(sf.getMytext());
		String filenameString = sf.getThefile().getFileName();
		
		if(!filenameString.equals(null) && !filenameString.equals("")){
			if(sf.getFiletitle().substring(0, 1).equals(" ") || sf.getFiletitle().equals("") || sf.getFiletitle().equals(null)){
				schoolfile.setFilename(filenameString);
			}else{
				schoolfile.setFilename(sf.getFiletitle());
			}
		}
		
		
		if (filenameString != null && !filenameString.equals("")) {
			FileNameFactory fnf = new FileNameFactory();
			filenameString = fnf.changeFileName(lt, filenameString);
			if (filenameString == null) {
				return mapping.findForward(FERROR);
			}
		}
		schoolfile.setFileurl(filenameString);
		if (schoolfile.getFileurl() != null && !schoolfile.getFileurl().equals("")) {// ����и������ϴ���Ƭ
			GetSourceUrlService gsus = new GetSourceUrlService();
			FileOperate fo = new FileOperate();
			if (!fo.AddOneFile(gsus.GetSchoolfileURL(), schoolfile.getFileurl(), sf.getThefile())) {
				return mapping.findForward(FERROR);
			}
		}

		if (ss.AddOneSchoolfile(schoolfile)) {
			return mapping.findForward(SUCCESS);
		} else {
			return mapping.findForward(DERROR);
		}
	}
}