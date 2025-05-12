package common;

//make sure that all possible message types between
//client and server are clearly defined
public enum MessageType {
	GET_ALL_ORDERS,
	UPDATE_ORDER,
	RESPONSE_SUCCESS,
	RESPONSE_ERROR,
	RESPONSE_ORDERS
}
