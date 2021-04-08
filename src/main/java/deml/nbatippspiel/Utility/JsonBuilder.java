package deml.nbatippspiel.Utility;

import java.util.List;

public class JsonBuilder {

    private StringBuilder jsonToBuild = new StringBuilder();

    public JsonBuilder() {
        jsonToBuild.append("{\n");
    }

    public JsonBuilder addJsonEntry(final JsonEntry jsonEntry) {
        jsonToBuild.append("'");
        jsonToBuild.append(jsonEntry.key);
        jsonToBuild.append("':'");
        jsonToBuild.append(jsonEntry.value);
        jsonToBuild.append("'\n");
        jsonToBuild.append(",\n");
        return this;
    }

    public JsonBuilder addJsonEntry(final JsonEntry jsonEntry, final boolean isEnd) {
        jsonToBuild.append("'");
        jsonToBuild.append(jsonEntry.key);
        jsonToBuild.append("':'");
        jsonToBuild.append(jsonEntry.value);
        jsonToBuild.append("'\n");
        if (isEnd) {
            jsonToBuild.append("}");
        } else {
            jsonToBuild.append(",\n");
        }
        return this;
    }

    public JsonBuilder addList(final List<?> list) {
        list.forEach(e -> {
            final boolean last = (long) list.size() == list.indexOf(e) + 1;
            addJsonEntry(new JsonEntry<>(String.valueOf(list.indexOf(e)), e), last);
        });
        return this;
    }

    public String build() {
        return jsonToBuild.toString();
    }

    public static class JsonEntry<V> {

        private final String key;
        private final V value;

        public JsonEntry(final String key, final V value) {
            this.key = key;
            this.value = value;
        }
    }
}
