## Environment Variables

Tasker uses Spring profiles to determine which endpoints to hit.


Set the `SPRING_PROFILE_ACTIVE` environment variable to one of the following. 

| Setting | Description |
| --- | --- |
| offline | Runs tasker using public endpoints that are accessible when not in the UPS network, this is the Default |
| development | Runs tasker using UPS endpoints |
| openshift | Runs tasker using UPS endpoints in Openshift |


## Running Tasker 

You can run Tasker by invoking the following in a terminal or command prompt.
```bash
mvn package && java -jar target/tasker-*.jar
```

## Yaml Configuration

The application.yml is the main configuration file. 

Modify the configuration.data entries as necessary, using the following as a guide.

```yaml
configuration:
  data:
    - name: <name this anything you want, but follow a naming convention.  I like to describe the CRON>
      cron: <similar to a crontab, but uses "+" (plus) signs instead of spaces as required by Camel>
      endpoints:
        earmo: <http[s] url to hit>
        lchky: <add any additional urls to this list>
    - name:
      cron:
      endpoints:
        endpoint1:
        endpoint2:
        endpoint3:
      headers:
        header1 : <any custom headers you want to add to the request, such as GET or POST>
        header2 : ...
    ...
```