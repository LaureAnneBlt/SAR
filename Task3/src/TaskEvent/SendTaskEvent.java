package TaskEvent;

import Events.EventMessageQueue;
import Events.EventTask;
import Events.Message;
import Implem.DisconnectedException;

public class SendTaskEvent extends EventTask {
	
	EventMessageQueue messageQueue;
	Message message;
	
	public SendTaskEvent(EventMessageQueue messageQueue, Message message) {
		this.messageQueue = messageQueue;
		this.message = message;
	}

	@Override
	public void run() {
        try {
            if (messageQueue.send(message)) {
                this.kill();
            }
        } catch (DisconnectedException e) {
            e.printStackTrace();
        }
        
    }
}
