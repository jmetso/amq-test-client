# amq-test-client
AMQ client for testing various things

TBD

## List of environment variables

|Variable name|Description|Default|
|-------------|-----------|-------|
|AMQ_USERNAME|user name for AMQ|username|
|AMQ_PASSWORD|password for AMQ|password|
|AMQ_URL|connection url for AMQ|tcp://localhost:61616|
|READ_QUEUE|queue to read messages from|read|
|DEFAULT_WRITE_QUEUE|default queue to write messages to|test|
|DISABLE_READS|disable reading messages from READ_QUEUE|true|
|TIME_TO_LIVE|time to live for messages|100 seconds|
|CONNECTION_ATTEMPTS|how many attempts to connect to AMQ are done before failing|1|
