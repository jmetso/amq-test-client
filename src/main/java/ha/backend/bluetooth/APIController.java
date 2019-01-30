package ha.backend.bluetooth;

import ha.backend.bluetooth.messaging.ActiveMQClient;
import ha.backend.bluetooth.messaging.MessagingClient;
import ha.backend.bluetooth.messaging.QueueClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {

    private QueueClient client;

    public APIController() {}

    @RequestMapping(value = "/amq/api/v1/send/{queue}/{payload}", produces= MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.GET)
    public ResponseEntity<Boolean> sendMessage(@PathVariable(name="payload") String payload, @PathVariable(name="queue") String queue) {
        this.client.sendMessage(queue, payload);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @RequestMapping(value = "/amq/api/v1/send/{payload}", produces= MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.GET)
    public ResponseEntity<Boolean> sendMessage(@PathVariable(name="payload") String payload) {
        this.client.sendMessage(payload);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @Autowired
    public void setQueueClient(QueueClient queueClient) {
        this.client = queueClient;
    }

}
