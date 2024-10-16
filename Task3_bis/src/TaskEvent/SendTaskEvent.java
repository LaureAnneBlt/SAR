package TaskEvent;

import Abstract.AbstractEventMessageQueue.Listener;
import Events.EventMessageQueue;
import Events.EventTask;
import Events.Message;
import Implem.DisconnectedException;

public class SendTaskEvent extends EventTask {
	
	EventMessageQueue messageQueue;
	Message message;
	Listener listener;
	
	public SendTaskEvent(EventMessageQueue messageQueue, Message message, Listener listener) {
		this.messageQueue = messageQueue;
		this.message = message;
		this.listener = listener;
	}

	@Override
	public void run() {
        try {
			messageQueue._send(message, listener);
		} catch (DisconnectedException e) {
			e.printStackTrace();
		}
        this.kill();
    }
}
