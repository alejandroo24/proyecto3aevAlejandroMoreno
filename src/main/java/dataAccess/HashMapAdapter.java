package dataAccess;

import model.Producto;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashMapAdapter extends XmlAdapter<HashMapAdapter.AdaptedMap, HashMap<Producto, Integer>> {

    public static class AdaptedMap {
        public List<Entry> entry = new ArrayList<>();
    }

    public static class Entry {
        public Producto key;
        public Integer value;
    }

    @Override
    public HashMap<Producto, Integer> unmarshal(AdaptedMap adaptedMap) {
        HashMap<Producto, Integer> map = new HashMap<>();
        for (Entry entry : adaptedMap.entry) {
            map.put(entry.key, entry.value);
        }
        return map;
    }

    @Override
    public AdaptedMap marshal(HashMap<Producto, Integer> map) {
        AdaptedMap adaptedMap = new AdaptedMap();
        for (Map.Entry<Producto, Integer> entry : map.entrySet()) {
            Entry adaptedEntry = new Entry();
            adaptedEntry.key = entry.getKey();
            adaptedEntry.value = entry.getValue();
            adaptedMap.entry.add(adaptedEntry);
        }
        return adaptedMap;
    }
}
