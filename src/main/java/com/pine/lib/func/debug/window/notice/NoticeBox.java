package com.pine.lib.func.debug.window.notice;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.text.ClipboardManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

import com.pine.lib.R;
import com.pine.lib.base.activity.A;
import com.pine.lib.func.broadcast.BroadCastManager;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.window.key.DebugInfoBox;
import com.pine.lib.func.debug.window.report.ErrorListBox;
import com.pine.lib.func.file.DataCleanManager;
import com.pine.lib.windows.alter.MessageBox;
import com.pine.lib.windows.alter.OnMessageClickListener;

/**
 * 消息框
 * 
 * <pre>
 * 
 * </pre>
 */
public class NoticeBox implements OnItemClickListener
{
	private static List<DebugInfoBean> infoBeans = new ArrayList<DebugInfoBean>();
	private static G g = new G(NoticeBox.class);
	private static Boolean isShowing = false;
	private Context context;
	private GridView gridView;
	private DebugDialog dialog;
	private Button retBtn;
	private Button copyCrash, goSetting, doDebug;



	public static List<DebugInfoBean> getInfoBeans()
	{
		return infoBeans;
	}


	public static void addFuncBtn(String showMsg)
	{
		addFuncBtn(showMsg, showMsg);
	}


	public static void addFuncBtn(String showMsg, String broadcastInfo)
	{
		DebugInfoBean debugInfoBean = new DebugInfoBean();
		debugInfoBean.setBroadcastInfo(broadcastInfo);
		debugInfoBean.setShowMsg(showMsg);
		addFuncBtn(debugInfoBean);
	}


	public static void addFuncBtn(DebugInfoBean debugInfoBean)
	{
		for (DebugInfoBean debug : infoBeans)
		{
			if (debug.getShowMsg().equals(debugInfoBean.getShowMsg()))
			{
				if (debug.getBroadcastInfo().equals(
						debugInfoBean.getBroadcastInfo()))
				{
					g.e("重复添加");
					return;
				}
			}
		}
		infoBeans.add(debugInfoBean);
	}


	private NoticeBox(Context context)
	{
		this.context = context;
	}


	/**
	 * 初始化
	 * 
	 * <pre>
	 * 必须将基础activity继承QFActivity/QFFragmentActivity
	 * 才能用这个初始化 
	 * 否则 请传入上下文
	 * </pre>
	 * 
	 * @return
	 */
	public static NoticeBox i()
	{
		return new NoticeBox(A.a());
	}


	public static NoticeBox i(Context context)
	{
		return new NoticeBox(context);
	}


	// 清理前端窗口
	public void dismiss()
	{
		isShowing = false;

		if (dialog != null)
		{
			dialog.cancel();
			dialog = null;
		}
	}


	/**
	 * 通过这里显示提示框
	 * 
	 * @param message
	 * @param buttons
	 */
	public void show()
	{

		if (context == null)
		{
			g.e("消息框无法显示 - 上下文失效");
			return;
		}

		if (!isShowing)
		{
			dialog = new DebugDialog(context, R.style.dialog);

			dialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
			dialog.show();
			dialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface arg0)
				{
					isShowing = false;

				}
			});
			isShowing = true;

			DebugDialog view = dialog.getView();

			retBtn = (Button) view.findViewById(R.id.debug_runtime_ret_btn);
			goSetting = (Button) view
					.findViewById(R.id.debug_runtime_setting_btn);
			copyCrash = (Button) view.findViewById(R.id.debug_runtime_copy_btn);
			doDebug = (Button) view.findViewById(R.id.debug_runtime_debug_btn);
			gridView = (GridView) view
					.findViewById(R.id.debug_runtime_func_list);
			gridView.setAdapter(new Adapter());
			gridView.setOnItemClickListener(this);

			doDebug.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v)
				{
					DebugInfoBox.i().show();
					dismiss();
				}
			});

			retBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0)
				{
					dismiss();

				}
			});

			goSetting.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0)
				{
					MessageBox.i().setListener(new OnMessageClickListener() {

						@Override
						public void messageBoxChoose(int id)
						{
							if (id == 1)
							{
								DataCleanManager.cleanInternalCache(A.c());
								DataCleanManager.cleanDatabases(A.c());
								DataCleanManager.cleanSharedPreference(A.c());
								DataCleanManager.cleanFiles(A.c());
								DataCleanManager.cleanExternalCache(A.c());

								System.exit(0);
							}
							else if (id == 2)
							{

								Intent intent = new Intent();
								intent.setAction(Intent.ACTION_DELETE);
								intent.setData(Uri
										.parse("package:" + A.c().getPackageName()));
								A.a().startActivity(intent);

							}

						}
					}).show("应用将退出！", "清除数据", "卸载应用", "取消");

					// Uri packageURI = Uri.parse("package:"
					// + A.c().getPackageName());
					// Intent uninstallIntent = new Intent(Intent.ACTION_DELETE,
					// packageURI);
					// uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					// A.c().startActivity(uninstallIntent);

					dismiss();
				}
			});

			copyCrash.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0)
				{
					ErrorListBox.i().show();

					dismiss();

				}
			});

		}

	}


	public static Boolean getIsShowing()
	{
		return isShowing;
	}


	public static void setIsShowing(Boolean isShowing)
	{
		NoticeBox.isShowing = isShowing;
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	{
		if (arg2 < infoBeans.size())
		{
			// 将显示内容复制
			ClipboardManager clipboardManager = (ClipboardManager) A.c()
					.getSystemService(Context.CLIPBOARD_SERVICE);
			clipboardManager.setText(infoBeans.get(arg2).getBroadcastInfo());

			BroadCastManager.i().send(infoBeans.get(arg2).getBroadcastInfo());
		}
		dismiss();

	}

}
