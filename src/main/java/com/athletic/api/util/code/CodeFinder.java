package com.athletic.api.util.code;

import com.athletic.api.common.dto.ResponseDto;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unchecked")
public class CodeFinder {

    private static final String KEY_CODE = "code";
    private static final String KEY_NAME = "name";
    private static final String KEY_DETAIL_LIST = "detailList";

    public static JSONArray findAll() {
        JSONArray jsonArray = new JSONArray();
        Arrays.stream(CodeGroup.values())
                .forEach(group -> jsonArray.add(getGroup(group)));
        return jsonArray;
    }

    public static JSONArray findAllByExcludeGroupCode(String groupCode) {
        JSONArray jsonArray = new JSONArray();
        Arrays.stream(CodeGroup.values())
                .filter(group -> !StringUtils.equals(group.getCode(), groupCode))
                .forEach(group -> jsonArray.add(getGroup(group)));
        return jsonArray;
    }

    public static JSONArray findAllByExcludeGroupCodes(List<String> groupCodes) {
        JSONArray jsonArray = new JSONArray();
        Arrays.stream(CodeGroup.values())
                .filter(group -> groupCodes.stream().noneMatch(code -> StringUtils.equals(code, group.getCode())))
                .forEach(group -> jsonArray.add(getGroup(group)));
        return jsonArray;
    }

    public static ResponseDto findByGroupCode(String groupCode) {
        JSONArray jsonArray = new JSONArray();
        Arrays.stream(CodeGroup.values())
                .filter(group -> StringUtils.equals(group.getCode(), groupCode))
                .forEach(group -> jsonArray.add(getGroup(group)));

        return ResponseDto.success(jsonArray);
    }

    public static ResponseDto findByGroupCodes(List<String> groupCodes) {
        JSONArray jsonArray = new JSONArray();
        Arrays.stream(CodeGroup.values())
                .filter(group -> groupCodes.stream().anyMatch(code -> StringUtils.equals(code, group.getCode())))
                .forEach(group -> jsonArray.add(getGroup(group)));

        return ResponseDto.success(jsonArray);
    }


    private static JSONObject getGroup(CodeGroup group) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_CODE, group.getCode());
        jsonObject.put(KEY_NAME, group.getName());
        if (!group.getDetailList().isEmpty()) {
            jsonObject.put(KEY_DETAIL_LIST, getDetailList(group));
        }
        return jsonObject;
    }

    private static JSONArray getDetailList(CodeGroup group) {
        JSONArray detailArray = new JSONArray();
        group.getDetailList().forEach(detail -> {
            JSONObject detailObject = new JSONObject();
            detailObject.put(KEY_CODE, detail.getCode());
            detailObject.put(KEY_NAME, detail.getName());
            detailArray.add(detailObject);
        });
        return detailArray;
    }

    public static String findCodeByGroupName(String name) {
        return Arrays.stream(CodeGroup.values())
                .filter(group -> StringUtils.equals(group.getName(), name))
                .findAny()
                .map(CodeGroup::getCode)
                .orElse("");
    }

    public static String findCodeByDetailName(String name) {
        return Arrays.stream(CodeDetail.values())
                .filter(detail -> StringUtils.equals(detail.getName(), name))
                .findAny()
                .map(CodeDetail::getCode)
                .orElse("");
    }

    public static String findNameByGroupCode(String code) {
        return Arrays.stream(CodeGroup.values())
                .filter(group -> StringUtils.equals(group.getCode(), code))
                .findAny()
                .map(CodeGroup::getName)
                .orElse("");
    }

    public static String findNameByDetailCode(String code) {
        return Arrays.stream(CodeDetail.values())
                .filter(detail -> StringUtils.equals(detail.getCode(), code))
                .findAny()
                .map(CodeDetail::getName)
                .orElse("");
    }

}
