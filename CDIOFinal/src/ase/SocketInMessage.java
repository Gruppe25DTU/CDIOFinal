package ase;


public class SocketInMessage {

	private MessageType replyType;
	private String msg;
	
	public SocketInMessage(MessageType replyType , String msg) {
		this.replyType = replyType;
		this.msg = msg;
		
	}

	public MessageType getReplyType() {
		return replyType;
	}

	public String getMsg() {
		return msg;
	}

	public void setReplyType(MessageType replyType) {
		this.replyType = replyType;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
