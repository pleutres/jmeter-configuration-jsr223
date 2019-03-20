package org.pleutre.jmeter.plugin;


import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.engine.PreCompiler;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;
import java.util.Properties;

public class ConfigurationJSR223TestElement extends Arguments {

    private static final Logger log = LoggerFactory.getLogger(PreCompiler.class);

    public static final String DEFAULT_SCRIPT_LANGUAGE = "groovy";

    public static final String SCRIPT = "script";

    public String getScript() {
        return getPropertyAsString(SCRIPT);
    }

    public void setScript(String prefix) {
        setProperty(SCRIPT, prefix);
    }

    @Override
    public Map<String, String> getArgumentsAsMap() {

        // Call Script

        try {

            ScriptEngine scriptEngine = getScriptEngine();
            Bindings bindings = scriptEngine.createBindings();
            JMeterContext jmctx = JMeterContextService.getContext();
            bindings.put("ctx", jmctx);
            JMeterVariables vars = jmctx.getVariables();
            bindings.put("vars", vars);
            Properties props = JMeterUtils.getJMeterProperties();
            bindings.put("props", props);
            Logger logger = LoggerFactory.getLogger(this.getClass());
            bindings.put("log", logger);


            getScriptEngine().eval(getScript(), bindings);
        } catch (ScriptException e) {
            log.error("Error while running script", e);
        }


        return super.getArgumentsAsMap();
    }


    private static class LazyHolder {
        public static final ScriptEngineManager INSTANCE = new ScriptEngineManager();

        private LazyHolder() {
        }// 78
    }

    protected ScriptEngine getScriptEngine() throws ScriptException {
        ScriptEngine scriptEngine = LazyHolder.INSTANCE.getEngineByName(DEFAULT_SCRIPT_LANGUAGE);// 99
        if (scriptEngine == null) {// 100
            throw new ScriptException("Cannot find engine named: '" + DEFAULT_SCRIPT_LANGUAGE + "', ensure you set language field in JSR223 Test Element: " + this.getName());// 101
        } else {
            return scriptEngine;// 104
        }
    }

}
