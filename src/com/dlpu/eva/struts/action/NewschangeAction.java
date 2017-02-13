/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.dlpu.eva.struts.action;

import hibernate.News;

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
import com.dlpu.eva.service.NewsService;
import com.dlpu.eva.struts.form.NewsuploadForm;

/**
 * MyEclipse Struts Creation date: 10-23-2014
 * 
 * XDoclet definition:
 * 
 * @struts.action
 */
@Entity
public class NewschangeAction extends Action {
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
		NewsuploadForm newsuploadForm = (NewsuploadForm) form;
		News n = new News();

		String getid = request.getParameter("id");
		if (getid == null || getid.length() < 1) {
			return mapping.findForward(ERROR);
		}

		NewsService ns = new NewsService();
		News orgn = new News();
		orgn = ns.GetNewsById(getid);

		n.setNewsid(getid);
		n.setNewstitle(newsuploadForm.getNewstitle());
		n.setNewscontent(newsuploadForm.getMytext());
		n.setNewsfiletitle(newsuploadForm.getImgtitle());
		n.setNewstime(orgn.getNewstime());

		String filenameString = newsuploadForm.getImgfile().getFileName();
		if (filenameString == null || filenameString.length() < 1) {
			n.setNewsfileurl(orgn.getNewsfileurl());// û���ϴ����ļ��򱣳�ԭ���ļ���
		} else {// �ϴ������ļ������
			FileNameFactory fnf = new FileNameFactory();
			filenameString = fnf.changeFileName(getid, filenameString);
			if (filenameString == null) {
				return mapping.findForward(FERROR);
			}
			n.setNewsfileurl(filenameString);
			// ȡ���ļ������Ӧ�ĵ�ַ
			GetSourceUrlService gsurl = new GetSourceUrlService();
			FileOperate fo = new FileOperate();
			fo.DelOneFile(gsurl.GetNewsFileUrl(), orgn.getNewsfileurl(), false);// ɾ��ԭ�ļ�
			if (!fo.AddOneFile(gsurl.GetNewsFileUrl(), filenameString, newsuploadForm.getImgfile())) {
				return mapping.findForward(FERROR);// ��Ƭ�ϴ�ʧ�ܴ���
			}
		}

		if (ns.UpdateOneNews(n)) {
			return mapping.findForward(SUCCESS);// �޸ĳɹ�
		} else {
			return mapping.findForward(DERROR);// �޸�ʧ��
		}
	}
}