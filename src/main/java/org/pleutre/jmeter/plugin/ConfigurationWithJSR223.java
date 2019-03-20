package org.pleutre.jmeter.plugin;

import org.apache.jmeter.config.gui.AbstractConfigGui;
import org.apache.jmeter.testelement.TestElement;

import javax.swing.*;
import java.awt.*;

public class ConfigurationWithJSR223 extends AbstractConfigGui {


    private JTextArea script;

    public ConfigurationWithJSR223() {
        super();
        init();
        initFields();
    }

    @Override
    public String getStaticLabel() {
        return "Configuration with JSR223";
    }

    @Override
    public String getLabelResource() {
        return getClass().getCanonicalName();
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);
        if (element instanceof ConfigurationJSR223TestElement) {
            ConfigurationJSR223TestElement varsCsv = (ConfigurationJSR223TestElement)element;
            script.setText(varsCsv.getScript());
        }
    }

    @Override
    public TestElement createTestElement() {
        ConfigurationJSR223TestElement varsCsv = new ConfigurationJSR223TestElement();
        modifyTestElement(varsCsv);
        return varsCsv;
    }

    @Override
    public void modifyTestElement(TestElement te) {
        configureTestElement(te);
        if (te instanceof ConfigurationJSR223TestElement) {
            ConfigurationJSR223TestElement varsCsv = (ConfigurationJSR223TestElement) te;
            varsCsv.setScript(script.getText());
        }
    }

    @Override
    public void clearGui() {
        super.clearGui();
        initFields();
    }

    private void init() {
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.anchor = GridBagConstraints.FIRST_LINE_START;

        GridBagConstraints editConstraints = new GridBagConstraints();
        editConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        editConstraints.weightx = 1.0;
        editConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("Groovy script. Available variables : ctx vars props log");
        addToPanel(mainPanel, labelConstraints, 1, 1, label);

        script = new JTextArea();
        addToPanel(mainPanel, editConstraints, 1, 5, getTextAreaScrollPaneContainer(script, 10));

        JPanel container = new JPanel(new BorderLayout());
        container.add(mainPanel, BorderLayout.NORTH);
        add(container, BorderLayout.CENTER);
    }

    private void addToPanel(JPanel panel, GridBagConstraints constraints, int col, int row, JComponent component) {
        constraints.gridx = col;
        constraints.gridy = row;
        panel.add(component, constraints);
    }

    private void initFields() {
        script.setText("");
    }

    private JScrollPane getTextAreaScrollPaneContainer(JTextArea textArea, int nbLines) {
        JScrollPane ret = new JScrollPane();
        textArea.setRows(nbLines);
        textArea.setColumns(20);
        ret.setViewportView(textArea);
        return ret;
    }

}
