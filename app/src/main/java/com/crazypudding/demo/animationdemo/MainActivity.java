package com.crazypudding.demo.animationdemo;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参考官方 ApiDemos 实现一个 Activity 展示多级列表
 * 通过 Intent.Category 在 Manifest 文件中设置目标 Activity，在 MainActivity 中搜寻所有符合项并借助 Activity 的 label 进行分类
 */
public class MainActivity extends ListActivity {
    private static final String TAG_PATH = "com.crazypudding.demo.animationdemo.path";
    private static final String CATEGORY_SAMPLE_CODE = "com.crazypudding.demo.animationdemo.SAMPLE_CODE";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 由一级列表点击接收 Intent，与 onListItemClick 方法对应
        // 取到子列表的 path
        Intent intent = getIntent();
        String path = intent.getStringExtra(TAG_PATH);

        // path == null 表示是一级列表
        if (path == null) {
            path = "";
        }

        setListAdapter(new SimpleAdapter(this, getData(path), android.R.layout.simple_list_item_1, new String[]{"title"}, new int[]{android.R.id.text1}));
        getListView().setTextFilterEnabled(true);
    }

    protected List<Map<String, Object>> getData(String prefix) {
        List<Map<String, Object>> myData = new ArrayList<>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(CATEGORY_SAMPLE_CODE);

        // 通过自定义 category 查询所有符合的 Activity
        PackageManager pm = getPackageManager();
        List<ResolveInfo> infoList = pm.queryIntentActivities(mainIntent, 0);

        if (null == infoList) {
            return myData;
        }

        String[] prefixPath;
        String prefixWithSlash = prefix;

        if (prefix.equals("")) {
            prefixPath = null;
        } else {
            prefixPath = prefix.split("/");
            prefixWithSlash = prefix + "/";
        }

        Map<String, Boolean> entries = new HashMap<>();

        for (int i = 0; i < infoList.size(); i++) {
            // 获取单个 Activity 的 label
            ResolveInfo info = infoList.get(i);
            CharSequence labelSeq = info.loadLabel(pm);
            String label = labelSeq != null ? labelSeq.toString() : info.activityInfo.name;

            if (prefixWithSlash.length() == 0 || label.startsWith(prefixWithSlash)) {
                String[] labelPath = label.split("/");
                String nextLabel = prefixPath == null ? labelPath[0] : labelPath[prefixPath.length];

                if ((prefixPath != null ? prefixPath.length : 0) == labelPath.length - 1) {
                    addItem(myData, nextLabel, activityIntent(info.activityInfo.applicationInfo.packageName, info.activityInfo.name));
                } else {
                    if (entries.get(nextLabel) == null) {
                        addItem(myData, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "/" + nextLabel));
                        entries.put(nextLabel, true);
                    }
                }
            }
        }

        Collections.sort(myData, sDisplayNameComparator);

        return myData;
    }

    private final static Comparator<Map<String, Object>> sDisplayNameComparator =
            new Comparator<Map<String, Object>>() {
                private final Collator collator = Collator.getInstance();

                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    return collator.compare(o1.get("title"), o2.get("title"));
                }
            };

    protected Intent activityIntent(String pkgName, String componentName) {
        Intent result = new Intent();
        result.setClassName(pkgName, componentName);
        return result;
    }

    protected Intent browseIntent(String path) {
        Intent result = new Intent();
        result.setClass(this, MainActivity.class);
        result.putExtra(TAG_PATH, path);
        return result;
    }

    protected void addItem(List<Map<String, Object>> data, String name, Intent intent) {
        Map<String, Object> temp = new HashMap<>();
        temp.put("title", name);
        temp.put("intent", intent);
        data.add(temp);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Map<String, Object> map = (Map<String, Object>) l.getItemAtPosition(position);

        Intent intent = new Intent((Intent) map.get("intent"));
        intent.addCategory(CATEGORY_SAMPLE_CODE);
        startActivity(intent);
    }
}
