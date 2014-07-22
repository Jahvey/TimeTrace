package com.atomu.timetrace.process;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.atomu.timetrace.app.R;
import com.atomu.timetrace.database.TableInstalledHelper;
import com.atomu.timetrace.effect.ListScrollTitleListener;

import java.util.List;

/**
 * Created by Atomu on 2014/7/10.
 * test class: process tag module
 */
public class ProcessTagActivity extends Activity {

    private List<ProcessInfo> processInstalled;

    private void checkFirstTime() {
        SharedPreferences spf = ProcessTagActivity.this.getSharedPreferences("preference", Context.MODE_PRIVATE);
        if (spf.getBoolean("isFirstTime", true)) {
            SharedPreferences.Editor editor = spf.edit();
            editor.putBoolean("isFirstTime", false);
            editor.commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_tag);

        ListView lv_process_tag = (ListView) findViewById(R.id.lv_process_tag);
        lv_process_tag.setOnScrollListener(new ListScrollTitleListener(findViewById(R.id.tv_process_tag_title)));
        ImageButton ib_process_tag_ok = (ImageButton) findViewById(R.id.ib_process_tag_ok);
        ImageButton ib_process_tag_cancel = (ImageButton) findViewById(R.id.ib_process_tag_cancel);

        processInstalled = new ProcessInstalledProvider(ProcessTagActivity.this).getInstalled();
        ProcessTagItemAdapter adapter = new ProcessTagItemAdapter();
        lv_process_tag.setAdapter(adapter);

        ib_process_tag_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkTableInstalled(true);
                checkFirstTime();
                finish();
            }
        });
        ib_process_tag_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkTableInstalled(false);
                finish();
            }
        });
    }

    private ContentValues getContentValuesFromInfo(ProcessInfo info) {
        ContentValues values = new ContentValues();

        values.put(getString(R.string.process_info_app_name), info.getAppName());
        values.put(getString(R.string.process_info_pack_name), info.getPackName());
        values.put(getString(R.string.process_info_tag), info.getTag());
        values.put(getString(R.string.process_info_is_user_process), info.isUserProcess());
        values.put(getString(R.string.process_info_active_time), 0);
        values.put(getString(R.string.process_info_live_time), 0);
        values.put(getString(R.string.process_info_start_time), 0);

        return values;
    }

    private void checkTableInstalled(boolean saveChange) {
        TableInstalledHelper tableInstalledHelper = new TableInstalledHelper(ProcessTagActivity.this, getString(R.string.database_installed));
        SQLiteDatabase rdb = tableInstalledHelper.getReadableDatabase();
        SQLiteDatabase wdb = tableInstalledHelper.getWritableDatabase();

        if (rdb != null) {
            Cursor cursor = rdb.query(getString(R.string.table_installed), null, null, null, null, null, null);

            if (cursor.getCount() <= 0) {
                cursor.close();
                if (wdb != null) {
                    for (ProcessInfo info : processInstalled) {
                        wdb.insert(getString(R.string.table_installed), null, getContentValuesFromInfo(info));
                    }
                    wdb.close();
                }
            } else if (saveChange) {
                cursor.close();
                if (wdb != null) {
                    String[] columns = new String[]{ProcessTagActivity.this.getString(R.string.process_info_pack_name)};
                    String selection = ProcessTagActivity.this.getString(R.string.process_info_pack_name) + " = ? ";
                    String whereClause = ProcessTagActivity.this.getString(R.string.process_info_pack_name) + " = ? ";

                    for (ProcessInfo info : processInstalled) {
                        String[] selectionArgs = new String[]{info.getPackName()};
                        cursor = rdb.query(ProcessTagActivity.this.getString(R.string.table_installed), columns, selection, selectionArgs, null, null, null);

                        if (cursor.getCount() <= 0) {
                            wdb.insert(getString(R.string.table_installed), null, getContentValuesFromInfo(info));
                        } else {
                            ContentValues values = new ContentValues();
                            values.put(ProcessTagActivity.this.getString(R.string.process_info_tag), info.getTag());

                            String[] whereArgs = new String[]{info.getPackName()};
                            wdb.update(ProcessTagActivity.this.getString(R.string.table_installed), values, whereClause, whereArgs);
                        }
                        cursor.close();
                    }
                }
            }
            wdb.close();
            rdb.close();
        }

    }

    static class ProcessTagItemViewHolder {
        ImageView icon;
        TextView appName;
        TextView isUserApp;
        TextView tag;
        Button ignore;
        int position;
    }

    private class ProcessTagItemAdapter extends BaseAdapter {

        private ProcessTagItemViewHolder holder = null;
        private View view;

        @Override
        public int getCount() {
            return processInstalled.size();
        }

        @Override
        public Object getItem(int i) {
            return processInstalled.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            initViewHolder(view);

            ProcessInfo info = processInstalled.get(i);

            this.holder.icon.setImageDrawable(info.getIcon());
            this.holder.appName.setText(info.getAppName());
            this.holder.tag.setText(info.getTagString(ProcessTagActivity.this));
            this.holder.isUserApp.setText((info.isUserProcess()) ? "usr" : "sys");
            this.holder.ignore.setTag(i);
            this.holder.position = i;

            this.view.setTag(this.holder);
//            this.view.setOnTouchListener(new ItemSlideBarListener(this.view.findViewById(R.id.btn_process_tag_ignore)));
//            if (((ProcessInfo) getItem(Integer.valueOf(this.holder.ignore.getTag().toString()))).getTag() == ProcessTagActivity.this.getResources().getInteger(R.integer.process_key_ignore)){
//                this.holder.ignore.setVisibility(View.GONE);
//            }
            this.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final Spinner sp_ask_process = new Spinner(ProcessTagActivity.this);
                    String[] items = getResources().getStringArray(R.array.process_tag_array);
                    ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(ProcessTagActivity.this, android.R.layout.simple_spinner_dropdown_item, items);
                    sp_ask_process.setAdapter(arrAdapter);
                    sp_ask_process.setPrompt(ProcessTagActivity.this.getString(R.string.process_tag_unknown));

                    new AlertDialog.Builder(ProcessTagActivity.this)
                            .setTitle("set tag").setIcon(R.drawable.engine)
                            .setView(sp_ask_process)
                            .setPositiveButton("set", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    TextView tv_selected = (TextView) sp_ask_process.getSelectedView();
                                    if (tv_selected != null && tv_selected.getText() != null) {
                                        String tag = tv_selected.getText().toString();
                                        int index = ((ProcessTagItemViewHolder) view.getTag()).position;

                                        ((ProcessInfo) getItem(index)).setTag(ProcessTagActivity.this, tag);
                                        ProcessTagItemAdapter.this.notifyDataSetChanged();
                                    }
                                }
                            })
                            .setNegativeButton("cancel", null)
                            .show();
                }
            });

            this.holder.ignore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = Integer.valueOf(view.getTag().toString());
                    ((ProcessInfo) getItem(index)).setTag(ProcessTagActivity.this.getResources().getInteger(R.integer.process_key_ignore));
                    ProcessTagItemAdapter.this.notifyDataSetChanged();
                }
            });

            return this.view;
        }

        private void initViewHolder(View convertView) {
            if (convertView == null) {
                view = View.inflate(ProcessTagActivity.this, R.layout.process_tag_item, null);
                holder = new ProcessTagItemViewHolder();

                holder.icon = (ImageView) view.findViewById(R.id.iv_tag_item_icon);
                holder.appName = (TextView) view.findViewById(R.id.tv_process_tag_app_name);
                holder.isUserApp = (TextView) view.findViewById(R.id.tv_process_tag_user_app);
                holder.tag = (TextView) view.findViewById(R.id.tv_process_tag_tag);
                holder.ignore = (Button) view.findViewById(R.id.btn_process_tag_ignore);
            } else {
                view = convertView;
                holder = (ProcessTagItemViewHolder) view.getTag();
            }
        }

    }
}
