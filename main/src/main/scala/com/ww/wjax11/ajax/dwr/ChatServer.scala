package com.ww.wjax11.ajax.dwr


class ChatServer {
	def chat(message: String) = {
		println("Received message: " + message)
    message
	}
}
