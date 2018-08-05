package com.hami.biz.views;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hami.sys.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * <li>Program Name : BizMappingJacksonJsonView
 * <li>Description  :
 * <li>History      : 2018. 2. 6.
 * </pre>
 *
 * @author HHG
 */
public class BizMappingJacksonJsonView extends MappingJackson2JsonView {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    private ObjectMapper objectMapper;
    private JsonEncoding encoding;

    public BizMappingJacksonJsonView() {
        objectMapper = new ObjectMapper();
        encoding = JsonEncoding.UTF8;
    }

    protected void writeContent(OutputStream stream, Object value, String jsonPrefix) throws IOException {
        JsonGenerator generator = objectMapper.getFactory().createGenerator(stream);
        if (jsonPrefix != null) generator.writeRaw(jsonPrefix);
        if (value instanceof Map) makeNull2String((Map) ((Map) value).get("Data"));
        log.info("==================");
        log.info("# OUTPUT ");
        log.info("==================");
        log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString((Map) value));
        objectMapper.writeValue(generator, value);
    }

    private void makeNull2String(Map data) {
        for (Iterator iterator = data.entrySet().iterator(); iterator.hasNext();) {
            java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
            if (entry.getValue() instanceof List) {
                List list = (List) entry.getValue();
                for (int i = 0; list.size() > i; i++)
                    makeNull2String((Map) list.get(i));

            } else if (entry.getValue() instanceof String)
                entry.setValue(StringUtils.nvl((String) entry.getValue(), ""));
            else if (entry.getValue() == null)
                entry.setValue(StringUtils.nvl((String) entry.getValue(), ""));
        }

    }
}
