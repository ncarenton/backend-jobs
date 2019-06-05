package com.github.ncarenton.pdg.testutils;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import java.util.ArrayList;
import java.util.List;

@Plugin(
        name = "TestAppender",
        category = Core.CATEGORY_NAME,
        elementType = Appender.ELEMENT_TYPE)
public class TestAppender extends AbstractAppender {

    public static List<Record> messages = new ArrayList<>();

    protected TestAppender(String name, Filter filter) {
        super(name, filter, null, true, Property.EMPTY_ARRAY);
    }

    @PluginFactory
    public static TestAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Filter") Filter filter) {
        return new TestAppender(name, filter);
    }

    public static void clear() {
        messages.clear();
    }

    @Override
    public void append(LogEvent event) {
        messages.add(new Record(event.getLevel().name(), event.getMessage().getFormattedMessage()));
    }

    static class Record {

        private final String level;
        private final String message;

        Record(String level, String message) {
            this.level = level;
            this.message = message;
        }
    }
}
