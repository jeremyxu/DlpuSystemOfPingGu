/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.dlpu.eva.struts.action;

import hibernate.Bulletin;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dlpu.eva.file.FileNameFactory;
import com.dlpu.eva.file.FileOperate;
import com.dlpu.eva.service.BulletinService;
import com.dlpu.eva.service.GetSourceUrlService;
import com.dlpu.eva.struts.form.BulletinuploadForm;

/**
 * MyEclipse Struts Creation date: 10-28-2014
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/bulletinchange" name="bulletinuploadForm"
 *                scope="request"
 */
@Entity
public class BulletinchangeAction extends Action {
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
		BulletinuploadForm bulletinuploadForm = (BulletinuploadForm) form;
		Object s = request.getSession().getAttribute("adminuser");
		if (s == null) {// ��ֹ����
			return mapping.findForward(ERROR);
		}

		String id = request.getParameter("id");
		if (id == null) {// ��ֹ����
			return mapping.findForward(ERROR);
		}

		BulletinService bs = new BulletinService();
		Bulletin b = new Bulletin();
		Bulletin orgb = bs.GetBulletinById(id);

		b.setBulletinid(id);
		b.setBulletintitle(bulletinuploadForm.getThetitle());
		b.setBulletindate(orgb.getBulletindate());
//		if(bulletinuploadForm.getFiletitle().substring(0, 1).equals(" ") || bulletinuploadForm.getFiletitle().substring(0, 1).equals("")||bulletinuploadForm.getFiletitle().equals(null)){
//			b.setBulletinfilename(bulletinuploadForm.getThetitle());
//		}else{
//			b.setBulletinfilename(bulletinuploadForm.getFiletitle());
//		}
		
		String filenameString = bulletinuploadForm.getThefile().getFileName();
		if(!filenameString.equals(null) && !filenameString.equals("")){
			if(bulletinuploadForm.getFiletitle().substring(0, 1).equals(" ") || bulletinuploadForm.getFiletitle().equals("") || bulletinuploadForm.getFiletitle().equals(null)){
				b.setBulletinfilename(filenameString);
			}else{
				b.setBulletinfilename(bulletinuploadForm.getFiletitle());
			}
		}
		
		if (filenameString != null && !filenameString.equals("")) {
			FileNameFactory fnf = new FileNameFactory();
			filenameString = fnf.changeFileName(id, filenameString);
			if (filenameString == null) {
				return mapping.findForward(FERROR);
			}
			b.setBulletinurl(filenameString);
			// ȡ���ļ������Ӧ�ĵ�ַ
			GetSourceUrlService gsus = new GetSourceUrlService();
			FileOperate fo = new FileOperate();
			fo.DelOneFile(gsus.GetBulletinfileURL(), orgb.getBulletinurl(), false);// ɾ��ԭ���ĸ���
			if (!fo.AddOneFile(gsus.GetBulletinfileURL(), b.getBulletinurl(), bulletinuploadForm.getThefile())) {
				return mapping.findForward(FERROR);
			}
		} else {
			b.setBulletinurl(orgb.getBulletinurl());
		}

		if (bs.UpdateBulletin(b)) {
			return mapping.findForward(SUCCESS);
		} else {
			return mapping.findForward(DERROR);
		}
	}
}