package tk.mybatis.springboot.modal;

import java.io.Serializable;

public class ResponseModal extends Modal implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public ResponseModal(){
		super();
	}
	
	public ResponseModal(int code,boolean success,String message){
		super(code,success,message);
	}
	
	public ResponseModal(int code,boolean success,String message,Object obj){
		super(code,success,message);
		this.response = obj;
	}
	
	private Object response;

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}
	
}
