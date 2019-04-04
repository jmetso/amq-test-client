package amqclient;

import amqclient.messaging.QueueClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class APIController {

    private QueueClient client;

    public APIController() {}

    @RequestMapping(value = "/amq/api/v1/send/{queue}/{payload}", produces= MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.GET)
    public ResponseEntity<Boolean> sendMessage(@PathVariable(name="payload") String payload, @PathVariable(name="queue") String queue) {
        return new ResponseEntity<>(this.client.sendMessage(queue, payload), HttpStatus.OK);
    }

    @RequestMapping(value = "/amq/api/v1/send/{payload}", produces= MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.GET)
    public ResponseEntity<Boolean> sendMessage(@PathVariable(name="payload") String payload) {
        return new ResponseEntity<>(this.client.sendMessage(payload), HttpStatus.OK);
    }

    @RequestMapping(value = "/amq/api/v1/send", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Boolean> sendMessagePost(@RequestBody String payload) {
        return new ResponseEntity<>(this.client.sendMessage(payload), HttpStatus.OK);
    }

    @RequestMapping(value = "/amq/api/v1/reconnect", produces= MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.GET)
    public ResponseEntity<Boolean> reconnect() {
        return new ResponseEntity<>(this.client.reconnect(), HttpStatus.OK);
    }

    @RequestMapping(value = "/amq/api/v1/disconnect", produces= MediaType.APPLICATION_JSON_VALUE, method= RequestMethod.GET)
    public ResponseEntity<Boolean> disconnect() {
        return new ResponseEntity<>(this.client.disconnect(), HttpStatus.OK);
    }

    @Autowired
    public void setQueueClient(QueueClient queueClient) {
        this.client = queueClient;
    }

}
