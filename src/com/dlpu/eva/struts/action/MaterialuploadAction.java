/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.dlpu.eva.struts.action;

import hibernate.Material;

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
import com.dlpu.eva.service.MaterialService;
import com.dlpu.eva.struts.form.MaterialuploadForm;
import com.dlpu.eva.time.GetSystemTime;

/**
 * MyEclipse Struts Creation date: 10-28-2014
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/materialupload" name="materialuploadForm"
 *                scope="request"
 */
@Entity
public class MaterialuploadAction extends Action {
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
		MaterialuploadForm materialuploadForm = (MaterialuploadForm) form;
		Object s = request.getSession().getAttribute("adminuser");
		if (s == null) {// ��ֹ����
			return mapping.findForward(ERROR);
		}

		MaterialService ms = new MaterialService();
		Material m = new Material();

		// ��ȡ��ǰϵͳʱ��(��ȷ����)��Ϊ���ű��
		GetSystemTime gst = new GetSystemTime();
		String lt = gst.GetFullTimeSQLHelper();

		m.setMaterialid(lt);
		
//		if(materialuploadForm.getThetitle().substring(0, 1).equals(" ") || materialuploadForm.getThetitle().substring(0, 1).equals("")||materialuploadForm.getThetitle().equals(null)){
//			m.setMaterialname(materialuploadForm.getThefile().getFileName());
//		}else{
//			m.setMaterialname(materialuploadForm.getThetitle());
//		}
		String filenameString = materialuploadForm.getThefile().getFileName();
		
		if(!filenameString.equals(null) && !filenameString.equals("")){
			if(materialuploadForm.getThetitle().substring(0, 1).equals(" ") || materialuploadForm.getThetitle().equals("") || materialuploadForm.getThetitle().equals(null)){
				m.setMaterialname(filenameString);
			}else{
				m.setMaterialname(materialuploadForm.getThetitle());
			}
		}
		
		if (filenameString != null && !filenameString.equals("")) {
			FileNameFactory fnf = new FileNameFactory();
			filenameString = fnf.changeFileName(lt, filenameString);
			if (filenameString == null) {
				return mapping.findForward(FERROR);
			}
		}
		m.setMaterialurl(filenameString);
		if (m.getMaterialurl() != null && !m.getMaterialurl().equals("")) {// ����и������ϴ���Ƭ
			GetSourceUrlService gsus = new GetSourceUrlService();
			FileOperate fo = new FileOperate();
			if (!fo.AddOneFile(gsus.GetMaterialfileURL(), m.getMaterialurl(), materialuploadForm.getThefile())) {
				return mapping.findForward(FERROR);
			}
		}
		if (ms.AddOneMaterial(m)) {
			return mapping.findForward(SUCCESS);
		} else {
			return mapping.findForward(DERROR);
		}
	}
}