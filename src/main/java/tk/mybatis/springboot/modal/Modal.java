package tk.mybatis.springboot.modal;

public class Modal {
	

	public Modal() {
		super();
	}

	public Modal(int code, boolean success, String message) {
		super();
		this.code = code;
		this.success = success;
		this.message = message;
	}

	private int code;
	
	private boolean success;
	
	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
