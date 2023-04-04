package com.athletic.api.util.code;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked")
public class CodeFinder {

    private static final String KEY_ID = "id";
    private static final String KEY_DISPLAY_NAME = "displayName";
    private static final String KEY_DETAIL_LIST = "detailList";

    public static JSONArray findAll() {
        JSONArray jsonArray = new JSONArray();
        Arrays.stream(CodeGroup.values())
                .forEach(group -> jsonArray.add(getGroup(group)));
        return jsonArray;
    }

    public static JSONArray findAllByExcludeGroupId(String groupId) {
        JSONArray jsonArray = new JSONArray();
        Arrays.stream(CodeGroup.values())
                .filter(group -> !StringUtils.equals(group.getId(), groupId))
                .forEach(group -> jsonArray.add(getGroup(group)));
        return jsonArray;
    }

    public static JSONArray findAllByExcludeGroupIds(List<String> groupIds) {
        JSONArray jsonArray = new JSONArray();
        Arrays.stream(CodeGroup.values())
                .filter(group -> groupIds.stream().noneMatch(id -> StringUtils.equals(id, group.getId())))
                .forEach(group -> jsonArray.add(getGroup(group)));
        return jsonArray;
    }

    public static JSONArray findByGroupId(String groupId) {
        JSONArray jsonArray = new JSONArray();
        Arrays.stream(CodeGroup.values())
                .filter(group -> StringUtils.equals(group.getId(), groupId))
                .forEach(group -> jsonArray.add(getGroup(group)));
        return jsonArray;
    }

    public static JSONArray findByGroupIds(List<String> groupIds) {
        JSONArray jsonArray = new JSONArray();
        Arrays.stream(CodeGroup.values())
                .filter(group -> groupIds.stream().anyMatch(id -> StringUtils.equals(id, group.getId())))
                .forEach(group -> jsonArray.add(getGroup(group)));
        return jsonArray;
    }


    private static JSONObject getGroup(CodeGroup group) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_ID, group.getId());
        jsonObject.put(KEY_DISPLAY_NAME, group.getDisplayName());
        if (!group.getDetailList().isEmpty()) {
            jsonObject.put(KEY_DETAIL_LIST, getDetailList(group));
        }
        return jsonObject;
    }

    private static JSONArray getDetailList(CodeGroup group) {
        JSONArray detailArray = new JSONArray();
        group.getDetailList().forEach(detail -> {
            JSONObject detailObject = new JSONObject();
            detailObject.put(KEY_ID, detail.getId());
            detailObject.put(KEY_DISPLAY_NAME, detail.getDisplayName());
            detailArray.add(detailObject);
        });
        return detailArray;
    }
}
