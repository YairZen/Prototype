package common;

import java.io.Serializable;

//make sure that messages sent between client and server include a type and data
//this class represents a single communication unit in the system
public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	private MessageType type;
	private Object data;

	// make sure that a new message is created with a specific type and data
	public Message(MessageType type, Object data) {
		this.type = type;
		this.data = data;
	}

	// make sure that the type of the message can be retrieved
	public MessageType getType() {
		return type;
	}

	// make sure that the data carried by the message can be retrieved
	public Object getData() {
		return data;
	}
}
