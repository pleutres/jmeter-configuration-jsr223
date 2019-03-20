# jmeter-configuration-jsr223

A simple configuration to execute JSR 223 on configuration phase.
The only supported language is Groovy.

The plugin extends `org.apache.jmeter.config.Arguments` element and override `getArgumentsAsMap`.
The `getArgumentsAsMap` returns an empty collection, but execute the Groovy script.

### A simple use case :
* Build dynamically the script file base directory
* To allow jp@gc - Variables From CSV File to use variable.

### Example :

Step 1 : Add an configuration : Configuration with JSR223

```
import org.apache.jmeter.services.FileServer; 

String baseDir = FileServer.getFileServer().getBaseDir();

log.info("baseDir = " + baseDir);
vars.put("SCRIPT_BASE_DIR", baseDir);
```

Step 2 : Add an configuration : jp@gc - Variables

Set property using dynamic variable

* CSV file : ${SCRIPT_BASE_DIR}/env_testrules.csv


### Note

This also could be done with arg on command line and $(__P()). See https://jmeter.apache.org/usermanual/functions.html
                                                                   
