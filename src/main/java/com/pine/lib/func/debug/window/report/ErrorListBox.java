package com.pine.lib.func.debug.window.report;

import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.ClipboardManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.pine.lib.R;
import com.pine.lib.base.activity.A;
import com.pine.lib.func.debug.G;
import com.pine.lib.func.debug.window.CrashBean;
import com.pine.lib.storage.sqlite.SqliteManager;
import com.pine.lib.view.fasttoast.T;
import com.pine.lib.windows.alter.MessageBox;

/**
 * 消息框
 * 
 * <pre>
 * 
 * </pre>
 */
public class ErrorListBox implements OnItemClickListener
{
	private static G g = new G(ErrorListBox.class);
	private static Boolean isShowing = false;

	private TextView errorLog;
	private TextView delBtn;
	private GridView gridView;

	private Context context;
	private ErrorsDialog dialog;
	private Adapter adapter = new Adapter();



	private ErrorListBox(Context context)
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
	public static ErrorListBox i()
	{
		return new ErrorListBox(A.a());
	}


	public static ErrorListBox i(Context context)
	{
		return new ErrorListBox(context);
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
			dialog = new ErrorsDialog(context, R.style.dialog);

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

			ErrorsDialog view = dialog.getView();

			errorLog = (TextView) view.findViewById(R.id.errorLog);
			delBtn = (TextView) view.findViewById(R.id.delBtn);

			delBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v)
				{
					SqliteManager.i().execSQL("delete from `CrashBean`");
					adapter.notifyDataSetChanged();
					T.t("全都被删了");
				}
			});

			gridView = (GridView) view
					.findViewById(R.id.debug_runtime_func_list);
			gridView.setAdapter(adapter);
			gridView.setOnItemClickListener(this);

		}

	}


	public static Boolean getIsShowing()
	{
		return isShowing;
	}


	public static void setIsShowing(Boolean isShowing)
	{
		ErrorListBox.isShowing = isShowing;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id)
	{
		List<CrashBean> errorBeans = adapter.getErrorBeans();

		ClipboardManager clipboardManager = (ClipboardManager) A.c()
				.getSystemService(Context.CLIPBOARD_SERVICE);
		if (errorBeans.size() > position)
		{
			clipboardManager.setText(errorBeans.get(position).getDetail());
			g.e(errorBeans.get(position).getDetail());
			errorLog.setText(errorBeans.get(position).getDetail());
			// MessageBox.i().show("上一次的崩溃日志已经复制到剪贴板且打印到控制台");
		}
		else
		{
			MessageBox.i().show("数据库没有崩溃信息记录");
		}

	}

}
