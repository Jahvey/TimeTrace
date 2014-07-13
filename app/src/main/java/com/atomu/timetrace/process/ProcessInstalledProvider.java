package com.atomu.timetrace.process;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.atomu.timetrace.app.R;
import com.atomu.timetrace.database.TableInstalledHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Atomu on 2014/7/12.
 * get installed processes
 */
public class ProcessInstalledProvider {
    private Context context;

    public ProcessInstalledProvider(Context context) {
        this.context = context;
    }

    public boolean isUserApp(PackageInfo pInfo) {
        if (pInfo.applicationInfo != null && (pInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
        } else if (pInfo.applicationInfo != null && (pInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }

    private String getAppName(ApplicationInfo applicationInfo, PackageManager pm) {
        CharSequence cs = applicationInfo.loadLabel(pm);

        if (cs != null)
            return cs.toString();
        return null;
    }

    public List<ProcessInfo> getInstalled() {
        List<ProcessInfo> installed = new ArrayList<ProcessInfo>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packs = null;
        if (pm != null) {
            packs = pm.getInstalledPackages(0);
        }
        if (packs != null) {
            TableInstalledHelper installedHelper = new TableInstalledHelper(context, context.getString(R.string.database_installed));
            SQLiteDatabase rdb = installedHelper.getReadableDatabase();
            Cursor cursor;
            String[] columns = new String[]{context.getString(R.string.process_info_pack_name), context.getString(R.string.process_info_tag)};
            String selection = context.getString(R.string.process_info_pack_name) + " = ? ";

            for (PackageInfo pi : packs) {
                ApplicationInfo applicationInfo = pi.applicationInfo;
                if (applicationInfo != null) {
                    ProcessInfo info = new ProcessInfo();
                    int tag = 0;
                    boolean isUserApp = isUserApp(pi);

                    if (rdb != null) {
                        String[] selectionArgs = new String[]{pi.packageName};

                        cursor = rdb.query(context.getString(R.string.table_installed), columns, selection, selectionArgs, null, null, null);
                        if (cursor.moveToNext()) {
                            tag = cursor.getInt(cursor.getColumnIndex(context.getString(R.string.process_info_tag)));
                        }
                        cursor.close();
                    }

                    if (tag != context.getResources().getInteger(R.integer.process_key_ignore)){

                        info.setPackName(pi.packageName);
                        info.setAppName(getAppName(applicationInfo, pm));
                        info.setIcon(applicationInfo.loadIcon(pm));
                        info.setUserProcess(isUserApp);
                        if (tag == 0 && ! isUserApp) {
                            info.setTag(context.getResources().getInteger(R.integer.process_key_system));
                        } else {
                            info.setTag(tag);
                        }

                        installed.add(info);
                    }
                }

            }
            if (rdb != null) {
                rdb.close();
            }
        }
        return installed;
    }

}
