package org.fireflow.example.loan_process.mbeans;

import java.util.Date;

import org.fireflow.BasicManagedBean;
import org.fireflow.engine.EngineException;
import org.fireflow.engine.IProcessInstance;
import org.fireflow.engine.IWorkflowSession;
import org.fireflow.example.loan_process.persistence.LoanInfo;
import org.fireflow.example.loan_process.persistence.LoanInfoDAO;
import org.fireflow.kernel.KernelException;
import org.fireflow.security.persistence.User;
import org.fireflow.security.util.SecurityUtilities;
import org.operamasks.faces.annotation.Bind;
import org.operamasks.faces.annotation.ManagedBean;
import org.operamasks.faces.annotation.ManagedBeanScope;
import org.operamasks.faces.annotation.ManagedProperty;

@ManagedBean(name = "SubmitApplicationInfo2Bean", scope = ManagedBeanScope.REQUEST)
public class SubmitApplicationInfo2Bean extends BasicManagedBean {
	
	@ManagedProperty("#{LoanInfoDAO}")
	private LoanInfoDAO loanInfoDAO = null;

	@Bind
	LoanInfo loanInfo = this.initLoanInfo();
	
	protected String executeSaveBizData(){
		String errmsg = null;
		User currentUser = SecurityUtilities.getCurrentUser();
		// 一、执行业务业务操作，保存业务数据
		loanInfoDAO.attachDirty(loanInfo);
		
		// 二、开始执行流程操作
		IWorkflowSession workflowSession = workflowRuntimeContext
				.getWorkflowSession();
		try {
			// 1、创建流程实例
			IProcessInstance procInst = workflowSession
					.createProcessInstance("LoanProcess",currentUser==null?"":currentUser.getLoginid());
			// 2、设置流程变量/业务属性字段
			procInst.setProcessInstanceVariable("sn", loanInfo.getSn());// 设置流水号
			procInst.setProcessInstanceVariable("applicantName", loanInfo.getApplicantName());//贷款人姓名
			procInst.setProcessInstanceVariable("loanValue",loanInfo.getLoanValue());// 贷款数额
	
			// 3、启动流程实例,run()方法启动实例并创建第一个环节实例、分派任务
			procInst.run();
		} catch (EngineException e) {
			errmsg = e.getMessage();
			e.printStackTrace();
		} catch (KernelException e) {
			errmsg = e.getMessage();
			e.printStackTrace();
		}

		loanInfo = initLoanInfo();

		return errmsg;	
	}
	
	protected LoanInfo initLoanInfo(){
		LoanInfo loanInfoTmp = new LoanInfo();
		loanInfoTmp.setLoanteller(SecurityUtilities.getCurrentUser().getName());
		loanInfoTmp.setAppInfoInputDate(new Date());
		return loanInfoTmp;
	}
}
