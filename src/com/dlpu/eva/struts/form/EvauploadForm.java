/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.dlpu.eva.struts.form;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/** 
 * MyEclipse Struts
 * Creation date: 10-27-2014
 * 
 * XDoclet definition:
 * @struts.form name="evauploadForm"
 */
@Entity
public class EvauploadForm extends ActionForm {
	/*
	 * Generated Methods
	 */
	
	private String thetitle;
	private String filetitle;
	private FormFile thefile;

	public String getThetitle() {
		return thetitle;
	}

	public void setThetitle(String thetitle) {
		this.thetitle = thetitle;
	}

	public String getFiletitle() {
		return filetitle;
	}

	public void setFiletitle(String filetitle) {
		this.filetitle = filetitle;
	}

	public FormFile getThefile() {
		return thefile;
	}

	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
	}
}